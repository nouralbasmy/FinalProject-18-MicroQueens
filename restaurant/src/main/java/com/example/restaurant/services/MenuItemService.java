package com.example.restaurant.services;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
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
                    menuItem.setRestaurants(updatedItem.getRestaurants());
                    return menuItemRepository.save(menuItem);
                }).orElseThrow(() -> new RuntimeException("MenuItem not found"));
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
