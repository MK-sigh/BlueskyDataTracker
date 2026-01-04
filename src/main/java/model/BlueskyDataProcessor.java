package model;
import DAO.PostDAO;
import DAO.UserDAO;

public class BlueskyDataProcessor {
    private UserDAO userDao;
    private PostDAO postDao;
    
    public BlueskyDataProcessor(UserDAO userDao, PostDAO postDao){
        userDao = this.userDao;
        postDao = this.postDao;
    }

    public void processFeed (String jsonText){}

}
