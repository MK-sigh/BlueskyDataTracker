package tracker.processor.api_model;

import java.util.List;

public class FeedResponse {
    private List<ItemFeedJson> feed;
    private String cursor;

    // Jacksonが値を設定・取得できるように、ゲッターとセッターが必要です
    public FeedResponse() {}
    
    public List<ItemFeedJson> getFeed() { return feed; }
    public void setFeed(List<ItemFeedJson> feed) { this.feed = feed; }
    public String getCursor() { return cursor; }
    public void setCursor(String cursor) { this.cursor = cursor; }

}
