package tracker.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");

    }
}

// public class LoginController extends HttpServlet {
// 	private static final long serialVersionUID = 1L;
       
// 	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
// 			throws ServletException, IOException {
		
// 		//リクエストパラメータの取得
// 		request.setCharacterEncoding("UTF-8");
// 		String name = request.getParameter("name");
// 		String pass = request.getParameter("pass");
//         String data = request.getParameter("dataSource");

    //     //ユーザー名、PWが充たされている場合
	// 	if((name != null && name.length() !=0)
	// 		&& (pass != null && pass.length() !=0)
    //         && (data != null && data.length() !=0)){
	// 		User user = new User(pass,name);
	// 		LoginLogic loginLogic = new LoginLogic();
	// 		//DBに存在するかチェック（PWチェック込み）
	// 		User findUser = loginLogic.find(user);
			
	// 		//ログイン処理
	// 		if (findUser != null) {
	// 			// ログイン成功時：ユーザー情報をセッションに保存
	// 			HttpSession session = request.getSession();
	// 			session.setAttribute("loginUser", findUser);
	// 		}else{
	// 			request.setAttribute("errorMsg", "パスワードが間違っているか、ユーザーが未登録です");
	// 		}
	// 	}else{
	// 		//どちらか入力されていなければエラーメッセージ
	// 		request.setAttribute("errorMsg", "必要項目が未入力です");
	// 	}
	// 	//ログイン結果画面にフォワード
	// 	RequestDispatcher dispatcher = request.getRequestDispatcher
	// 			("WEB-INF/jsp/loginResult.jsp");
	// 	dispatcher.forward(request, response);
	// }

//     }
// }
// @Controller
// public class LoginController {
//     @Autowired //依存性注入
//     private AuthService service;

//     @GetMapping("/")
//     public String index() {
//     return "index";  // /WEB-INF/jsp/index.jsp を表示
//     }

//     @RequestMapping(value = "/login", method = RequestMethod.POST)
//     public String loginCheck (@ModelAttribute  LoginForm form, Model model, HttpSession session){

//         String dataSource = form.getDataSource();
//         String name = form.getName();
//         String pass = form.getPass();

//         boolean isOK = service.check(dataSource, name, pass);
    
//         if (isOK){
//             session.setAttribute("dataSource", dataSource);
//             session.setAttribute("loginUser", name);
//             return "home";
//         } else {
//             // 3. 失敗した理由を Model に入れて、画面に送る
//             model.addAttribute("errorMessage", "名前かパスワードが違います");
//             return "index"; // ログイン画面に戻る（画面側で errorMessage を表示できる）
//         }
//     }
// }
