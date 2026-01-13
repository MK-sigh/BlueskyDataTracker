package tracker.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tracker.model.PostSearchResult;
import tracker.model.PostSearchResultPK;

@Repository
public interface PostSearchResultDAO extends JpaRepository <PostSearchResult, PostSearchResultPK>{

}
