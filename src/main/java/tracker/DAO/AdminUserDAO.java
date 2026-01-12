package tracker.DAO;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import tracker.model.AdminUser;


public interface AdminUserDAO extends CrudRepository<AdminUser, Integer> {
    
    Optional<AdminUser>  findByName(String name);

    
}
