package tracker.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BlueskyApiClient {
    
    // 取得したいAPIエンドポイント
    private static final String API_URL =
    "https://api.bsky.app/xrpc/app.bsky.feed.searchPosts?q={q}&limit={limit}";

    // HTTPクライアント
    private final RestClient restClient;

    // コンストラクタ
    public BlueskyApiClient(RestClient restClient){this.restClient = restClient;}

    public String fetchFeedJson(String cursor) {
        String query = "#エンジニア";
        int limit = 100;
        
        // if (cursor != null) {
        //     uri += "&cursor=" + cursor;
        // }
        //外部のAPI（API_URL）に対して、『50件分データをください』というリクエストを送り、
        // 返ってきたデータ（JSONなど）を文字列として取得する
        return restClient.get()
            .uri(API_URL, query, limit)
            .retrieve()
            .body(String.class);
    }

}

