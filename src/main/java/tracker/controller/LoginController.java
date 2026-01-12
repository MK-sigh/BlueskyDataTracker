package tracker.controller;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tracker.model.LoginForm;
import tracker.service.AuthService;


@Controller
public class LoginController {
    @Autowired //依存性注入
    private AuthService service;

    @PostMapping("/login")
    public String loginCheck (@ModelAttribute  LoginForm form, Model model, HttpSession session){

        String database = form.getDatabase();
        String name = form.getName();
        String pass = form.getPass();

        boolean isOK = service.check(database, name, pass);
    
        if (isOK){
            session.setAttribute("database", database);
            session.setAttribute("loginUser", name);
            return "home";
        } else {
            // 3. 失敗した理由を Model に入れて、画面に送る
            model.addAttribute("errorMessage", "名前かパスワードが違います");
            return "index"; // ログイン画面に戻る（画面側で errorMessage を表示できる）
        }
    }
}
