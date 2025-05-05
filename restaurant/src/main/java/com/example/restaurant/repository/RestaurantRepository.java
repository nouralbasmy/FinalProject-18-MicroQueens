import com.example.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r JOIN r.menu m WHERE :restriction MEMBER OF m.dietaryRestrictions")
    List<Restaurant> findByMenuDietaryRestriction(@Param("restriction") String restriction);

}