package tracker.service;

import org.springframework.stereotype.Service;
import tracker.DAO.AdminUserDAO;

@Service
public class AuthService {
    private AdminUserDAO dao;
    public AuthService(AdminUserDAO dao) {
        this.dao = dao;
    }

    public boolean check(String dataSource, String name, String pass){
        return true;
        // return dao.findByDataSourceAndName(dataSource, name)
        //         .map(user -> user.getPass().equals(pass))
        //         .orElse(false);

    }
}
