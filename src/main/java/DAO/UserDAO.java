package DAO;
import java.util.Optional;

import model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository <User, Integer> {
// Spring Data の作法: 結果が 0件または 1件であるため、戻り値の型には Optional<User> を使用します。
// メソッド名のルール: findBy + [カラム名]
    Optional<User> findByDid(String did);


    
}

// Spring Data JPA のリポジトリでメソッドを定義する際、戻り値を Optional<T> にしておくと、
// ライブラリ側が自動で以下の処理をしてくれます。
// 検索結果が 1件 → Optional.of(実体) を返す。
// 検索結果が 0件 → Optional.empty() を返す。
// 検索結果が 2件以上 → 期待に反するため、自動的に例外（IncorrectResultSizeDataAccessException）を投げて
// 異常を知らせてくれる。
