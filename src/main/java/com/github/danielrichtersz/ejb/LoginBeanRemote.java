package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.entity.User;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;

public interface LoginBeanRemote {
    User login(String username, String password) throws ValidationException;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validate/{validationcode}/{userid}")
    User validateEmail(@PathParam("validationcode") String validationCode);

}
