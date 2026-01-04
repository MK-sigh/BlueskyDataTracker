package processor;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import DAO.PostDAO;
import DAO.UserDAO;
import model.User;
import org.springframework.stereotype.Service; // Spring Bootにコンポーネントであることを教える
import processor.api_model.FeedResponse;
import processor.api_model.PostItemJson;
// Spring Frameworkにおいて「ビジネスロジック（業務処理）」を担当するクラスであることを示すための目印です。
// 1. 主な役割
// ビジネスロジックの記述: 「データの計算」「複数のリポジトリを組み合わせた処理」「外部APIとの連携」など、
// そのアプリの「核」となる処理をここに書きます。
// Bean（ビーン）の自動登録: このアノテーションを付けると、Springが起動時に自動的にクラスを見つけ出し、
// インスタンス化して管理下に置きます。
// これにより、他のクラス（Controllerなど）で @Autowired などを使って呼び出せるようになります。
// 2. どこで使うのか（3層アーキテクチャ）
// 一般的なSpring Bootアプリは、役割ごとに3つの層に分けます。@Service はその真ん中に位置します。
// Controller (@RestController): 画面やAPIの窓口。リクエストを受け取り、Serviceを呼ぶ。
// Service (@Service): ここ！ 窓口から届いたデータを使って、実際の処理（計算や判定など）を行う。
// Repository (@Repository / CrudRepository): データベースとのやり取りだけを行う。
import tools.jackson.databind.ObjectMapper;
// 「JavaオブジェクトとJSONを相互変換するライブラリ」 です。
// 1. 主な役割（2つの変換）
// Jackson（ObjectMapper）の役割は、大きく分けて2つです。
// シリアライズ（書き出し）:
// Javaのオブジェクトを JSON文字列 に変換します。
// 例：Userオブジェクト → {"id": 1, "name": "alice"}
// デシリアライズ（読み込み）:
// JSON文字列 を解析して、Javaのオブジェクトに変換します。
// 例：{"id": 1, "name": "alice"} → Userオブジェクト

@Service // このクラスをSpringが管理するコンポーネントとしてマーク
public class BlueskyDataProcessor {
    private final UserDAO userDao;
    private final PostDAO postDao;
    private final ObjectMapper objectMapper;
    // Serviceクラスなどの部品（DAOやRepository）にだけ final を強く推奨
    // 理由①：コンストラクタ注入を強制するため
    // Javaで final をつけたフィールドは、コンストラクタが終わるまでに必ず値を入れなければならないというルールがあります。
    // 理由②：実行時の安全（不変性）の確保

    public BlueskyDataProcessor(UserDAO userDao, PostDAO postDao, ObjectMapper objectMapper){
        this.userDao = userDao;
        this.postDao = postDao;
        this.objectMapper = objectMapper;
    }


    public void processFeed (String jsonText){
        // ★投稿者のUserエンティティを保持する変数
        User authorUser;

        // 1. JSONパース FeedResponseはPostItemJsonのリストとcursorを持つ
        FeedResponse response = objectMapper.readValue(jsonText, FeedResponse.class);

        // 2. 投稿リストのループ処理 PostItemJsonはRecofdJsonとAuthorJsonを持つ
        for (PostItemJson item : response.getFeed()) {
            // ユーザー情報の抽出
            String did = item.getAuthor().getDid();
            String handle = item.getAuthor().getHandle();
            String displayName = item.getAuthor().getDisplayName();
            //ポスト情報
            ZonedDateTime createdAt = item.getPost().getCreatedAt();
            List<String> langs = item.getPost().getLangs();
            String text = item.getPost().getText();
            int replyCount = item.getPost().getReplyCount();
            int repostCount = item.getPost().getRepostCount();
            int likeCount = item.getPost().getLikeCount();



            // ★ユーザーの存在チェックと格納（UserDAOのメソッドを使って）
            // 最初にUserDAOを使って、このdidのユーザーがDBに存在するかどうかをチェックします。
            Optional <User> ExistingUser = userDao.findByDid(did);

            if (ExistingUser.isEmpty()){ /* UserDAOのメソッドでユーザーが存在しない場合 */
                // 新しいユーザーを作成・格納するロジック
                User newUser = new User();
                newUser.setDid(did);
                newUser.setHandle(handle);
                newUser.setDisplay_name(displayName);
                // TODO: followers_count, following_count, created_at は API の別の場所にあるため、
                // ここでは省略

                // データベースに新しいユーザーを格納し、最新の状態を返す（採番されたidが入る）
                authorUser = userDao.save(newUser);

                // 格納されたUserエンティティからIDを取得
                int id = authorUser.getId();
            }else{
                authorUser = ExistingUser.get();
            }




        }
    }

}
