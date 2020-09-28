package models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Basic
    @Column(name = "url")
    private String url;

    @Basic
    @Column(name = "title_")
    private String title;

    @Basic
    @Column(name = "datetime")
    private Timestamp datetime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return id == history.id &&
                Objects.equals(url, history.url) &&
                Objects.equals(datetime, history.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, datetime);
    }
}
