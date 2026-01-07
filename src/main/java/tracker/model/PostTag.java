package tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_tags")
@IdClass(PostTagPK.class) // ★複合主キーであることを指定！
public class PostTag {

    @Id // ★主キーの一部であると指定
    private int postId;
    @Id // ★主キーの一部であると指定
    private int tagId;

    public PostTag() {}

    public PostTag(int postId, int tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public int getTagId() { return tagId; }
    public void setTagId(int tagId) { this.tagId = tagId; }
}

