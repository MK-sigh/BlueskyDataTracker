package tracker.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tracker.model.PostSearchResults;
import tracker.model.PostSearchResultsPK;

@Repository
public interface PostSearchResultsDAO extends JpaRepository <PostSearchResults, PostSearchResultsPK>{

    boolean existsBySearchWordIdAndPostId(Integer searchWordId, Integer postId);
}
