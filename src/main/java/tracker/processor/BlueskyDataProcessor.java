package tracker.processor;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tracker.DAO.PostDAO;
import tracker.DAO.UserDAO;
import tracker.model.Post;
import tracker.model.User;
import tracker.processor.api_model.FeedResponse;
import tracker.processor.api_model.ItemFeedJson;
import tracker.processor.api_model.PostRecordJson;
import tracker.processor.api_model.PostViewJson;

@Service
public class BlueskyDataProcessor {
    private final UserDAO userDao;
    private final PostDAO postDao;
    private final ObjectMapper objectMapper;
    
    public BlueskyDataProcessor(UserDAO userDao, PostDAO postDao, ObjectMapper objectMapper){
        this.userDao = userDao;
        this.postDao = postDao;
        this.objectMapper = objectMapper;
    }
    
    public void processFeed (String jsonText){
        try{
            User authorUser;
            
            // 1. JSONパース
            FeedResponse response = objectMapper.readValue(jsonText, FeedResponse.class);
            
            // 2. 投稿リストのループ処理
            if (response.getFeed() == null) return; // feedが空の場合のガード

            for (ItemFeedJson item : response.getFeed()) {
                
                // ★階層の整理: Item -> PostView -> (Author, Record)
                PostViewJson postView = item.getPost(); // ここで "post" (View) を取得
                if (postView == null) continue;

                // ユーザー情報の抽出（PostViewJsonの中にAuthorがいる）
                if (postView.getAuthor() == null) continue;
                String did = postView.getAuthor().getDid();
                String handle = postView.getAuthor().getHandle();
                String displayName = postView.getAuthor().getDisplayName();
                String createdAccountAt = postView.getAuthor().getCreatedAt();

                // ポストの中身（Record）の抽出
                PostRecordJson record = postView.getRecord(); // ★先ほど名前を変えたメソッド
                if (record == null) continue;

                String text = record.getText();
                String createdAt = record.getCreatedAt();
                List<String> langs = record.getLangs();
                // labelは今Recordに定義がないようなので一旦スキップ、必要ならPostRecordJsonに追加

                // ポストのメタ情報（Viewにある情報）
                String uri = postView.getUri();
                String cid = postView.getCid();
                
                // indexedAtはViewにある場合とRecordにある場合がありますが、一旦Viewになければ無視
                String indexedAt = postView.getIndexedAt();
                int replyCount = postView.getReplyCount();
                int repostCount = postView.getRepostCount();
                int likeCount = postView.getLikeCount();
                int bookmarkCount = postView.getBookmarkCount();
                int quoteCount = postView.getQuoteCount();

                // --- ここからDB保存処理 ---

                // 1. ユーザーの処理
                Optional <User> ExistingUser = userDao.findByDid(did);

                if (ExistingUser.isEmpty()){
                    User newUser = new User();
                    newUser.setDid(did);
                    newUser.setHandle(handle);
                    newUser.setDisplayName(displayName);
                    newUser.setCreatedAccountAt(createdAccountAt);
                    newUser.setFollowersCount(1);
                    newUser.setFollowingCount(1);
                    // timestamp型変換が必要ですが、一旦Userエンティティ側で今は設定しないならOK
                    // newUser.setCreatedAt(ZonedDateTime.now());
                    
                    authorUser = userDao.save(newUser);
                } else {
                    authorUser = ExistingUser.get();
                }

                // 2. ポストの処理
                Post newPost = new Post();
                newPost.setUri(uri);
                newPost.setCid(cid);
                newPost.setText(text);
                newPost.setCreatedAt(createdAt); // Stringのまま保存
                newPost.setIndexedAt(indexedAt);
                
                // List<String> をカンマ区切り文字列に変換
                if (langs != null) {
                    newPost.setLanguage(String.join(",", langs));
                } else {
                    newPost.setLanguage("");
                }
                
                // // label処理（今は空文字）
                // newPost.setLabel("");

                newPost.setReplyCount(replyCount);
                newPost.setRepostCount(repostCount);
                newPost.setLikeCount(likeCount);
                newPost.setBookmarkCount(bookmarkCount);
                newPost.setQuoteCount(quoteCount);
                
                newPost.setAuthorId(authorUser.getId()); // ユーザーIDをセット

                // 重複チェック
                Optional<Post> existingPost = postDao.findByUri(uri);
                if (existingPost.isEmpty()) {
                    postDao.save(newPost);
                    System.out.println("Saved post: " + text); // デバッグ用ログ
                } else {
                    // 更新処理などを入れたい場合はここに記述
                    // System.out.println("Skipped duplicate post: " + uri);
                }
            }
        } catch(JacksonException e){
            e.printStackTrace();
        } catch(Exception e) {
            // その他の予期せぬエラーもキャッチしておくと安心
            e.printStackTrace();
        }
    }
}

    // stereotype
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
    
    // objectMapper
    // 「JavaオブジェクトとJSONを相互変換するライブラリ」 です。
    // 1. 主な役割（2つの変換）
    // Jackson（ObjectMapper）の役割は、大きく分けて2つです。
    // シリアライズ（書き出し）:
    // Javaのオブジェクトを JSON文字列 に変換します。
    // 例：Userオブジェクト → {"id": 1, "name": "alice"}
    // デシリアライズ（読み込み）:
    // JSON文字列 を解析して、Javaのオブジェクトに変換します。
    // 例：{"id": 1, "name": "alice"} → Userオブジェクト

//   FeedResponse (全体)
//  └─ feed (List<ItemFeedJson>)
//      └─ ItemFeedJson (1つの要素)
//          └─ post (PostViewJson / DBのViewのようなもの)
//              ├─ author (AuthorJson / 投稿者情報) ★ここにAuthorがいる
//              ├─ uri, cid, likeCount (メタデータ) ★ここにカウント数がある
//              └─ record (PostRecordJson / 生データ)
//                  ├─ text (本文) ★ここに本文がある
//                  └─ createdAt (作成日)