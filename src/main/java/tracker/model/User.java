package tracker.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

@Entity // ★Userクラスがデータベースのエンティティであることを示す
@Table(name = "users") // ★テーブル名を指定（省略可能だが明示を推奨）
public class User {
    @Id // ★主キーであることを示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ★DBのSERIAL（連番）に対応
    private int id;
    private String did;
    private String handle;
    private String display_name;
    private int followers_count;
    private int following_count;
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime created_at; // nullable=false が NOT NULL 制約に対応
    // private ZonedDateTime created_at;

    public User(){}
    public User(int id, String did, String handle, String display_name,
        int followers_count, int following_count, ZonedDateTime created_at){
        this.id = id;
        this.did = did;
        this.handle = handle;
        this.display_name = display_name;
        this.followers_count = followers_count;
        this.following_count = following_count;
        this.created_at = created_at;
        }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getDid() {return did;}
    public void setDid(String did) {this.did = did;}

    public String getHandle() {return handle;}
    public void setHandle(String handle) {this.handle = handle;}

    public String getDisplay_name() {return display_name;}
    public void setDisplay_name(String display_name) {this.display_name = display_name;}

    public int getFollowers_count() {return followers_count;}
    public void setFollowers_count(int followers_count) {this.followers_count = followers_count;}

    public int getFollowing_count() {return following_count;}
    public void setFollowing_count(int following_count) {this.following_count = following_count;}

    public ZonedDateTime getCreated_at() {return created_at;}
    public void setCreated_at(ZonedDateTime created_at) {this.created_at = created_at;}


    // ★追加するメソッド：保存直前に現在時刻を設定
    /**
     * エンティティがデータベースに永続化される直前に実行され、createdAtに現在時刻を設定します。
     */
    @PrePersist
    protected void onCreate() {
        if (this.created_at == null) {
            this.created_at = ZonedDateTime.now();
        }
    }
}

// id	SERIAL	PRIMARY KEY	主キー（連番）
// did	VARCHAR(255)	UNIQUE, NOT NULL	Bluesky特有のID
// handle	VARCHAR(255)	NOT NULL	ユーザー名
// display_name	VARCHAR(255)		表示名
// followers_count	INTEGER	NOT NULL	追加：現在のフォロワー総数
// following_count	INTEGER	NOT NULL	追加：現在のフォロー総数
// created_at	TIMESTAMP	NOT NULL	追加：アカウント作成日時