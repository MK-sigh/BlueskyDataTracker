package tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_tags")
public class PostTag {

    private int postId;
    private Integer tagId;

    public PostTag() {}

    public PostTag(int postId, Integer tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public Integer getTagId() { return tagId; }
    public void setTagId(Integer tagId) { this.tagId = tagId; }
}

