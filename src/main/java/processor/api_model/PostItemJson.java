package processor.api_model;

public class PostItemJson {
    private RecordJson post;
    private AuthorJson author;

    public PostItemJson(){}
    public PostItemJson(RecordJson post, AuthorJson author){
        this.post = post;
        this.author = author;
    }

    public RecordJson getPost() {return post;}
    public void setPost(RecordJson post) {this.post = post;}

    public AuthorJson getAuthor() {return author;}
    public void setAuthor(AuthorJson author) {this.author = author;}


}
