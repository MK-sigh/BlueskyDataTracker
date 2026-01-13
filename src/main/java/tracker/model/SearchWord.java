package tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "searh_word")
public class SearchWord {

    @Id // ★主キーであることを示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ★DBのSERIAL（連番）に対応
    private int id;
    @Column(unique = true, nullable = false) // ★一意制約とNOT NULLを追加
    private String seachWord;

}
