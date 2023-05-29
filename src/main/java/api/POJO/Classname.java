package api.POJO;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Classname {
    private String id;
    private Integer total;
    private Integer maleCount;
    private Integer femaleCount;

    public Classname(){}
    public Classname(String id) {
        this.id = id;
        this.total = 0;
        this.maleCount = 0;
        this.femaleCount = 0;
    }

    @Id
    @Column(name = "ID", nullable = false, length = 6)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Total")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    @Basic
    @Column(name = "MaleCount")
    public Integer getMaleCount() {
        return maleCount;
    }

    public void setMaleCount(Integer maleCount) {
        this.maleCount = maleCount;
    }

    @Basic
    @Column(name = "FemaleCount")
    public Integer getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(Integer femaleCount) {
        this.femaleCount = femaleCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classname classname = (Classname) o;

        if (!Objects.equals(id, classname.id) || !Objects.equals(total, classname.total) || !Objects.equals(maleCount, classname.maleCount)) return false;
        return Objects.equals(femaleCount, classname.femaleCount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (maleCount != null ? maleCount.hashCode() : 0);
        result = 31 * result + (femaleCount != null ? femaleCount.hashCode() : 0);
        return result;
    }
}
