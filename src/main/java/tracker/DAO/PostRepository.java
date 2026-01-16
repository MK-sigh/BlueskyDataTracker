package tracker.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tracker.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

    // List<Post> findBySearchWordRelations_SearchWord_Word(String word);
    // List<Post> findAll();
    @Query
    ("SELECT p FROM Post p JOIN p.searchWordRelations psr WHERE psr.searchWord.word = :query")

    List<Post> findPostsByKeyword(@Param("query") String query);
}
    // @Query(value = "SELECT p.*  FROM posts p " +
    //         "JOIN post_search_results psr ON p.id = psr.post_id " +
    //         "JOIN search_words sw ON psr.search_word_id = sw.id " +
    //         "JOIN users u ON p.author_id = u.id " +
    //         "WHERE sw.word = :query " +
    //         "ORDER BY p.created_at DESC", nativeQuery = true)
    // List<Post> findPostsByKeyword(@Param("query") String query);
// }
