package com.example.restaurant.services;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.model.Restaurant;
import com.example.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantService restaurantService;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantService restaurantService) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantService = restaurantService;
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    public List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public MenuItem updateMenuItem(Long id, MenuItem updatedItem) {
        return menuItemRepository.findById(id)
                .map(menuItem -> {
                    menuItem.setName(updatedItem.getName());
                    menuItem.setPrice(updatedItem.getPrice());
                    menuItem.setInventory(updatedItem.getInventory());
                    menuItem.setDietaryRestrictions(updatedItem.getDietaryRestrictions());
                    menuItem.setRestaurant(updatedItem.getRestaurant());
                    return menuItemRepository.save(menuItem);
                }).orElseThrow(() -> new RuntimeException("MenuItem not found"));
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    // ------------FOR SYNC COMMUNICATION WITH ORDER MICROSERVICE----------------
    // (1) inventory checking
    public boolean checkInventory(Long menuItemId, int quantity) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);
        if (optionalMenuItem.isEmpty())
            return false;
        MenuItem menuItem = optionalMenuItem.get();
        return menuItem.getInventory() >= quantity;
    }

    // (2) decrementing inventory on successful order placed
    public boolean decrementInventory(Long menuItemId, int quantity) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);
        if (optionalMenuItem.isEmpty())
            return false; // failed to find item

        MenuItem menuItem = optionalMenuItem.get();
        menuItem.setInventory(menuItem.getInventory() - quantity);
        menuItemRepository.save(menuItem);
        return true;
    }

    // (3) Increment inventory on restaurant adding
    public boolean incrementInventory(Long menuItemId, int quantity) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);
        if (optionalMenuItem.isEmpty())
            return false; // failed to find item

        MenuItem menuItem = optionalMenuItem.get();
        menuItem.setInventory(menuItem.getInventory() + quantity);
        menuItemRepository.save(menuItem);
        return true;
    }

    public Optional<Restaurant> getRestaurantById(Long restaurantId){

        return restaurantService.getRestaurantById(restaurantId);

    }

}
