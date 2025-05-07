package com.example.restaurant.controller;

import com.example.restaurant.services.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menuItems")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    //------------FOR SYNC COMMUNICATION WITH ORDER MICROSERVICE----------------
    // (1) inventory checking
    @GetMapping("/checkInventory/{menuItemId}")
    public boolean checkInventory(@PathVariable Long menuItemId, @RequestParam int quantity)
    {
        return menuItemService.checkInventory(menuItemId,quantity);
    }

    // (2) decrementing inventory on successful order placed
    @PutMapping("/decrementInventory/{menuItemId}")
    public boolean decrementInventory(@PathVariable Long menuItemId, @RequestParam int quantity)
    {
        return menuItemService.decrementInventory(menuItemId,quantity);
    }

}
