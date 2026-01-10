package tracker.processor.api_model;

import java.util.List;

public class FeedResponse {
    private List<PostViewJson> posts;
    private String cursor;

    // Jacksonが値を設定・取得できるように、ゲッターとセッターが必要です
    public FeedResponse() {}

    public List<PostViewJson> getPosts() { return posts; }
    public void setPosts(List<PostViewJson> posts) { this.posts = posts; }
    public String getCursor() { return cursor; }
    public void setCursor(String cursor) { this.cursor = cursor; }

}
