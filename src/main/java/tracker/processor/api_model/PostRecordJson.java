package tracker.processor.api_model;

import java.util.List;

public class PostRecordJson {
    private String text;
    private String createdAt; // JSON上の型に合わせてStringで受けるのが無難
    private List<String> langs;

    // Jacksonがパースできるように、デフォルトコンストラクタとゲッター/セッター（またはpublicフィールド）が必要です
    public PostRecordJson() {}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public String getCreatedAt() {return createdAt;}
    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}

    public List<String> getLangs() {return langs;}
    public void setLangs(List<String> langs) {this.langs = langs;}


}
