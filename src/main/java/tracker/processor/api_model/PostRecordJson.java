package tracker.processor.api_model;

import java.util.List;
import java.util.Map;

public class PostRecordJson {
    private String text;
    private String createdAt; // JSON上の型に合わせてStringで受けるのが無難
    private List<String> langs;
    private List<Map<String, Object>> facets;

    // Jacksonがパースできるように、デフォルトコンストラクタとゲッター/セッター（またはpublicフィールド）が必要です
    public PostRecordJson() {}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public String getCreatedAt() {return createdAt;}
    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}

    public List<String> getLangs() {return langs;}
    public void setLangs(List<String> langs) {this.langs = langs;}

    public List<Map<String, Object>> getFacets(){return facets;}
    public void setFacets(List<Map<String, Object>> facets){this.facets = facets;}


}
