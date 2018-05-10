package com.github.danielrichtersz.ejb;


import com.github.danielrichtersz.entity.Like;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.entity.User;
import com.github.danielrichtersz.services.MockDatabaseService;

import javax.ejb.EJB;
import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/tweets")
public class TweetBean {

    @EJB
    MockDatabaseService mockDatabaseService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create/{userid}")
    public Tweet createTweet(@PathParam("userid") long userId,
                                @FormParam("datecreated") String dateCreated,
                                @FormParam("message") String message) throws ParseException {
        Tweet tweet = new Tweet();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk-mm-ss");
        Date date;
        try {
            //Convert the dateCreated from formParam string to Date format
            date = simpleDateFormat.parse(dateCreated);
        } catch (ParseException e) {
            throw e;
        }

        //If parsing the date was succesfull create Tweet
        tweet.setDateCreated(date);

        //Get the owner by ID
        for (User user : mockDatabaseService.getDb().getUserList()) {
            if (user.getId() == userId) {
                tweet.setOwner(user);
            }
        }

        //Check if an owner was found
        if (tweet.getOwner() == null) {
            throw new NotFoundException("User does not exist");
        }

        //Last, add the message to the tweet
        if (message != null && !message.isEmpty()) {
            tweet.setMessage(message);
        } else {
            throw new NullPointerException("No message input found");
        }

        //Add the Tweet to the database
        tweet.setId((long) mockDatabaseService.getDb().getTweetList().size() + 1);
        mockDatabaseService.getDb().getTweetList().add(tweet);
        return tweet;
    }

    //Should be in UserBean?
    @DELETE
    @Path("/delete/{tweetid}/{userid}")
    public void removeTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()) {
            if (tweet.getId() == tweetId && tweet.getOwner().getId() == userId) {
                mockDatabaseService.getDb().getTweetList().remove(tweet);
                //Succes
            }
        }
        throw new NotFoundException("The specified tweet could not be found");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/{tweetid}/{userid}")
    public Like addLikeToTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) throws InstanceAlreadyExistsException {
        //Find the corresponding tweet and user
        //Create the like and add to the tweet
        //With JPA this will be done only with the ID's, however using a mockdatabase this is the fastest solution
        //Event though this is far from pretty coding
        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()) {
            if (tweet.getId() == tweetId) {
                for (Like tweetLike : tweet.getLikes()) {

                    //Check if the user with this userId has already liked this tweet
                    if (tweetLike.getUserId() == userId) {
                        throw new InstanceAlreadyExistsException();
                    }
                }
                //If no like with the specified userid exists, create the like
                Like like = tweet.addLike(userId);
                return like;
            }
        }
        throw new NotFoundException("The specified tweet could not be found");
    }

    //Find method to have userid protected so a user can only delete its own tweets
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/delete/{tweetid}/{userid}")
    public void removeLikeFromTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()) {
            if (tweet.getId() == tweetId) {
                for (Like like : tweet.getLikes()) {
                    if (like.getUserId() == userId) {
                        tweet.getLikes().remove(like);
                        //Succes
                    }
                }
            }
        }
        throw new NotFoundException("The specified like could not be found");
    }
}
