package tracker.processor;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tracker.DAO.PostDAO;
import tracker.DAO.PostTagDAO;
import tracker.DAO.TagDAO;
import tracker.DAO.UserDAO;
import tracker.model.Post;
import tracker.model.PostTag;
import tracker.model.Tag;
import tracker.model.User;
import tracker.processor.api_model.FeedResponse;
import tracker.processor.api_model.PostRecordJson;
import tracker.processor.api_model.PostViewJson;

@Service
public class BlueskyDataProcessor {
    private final UserDAO userDao;
    private final PostDAO postDao;
    private final TagDAO tagDao;
    private final PostTagDAO postTagDao;
    private final ObjectMapper objectMapper;
    
    public BlueskyDataProcessor(UserDAO userDao, PostDAO postDao,
        TagDAO tagDao, PostTagDAO postTagDao,
         ObjectMapper objectMapper){
        this.userDao = userDao;
        this.postDao = postDao;
        this.tagDao = tagDao; 
        this.postTagDao = postTagDao; 
        this.objectMapper = objectMapper;
    }
    
    public String processFeed (String jsonText){
        try{
            User authorUser;
            // ログを追加して動作確認しやすくします
            System.out.println("JSON Length: " + jsonText.length());
            // 1. JSONパース
            FeedResponse response = objectMapper.readValue(jsonText, FeedResponse.class);
            
            // 2. 投稿リストのループ処理
            if (response.getPosts() == null|| response.getPosts().isEmpty())
                return ""; // feedが空の場合のガード

            System.out.println("Found " + response.getPosts().size() + " posts.");

            for (PostViewJson postView : response.getPosts()) {
                
                // ★階層の整理: PostView -> (Author, Record)
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
                List<Map<String,Object>> facets = record.getFacets();

                // ポストのメタ情報（Viewにある情報）
                String uri = postView.getUri();
                String cid = postView.getCid();
                
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
                    
                    authorUser = userDao.save(newUser);

                } else {
                    authorUser = ExistingUser.get();
                }
                
                // 2. ポストの処理
                Post newPost = new Post();

                // --- ★デバッグ用ログの追加 ---
                System.out.println("--- 💥 DB SAVE DEBUG ---");
                System.out.println("URI: " + newPost.getUri());
                // 文字列内のヌル文字を視覚化するために置換してから出力する
                System.out.println("Text (NUL replaced): " + newPost.getText().replace("\u0000", "null"));
                System.out.println("--- -------------------");
                // -----------------------------

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
                
                newPost.setReplyCount(replyCount);
                newPost.setRepostCount(repostCount);
                newPost.setLikeCount(likeCount);
                newPost.setBookmarkCount(bookmarkCount);
                newPost.setQuoteCount(quoteCount);
                
                newPost.setAuthorId(authorUser.getId()); // ユーザーIDをセット
                
                // 重複チェック
                Optional<Post> existingPost = postDao.findByUri(uri);
                if (existingPost.isEmpty()) {
                    Post savedPost = postDao.save(newPost); // ★saveの戻り値を受け取る(IDが入っている)
                    System.out.println("Saved post: " + text);
                    // ==========================================
                    // タグ保存処理を追加
                    // ==========================================

                    // 1. テキストからタグ文字列のリストを抽出
                    List<String> hashtagList = extractHashtags(facets);

                    for (String tagStr : hashtagList) {
                        // 2. タグマスタ(tagsテーブル)の処理
                        Tag tagEntity;
                        Optional<Tag> existingTag = tagDao.findByTag(tagStr);

                        if (existingTag.isPresent()) { //optionalの中身があるかどうか判定する
                            tagEntity = existingTag.get();
                        } else {
                            // 新しいタグなら保存
                            Tag newTag = new Tag();
                            newTag.setTag(tagStr);
                            tagEntity = tagDao.save(newTag);
                        }

                        // 3. 中間テーブル(post_tags)の処理
                        // PostのIDと、TagのIDを紐付ける
                        PostTag relation = new PostTag(savedPost.getId(), tagEntity.getId());
                        postTagDao.save(relation);
                    }
                    // ==========================================
                } else {
                    // 更新処理などを入れたい場合はここに記述
                    // System.out.println("Skipped duplicate post: " + uri);
                }
            }
            return response.getCursor();
        } catch(JacksonException e){
            e.printStackTrace();
            return "";
        } catch(Exception e) {
            // その他の予期せぬエラーもキャッチしておくと安心
            e.printStackTrace();
            return "";
        }
    }

    // private List<String> extractHashtags(String text) {
    //     if (text == null || text.isBlank()) return List.of(); //「空の固定リスト」を返す(immutable)

    //     return Pattern.compile("#[\\p{L}\\p{N}_]+") //正規表現のコンパイル Stream API
    //             .matcher(text)
    //             .results() // Stream<MatchResult>を取得
    //             .map(match -> match.group())
    //             .map(String::toLowerCase)  
    //             // map：結果を加工する
    //             // ラムダ式：関数の引数 -> 処理の内容
    //             .distinct() // 重複削除
    //             .toList(); // Listに変換
    // }
private List<String> extractHashtags(List<Map<String, Object>> facets) {
    if (facets == null) return List.of();

    List<String> tags = facets.stream()
                        // 1. 各facetから "features"という名前のリストを取り出す
                        .map(facet ->  (List<Map<String, Object>>) facet.get("features"))
                        // 2. nullチェック（念のため）
                        .filter(Objects::nonNull)
                        // 3. List<List<...>> を平坦化して、中の要素（Map）を直接扱えるようにする
                        .flatMap(List::stream)
                        // 4. Mapから "tag" の値を取り出す
                        .map(feature -> (String) feature.get("tag"))
                        // 5. tagが存在するものだけ絞り込む
                        .filter(Objects::nonNull)
                        // 6. 最後にListにまとめる
                        .toList();
    return tags;
}

// text.isEmpty()
// 文字数が 「完全に0」 のときだけ true になります。
// ""（空文字） → true
// " "（スペースのみ） → false（中身があると判定される）
// text.isBlank()（Java 11以降）
// 文字が0、または 「空白（スペース、タブ、全角スペースなど）だけ」 の場合も true になります。

// private List<String> extractHashtags(String text) {
//     List<String> tags = new ArrayList<>();
//     if (text == null || text.isEmpty()) {
//         return tags;  //new ArrayList<>() を返す
//     }

//     // 正規表現のコンパイル
//     // "#" : #で始まる
//     // "[^\\s]+" : 空白文字(\s)以外の文字(^)が1回以上続く(+)
//     // ※日本語対応のため、単純な \w ではなく「空白以外」で判定するのがコツです
//     Pattern pattern = Pattern.compile("#[^\\s]+");
//     Matcher matcher = pattern.matcher(text);

//     // 見つかるたびにループする
//     while (matcher.find()) {  //find:文中を検索しtextが見つかれば true を返す
//         String rawTag = matcher.group(); // group: 見つかった具体的な文字列（例：#Java,）を抜き出す

//         // 末尾の記号（句読点など）を取り除く処理が必要
//         // 例: "こんにちは #Bluesky." -> "#Bluesky." -> "#Bluesky"
//         // ここでは簡単に、英数字・日本語以外の末尾記号を削除する簡易処理を入れます
//         // (厳密にやるならもっと複雑になりますが、まずはこれで十分動きます)
//         String cleanTag = rawTag.replaceAll("[.,!?。、]$", "");
//         // [ ] の中の文字（カンマ、ドット、感嘆符、句読点など）のいずれかが、
//         // $（文字列の末尾）にある場合、
//         // ""（空文字）に置き換えて削除します。
        
//         // 重複を防ぐため、リストになければ追加
//         if (!tags.contains(cleanTag)) {
//             tags.add(cleanTag);
//         }
//     }
//     return tags;
// }

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