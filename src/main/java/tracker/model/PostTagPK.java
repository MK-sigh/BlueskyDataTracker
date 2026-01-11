package tracker.model;

import java.io.Serializable;
import java.util.Objects;

public class PostTagPK implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主キーを構成するカラム名と型を合わせる
    private int postId;
    private int tagId;

    public PostTagPK() {}

    // equals() と hashCode() のオーバーライドが必須！
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostTagPK that = (PostTagPK) o;
        return postId == that.postId && tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, tagId); //2つのIDを混ぜ合わせて「この組み合わせ専用の番号」を発行する
    }

    // Getter/Setter (省略可だが、IDEで自動生成推奨)
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public int getTagId() { return tagId; }
    public void setTagId(int tagId) { this.tagId = tagId; }



}