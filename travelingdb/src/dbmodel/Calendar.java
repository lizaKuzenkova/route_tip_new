package dbmodel;
// Generated Apr 27, 2020 7:51:12 PM by Hibernate Tools 5.2.12.Final

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Calendar generated by hbm2java
 */
@Entity
@Table(name = "calendar", catalog = "traveling")
public class Calendar implements java.io.Serializable {

	private Integer idCalendar;
	private Date calendarDate;
	private Set<Tickets> ticketsesForIdCalendarEnd = new HashSet<Tickets>(0);
	private Set<Tickets> ticketsesForIdCalendarStart = new HashSet<Tickets>(0);

	public Calendar() {
	}

	public Calendar(Date calendarDate) {
		this.calendarDate = calendarDate;
	}

	public Calendar(Date calendarDate, Set<Tickets> ticketsesForIdCalendarEnd,
			Set<Tickets> ticketsesForIdCalendarStart) {
		this.calendarDate = calendarDate;
		this.ticketsesForIdCalendarEnd = ticketsesForIdCalendarEnd;
		this.ticketsesForIdCalendarStart = ticketsesForIdCalendarStart;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_calendar", unique = true, nullable = false)
	public Integer getIdCalendar() {
		return this.idCalendar;
	}

	public void setIdCalendar(Integer idCalendar) {
		this.idCalendar = idCalendar;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "calendar_date", nullable = false, length = 19)
	public Date getCalendarDate() {
		return this.calendarDate;
	}

	public void setCalendarDate(Date calendarDate) {
		this.calendarDate = calendarDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "calendarByIdCalendarEnd")
	public Set<Tickets> getTicketsesForIdCalendarEnd() {
		return this.ticketsesForIdCalendarEnd;
	}

	public void setTicketsesForIdCalendarEnd(Set<Tickets> ticketsesForIdCalendarEnd) {
		this.ticketsesForIdCalendarEnd = ticketsesForIdCalendarEnd;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "calendarByIdCalendarStart")
	public Set<Tickets> getTicketsesForIdCalendarStart() {
		return this.ticketsesForIdCalendarStart;
	}

	public void setTicketsesForIdCalendarStart(Set<Tickets> ticketsesForIdCalendarStart) {
		this.ticketsesForIdCalendarStart = ticketsesForIdCalendarStart;
	}

}
