package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.dao.TweetDAOLocal;
import com.github.danielrichtersz.dao.UserDAOLocal;
import com.github.danielrichtersz.entity.Email;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.entity.User;

import javax.ejb.CreateException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;
import java.io.Serializable;
import java.util.List;

@Path("/users")
@ApplicationScoped
public class UserBean implements UserBeanRemote, Serializable {

    // Doesn't call the interface. The reason for this is that calling the interface gives an exception which we cannot resolve.
    // Temporary solution
    @Inject
    UserDAOLocal userDAOLocal;

    @Inject
    TweetDAOLocal tweetDAOLocal;

    //@Inject
    //MockDatabaseService mockDatabaseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public Response testGetCheck() {
        return Response.ok(userDAOLocal.getDatabaseTest()).build();
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/get")
    public User getUser(@PathParam("userid") long userId) {
        return userDAOLocal.getByID(userId);
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
        User user = createOrUpdateUser(firstName, lastName, email, password, phonenumber, profilePicture);
        if (user != null) {
            userDAOLocal.create(user);
            return user;
        } else {
            throw new CreateException("User could not be created");
        }
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
        // Get original user
        User foundUser = userDAOLocal.getByID(userId);

        // Edit user
        User editedUser = createOrUpdateUser(firstName, lastName, email, password, phonenumber, profilePicture);

        // Replace ID
        editedUser.setId(foundUser.getId());

        // foundUser gets removed and editedUser created within the DAO method
        userDAOLocal.edit(editedUser);
        return editedUser;
    }

    @Override
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}")
    public Response removeUser(@FormParam("userid") long userID) {
        User user = userDAOLocal.getByID(userID);

        // Delete user's tweets
        List<Tweet> toBeDeletedTweets = tweetDAOLocal.getTweetsByUserID(userID);
        for (Tweet tweet : toBeDeletedTweets) {

            // TODO: Check if no error with iterator occurs (Due to reference list?)
            tweetDAOLocal.remove(tweet);
        }

        // Delete user's likes
        tweetDAOLocal.removeLikesByUserID(userID);

        // Delete user
        userDAOLocal.remove(user);

        return Response.ok().build();
    }

    //Creates or, if a user is given with editUser, updates a user with the given information.
    //If a new user is to be created, set editUser to null
    private User createOrUpdateUser(@FormParam("firstname") String firstName,
                                    @FormParam("lastname") String lastName,
                                    @FormParam("email") String email,
                                    @FormParam("password") String password,
                                    @FormParam("phonenumber") String phonenumber,
                                    @FormParam("profilepictureurl") String profilePicture) {
        try {
            User user = new User();
            if ((firstName == null || firstName.isEmpty()) ||
                    (lastName == null || lastName.isEmpty()) ||
                    (email == null || email.isEmpty()) ||
                    (password == null || password.isEmpty())) {
                throw new NullPointerException("Could not create the user due to missing input");
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
