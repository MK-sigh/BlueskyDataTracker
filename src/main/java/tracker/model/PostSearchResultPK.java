package tracker.model;

import java.io.Serializable;
import java.util.Objects;

public class PostSearchResultPK implements Serializable{
    private static final long serialVersionUID = 1L;

    // 主キーを構成するカラム名と型を合わせる
    private int postId;
    private int searchWordId;

    public PostSearchResultPK() {}

    // equals() と hashCode() のオーバーライドが必須！
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostSearchResultPK that = (PostSearchResultPK) o;
        return postId == that.postId && searchWordId == that.searchWordId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, searchWordId); //2つのIDを混ぜ合わせて「この組み合わせ専用の番号」を発行する
    }

}
