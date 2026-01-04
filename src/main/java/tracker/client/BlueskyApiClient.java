package tracker.client;

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
    // 構造をクリーンにし、コメントを完全に排除
return """
{
  "feed": [
    {
      "post": {
        "uri": "at://did:plc:example123/app.bsky.feed.post/3lc5j6k7m8n2p",
        "cid": "bafyreihdx7o...",
        "record": {
          "$type": "app.bsky.feed.post",
          "createdAt": "2026-01-03T16:00:00.000Z",
          "langs": ["ja"],
          "text": "JSONの整形を厳密にしました！"
        },
        "replyCount": 5,
        "repostCount": 12,
        "likeCount": 42,
        "indexedAt": "2026-01-03T16:00:05.123Z",
        "viewer": {
          "like": "at://did:plc:yourid/app.bsky.feed.like/3lc..."
        },
        "labels": []
      },
      "author": {
        "did": "did:plc:example123",
        "handle": "user.bsky.social",
        "displayName": "サンプル太郎",
        "avatar": "cdn.bsky.app"
      }
    }
  ],
  "cursor": "1704297600000::3lc5j6k7m8n2p"
}
"""; // ★ここも重要: }の直後に"""を置き、インデントを入れない
}
}