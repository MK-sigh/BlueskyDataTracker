package tracker.processor.api_model;

public class ItemFeedJson {
    private PostRecordJson post;
    private AuthorJson author;

    public ItemFeedJson(){}
    public ItemFeedJson(PostRecordJson post, AuthorJson author){
        this.post = post;
        this.author = author;
    }

    public PostRecordJson getPost() {return post;}
    public void setPost(PostRecordJson post) {this.post = post;}

    public AuthorJson getAuthor() {return author;}
    public void setAuthor(AuthorJson author) {this.author = author;}


}
