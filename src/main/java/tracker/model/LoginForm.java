package tracker.model;
import jakarta.validation.constraints.NotBlank;

//データ転送オブジェクト（DTO）
// @Getter @Setter
public class LoginForm {

    @NotBlank(message = "データソースは必須です")
    private String dataSource;  // データベースURL
    
    @NotBlank(message = "ユーザー名は必須です")
    private String name;     // DBユーザー名
    
    @NotBlank(message = "パスワードは必須です")
    private String pass;     // DBパスワード


    public String getDataSource() {
        return dataSource;
    }
    public String getName() {
        return name;
    }
    public String getPass() {
        return pass;
    }
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

}
