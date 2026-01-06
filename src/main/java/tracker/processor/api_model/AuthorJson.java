package tracker.processor.api_model;

public class AuthorJson {
    private String did;
    private String handle;
    private String displayName;
    private String createdAt;

    public String getDid() {return did;}
    public void setDid(String did) {this.did = did;}

    public String getHandle() {return handle;}
    public void setHandle(String handle) {this.handle = handle;}

    public String getDisplayName() {return displayName;}
    public void setDisplayName(String displayName) {this.displayName = displayName;}

    public String getCreatedAt() {return createdAt;}
    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}
}

