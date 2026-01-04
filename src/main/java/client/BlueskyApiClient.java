package client;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BlueskyApiClient {
    
    // 取得したいAPIエンドポイント
    private static final String API_URL = "https://bsky.social/xrpc/app.bsky.feed.getTimeline";
    
    // HTTPクライアント
    private final RestClient restClient;

    // コンストラクタで必要な依存性を注入する（今回は省略）
    public BlueskyApiClient(RestClient restClient){
        this.restClient = restClient;
    }

    // ★このメソッドを定義してください
    // 戻り値は取得したJSON文字列、例外はIOExceptionをスローします。
    public String fetchFeedJson() throws IOException {
        // ここにHTTPリクエストを送るロジックを実装します。
        // ただし、今回は実際の外部通信は行わず、ダミーのJSON文字列を返すことで実装を先に進めます。
        
        // ★ダミーの JSON 文字列を return するコードを記述してください
        // （例: "{\"feed\": [{\"post\": {...}, \"author\": {...}], \"cursor\": \"abc\"}"）
        // 実際のBlueskyのJSON構造に合った、最低限のダミーデータを含めてください。
        
        return "{\r\n" + //
                        "\r\n" + //
                        "  \"feed\": [\r\n" + //
                        "    {\r\n" + //
                        "      \"post\": {\r\n" + //
                        "        \"uri\": \"at://did:plc:example123/app.bsky.feed.post/3lc5j6k7m8n2p\",\r\n" + //
                        "        \"cid\": \"bafyreihdx7o...\",\r\n" + //
                        "        \"author\": {\r\n" + //
                        "          \"did\": \"did:plc:example123\",\r\n" + //
                        "          \"handle\": \"user.bsky.social\",\r\n" + //
                        "          \"displayName\": \"サンプル太郎\",\r\n" + //
                        "          \"avatar\": \"cdn.bsky.app\",\r\n" + //
                        "          \"viewer\": {\r\n" + //
                        "            \"muted\": false,\r\n" + //
                        "            \"blockedBy\": false\r\n" + //
                        "          },\r\n" + //
                        "          \"labels\": []\r\n" + //
                        "        },\r\n" + //
                        "        \"record\": {\r\n" + //
                        "          \"$type\": \"app.bsky.feed.post\",\r\n" + //
                        "          \"createdAt\": \"2026-01-03T16:00:00.000Z\",\r\n" + //
                        "          \"langs\": [\"ja\"],\r\n" + //
                        "          \"text\": \"BlueskyのAPIから取得したダミーデータです。2026年も分散型SNSが活発ですね！\"\r\n" + //
                        "        },\r\n" + //
                        "\r\n" + //
                        "        \"replyCount\": 5,\r\n" + //
                        "        \"repostCount\": 12,\r\n" + //
                        "        \"likeCount\": 42,\r\n" + //
                        "        \"indexedAt\": \"2026-01-03T16:00:05.123Z\",\r\n" + //
                        "        \"viewer\": {\r\n" + //
                        "          \"like\": \"at://did:plc:yourid/app.bsky.feed.like/3lc...\"\r\n" + //
                        "        },\r\n" + //
                        "        \"labels\": []\r\n" + //
                        "      }\r\n" + //
                        "    }\r\n" + //
                        "  ],\r\n" + //
                        "  \"cursor\": \"1704297600000::3lc5j6k7m8n2p\"\r\n" + //
                        "}"; // ここをダミーJSONで置き換える
    }
}
