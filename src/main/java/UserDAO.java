import java.util.List;

public interface UserDAO {

    User getUser() ;
    List<User> getUsers() ;
    boolean insertUser(User user) ;
    boolean updateUser(User user) ;
    boolean deleteUser(User user) ;

}
