package dbmodel;
// Generated May 9, 2020 2:31:40 PM by Hibernate Tools 5.2.12.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * PlaceTypes generated by hbm2java
 */
@Entity
@Table(name = "place_types", catalog = "traveling", uniqueConstraints = @UniqueConstraint(columnNames = "place_type_code"))
public class PlaceTypes implements java.io.Serializable {

	private Integer idPlaceType;
	private String placeTypeName;
	private String placeTypeCode;
	private Set<PlaceStats> placeStatses = new HashSet<PlaceStats>(0);

	public PlaceTypes() {
	}

	public PlaceTypes(String placeTypeName, String placeTypeCode) {
		this.placeTypeName = placeTypeName;
		this.placeTypeCode = placeTypeCode;
	}

	public PlaceTypes(String placeTypeName, String placeTypeCode, Set<PlaceStats> placeStatses) {
		this.placeTypeName = placeTypeName;
		this.placeTypeCode = placeTypeCode;
		this.placeStatses = placeStatses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_place_type", unique = true, nullable = false)
	public Integer getIdPlaceType() {
		return this.idPlaceType;
	}

	public void setIdPlaceType(Integer idPlaceType) {
		this.idPlaceType = idPlaceType;
	}

	@Column(name = "place_type_name", nullable = false, length = 64)
	public String getPlaceTypeName() {
		return this.placeTypeName;
	}

	public void setPlaceTypeName(String placeTypeName) {
		this.placeTypeName = placeTypeName;
	}

	@Column(name = "place_type_code", unique = true, nullable = false, length = 64)
	public String getPlaceTypeCode() {
		return this.placeTypeCode;
	}

	public void setPlaceTypeCode(String placeTypeCode) {
		this.placeTypeCode = placeTypeCode;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "placeTypes")
	public Set<PlaceStats> getPlaceStatses() {
		return this.placeStatses;
	}

	public void setPlaceStatses(Set<PlaceStats> placeStatses) {
		this.placeStatses = placeStatses;
	}

}