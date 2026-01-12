package tracker.model;

import lombok.Getter;
import lombok.Setter;

//データ転送オブジェクト（DTO）
@Getter @Setter
public class LoginForm {

    private String name;
    private String pass;

}
