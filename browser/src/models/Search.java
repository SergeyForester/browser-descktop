package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "searches", schema = "browser", catalog = "")
public class Search {
    private int id;
    private String value;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return id == search.id &&
                Objects.equals(value, search.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
