package tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tracker.DAO.PostRepository;
import tracker.client.BlueskyApiClient;
import tracker.model.Post;
import tracker.processor.BlueskyDataProcessor;


@RestController
@RequestMapping("/api/v1/collector")
public class CollectorController {
    private final BlueskyApiClient apiClient;
    private final BlueskyDataProcessor dataProcessor;
    private final PostRepository postRepository;

    public CollectorController
    (BlueskyApiClient apiClient, BlueskyDataProcessor dataProcessor, PostRepository postRepository) {
        this.apiClient = apiClient;
        this.dataProcessor = dataProcessor;
        this.postRepository = postRepository;
    }

    // Pythonから POST /api/v1/collector/run?q=Python で呼び出す
    @PostMapping("/run")
    public ResponseEntity<String> runManualCollection(@RequestParam("q") String query) {
        // 単発実行（カーソルなしで最新から取得）
        query = "#"+query;
        String jsonstr = apiClient.fetchFeedJson(query, null);
        dataProcessor.processFeed(jsonstr, query);
        
        return ResponseEntity.ok("キーワード '" + query + "' の収集を1回実行しました。");
    }

    // --- 追加する検索用メソッド ---
    // GET /api/v1/collector/search?query=Python で呼び出し
    @GetMapping("/search")
    public List<Post> searchPostsInDB(@RequestParam("q") String query) {
        // DBからキーワードに紐づく投稿リストを取得して返す
        // もし query に既に # が付いていなければ付ける
        String searchTerm = query.startsWith("#") ? query : "#" + query;
        return postRepository.findPostsByKeyword(searchTerm);
       
    }
}
