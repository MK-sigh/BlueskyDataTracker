package DAO;
import model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository <User, Integer> {


    
}
