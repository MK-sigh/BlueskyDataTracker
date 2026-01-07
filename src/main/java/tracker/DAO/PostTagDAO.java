package tracker.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tracker.model.PostTag;
import tracker.model.PostTagPK; // 先ほど作った複合主キー・クラス

@Repository
// IDの型が Integer ではなく PostTagPK になる点に注意！
public interface PostTagDAO extends JpaRepository<PostTag, PostTagPK> {
}