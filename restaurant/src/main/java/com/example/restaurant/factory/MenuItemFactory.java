package com.example.restaurant.factory;
import com.example.restaurant.enums.DietaryOption;
import com.example.restaurant.model.*;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuItemFactory {
    public static MenuItem createMenuItem( Map<String, Object> attributes, Restaurant restaurant) {
        String type = ((String) attributes.get("type")).toUpperCase();
        switch (type.toUpperCase()) {
            case "MAIN_DISH" -> {
                MainDish dish = new MainDish();
                dish.setProtein((int) attributes.get("protein"));
                dish.setSpiceLevel((String) attributes.get("spiceLevel"));
                return setCommonFields(dish, attributes, restaurant);
            }
            case "DRINK" -> {
                Drink drink = new Drink();
                drink.setIsCold((boolean) attributes.get("isCold"));
                drink.setVolume((String) attributes.get("volume"));
                drink.setFlavor((String) attributes.get("flavor"));
                return setCommonFields(drink, attributes, restaurant);
            }
            case "DESSERT" -> {
                Dessert dessert = new Dessert();
                dessert.setIsGlutenFree((boolean) attributes.get("isGlutenFree"));
                dessert.setContainsNuts((boolean) attributes.get("containsNuts"));
                dessert.setServingTemperature((String) attributes.get("servingTemperature"));
                return setCommonFields(dessert, attributes, restaurant);
            }
            case "SALAD" -> {
                Salad salad = new Salad();
                salad.setDressingType((String) attributes.get("dressingType"));
                salad.setHasProtein((boolean) attributes.get("hasProtein"));
                salad.setIsVegan((boolean) attributes.get("isVegan"));
                return setCommonFields(salad, attributes, restaurant);
            }
            case "SANDWICH" -> {
                Sandwich sandwich = new Sandwich();
                sandwich.setBreadType((String) attributes.get("breadType"));
                sandwich.setIsToasted((boolean) attributes.get("isToasted"));
                sandwich.setFilling((String) attributes.get("filling"));
                return setCommonFields(sandwich, attributes, restaurant);
            }
            case "SIDE_DISH" -> {
                SideDish sideDish = new SideDish();
                sideDish.setSize((String) attributes.get("size"));
                sideDish.setDip((String) attributes.get("dip"));
                sideDish.setIsSpicy((boolean) attributes.get("isSpicy"));
                return setCommonFields(sideDish, attributes, restaurant);
            }

            default -> throw new IllegalArgumentException("Invalid menu item type");
        }
    }

    private static MenuItem setCommonFields(MenuItem item, Map<String, Object> attrs, Restaurant restaurant) {
        item.setName((String) attrs.get("name"));
        item.setPrice(((Number) attrs.get("price")).floatValue());
        item.setInventory((int) attrs.get("inventory"));
        List<DietaryOption> dietaryOptions = parseDietaryOptions(attrs.get("dietaryRestrictions"));
        item.setDietaryRestrictions(dietaryOptions);
        item.setType((String) attrs.get("type"));
        item.setRestaurant(restaurant);
        return item;
    }
    private static List<DietaryOption> parseDietaryOptions(Object raw) {
        if (raw instanceof List<?> rawList) {
            return rawList.stream()
                    .map(Object::toString)
                    .map(String::toUpperCase)
                    .map(DietaryOption::valueOf)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
