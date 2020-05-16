package dbmodel;
// Generated Apr 27, 2020 7:51:12 PM by Hibernate Tools 5.2.12.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Routes generated by hbm2java
 */
@Entity
@Table(name = "routes", catalog = "traveling")
public class Routes implements java.io.Serializable {

	private Integer idRoute;
	private Attractions attractions;
	private Restaurants restaurants;
	private Integer idHotel;
	private Set<UsersRoutes> usersRouteses = new HashSet<UsersRoutes>(0);

	public Routes() {
	}

	public Routes(Attractions attractions, Restaurants restaurants, Integer idHotel, Set<UsersRoutes> usersRouteses) {
		this.attractions = attractions;
		this.restaurants = restaurants;
		this.idHotel = idHotel;
		this.usersRouteses = usersRouteses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_route", unique = true, nullable = false)
	public Integer getIdRoute() {
		return this.idRoute;
	}

	public void setIdRoute(Integer idRoute) {
		this.idRoute = idRoute;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_attraction")
	public Attractions getAttractions() {
		return this.attractions;
	}

	public void setAttractions(Attractions attractions) {
		this.attractions = attractions;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_restaurant")
	public Restaurants getRestaurants() {
		return this.restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	@Column(name = "id_hotel")
	public Integer getIdHotel() {
		return this.idHotel;
	}

	public void setIdHotel(Integer idHotel) {
		this.idHotel = idHotel;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "routes")
	public Set<UsersRoutes> getUsersRouteses() {
		return this.usersRouteses;
	}

	public void setUsersRouteses(Set<UsersRoutes> usersRouteses) {
		this.usersRouteses = usersRouteses;
	}

}
