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
        int limit = 2;
        
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

//     // 戻り値は取得したJSON文字列、例外はIOExceptionをスローします。
//     public String fetchFeedJson() throws IOException {
//     // 構造をクリーンにし、コメントを完全に排除
// return """
// {
//     "feed":[{
//         "post":{
//             "uri":"at://did:plc:qqqq/app.bsky.feed.post/mmmmmm",
//             "cid":"yesman",
//             "author":{
//                 "did":"did:plc:qqqq",
//                 "handle":"bbbb.bsky.social",
//                 "displayName":"Taro Sato",
//                 "avatar":"https://cdn.bsky.app/img/avatar/plain/did:plc:qqqq/ggggg@jpeg",
//                 "associated":{"chat":{"allowIncoming":"all"},"activitySubscription":{"allowSubscriptions":"followers"}},
//                 "labels":[],
//                 "createdAt":"2023-06-27T02:12:21.916Z"
//             },
//             "record":{
//                 "$type":"app.bsky.feed.post",
//                 "createdAt":"2026-01-05T00:36:06.836Z",
//                 "embed":{"$type":"app.bsky.embed.video","aspectRatio":{"height":480,"width":868},"video":{"$type":"blob","ref":{"$link":"linlinlinlinlin"},"mimeType":"video/mp4","size":728866}},
//                 "langs":["en"],
//                 "reply":{
//                     "parent":{"cid":"baybaybay","uri":"at://did:plc:qqqq/app.bsky.feed.post/vvvvvv"},
//                     "root":{"cid":"rererere","uri":"at://did:plc:qqqq/app.bsky.feed.post/hhhhhhhh"}
//                 },
//                 "text":"HEHEHE #endregion #こんにちは。"
//             },
//             "embed":{
//                 "$type":"app.bsky.embed.video#view",
//                 "cid":"linlinlinlinlin",
//                 "playlist":"https://video.bsky.app/watch/did%monmon%pepepe/linlinlinlinlin/playlist.zzzz",
//                 "nananana":"https://video.bsky.app/watch/did%monmon%pepepe/linlinlinlinlin/nananana.jpg",
//                 "aspectRatio":{"height":480,"width":868}
//             },
//             "bookmarkCount":0,
//             "replyCount":1,
//             "repostCount":0,
//             "likeCount":11,
//             "quoteCount":0,
//             "indexedAt":"2026-01-05T00:36:08.131Z",
//             "labels":[]
//         },
//         "reply":{
//             "root":{
//                 "uri":"at://did:plc:qqqq/app.bsky.feed.post/hhhhhhhh",
//                 "cid":"rererere",
//                 "author":{
//                     "did":"did:plc:qqqq",
//                     "handle":"bbbb.bsky.social",
//                     "displayName":"Taro Sato",
//                     "avatar":"https://cdn.bsky.app/img/avatar/plain/did:plc:qqqq/ggggg@jpeg",
//                     "associated":{
//                         "chat":{"allowIncoming":"all"},
//                         "activitySubscription":{"allowSubscriptions":"followers"}
//                     },
//                     "labels":[],
//                     "createdAt":"2023-06-27T02:12:21.916Z"
//                 },
//                 "record":{
//                     "$type":"app.bsky.feed.post",
//                     "createdAt":"2026-01-05T00:21:57.393Z",
//                     "embed":{
//                         "$type":"app.bsky.embed.video",
//                         "aspectRatio":{"height":720,"width":720},
//                         "video":{"$type":"blob","ref":{"$link":"pppppppp"},"mimeType":"video/mp4","size":1392125}
//                     },
//                     "langs":["en"],
//                     "text":"Hello"
//                 },
//                 "embed":{
//                     "$type":"app.bsky.embed.video#view","cid":"pppppppp",
//                     "playlist":"https://video.bsky.app/watch/did%monmon%pepepe/pppppppp/playlist.zzzz",
//                     "nananana":"https://video.bsky.app/watch/did%monmon%pepepe/pppppppp/nananana.jpg",
//                     "aspectRatio":{"height":720,"width":720}
//                 },
//                 "bookmarkCount":2,
//                 "replyCount":2,
//                 "repostCount":4,
//                 "likeCount":43,
//                 "quoteCount":0,
//                 "indexedAt":"2026-01-05T00:21:58.728Z",
//                 "labels":[],
//                 "$type":"app.bsky.feed.defs#postView"
//             },
//             "parent":{
//                 "uri":"at://did:plc:qqqq/app.bsky.feed.post/vvvvvv",
//                 "cid":"baybaybay",
//                 "author":{
//                     "did":"did:plc:qqqq",
//                     "handle":"bbbb.bsky.social",
//                     "displayName":"Taro Sato",
//                     "avatar":"https://cdn.bsky.app/img/avatar/plain/did:plc:qqqq/ggggg@jpeg",
//                     "associated":{"chat":{"allowIncoming":"all"},"activitySubscription":{"allowSubscriptions":"followers"}},
//                     "labels":[],
//                     "createdAt":"2023-06-27T02:12:21.916Z"
//                 },
//                 "record":{
//                     "$type":"app.bsky.feed.post","createdAt":"2026-01-05T00:35:54.714Z",
//                     "embed":{"$type":"app.bsky.embed.record","record":{"cid":"sasasasa","uri":"at://did:plc:qqqq/app.bsky.feed.post/sksksk"}},
//                     "facets":[{
//                         "features":[{
//                             "$type":"app.bsky.richtext.facet#link",
//                             "uri":"https://bsky.app/profile/did:plc:qqqq/post/sksksk"
//                         }],"index":{"byteEnd":91,"byteStart":67}
//                     }],
//                     "langs":["en"],
//                     "reply":{
//                         "parent":{"cid":"rererere","uri":"at://did:plc:qqqq/app.bsky.feed.post/hhhhhhhh"},
//                         "root":{"cid":"rererere","uri":"at://did:plc:qqqq/app.bsky.feed.post/hhhhhhhh"}
//                     },
//                     "text":"SOSOSO"
//                 },
//                 "embed":{
//                     "$type":"app.bsky.embed.record#view",
//                     "record":{
//                         "$type":"app.bsky.embed.record#viewRecord",
//                         "uri":"at://did:plc:qqqq/app.bsky.feed.post/sksksk",
//                         "cid":"sasasasa",
//                         "author":{
//                             "did":"did:plc:qqqq",
//                             "handle":"bbbb.bsky.social",
//                             "displayName":"Taro Sato",
//                             "avatar":"https://cdn.bsky.app/img/avatar/plain/did:plc:qqqq/ggggg@jpeg",
//                             "associated":{
//                                 "chat":{"allowIncoming":"all"},
//                                 "activitySubscription":{"allowSubscriptions":"followers"}
//                             },
//                             "labels":[],
//                             "createdAt":"2023-06-27T02:12:21.916Z"
//                         },
//                         "value":{
//                             "$type":"app.bsky.feed.post",
//                             "createdAt":"2026-01-05T00:33:39.040Z",
//                             "embed":{
//                                 "$type":"app.bsky.embed.video",
//                                 "alt":"hahaha",
//                                 "aspectRatio":{"height":480,"width":648},
//                                 "video":{"$type":"blob","ref":{"$link":"242424"},"mimeType":"video/mp4","size":156691}
//                             },
//                             "langs":["en"],
//                             "reply":{
//                                 "parent":{"cid":"55555","uri":"at://did:plc:77777/app.bsky.feed.post/8888"},
//                                 "root":{"cid":"55555","uri":"at://did:plc:77777/app.bsky.feed.post/8888"}
//                             },
//                             "text":"NICE!"
//                         },
//                         "labels":[],
//                         "likeCount":3,
//                         "replyCount":0,
//                         "repostCount":0,
//                         "quoteCount":1,
//                         "indexedAt":"2026-01-05T00:33:40.931Z",
//                         "embeds":[{
//                             "$type":"app.bsky.embed.video#view",
//                             "cid":"242424",
//                             "playlist":"https://video.bsky.app/watch/did%monmon%pepepe/242424/playlist.zzzz",
//                             "nananana":"https://video.bsky.app/watch/did%monmon%pepepe/242424/nananana.jpg",
//                             "alt":"hahaha",
//                             "aspectRatio":{"height":480,"width":648}
//                         }]
//                     }
//                 },
//                 "bookmarkCount":0,
//                 "replyCount":1,
//                 "repostCount":0,
//                 "likeCount":5,
//                 "quoteCount":0,
//                 "indexedAt":"2026-01-05T00:35:56.036Z",
//                 "labels":[],
//                 "$type":"app.bsky.feed.defs#postView"
//             },
//             "grandparentAuthor":{
//                 "did":"did:plc:qqqq",
//                 "handle":"bbbb.bsky.social",
//                 "displayName":"Taro Sato",
//                 "avatar":"https://cdn.bsky.app/img/avatar/plain/did:plc:qqqq/ggggg@jpeg",
//                 "associated":{"chat":{"allowIncoming":"all"},"activitySubscription":{"allowSubscriptions":"followers"}},
//                 "labels":[],
//                 "createdAt":"2023-06-27T02:12:21.916Z"
//             }
//         }
//     }]
// }
// """; // ★ここも重要: }の直後に"""を置き、インデントを入れない
// }
// }