//package com.example.restaurant;
//
//import com.example.restaurant.factory.MenuItemFactory;
//import com.example.restaurant.model.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class MenuItemFactoryTest {
//
//    private final Restaurant dummyRestaurant = new Restaurant(
//            1L, "Testaurant", "Cuisine", "123 Street", "1234567890",
//            5, true, List.of(), List.of()
//    );
//
//    @Test
//    public void testCreateMainDish() {
//        Map<String, Object> attributes = Map.of(
//                "name", "Grilled Chicken",
//                "price", 15.99f,
//                "inventory", 10,
//                "dietaryRestrictions", List.of("gluten-free"),
//                "protein", 30,
//                "spiceLevel", "medium"
//        );
//
//        MenuItem item = MenuItemFactory.createMenuItem("MAIN_DISH", attributes, dummyRestaurant);
//
//        assertTrue(item instanceof MainDish);
//        MainDish dish = (MainDish) item;
//        assertEquals("Grilled Chicken", dish.getName());
//        assertEquals(30, dish.getProtein());
//        assertEquals("medium", dish.getSpiceLevel());
//        assertEquals(dummyRestaurant, dish.getRestaurant());
//    }
//
//    @Test
//    public void testCreateDrink() {
//        Map<String, Object> attributes = Map.of(
//                "name", "Cola",
//                "price", 2.5f,
//                "inventory", 50,
//                "dietaryRestrictions", List.of("vegan"),
//                "isCold", true,
//                "volume", "330ml",
//                "flavor", "cola"
//        );
//
//        MenuItem item = MenuItemFactory.createMenuItem("DRINK", attributes, dummyRestaurant);
//
//        assertTrue(item instanceof Drink);
//        Drink drink = (Drink) item;
//        assertTrue(drink.isCold());
//        assertEquals("330ml", drink.getVolume());
//        assertEquals("cola", drink.getFlavor());
//    }
//
//    @Test
//    public void testInvalidTypeThrowsException() {
//        Map<String, Object> attributes = Map.of(
//                "name", "Invalid",
//                "price", 10,
//                "inventory", 5,
//                "dietaryRestrictions", List.of()
//        );
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            MenuItemFactory.createMenuItem("INVALID_TYPE", attributes, dummyRestaurant);
//        });
//    }
//}