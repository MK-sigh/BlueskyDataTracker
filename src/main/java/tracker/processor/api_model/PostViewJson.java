package tracker.processor.api_model;

public class PostViewJson {
    private String uri;
    private String cid;
    private AuthorJson author;
    private PostRecordJson record; // JSONのフィールド名は "record"
    private String indexedAt;
    
    // 数値系フィールド
    private int bookmarkCount;
    private int replyCount;
    private int repostCount;
    private int likeCount;
    private int quoteCount;

    // Getters / Setters
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }

    public String getCid() { return cid; }
    public void setCid(String cid) { this.cid = cid; }

    public AuthorJson getAuthor() { return author; }
    public void setAuthor(AuthorJson author) { this.author = author; }

    public PostRecordJson getRecord() { return record; }
    public void setRecord(PostRecordJson record) { this.record = record; }

    public String getIndexedAt() {return indexedAt;}
    public void setIndexedAt(String indexedAt) {this.indexedAt = indexedAt;}

    // 数値系のGetter/Setter
    public int getReplyCount() { return replyCount; }
    public void setReplyCount(int replyCount) { this.replyCount = replyCount; }
    public int getRepostCount() { return repostCount; }
    public void setRepostCount(int repostCount) { this.repostCount = repostCount; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public int getBookmarkCount() { return bookmarkCount; }
    public void setBookmarkCount(int bookmarkCount) { this.bookmarkCount = bookmarkCount; }
    public int getQuoteCount() { return quoteCount; }
    public void setQuoteCount(int quoteCount) { this.quoteCount = quoteCount; }
}