package model;
import java.time.ZonedDateTime;

public class User {
    private int id;
    private String did;
    private String handle;
    private String display_name;
    private int followers_count;
    private int following_count;
    private ZonedDateTime created_at;

    public User(){}
    public User(int id, String did, String handle, String display_name,
        int followers_count, int following_count, ZonedDateTime created_at){
            id = this.id;
            did = this.did;
            handle = this.handle;
            display_name = this.display_name;
            followers_count = this.followers_count;
            following_count = this.following_count;
            created_at = this.created_at;
        }




}

// id	SERIAL	PRIMARY KEY	主キー（連番）
// did	VARCHAR(255)	UNIQUE, NOT NULL	Bluesky特有のID
// handle	VARCHAR(255)	NOT NULL	ユーザー名
// display_name	VARCHAR(255)		表示名
// followers_count	INTEGER	NOT NULL	追加：現在のフォロワー総数
// following_count	INTEGER	NOT NULL	追加：現在のフォロー総数
// created_at	TIMESTAMP	NOT NULL	追加：アカウント作成日時