package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bookmarks", schema = "browser", catalog = "")
public class Bookmark {
    private int id;
    private String url;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return id == bookmark.id &&
                Objects.equals(url, bookmark.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }
}
