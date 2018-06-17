package com.github.danielrichtersz.services.interfaces;

import com.github.danielrichtersz.entity.User;

import javax.ejb.CreateException;
import javax.ws.rs.core.Response;

public interface UserService {


    User getUser(long userId);

    User createUser(String firstName, String lastName, String email, String password, String phonenumber, String profilePicture) throws CreateException;

    User editUser(long userId, String firstName, String lastName, String email, String password, String phonenumber, String profilePicture);

    Response removeUser(long userID);

    Response stopFollowing(long followerID, long followingID);

    Response follow(long followerID, long followingID);

    Response getTestCheck();

}