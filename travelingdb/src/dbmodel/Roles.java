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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Roles generated by hbm2java
 */
@Entity
@Table(name = "roles", catalog = "traveling")
public class Roles implements java.io.Serializable {

	private Integer idRole;
	private String roleName;
	private Set<Users> userses = new HashSet<Users>(0);

	public Roles() {
	}

	public Roles(String roleName) {
		this.roleName = roleName;
	}

	public Roles(String roleName, Set<Users> userses) {
		this.roleName = roleName;
		this.userses = userses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_role", unique = true, nullable = false)
	public Integer getIdRole() {
		return this.idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

	@Column(name = "role_name", nullable = false, length = 20)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

}
