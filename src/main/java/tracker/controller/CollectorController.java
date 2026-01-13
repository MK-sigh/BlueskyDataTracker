package tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tracker.client.BlueskyApiClient;
import tracker.processor.BlueskyDataProcessor;


@RestController
@RequestMapping("/api/v1/collector")
public class CollectorController {
    private final BlueskyApiClient apiClient;
    private final BlueskyDataProcessor dataProcessor;

    public CollectorController(BlueskyApiClient apiClient, BlueskyDataProcessor dataProcessor) {
        this.apiClient = apiClient;
        this.dataProcessor = dataProcessor;
    }

    // Pythonから POST /api/v1/collector/run?q=Python で呼び出す
    @PostMapping("/run")
    public ResponseEntity<String> runManualCollection(@RequestParam String query) {
        // 単発実行（カーソルなしで最新から取得）
        query = "#"+query;
        String jsonstr = apiClient.fetchFeedJson(query, null);
        dataProcessor.processFeed(jsonstr);
        
        return ResponseEntity.ok("キーワード '" + query + "' の収集を1回実行しました。");
    }
}
