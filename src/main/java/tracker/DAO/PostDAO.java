package tracker.DAO;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
// CrudRepositoryをインポート
// データベースの基本操作（CRUD）を自動化するためのインターフェースです。
// これを継承するだけで、SQLを書かなくてもデータの保存、取得、削除などの機能が使えるようになります。
// 型安全: ジェネリクス（<User, Long>など）を使うため、データの型が保証されます。
// UserRepository インスタンスを通じて、以下のメソッドがすぐに呼べます。
// save(entity) : データの保存（新規作成・更新）
// findById(id) : IDによる1件検索
// findAll() : 全件取得
// count() : データ件数の取得
// deleteById(id) : IDを指定して削除
import tracker.model.Post;

// ★Postエンティティと主キーの型（Integer）を指定して継承するだけでOK
public interface PostDAO extends CrudRepository<Post, Integer> {
    
    // insertPost() の実装は不要
    // 必要なメソッドはすべて CrudRepository が自動で提供してくれる。
    Optional<Post>  findByUri(String uri);

}
