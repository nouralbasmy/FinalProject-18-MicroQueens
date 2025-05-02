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






}