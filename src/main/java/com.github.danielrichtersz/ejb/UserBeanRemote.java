package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.entity.User;

import javax.ejb.CreateException;
import javax.ejb.Remote;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;

@Remote
public interface UserBeanRemote {

    Response testGetCheck();

    User logInCheck(@FormParam("email") String email, @FormParam("password") String password) throws ValidationException;

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
}
