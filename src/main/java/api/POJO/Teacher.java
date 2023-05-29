package api.POJO;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Teacher {
    private String id;
    private String firstName;
    private String lastName;
    private String sex;
    private Account accountByAccount;

    public Teacher() {
    }

    public Teacher(String id, String firstName, String lastName, String sex, Account acc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.accountByAccount = acc;
        this.accountByAccount.setRole("GV");
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

        Teacher teacher = (Teacher) o;

        if (!Objects.equals(id, teacher.id) || !Objects.equals(firstName, teacher.firstName) || !Objects.equals(lastName, teacher.lastName)) return false;
        return Objects.equals(sex, teacher.sex);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Account", referencedColumnName = "AccountID")
    public Account getAccountByAccount() {
        return accountByAccount;
    }

    public void setAccountByAccount(Account accountByAccount) {
        this.accountByAccount = accountByAccount;
    }


}
