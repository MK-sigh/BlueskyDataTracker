package processor.api_model;

public class RecordItemJson {
    private PostJson post;
    private AuthorJson author;

    public RecordItemJson(){}
    public RecordItemJson(PostJson post, AuthorJson author){
        this.post = post;
        this.author = author;
    }

    public PostJson getPost() {return post;}
    public void setPost(PostJson post) {this.post = post;}

    public AuthorJson getAuthor() {return author;}
    public void setAuthor(AuthorJson author) {this.author = author;}


}
