package tracker.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // SERIAL PRIMARY KEY に対応
    private String uri;
    private String cid;
    private String text; // TEXT NOT NULL に対応
    private String createdAt; // TIMESTAMP WITH TIME ZONE NOT NULL に対応
    private String indexedAt;
    private String language;
    // private String label;
    private int bookmarkCount;
    private int replyCount;
    private int repostCount;
    private int likeCount;
    private int quoteCount;
    private int authorId; // INTEGER (外部キー) に対応

    
    
    public Post(){};
    public Post(int id, String uri, String cid, String text, String createdAt, 
        String indexedAt, String language,
        int bookmarkCount, int replyCount, int repostCount, int likeCount, int quoteCount,
        int authorId){
            this.id = id;
            this.uri = uri;
            this.cid = cid;
            this.text = text;
            this.createdAt = createdAt;
            this.indexedAt = indexedAt;
            this.language = language;
            // this.label = label;
            this.bookmarkCount = bookmarkCount;
            this.replyCount = replyCount;
            this.repostCount = repostCount;
            this.likeCount = likeCount;
            this.quoteCount = quoteCount;
            this.authorId = authorId;
        }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getUri() {return uri;}
    public void setUri(String uri) {this.uri = uri;}

    public String getCid() {return cid;}
    public void setCid(String cid) {this.cid = cid;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public String getCreatedAt() {return createdAt;}
    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}

    public String getIndexedAt() {return indexedAt;}
    public void setIndexedAt(String indexedAt) {this.indexedAt = indexedAt;}

    public String getLanguage() {return language;}
    public void setLanguage(String language) {this.language = language;}

    // public String getLabel() {return label;}
    // public void setLabel(String label) {this.label = label;}

    public int getBookmarkCount() { return bookmarkCount; }
    public void setBookmarkCount(int bookmarkCount) { this.bookmarkCount = bookmarkCount; }

    public int getReplyCount() {return replyCount;}
    public void setReplyCount(int replyCount) {this.replyCount = replyCount;}

    public int getRepostCount() {return repostCount;}
    public void setRepostCount(int repostCount) {this.repostCount = repostCount;}

    public int getLikeCount() {return likeCount;}
    public void setLikeCount(int likeCount) {this.likeCount = likeCount;}

    public int getQuoteCount() { return quoteCount; }
    public void setQuoteCount(int quoteCount) { this.quoteCount = quoteCount; }

    public int getAuthorId() {return authorId;}
    public void setAuthorId(int authorId) {this.authorId = authorId;}
}


// カラム名	DB型	制約	変更点
// id	SERIAL	PRIMARY KEY	主キー（連番）
// uri	VARCHAR(255)	UNIQUE, NOT NULL	追加：投稿のユニークID
// cid	VARCHAR(255)	NOT NULL	追加：コンテンツID（データ参照に必須）
// text	TEXT	NOT NULL	投稿内容
// createdAt	TIMESTAMP	NOT NULL	投稿日
// indexedAt	TIMESTAMP	NOT NULL	追加：投稿がBlueskyにインデックスされた時刻
// language	VARCHAR(10)	NOT NULL	言語
// authorId	INTEGER	FK users(id)	投稿者ID