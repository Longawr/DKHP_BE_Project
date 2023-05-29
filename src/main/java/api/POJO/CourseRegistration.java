package api.POJO;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "course_registration", schema = "dbo", catalog = "")
@IdClass(CourseRegistrationPK.class)
public class CourseRegistration {
    private int id;
    private int semesterId;
    private int year;
    private Date dateStart;
    private Date dateEnd;

    public CourseRegistration() {
    }

    public CourseRegistration(int id, int semesterId, int year, Date dateStart, Date dateEnd) {
        this.id = id;
        this.semesterId = semesterId;
        this.year = year;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "SemesterID", nullable = false)
    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    @Id
    @Column(name = "Year", nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Basic
    @Column(name = "DateStart")
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Basic
    @Column(name = "DateEnd")
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseRegistration that = (CourseRegistration) o;

        if ((id != that.id) || (semesterId != that.semesterId) || (year != that.year) || !Objects.equals(dateStart, that.dateStart)) return false;
        return Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + semesterId;
        result = 31 * result + year;
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        return result;
    }
}
