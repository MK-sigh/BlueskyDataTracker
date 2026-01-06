package tracker.processor.api_model;

import java.util.List;

public class PostRecordJson {
    private String text;
    private String createdAt; // JSON上の型に合わせてStringで受けるのが無難
    // private String indexedAt;
    private List<String> langs;

    // List<String> label;
    // private int likeCount;
    // private int replyCount;

    // Jacksonがパースできるように、デフォルトコンストラクタとゲッター/セッター（またはpublicフィールド）が必要です
    public PostRecordJson() {}

    // public String getUri() {return uri;}
    // public void setUri(String uri) {this.uri = uri;}

    // public String getCid() {return cid;}
    // public void setCid(String cid) {this.cid = cid;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public String getCreatedAt() {return createdAt;}
    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}

    // public String getIndexedAt() {return indexedAt;}
    // public void setIndexedAt(String indexedAt) {this.indexedAt = indexedAt;}

    public List<String> getLangs() {return langs;}
    public void setLangs(List<String> langs) {this.langs = langs;}

    // public List<String> getLabel() {return label;}
    // public void setLabel(List<String> label) {this.label = label;}

    // public int getReplyCount() {return replyCount;}
    // public void setReplyCount(int replyCount) {this.replyCount = replyCount;}

    // public int getRepostCount() {return repostCount;}
    // public void setRepostCount(int repostCount) {this.repostCount = repostCount;}

    // public int getLikeCount() {return likeCount;}
    // public void setLikeCount(int likeCount) {this.likeCount = likeCount;}

    // public int getBookmarkCount() { return bookmarkCount; }
    // public void setBookmarkCount(int bookmarkCount) { this.bookmarkCount = bookmarkCount; }

    // public int getQuoteCount() { return quoteCount; }
    // public void setQuoteCount(int quoteCount) { this.quoteCount = quoteCount; }

}
