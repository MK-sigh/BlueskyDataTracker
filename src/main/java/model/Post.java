package model;

public class Post {
    private int id; // SERIAL PRIMARY KEY に対応
    private String text; // TEXT NOT NULL に対応
    private java.time.ZonedDateTime created_at; // TIMESTAMP WITH TIME ZONE NOT NULL に対応
    private int author_id; // INTEGER (外部キー) に対応

    public Post(){};
    public Post(int id, String text, java.time.ZonedDateTime created_at, int author_id){
        id = this.id;
        text = this.text;
        created_at = this.created_at;
        author_id = this.author_id;
    }

    public int getId(){return id;}
    public String getText(){return text;}
    public java.time.ZonedDateTime getCreated_at(){return created_at;}
    public int getAuthor_id(){return author_id;}


}
