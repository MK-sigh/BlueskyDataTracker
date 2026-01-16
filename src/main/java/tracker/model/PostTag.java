package tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "post_tags")
@IdClass(PostTagPK.class) // ★複合主キーであることを指定！
public class PostTag {

    @Id // ★主キーの一部であると指定
    @Column(name = "post_id")
    private int postId;
    @Id // ★主キーの一部であると指定
    @Column(name = "tag_id")
    private int tagId;

    // 投稿と紐付け
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    // 検索ワードと紐付け
    @ManyToOne
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private Tag Tag;

    public PostTag() {}

    public PostTag(int postId, int tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public int getTagId() { return tagId; }
    public void setTagId(int tagId) { this.tagId = tagId; }


    // --- ここを追加 ---
    public Tag getTag() {
        return Tag;
    }
    public void setTag(Tag tag) {
        this.Tag = tag;
    }
}

