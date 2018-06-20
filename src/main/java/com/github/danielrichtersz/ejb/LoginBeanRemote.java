package ejb;
import dto.LoginDTO;
import entity.User;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.ValidationException;

public interface LoginBeanRemote {
    LoginDTO login(String username, String password) throws ValidationException;

    User validateEmail(String validationCode);

}
