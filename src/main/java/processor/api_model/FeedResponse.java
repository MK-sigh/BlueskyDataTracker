package processor.api_model;

import java.util.List;

public class FeedResponse {
    private List<PostItem> feed;
    private String cursor;

    // Jacksonが値を設定・取得できるように、ゲッターとセッターが必要です
    public FeedResponse() {}
    public List<PostItem> getFeed() { return feed; }
    public void setFeed(List<PostItem> feed) { this.feed = feed; }
    public String getCursor() { return cursor; }
    public void setCursor(String cursor) { this.cursor = cursor; }
    
}
