package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.dao.UserDAOLocal;
import com.github.danielrichtersz.entity.Email;
import com.github.danielrichtersz.entity.User;

import javax.ejb.CreateException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;
import java.io.Serializable;

@Path("/users")
@ApplicationScoped
public class UserBean implements UserBeanRemote, Serializable {

    // Doesn't call the interface. The reason for this is that calling the interface gives an exception which we cannot resolve.
    // Temporary solution
    @Inject
    UserDAOLocal ud;

    //@Inject
    //MockDatabaseService mockDatabaseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public Response testGetCheck() {
        return Response.ok(ud.getDatabaseTest()).build();
    }

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public User logInCheck(@FormParam("email") String email, @FormParam("password") String password) throws ValidationException {
        User user = ud.getByCredentials(email, password);
        if (user != null) {
            return user;
        }
        throw new ValidationException("The login credentials could not be validated");
    }

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/create")
    public User createUser(@FormParam("firstname") String firstName,
                           @FormParam("lastname") String lastName,
                           @FormParam("email") String email,
                           @FormParam("password") String password,
                           @FormParam("phonenumber") String phonenumber,
                           @FormParam("profilepictureurl") String profilePicture) throws CreateException {
        User user = createOrUpdateUser(firstName, lastName, email, password, phonenumber, profilePicture, null);
        if (user != null) {
            ud.create(user);
            return user;
        } else {
            throw new CreateException("User could not be created");
        }
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/get")
    public User getUser(@PathParam("userid") long userId) {
        return ud.getByID(userId);
    }

    @Override
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/edit")
    public User editUser(@PathParam("userid") long userId,
                         @FormParam("firstname") String firstName,
                         @FormParam("lastname") String lastName,
                         @FormParam("email") String email,
                         @FormParam("password") String password,
                         @FormParam("phonenumber") String phonenumber,
                         @FormParam("profilepictureurl") String profilePicture) throws CreateException {
        User foundUser = ud.getByID(userId);
        foundUser = createOrUpdateUser(firstName, lastName, email, password, phonenumber, profilePicture, foundUser);
        ud.edit(foundUser);
        return foundUser;
    }

    //Creates or, if a user is given with editUser, updates a user with the given information.
    //If a new user is to be created, set editUser to null
    private User createOrUpdateUser(@FormParam("firstname") String firstName,
                                    @FormParam("lastname") String lastName,
                                    @FormParam("email") String email,
                                    @FormParam("password") String password,
                                    @FormParam("phonenumber") String phonenumber,
                                    @FormParam("profilepictureurl") String profilePicture,
                                    User editUser) {
        try {
            User user;
            if ((firstName == null || firstName.isEmpty()) ||
                    (lastName == null || lastName.isEmpty()) ||
                    (email == null || email.isEmpty()) ||
                    (password == null || password.isEmpty())) {
                throw new NullPointerException("Could not create the user due to missing input");
            }
            //Check if used is to be edited
            if (editUser != null) {
                user = editUser;
            } else {
                user = new User();
                user.setId(ud.getNewUserID());
            }

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(new Email(email, false));
            user.setPassword(password);

            if (phonenumber != null && phonenumber != "") {
                user.setPhonenumber(phonenumber);
            }

            if (profilePicture != null && profilePicture != "") {
                user.setProfilePictureURL(profilePicture);
            }

            return user;
        } catch (Exception ex) {
            return null;
        }
    }
}
