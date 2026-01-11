package tracker.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tracker.model.Tag;

@Repository
public interface TagDAO extends JpaRepository<Tag, Integer> {
    // 文字列（タグ名）からTagエンティティを探すメソッド
    // SQL: SELECT * FROM tags WHERE tag = ?
    Optional<Tag> findByTag(String tag);
}