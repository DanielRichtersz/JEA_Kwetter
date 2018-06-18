package ejb;

import services.impl.UserServiceImpl;
import entity.User;
import services.interfaces.UserService;

import javax.ejb.CreateException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@Path("/users")
@ApplicationScoped
public class UserBean implements UserBeanRemote, Serializable {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public Response testGetCheck() {
        return userService.getTestCheck();
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/get")
    public User getUser(@PathParam("userid") long userId) {
        return userService.getUser(userId);
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
        return userService.createUser(firstName, lastName, email, password, phonenumber, profilePicture);

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
        return userService.editUser(userId, firstName, lastName, email, password, phonenumber, profilePicture);

    }

    @Override
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/remove")
    public Response removeUser(@FormParam("userid") long userID) {
        return userService.removeUser(userID);
    }



    @Override
    @POST
    @Path("/stopfollow/{followerID}/{followingID}")
    public Response stopFollowing(@PathParam("followerID") long followerID, @PathParam("followerID") long followingID) {
        return userService.stopFollowing(followerID, followingID);
    }

    @Override
    @POST
    @Path("/follow//{followerID}/{followingID}")
    public Response follow(@PathParam("followerID") long followerID, @PathParam("followerID") long followingID) {
        return userService.follow(followerID, followingID);
    }




}
