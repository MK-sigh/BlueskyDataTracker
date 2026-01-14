package tracker.model;

import java.io.Serializable;
import java.util.Objects;

public class PostSearchResultsPK implements Serializable{
    private static final long serialVersionUID = 1L;

    // 主キーを構成するカラム名と型を合わせる
    private int postId;
    private int searchWordsId;

    public PostSearchResultsPK() {}

    // equals() と hashCode() のオーバーライドが必須！
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostSearchResultsPK that = (PostSearchResultsPK) o;
        return postId == that.postId && searchWordsId == that.searchWordsId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, searchWordsId); //2つのIDを混ぜ合わせて「この組み合わせ専用の番号」を発行する
    }

}
