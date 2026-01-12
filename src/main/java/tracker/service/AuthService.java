package tracker.service;

import org.springframework.stereotype.Service;
import tracker.DAO.AdminUserDAO;

@Service
public class AuthService {
    private AdminUserDAO dao;
    public AuthService(AdminUserDAO dao) {
        this.dao = dao;
    }

    public boolean check (String name, String pass){
        return dao.findByName(name)
                .map(user -> user.getPass().equals(pass))
                .orElse(false);

    }
}
