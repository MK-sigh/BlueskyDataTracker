package tracker.model;

import java.io.Serializable;
import java.util.Objects;

// public record PostTagPK(int postId, int tagId) implements Serializable {}
// これだけで equals(), hashCode(), Getter, コンストラクタがすべて揃います

public class PostTagPK implements Serializable {
    // JPAでは、複合主キーとして使うクラスは Serializable を実装していなければならないという厳格なルールがあります。
    // 中身にメソッドを実装する必要はありません。これを 「マーカー・インターフェース」 と呼びます。
    // Javaの実行環境（JVM）は、この目印がついているクラスを見て、
    // 「このクラスはバラバラにしてデータ列に変換してもいいんだな」と判断します。
    // もしこの目印がないクラスを無理やり保存しようとすると、
    // NotSerializableException というエラーが出てプログラムが止まってしまいます。
    
    private static final long serialVersionUID = 1L;
    // 「データのバージョン番号」です。クラスの内容（例えばフィールドの追加など）を書き換えた際、
    // 古いデータと新しいデータが混ざって復元に失敗するのを防ぐための「合言葉」のような役割を果たします。

    // 主キーを構成するカラム名と型を合わせる
    private int postId;
    private int tagId;

    public PostTagPK() {}

    // equals() と hashCode() のオーバーライドが必須！
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostTagPK that = (PostTagPK) o;
        return postId == that.postId && tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, tagId); //2つのIDを混ぜ合わせて「この組み合わせ専用の番号」を発行する
    }

    // Getter/Setter (省略可だが、IDEで自動生成推奨)
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public int getTagId() { return tagId; }
    public void setTagId(int tagId) { this.tagId = tagId; }



}