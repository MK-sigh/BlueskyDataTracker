package model;
import java.time.ZonedDateTime;

public class Post {
    private int id; // SERIAL PRIMARY KEY に対応
    private String uri;
    private String cid;
    private String text; // TEXT NOT NULL に対応
    private ZonedDateTime created_at; // TIMESTAMP WITH TIME ZONE NOT NULL に対応
    private ZonedDateTime indexed_at;
    private String language;
    private String label;
    private int replyCount;
    private int repostCount;
    private int likeCount;
    private int author_id; // INTEGER (外部キー) に対応


    public Post(){};
    public Post(int id, String uri, String cid, String text, ZonedDateTime created_at, 
        ZonedDateTime indexed_at, String language, String label,
        int replyCount, int repostCount, int likeCount, int author_id){
        this.id = id;
        this.uri = uri;
        this.cid = cid;
        this.text = text;
        this.created_at = created_at;
        this.indexed_at = indexed_at;
        this.language = language;
        this.label = label;
        this.replyCount = replyCount;
        this.repostCount = repostCount;
        this.likeCount = likeCount;
        this.author_id = author_id;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getUri() {return uri;}
    public void setUri(String uri) {this.uri = uri;}

    public String getCid() {return cid;}
    public void setCid(String cid) {this.cid = cid;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public ZonedDateTime getCreated_at() {return created_at;}
    public void setCreated_at(ZonedDateTime created_at) {this.created_at = created_at;}

    public ZonedDateTime getIndexed_at() {return indexed_at;}
    public void setIndexed_at(ZonedDateTime indexed_at) {this.indexed_at = indexed_at;}

    public String getLanguage() {return language;}
    public void setLanguage(String language) {this.language = language;}

    public String getLabel() {return label;}
    public void setLabel(String label) {this.label = label;}

    public int getReplyCount() {return replyCount;}
    public void setReplyCount(int replyCount) {this.replyCount = replyCount;}

    public int getRepostCount() {return repostCount;}
    public void setRepostCount(int repostCount) {this.repostCount = repostCount;}

    public int getLikeCount() {return likeCount;}
    public void setLikeCount(int likeCount) {this.likeCount = likeCount;}

    public int getAuthor_id() {return author_id;}
    public void setAuthor_id(int author_id) {this.author_id = author_id;}


}


// カラム名	DB型	制約	変更点
// id	SERIAL	PRIMARY KEY	主キー（連番）
// uri	VARCHAR(255)	UNIQUE, NOT NULL	追加：投稿のユニークID
// cid	VARCHAR(255)	NOT NULL	追加：コンテンツID（データ参照に必須）
// text	TEXT	NOT NULL	投稿内容
// created_at	TIMESTAMP	NOT NULL	投稿日
// indexed_at	TIMESTAMP	NOT NULL	追加：投稿がBlueskyにインデックスされた時刻
// language	VARCHAR(10)	NOT NULL	言語
// author_id	INTEGER	FK users(id)	投稿者ID