package tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "post_search_results")
@IdClass(PostSearchResultsPK.class) // PostTagと同様に複合主キー
public class PostSearchResults {
    @Id
    private int searchWordsId;
    @Id
    private int postId;
    private LocalDateTime fetchedAt; // いつ取得したかの記録（分析に便利）

    public PostSearchResults() {}
    public PostSearchResults(int searchWordsId, int postId, LocalDateTime fetchedAt) {
        this.searchWordsId = searchWordsId;
        this.postId = postId;
        this.fetchedAt = fetchedAt;
    }
    // コンストラクタ・Getter/Setter
    public int getSearchWordId() {
        return searchWordsId;
    }
    public void setSearchWordId(int searchWordsId) {
        this.searchWordsId = searchWordsId;
    }
    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }

}

