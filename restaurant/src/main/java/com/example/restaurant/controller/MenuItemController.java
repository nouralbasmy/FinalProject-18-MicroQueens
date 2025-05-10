package com.example.restaurant.controller;

import com.example.restaurant.model.MenuItem;
import com.example.restaurant.services.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menuitems")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(menuItemService.createMenuItem(menuItem));
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurantId(restaurantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updatedItem) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, updatedItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    // ------------FOR SYNC COMMUNICATION WITH ORDER MICROSERVICE----------------
    // (1) inventory checking
    @GetMapping("/checkInventory/{menuItemId}")
    public boolean checkInventory(@PathVariable Long menuItemId, @RequestParam int quantity) {
        return menuItemService.checkInventory(menuItemId, quantity);
    }

    // (2) decrementing inventory on successful order placed
    @PutMapping("/decrementInventory/{menuItemId}")
    public boolean decrementInventory(@PathVariable Long menuItemId, @RequestParam int quantity) {
        return menuItemService.decrementInventory(menuItemId, quantity);
    }
}
