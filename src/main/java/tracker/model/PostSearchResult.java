package tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

@Entity
@Table(name = "post_search_results")
@IdClass(PostSearchResultPK.class) // PostTagと同様に複合主キー
public class PostSearchResult {
    @Id
    private int searchWordId;
    @Id
    private int postId;
    private LocalDateTime fetchedAt; // いつ取得したかの記録（分析に便利）

    public PostSearchResult(int searchWordId, int postId, LocalDateTime fetchedAt) {
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

}

