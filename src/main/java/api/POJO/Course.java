package api.POJO;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@Entity
public class Course {
    private int id;
    private int semesterId;
    private int year;
    private String room;
    private String day;
    private String period;
    private Integer maxSlot;
    private Subject subjectBySubjectId;
    private String teacherName;
    private Integer registerSlot;
    @Transient
    private final BooleanProperty checked;

    public Course() {
        this.checked = new SimpleBooleanProperty(false);
    }

    public Course(int semesterId, int year, String teacherName, Subject subject, String room, String day, String period, Integer maxSlot) {
        this.semesterId = semesterId;
        this.year = year;
        this.teacherName = teacherName;
        this.room = room;
        this.day = day;
        this.period = period;
        this.maxSlot = maxSlot;
        this.registerSlot = 0;
        this.subjectBySubjectId = subject;
        this.checked = new SimpleBooleanProperty(false);
    }
    @Basic
    @Column(name = "TeacherName", length = 50)
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SemesterID", nullable = false)
    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    @Basic
    @Column(name = "Year", nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Basic
    @Column(name = "Room", length = 10)
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Basic
    @Column(name = "Day", length = 10)
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Basic
    @Column(name = "Period", length = 45)
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Basic
    @Column(name = "maxSlot")
    public Integer getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(Integer maxSlot) {
        this.maxSlot = maxSlot;
    }

    @Basic
    @Column(name = "registerSlot")
    public Integer getRegisterSlot() {
        return registerSlot;
    }

    public void setRegisterSlot(Integer registerSlot) {
        this.registerSlot = registerSlot;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }
    public BooleanProperty checkedProperty() {
        return checked;
    }
    public boolean isChecked() {
        return checked.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if ((id != course.id) || (semesterId != course.semesterId) || (year != course.year) || !Objects.equals(room, course.room)) return false;
        if (!Objects.equals(day, course.day) || !Objects.equals(period, course.period) || !Objects.equals(maxSlot, course.maxSlot)) return false;
        return Objects.equals(registerSlot, course.registerSlot);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + semesterId;
        result = 31 * result + year;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (maxSlot != null ? maxSlot.hashCode() : 0);
        result = 31 * result + (maxSlot != null ? registerSlot.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "SubjectID", referencedColumnName = "ID")
    public Subject getSubjectBySubjectId() {
        return subjectBySubjectId;
    }

    public void setSubjectBySubjectId(Subject subjectBySubjectId) {
        this.subjectBySubjectId = subjectBySubjectId;
    }

}
