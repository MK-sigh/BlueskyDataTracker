package model;
import DAO.PostDAO;
import DAO.UserDAO;

public class BlueskyDataProcessor {
    private final UserDAO userDao;
    private final PostDAO postDao;
    
    public BlueskyDataProcessor(UserDAO userDao, PostDAO postDao){
        this.userDao = userDao;
        this.postDao = postDao;
    }

    public void processFeed (String jsonText){}

}
