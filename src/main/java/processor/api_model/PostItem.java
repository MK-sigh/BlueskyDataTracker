package processor.api_model;

public class PostItem {
    private PostModel post;
    private PostAuthor author;

    public PostItem(){}
    public PostItem(PostModel post, PostAuthor author){
        this.post = post;
        this.author = author;
    }

}
