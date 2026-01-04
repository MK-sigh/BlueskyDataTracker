package processor.api_model;

public class PostItem {
    private PostModel post;
    private PostAuthor author;

    public PostItem(){}
    public PostItem(PostModel post, PostAuthor author){
        this.post = post;
        this.author = author;
    }

    public PostModel getPost() {return post;}
    public void setPost(PostModel post) {this.post = post;}

    public PostAuthor getAuthor() {return author;}
    public void setAuthor(PostAuthor author) {this.author = author;}


}
