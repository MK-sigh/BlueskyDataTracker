// package DAO;

// import java.io.FileInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
// import java.sql.Timestamp;
// import java.util.Properties;

// import model.Post;

// public class PostDAO {
//     private final String JDBC_URL ="jdbc:postgresql://localhost:5432/bluesky_tracker_db";
//     private final String DB_USER = "bsky_admin_user";
//     private final String DB_PASS;

    // ★コンストラクタでパスワードを読み込み、フィールドを初期化する
//     public PostDAO() {
//         Properties properties = new Properties();
//         String tempPass = null; // 一時的な変数
//         try (InputStream is = new FileInputStream("application.properties")) {
//             properties.load(is);
//             // ★正しいキー名（config.propertiesに記述するキー）を指定
//             tempPass = properties.getProperty("db.password");
//         } catch (IOException e) {
//             System.err.println("設定ファイルの読み込みに失敗しました。");
//             e.printStackTrace();
//             // 接続に失敗した場合はランタイム例外をスローすることも考慮
//             // throw new RuntimeException("DB設定ファイルの読み込みに失敗しました", e);
//         }
//         // コンストラクタでfinal変数を初期化
//         this.DB_PASS = tempPass;
//      }

//     public void insertPost(Post post){
//         // nullチェック：パスワードが設定できていない場合は処理を中断
//         if (DB_PASS == null) {
//             System.err.println("DBパスワードが設定されていません。処理を中断します。");
//             return;
//         }
//         String sql = "INSERT INTO posts (text, created_at, author_id) VALUES (?, ?, ?)";

//         //データベースに接続
//         try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
//             PreparedStatement pStmt = conn.prepareStatement(sql);
//             pStmt.setString(1, post.getText());
//             // ★時刻型変換の修正: ZonedDateTime -> Instant -> Timestamp
//             Timestamp timestamp = Timestamp.from(post.getCreated_at().toInstant());
//             pStmt.setTimestamp(2, timestamp);
//             pStmt.setInt(3, post.getAuthor_id());
//             pStmt.executeUpdate();
//         } catch (SQLException e){
//             e.printStackTrace();
//         }
//     }
// }