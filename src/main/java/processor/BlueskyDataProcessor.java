package processor;
import DAO.PostDAO;
import DAO.UserDAO;
import org.springframework.stereotype.Service; // Spring Bootにコンポーネントであることを教える
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

@Service // このクラスをSpringが管理するコンポーネントとしてマーク
public class BlueskyDataProcessor {
    private final UserDAO userDao;
    private final PostDAO postDao;
    // Serviceクラスなどの部品（DAOやRepository）にだけ final を強く推奨
    // 理由①：コンストラクタ注入を強制するため
    // Javaで final をつけたフィールドは、コンストラクタが終わるまでに必ず値を入れなければならないというルールがあります。
    // 理由②：実行時の安全（不変性）の確保

    public BlueskyDataProcessor(UserDAO userDao, PostDAO postDao){
        this.userDao = userDao;
        this.postDao = postDao;
    }

    public void processFeed (String jsonText){}

}
