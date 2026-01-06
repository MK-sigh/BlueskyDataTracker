package tracker.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // ★Userクラスがデータベースのエンティティであることを示す
@Table(name = "users") // ★テーブル名を指定（省略可能だが明示を推奨）
public class User {
    @Id // ★主キーであることを示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ★DBのSERIAL（連番）に対応
    private int id;
    private String did;
    private String handle;
    private String displayName;
    private int followersCount;
    private int followingCount;
    private String createdAccountAt;

    public User(){}
    public User(int id, String did, String handle, String displayName,
        int followersCount, int followingCount, String createdAccountAt){
        this.id = id;
        this.did = did;
        this.handle = handle;
        this.displayName = displayName;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.createdAccountAt = createdAccountAt;
        }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getDid() {return did;}
    public void setDid(String did) {this.did = did;}

    public String getHandle() {return handle;}
    public void setHandle(String handle) {this.handle = handle;}

    public String getDisplayName() {return displayName;}
    public void setDisplayName(String displayName) {this.displayName = displayName;}

    public int getFollowersCount() {return followersCount;}
    public void setFollowersCount(int followersCount) {this.followersCount = followersCount;}

    public int getFollowingCount() {return followingCount;}
    public void setFollowingCount(int followingCount) {this.followingCount = followingCount;}

    public String getCreatedAccountAt() {return createdAccountAt;}
    public void setCreatedAccountAt(String createdAccountAt) {this.createdAccountAt = createdAccountAt;}


}

// id	SERIAL	PRIMARY KEY	主キー（連番）
// did	VARCHAR(255)	UNIQUE, NOT NULL	Bluesky特有のID
// handle	VARCHAR(255)	NOT NULL	ユーザー名
// displayName	VARCHAR(255)		表示名
// followersCount	INTEGER	NOT NULL	追加：現在のフォロワー総数
// followingCount	INTEGER	NOT NULL	追加：現在のフォロー総数
// createdAccountAt	TIMESTAMP	NOT NULL	追加：アカウント作成日時