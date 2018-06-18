package ejb;
import services.impl.LoginServiceImpl;
import entity.User;
import services.interfaces.LoginService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.ValidationException;

@Path("/userlr")
@ApplicationScoped
public class LoginBean implements LoginBeanRemote {

    @Inject
    LoginService loginService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    @Override
    public User login(@FormParam("username") String email, @FormParam("password") String password) throws ValidationException {
        return loginService.login(email, password);
    }

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validate/{validationcode}/{userid}")
    public User validateEmail(@PathParam("validationcode") String validationCode) {
        return loginService.validateEmail(validationCode);
    }
}
