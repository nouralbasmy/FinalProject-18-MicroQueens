@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private float id;
    private String name;
    private Long price;
    private String Inventory;
    @ElementCollection
    private List<String> dietaryRestrictions;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    // 1. No-arg constructor
    public MenuItem() {
    }

    // 2. All-args constructor (with ID)
    public MenuItem(Long id, String name, Long price, String inventory,
                    List<String> dietaryRestrictions, List<Restaurant> restaurants) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.dietaryRestrictions = dietaryRestrictions;
        this.restaurants = restaurants;
    }

    // 3. Constructor without ID (for creating new items)
    public MenuItem(String name, Long price, String inventory,
                    List<String> dietaryRestrictions, List<Restaurant> restaurants) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.dietaryRestrictions = dietaryRestrictions;
        this.restaurants = restaurants;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }



}