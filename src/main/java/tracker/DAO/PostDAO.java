package tracker.DAO;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
// CrudRepositoryをインポート
// save(entity) : データの保存（新規作成・更新）
// findById(id) : IDによる1件検索
// findAll() : 全件取得
// count() : データ件数の取得
// deleteById(id) : IDを指定して削除
import tracker.model.Post;

public interface PostDAO extends CrudRepository<Post, Integer> {
    
    Optional<Post>  findByUri(String uri);

}
