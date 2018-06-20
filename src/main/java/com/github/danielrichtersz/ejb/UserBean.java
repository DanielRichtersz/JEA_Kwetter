package ejb;

import services.impl.UserServiceImpl;
import entity.User;
import services.interfaces.LoginService;
import services.interfaces.UserService;

import javax.ejb.CreateException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.nio.file.AccessDeniedException;

@Path("/users")
@ApplicationScoped
public class UserBean implements UserBeanRemote, Serializable {

    @Inject
    UserService userService;

    @Inject
    LoginService loginService;

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
    @Path("/create")
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
    public User editUser(@HeaderParam("token") String token,
                         @HeaderParam("userid") String userId,
                         @FormParam("firstname") String firstName,
                         @FormParam("lastname") String lastName,
                         @FormParam("email") String email,
                         @FormParam("password") String password,
                         @FormParam("phonenumber") String phonenumber,
                         @FormParam("profilepictureurl") String profilePicture) throws CreateException, AccessDeniedException {
        if (loginService.validateToken(token, userId)) {
            return userService.editUser(Long.valueOf(userId), firstName, lastName, email, password, phonenumber, profilePicture);
        } else {
            throw new AccessDeniedException("You need to be logged in with the proper rights to edit this user");
        }
    }

    @Override
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/remove")
    public Response removeUser(@HeaderParam("token") String token,
                               @HeaderParam("userid") String userId) throws AccessDeniedException {
        if (loginService.validateToken(token, userId)) {
            return userService.removeUser(Long.valueOf(userId));
        } else {
            throw new AccessDeniedException("You need to be logged in with the proper rights to edit this user");
        }
    }

    @Override
    @POST
    @Path("/stopfollow/{followerID}/{followingID}")
    public Response stopFollowing(@HeaderParam("token") String token,
                                  @HeaderParam("userid") String userId,
                                  @PathParam("followerID") long followingID) throws AccessDeniedException {
        if (loginService.validateToken(token, userId)) {
            return userService.stopFollowing(Long.valueOf(userId), followingID);
        } else {
            throw new AccessDeniedException("You need to be logged in to perform this action");
        }
    }

    @Override
    @POST
    @Path("/follow//{followerID}/{followingID}")
    public Response follow(@HeaderParam("token") String token,
                           @HeaderParam("userid") String userId,
                           @PathParam("followerID") long followingID) throws AccessDeniedException {
        if (loginService.validateToken(token, userId)) {
            return userService.follow(Long.valueOf(userId), followingID);
        } else {
            throw new AccessDeniedException("You need to be logged in to perform this action");
        }
    }
}