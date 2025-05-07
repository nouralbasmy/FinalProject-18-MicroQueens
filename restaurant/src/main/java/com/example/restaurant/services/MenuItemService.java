package com.example.restaurant.services;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository)
    {
        this.menuItemRepository = menuItemRepository;
    }

    //------------FOR SYNC COMMUNICATION WITH ORDER MICROSERVICE----------------
    // (1) inventory checking
    public boolean checkInventory(Long menuItemId, int quantity)
    {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);
        if(optionalMenuItem.isEmpty())
            return false;
        MenuItem menuItem = optionalMenuItem.get();
        return menuItem.getInventory() >= quantity;
    }

    // (2) decrementing inventory on successful order placed
    public boolean decrementInventory(Long menuItemId, int quantity)
    {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);
        if(optionalMenuItem.isEmpty())
            return false; //failed to find item

        MenuItem menuItem = optionalMenuItem.get();
        menuItem.setInventory(menuItem.getInventory() - quantity);
        menuItemRepository.save(menuItem);
        return true;
    }

}
