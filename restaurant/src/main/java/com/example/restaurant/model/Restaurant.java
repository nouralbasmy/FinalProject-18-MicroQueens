import restaurant.src.main.java.com.example.restaurant.model.*;
@Entity
@Table(name = "\"restaurant\"")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private float id;

    private String name;
    private String cuisine;
    private String address;
    private String phone;
    private float rating;
    private boolean active;

    @ElementCollection
    private List<String> dietaryOptions;
    @ElementCollection
    private List<MenuItem> menu;



}