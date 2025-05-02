@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Restaurant restaurant;

    private String comment;

    // Getters
    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getComment() {
        return comment;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

// Constructors

    // 1. No-arg constructor
    public Rating() {
    }

    // 2. All-args constructor
    public Rating(Long id, int score, Customer customer, Restaurant restaurant, String comment) {
        this.id = id;
        this.score = score;
        this.customer = customer;
        this.restaurant = restaurant;
        this.comment = comment;
    }

    // 3. Constructor without ID
    public Rating(int score, Customer customer, Restaurant restaurant, String comment) {
        this.score = score;
        this.customer = customer;
        this.restaurant = restaurant;
        this.comment = comment;
    }

}