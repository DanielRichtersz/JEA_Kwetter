package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.dao.UserDAO;
import com.github.danielrichtersz.dao.UserDAOLocal;
import com.github.danielrichtersz.entity.Email;
import com.github.danielrichtersz.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;

@Path("/userlr")
@ApplicationScoped
public class LoginBean implements LoginBeanRemote {

    // Not the interface >>> causes unfixable error
    @Inject
    UserDAOLocal userDAOLocal;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    @Override
    public User login(@FormParam("username") String email, @FormParam("password") String password) throws ValidationException {
        User user = userDAOLocal.getByCredentials(email, password);
        if (user != null) {
            return user;
        }
        throw new ValidationException("The login credentials could not be validated");
    }

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validate/{validationcode}/{userid}")
    public User validateEmail(@PathParam("validationcode") String validationCode) {
        return userDAOLocal.validateEmail(validationCode);
    }
}
