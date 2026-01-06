package tracker.processor.api_model;

public class ItemFeedJson {
    private PostViewJson post; // postオブジェクト
    // private PostRecordJson record;
    // private AuthorJson author;

    public ItemFeedJson(){}

    public PostViewJson getPost() {return post;}
    public void setPost(PostViewJson post) {this.post = post;}

    // public ItemFeedJson(PostRecordJson post, AuthorJson author){
        
    //     this.post = post;
    //     this.author = author;
    // }

    // public PostRecordJson getPost() {return post;}
    // public void setPost(PostRecordJson post) {this.post = post;}

    // public AuthorJson getAuthor() {return author;}
    // public void setAuthor(AuthorJson author) {this.author = author;}


}
