package tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag {

    @Id // ★主キーであることを示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ★DBのSERIAL（連番）に対応
    private int id;
    @Column(unique = true, nullable = false) // ★一意制約とNOT NULLを追加
    private String tag;

    public Tag() {}

    public Tag(int id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
}
