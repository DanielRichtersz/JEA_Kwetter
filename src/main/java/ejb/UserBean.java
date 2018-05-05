package ejb;

import entity.App;
import entity.Email;
import entity.User;

import javax.ws.rs.core.Response;

public class UserBean {

    public Response logInCheck(String email, String password) {
        for (User user : App.db.getUserList()) {
            if (email == user.getEmail().getEmail() && password == user.getPassword()) {
                return Response.ok(user).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response createUser(String firstName, String lastName, String email, String password, String phonenumber,
                               String profilePicture) {
        User user = new User();
        try {
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

            App.db.getUserList().add(user);
            return Response.ok(user).build();
        } catch (NullPointerException ex) {
            System.out.println(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }
    }

    public Response getUser(long userId) {
        for (User user : App.db.getUserList()) {
            if (user.getId() == userId) {
                return Response.status(Response.Status.FOUND).entity(user).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response editUser(User user) {
        for (User foundUser : App.db.getUserList()) {
            if (foundUser.getId() == user.getId()) {
                foundUser = user;
                return Response.ok(user).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
