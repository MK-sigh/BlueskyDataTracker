package tracker.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BlueskyApiClient {
    
    // 取得したいAPIエンドポイント
    private static final String API_URL =
    "https://api.bsky.app/xrpc/app.bsky.feed.searchposts?q={q}&limit={limit}";

    // HTTPクライアント
    private final RestClient restClient;

    // コンストラクタ
    public BlueskyApiClient(RestClient restClient){this.restClient = restClient;}

        /**
     * 指定されたキーワードとカーソルでBluesky APIから投稿を取得します
     * @param query 検索キーワード（例: #エンジニア）
     * @param cursor 次のページへのポインタ（初回はnull）
     * @return APIからのレスポンスJSON
     */

    public String fetchFeedJson(String query, String cursor) {

        int limit = 100;
        
        String url = API_URL;
        if (cursor != null) {
            url += "&cursor=" + cursor;
        }
        //外部のAPI（API_URL）に対してリクエストを送り、
        // 返ってきたデータ（JSONなど）を文字列として取得する
        return restClient.get()
            .uri(url, query, limit)
            .retrieve()
            .body(String.class);
    }

}

