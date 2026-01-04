package model;

import java.time.ZonedDateTime;
import java.util.List;

public class PostModel {
    ZonedDateTime createdAt;
    List<String> langs;
    String text;
    int replyCount;
    int repostCount;
    int likeCount;

    // Jacksonがパースできるように、デフォルトコンストラクタとゲッター/セッター（またはpublicフィールド）が必要です
    public PostModel() {}
    
}
