package dbmodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity(name = "AttractionStats")
@Subselect( value = "select a.id_attraction, a.attraction_name, c.city_name, count(*) num_of_searches " + 
	"from traveling.place_stats ps " + 
	"join traveling.place_types pt on pt.id_place_type = ps.id_place_type " + 
	"join traveling.attractions a on a.id_attraction = ps.place_id " + 
	"join traveling.cities c on c.id_city = a.id_city " +
	"where pt.place_type_code = 'ATTRACTION' " + 
	"group by a.id_attraction, a.attraction_name, c.city_name " + 
	"order by num_of_searches desc "
)
@Synchronize( {"place_stats", "attractions", "place_types", "cities"} )
public class AttractionStats implements java.io.Serializable {
	
	private Integer idAttraction;
	private String attractionName;
	private String cityName;
	private Integer num_of_searches;
	
	@Id
	@Column(name = "id_attraction", unique = true, nullable = false)
	public Integer getIdAttraction() {
		return this.idAttraction;
	}
	public void setIdAttraction(Integer idAttraction) {
		this.idAttraction = idAttraction;
	}
	
	@Column(name = "attraction_name", nullable = false, length = 60)
	public String getAttractionName() {
		return this.attractionName;
	}
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
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
