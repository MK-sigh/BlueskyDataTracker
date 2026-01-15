package tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "post_search_results")
@IdClass(PostSearchResultsPK.class) // PostTagと同様に複合主キー
public class PostSearchResults {
    @Id
    @Column(name = "search_word_id") // 明示的に物理カラム名を指定
    private int searchWordId;
    @Id
    @Column(name = "post_id")
    private int postId;

    // 検索ワードと紐付け
    @ManyToOne
    @JoinColumn(name = "search_word_id", insertable = false, updatable = false)
    private SearchWord searchWord;

    // 投稿と紐付け
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    private LocalDateTime fetchedAt; // いつ取得したかの記録（分析に便利）

    public PostSearchResults() {}
    public PostSearchResults(int searchWordId, int postId, LocalDateTime fetchedAt) {
        this.searchWordId = searchWordId;
        this.postId = postId;
        this.fetchedAt = fetchedAt;
    }
    // コンストラクタ・Getter/Setter
    public int getSearchWordId() {
        return searchWordId;
    }
    public void setSearchWordId(int searchWordId) {
        this.searchWordId = searchWordId;
    }
    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }

    // PostSearchResults.java に追加
    public SearchWord getSearchWord() {
        return searchWord;
    }
    public void setSearchWord(SearchWord searchWord) {
        this.searchWord = searchWord;
}

}

