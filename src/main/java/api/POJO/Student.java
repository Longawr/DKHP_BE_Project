package api.POJO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String sex;
    private Classname classnameByClassName;
    private Account accountByAccount;
    private Collection<Courseattend> courseattendsById = new ArrayList<>();

    public Student() {
    }
    public Student(String id, String firstName, String lastName, Date dateOfBirth, String sex, Classname className, Account acc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.classnameByClassName = className;
        this.accountByAccount = acc;
        this.accountByAccount.setRole("SV");
    }

    @Id
    @Column(name = "ID", nullable = false, length = 8)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FirstName", length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LastName", length = 10)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "DateOfBirth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "Sex", length = 3)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!Objects.equals(id, student.id) || !Objects.equals(firstName, student.firstName) || !Objects.equals(lastName, student.lastName) || !Objects.equals(dateOfBirth, student.dateOfBirth)) return false;
        return Objects.equals(sex, student.sex);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ClassName", referencedColumnName = "ID")
    public Classname getClassnameByClassName() {
        return classnameByClassName;
    }

    public void setClassnameByClassName(Classname classnameByClassName) {
        this.classnameByClassName = classnameByClassName;
    }

    @ManyToOne
    @JoinColumn(name = "Account", referencedColumnName = "AccountID")
    public Account getAccountByAccount() {
        return accountByAccount;
    }

    public void setAccountByAccount(Account accountByAccount) {
        this.accountByAccount = accountByAccount;
    }


    @OneToMany(mappedBy = "studentByStudentId")
    @JsonIgnore
    public Collection<Courseattend> getCourseattendsById() {
        return courseattendsById;
    }

    public void setCourseattendsById(Collection<Courseattend> courseattendsById) {
        this.courseattendsById = courseattendsById;
    }
}
