package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.entity.Email;
import com.github.danielrichtersz.entity.User;
import com.github.danielrichtersz.mock.MockDatabase;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserBean {

    @EJB
    MockingBean mockingBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public Response testGetCheck() {
        MockDatabase mdb = mockingBean.getDb();
        return Response.ok(mdb).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response logInCheck(@FormParam("email") String email, @FormParam("password") String password) {
        for (User user : mockingBean.getDb().getUserList()) {
            //Comparison doesn't work, something going wrong in string format? In debug all values are the same ('test@mail.com' and 'Password')
            if (email.equals(user.getEmail().getEmail()) && password.equals(user.getPassword())) {
                return Response.ok(user).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/create")
    public Response createUser(@FormParam("firstname") String firstName,
                               @FormParam("lastname") String lastName,
                               @FormParam("email") String email,
                               @FormParam("password") String password,
                               @FormParam("phonenumber") String phonenumber,
                               @FormParam("profilepictureurl") String profilePicture) {
        User user = createOrUpdateUser(firstName, lastName, email, password, phonenumber, profilePicture, null);
        if (user != null) {
            mockingBean.getDb().getUserList().add(user);
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/get")
    public Response getUser(@PathParam("userid") long userId) {
        for (User user : mockingBean.getDb().getUserList()) {
            if (user.getId() == userId) {
                return Response.status(Response.Status.FOUND).entity(user).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}/edit")
    public Response editUser(@PathParam("userid") long userId,
                             @FormParam("firstname") String firstName,
                             @FormParam("lastname") String lastName,
                             @FormParam("email") String email,
                             @FormParam("password") String password,
                             @FormParam("phonenumber") String phonenumber,
                             @FormParam("profilepictureurl") String profilePicture) {
        for (User foundUser : mockingBean.getDb().getUserList()) {
            if (foundUser.getId() == userId) {
                foundUser = createOrUpdateUser(firstName, lastName, email, password, phonenumber, profilePicture, foundUser);

                //User was updated
                return Response.ok(foundUser).build();
            }
        }
        //User was not found
        return Response.status(Response.Status.NOT_FOUND).build();
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
                return null;
            }
            //Check if used is to be edited
            if (editUser != null) {
                user = editUser;
            } else {
                user = new User();
                user.setId((long)mockingBean.getDb().getUserList().size() + 1);
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
