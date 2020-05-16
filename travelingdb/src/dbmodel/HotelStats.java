package dbmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity(name = "HotelStats")
@Subselect( value = "select h.id_hotel, h.hotel_name, c.city_name, count(*) num_of_searches " + 
		"from traveling.place_stats ps " + 
		"join traveling.place_types pt on pt.id_place_type = ps.id_place_type " + 
		"join traveling.hotels h on h.id_hotel = ps.place_id " + 
		"join traveling.cities c on c.id_city = h.id_city " + 
		"where pt.place_type_code = 'HOTEL' " + 
		"group by h.id_hotel, h.hotel_name, c.city_name " + 
		"order by num_of_searches desc"
)
@Synchronize( {"place_stats", "hotels", "place_types", "cities"} )

public class HotelStats implements Serializable {
	
	private Integer idHotel;
	private String hotelName;
	private String cityName;
	private Integer num_of_searches;
	
	@Id
	@Column(name = "id_hotel", unique = true, nullable = false)
	public Integer getIdHotel() {
		return this.idHotel;
	}
	public void setIdHotel(Integer idHotel) {
		this.idHotel = idHotel;
	}
	
	@Column(name = "hotel_name", nullable = false, length = 60)
	public String getHotelName() {
		return this.hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
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
