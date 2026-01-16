package tracker.processor;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tracker.DAO.PostDAO;
import tracker.DAO.PostSearchResultsDAO;
import tracker.DAO.PostTagDAO;
import tracker.DAO.SearchWordDAO;
import tracker.DAO.TagDAO;
import tracker.DAO.UserDAO;
import tracker.model.Post;
import tracker.model.PostSearchResults;
import tracker.model.PostTag;
import tracker.model.SearchWord;
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
    private final SearchWordDAO searchWordDAO;
    private final PostSearchResultsDAO postSearchResultsDAO;
    private final ObjectMapper objectMapper;
    
    public BlueskyDataProcessor(UserDAO userDao, PostDAO postDao,
        TagDAO tagDao, PostTagDAO postTagDao, SearchWordDAO searchWordDAO,
        PostSearchResultsDAO postSearchResultsDAO, ObjectMapper objectMapper){
        this.userDao = userDao;
        this.postDao = postDao;
        this.tagDao = tagDao; 
        this.postTagDao = postTagDao; 
        this.searchWordDAO = searchWordDAO;
        this.postSearchResultsDAO = postSearchResultsDAO;
        this.objectMapper = objectMapper;
    }
    
    @Transactional
    public String processFeed (String jsonText, String query){
        // 検索語テーブル(search_words)のIDを確定させる

        SearchWord newWord = searchWordDAO.findByWord(query)
        .orElseGet(() -> {
            SearchWord word = new SearchWord();
            word.setWord(query); // クエリをセットする
            return searchWordDAO.save(word); // 保存してその結果を返す
        });
        
        try{
            User authorUser;
            // 1. JSONパース
            FeedResponse response = objectMapper.readValue(jsonText, FeedResponse.class);
            
            // 2. 投稿リストのループ処理
            if (response.getPosts() == null|| response.getPosts().isEmpty())
                return ""; // feedが空の場合のガード

            System.out.println("Found " + response.getPosts().size() + " posts.");//取得ログ件数

            for (PostViewJson postView : response.getPosts()) {
                if (postView == null) continue;
                // ★階層の整理: PostView -> (Author, Record)
                // ユーザー情報の抽出（PostViewJsonの中にAuthorがいる）
                if (postView.getAuthor() == null) continue;
                String did = postView.getAuthor().getDid();
                String handle = postView.getAuthor().getHandle();
                String displayName = postView.getAuthor().getDisplayName();
                String createdAccountAt = postView.getAuthor().getCreatedAt();

                // ポストの中身（Record）の抽出
                PostRecordJson record = postView.getRecord();
                if (record == null ||
                    record.getFacets() == null ||
                    record.getFacets().isEmpty() ||
                    record.getText() == null ||
                    record.getText().isBlank()) continue;

                String text = record.getText();

                // if (!text.contains("#" + searchTag)) continue;


                String createdAt = record.getCreatedAt();
                List<String> langs = record.getLangs();
                List<Map<String,Object>> facets = record.getFacets();

                // ポストのメタ情報（Viewにある情報）
                String uri = postView.getUri();
                System.out.println(uri);
                String cid = postView.getCid();
                System.out.println(cid);

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
                
                newPost.setReplyCount(replyCount);
                newPost.setRepostCount(repostCount);
                newPost.setLikeCount(likeCount);
                newPost.setBookmarkCount(bookmarkCount);
                newPost.setQuoteCount(quoteCount);
                
                newPost.setAuthorId(authorUser.getId()); // ユーザーIDをセット
                
                // 重複チェック
                Optional<Post> existingPost = postDao.findByUri(uri);
                Post savedPost; // 紐付け対象のポストを保持する変数
                if (existingPost.isEmpty()) {
                    savedPost = postDao.save(newPost); // ★saveの戻り値を受け取る(IDが入っている)
                    System.out.println("Saved post: " + text);

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
                    savedPost = existingPost.get();
                }
                //検索結果としての紐付けを保存
                LocalDateTime fetchedAt = LocalDateTime.now(); // 現在時刻をセット
                PostSearchResults result =
                    new PostSearchResults(newWord.getId(), savedPost.getId(), fetchedAt);
                    // 保存前に、すでに同じ「キーワードID」と「ポストID」の組み合わせがないか確認
                    boolean exists =
                        postSearchResultsDAO.existsBySearchWordIdAndPostId
                            (newWord.getId(), savedPost.getId());
                    if (!exists) {
                        postSearchResultsDAO.save(result);
                    } else {
                        System.out.println("Skip: Already linked.");
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
}