package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.entity.User;

import javax.ejb.CreateException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;

public interface UserBeanRemote {

    User createUser(@FormParam("firstname") String firstName,
                    @FormParam("lastname") String lastName,
                    @FormParam("email") String email,
                    @FormParam("password") String password,
                    @FormParam("phonenumber") String phonenumber,
                    @FormParam("profilepictureurl") String profilePicture) throws CreateException;

    User getUser(long userId);

    User editUser(long userId,
                  @FormParam("firstname") String firstName,
                  @FormParam("lastname") String lastName,
                  @FormParam("email") String email,
                  @FormParam("password") String password,
                  @FormParam("phonenumber") String phonenumber,
                  @FormParam("profilepictureurl") String profilePicture) throws CreateException;

    Response removeUser(@FormParam("userid") long userID);
}
