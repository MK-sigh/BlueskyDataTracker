package processor.api_model;

import java.time.ZonedDateTime;
import java.util.List;

public class PostJson {
    String uri;
    String cid;
    String text;
    ZonedDateTime createdAt;
    ZonedDateTime indexedAt;
    List<String> langs;
    List<String> label;
    int replyCount;
    int repostCount;
    int likeCount;

    // Jacksonがパースできるように、デフォルトコンストラクタとゲッター/セッター（またはpublicフィールド）が必要です
    public PostJson() {}

    public ZonedDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(ZonedDateTime createdAt) {this.createdAt = createdAt;}

    public List<String> getLangs() {return langs;}
    public void setLangs(List<String> langs) {this.langs = langs;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public int getReplyCount() {return replyCount;}
    public void setReplyCount(int replyCount) {this.replyCount = replyCount;}

    public int getRepostCount() {return repostCount;}
    public void setRepostCount(int repostCount) {this.repostCount = repostCount;}

    public int getLikeCount() {return likeCount;}
    public void setLikeCount(int likeCount) {this.likeCount = likeCount;}


}
