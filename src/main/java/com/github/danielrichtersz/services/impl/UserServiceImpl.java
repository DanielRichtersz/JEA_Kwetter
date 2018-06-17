package com.github.danielrichtersz.services.impl;

import com.github.danielrichtersz.dao.TweetDAOLocal;
import com.github.danielrichtersz.dao.UserDAOLocal;
import com.github.danielrichtersz.entity.Email;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.entity.User;
import com.github.danielrichtersz.services.interfaces.UserService;

import javax.ejb.CreateException;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import java.util.List;

public class UserServiceImpl implements UserService {

    // Doesn't call the interface. The reason for this is that calling the interface gives an exception which we cannot resolve.
    // Temporary solution
    @Inject
    UserDAOLocal userDAOLocal;

    @Inject
    TweetDAOLocal tweetDAOLocal;

    @Override
    public User getUser(long userId) {
        return userDAOLocal.getByID(userId);
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String phonenumber, String profilePicture) throws CreateException {
        User user = createOrUpdateUser(firstName, lastName, email, password, phonenumber, profilePicture);
        if (user != null) {
            userDAOLocal.create(user);
            return user;
        } else {
            throw new CreateException("User could not be created");
        }
    }

    @Override
    public User editUser(long userId, String firstName, String lastName, String email, String password, String phonenumber, String profilePicture) {
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
    public Response removeUser(long userID) {
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

    @Override
    public Response stopFollowing(long followerID, long followingID) {
        User follower = userDAOLocal.getByID(followerID);
        User following = userDAOLocal.getByID(followingID);

        follower.removeFollowing(following);
        following.removeFollower(follower);

        userDAOLocal.edit(follower);
        userDAOLocal.edit(following);

        return Response.ok().build();
    }

    @Override
    public Response follow(long followerID, long followingID) {
        User follower = userDAOLocal.getByID(followerID);
        User following = userDAOLocal.getByID(followingID);

        follower.addFollowing(following);
        following.addFollower(follower);

        userDAOLocal.edit(follower);
        userDAOLocal.edit(following);

        return Response.ok().build();
    }

    @Override
    public Response getTestCheck() {
        return Response.ok(userDAOLocal.getDatabaseTest()).build();
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
