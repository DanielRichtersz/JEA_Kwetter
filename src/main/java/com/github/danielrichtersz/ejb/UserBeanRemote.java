package ejb;

import entity.User;

import javax.ejb.CreateException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.file.AccessDeniedException;

public interface UserBeanRemote {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/get")
    User getUser(@PathParam("userid") long userId);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/create")
    User createUser(@FormParam("firstname") String firstName,
                    @FormParam("lastname") String lastName,
                    @FormParam("email") String email,
                    @FormParam("password") String password,
                    @FormParam("phonenumber") String phonenumber,
                    @FormParam("profilepictureurl") String profilePicture) throws CreateException;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/edit")
    User editUser(@HeaderParam("token") String token,
                  @HeaderParam("userid") String userId,
                  @FormParam("firstname") String firstName,
                  @FormParam("lastname") String lastName,
                  @FormParam("email") String email,
                  @FormParam("password") String password,
                  @FormParam("phonenumber") String phonenumber,
                  @FormParam("profilepictureurl") String profilePicture) throws CreateException, AccessDeniedException;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/remove")
    Response removeUser(@HeaderParam("token") String token,
                        @HeaderParam("userid") String userId) throws AccessDeniedException;

    @POST
    @Path("/stopfollow/{followerID}/{followingID}")
    Response stopFollowing(@HeaderParam("token") String token,
                           @HeaderParam("userid") String userId,
                           @PathParam("followerID") long followingID) throws AccessDeniedException;

    @POST
    @Path("/follow//{followerID}/{followingID}")
    Response follow(@HeaderParam("token") String token,
                    @HeaderParam("userid") String userId,
                    @PathParam("followerID") long followingID) throws AccessDeniedException;
}
