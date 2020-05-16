package dbmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity(name = "RestaurantStats")
@Subselect( value = "select r.id_restaurant, r.rest_name, c.city_name, count(*) num_of_searches " + 
		"from traveling.place_stats ps " + 
		"join traveling.place_types pt on pt.id_place_type = ps.id_place_type " + 
		"join traveling.restaurants r on r.id_restaurant = ps.place_id " + 
		"join traveling.cities c on c.id_city = r.id_city " + 
		"where pt.place_type_code = 'RESTAURANT' " + 
		"group by r.id_restaurant, r.rest_name, c.city_name " + 
		"order by num_of_searches desc"
)
@Synchronize( {"place_stats", "restaurants", "place_types", "cities"} )

public class RestaurantStats {
	private Integer idRestaurant;
	private String restaurantName;
	private String cityName;
	private Integer num_of_searches;
	
	@Id
	@Column(name = "id_restaurant", unique = true, nullable = false)
	public Integer getIdRestaurant() {
		return this.idRestaurant;
	}
	public void setIdRestaurant(Integer idRestaurant) {
		this.idRestaurant = idRestaurant;
	}
	
	@Column(name = "rest_name", nullable = false, length = 60)
	public String getRestaurantName() {
		return this.restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	
	@Column(name = "city_name", nullable = false, length = 50)
	public String getCityName() {
		return this.cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "num_of_searches", nullable = true)
	public Integer getNumOfSearches() {
		return this.num_of_searches;
	}
	public void setNumOfSearches(Integer num_of_searches) {
		this.num_of_searches = num_of_searches;
	}
}
