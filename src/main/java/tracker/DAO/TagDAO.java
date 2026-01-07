package tracker.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tracker.model.Tag;

@Repository
public interface TagDAO extends JpaRepository<Tag, Integer> {
    // データベース操作（CRUD操作）を簡単に行うためのインターフェース
    // JpaRepository<扱うエンティティ, 主キーの型> を継承する
    // 文字列（タグ名）からTagエンティティを探すメソッド
    // SQL: SELECT * FROM tags WHERE tag = ?
    Optional<Tag> findByTag(String tag);
}