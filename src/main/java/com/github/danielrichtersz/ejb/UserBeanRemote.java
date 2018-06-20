package ejb;

import entity.User;

import javax.ejb.CreateException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.file.AccessDeniedException;

public interface UserBeanRemote {


    User getUser(long userId);


    User createUser(String firstName,
                    String lastName,
                    String email,
                    String password,
                    String phonenumber,
                    String profilePicture) throws CreateException;

    User editUser(String token,
                  String userId,
                  String firstName,
                  String lastName,
                  String email,
                  String password,
                  String phonenumber,
                  String profilePicture) throws CreateException, AccessDeniedException;

    Response removeUser(String token,
                        String userId) throws AccessDeniedException;

    Response stopFollowing(String token,
                           String userId,
                           long followingID) throws AccessDeniedException;

    Response follow(String token,
                    String userId,
                    long followingID) throws AccessDeniedException;
}
