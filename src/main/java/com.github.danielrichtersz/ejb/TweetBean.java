package com.github.danielrichtersz.ejb;


import com.github.danielrichtersz.entity.Like;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.entity.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/tweets")
public class TweetBean {

    @EJB
    MockingBean mockingBean;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create/{userid}")
    public Response createTweet(@PathParam("userid") long userId,
                                @FormParam("datecreated") String dateCreated,
                                @FormParam("message") String message) {
        Tweet tweet = new Tweet();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk-mm-ss");
        Date date;
        try {
            //Convert the dateCreated from formParam string to Date format
            date = simpleDateFormat.parse(dateCreated);
        } catch (ParseException e) {
            return Response.status(500, "Unknown date format").build();
        }

        //If parsing the date was succesfull create Tweet
        tweet.setDateCreated(date);

        //Get the owner by ID
        for (User user : mockingBean.getDb().getUserList()) {
            if (user.getId() == userId) {
                tweet.setOwner(user);
            }
        }

        //Check if an owner was found
        if (tweet.getOwner() == null) {
            return Response.status(500, "Non-existing user").build();
        }

        //Last, add the message to the tweet
        if (message != null && !message.isEmpty()) {
            tweet.setMessage(message);
        } else {
            return Response.status(500, "No message entered").build();
        }

        //Add the Tweet to the database
        tweet.setId((long) mockingBean.getDb().getTweetList().size() + 1);
        mockingBean.getDb().getTweetList().add(tweet);
        return Response.ok(tweet).build();
    }

    //Should be in UserBean?
    @DELETE
    @Path("/delete/{tweetid}/{userid}")
    public Response removeTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        for (Tweet tweet : mockingBean.getDb().getTweetList()) {
            if (tweet.getId() == tweetId && tweet.getOwner().getId() == userId) {
                mockingBean.getDb().getTweetList().remove(tweet);
                return Response.status(Response.Status.FOUND).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/{tweetid}/{userid}")
    public Response addLikeToTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        //Find the corresponding tweet and user
        //Create the like and add to the tweet
        //With JPA this will be done only with the ID's, however using a mockdatabase this is the fastest solution
        //Event though this is far from pretty coding
        for (Tweet tweet : mockingBean.getDb().getTweetList()) {
            if (tweet.getId() == tweetId) {
                for (Like tweetLike : tweet.getLikes()) {

                    //Check if the user with this userId has already liked this tweet
                    if (tweetLike.getUserId() == userId) {
                        return Response.status(500, "Post is already liked by this user").build();
                    }
                }
                //If no like with the specified userid exists, create the like
                Like like = tweet.addLike(userId);
                return Response.ok(like).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //Find method to have userid protected so a user can only delete its own tweets
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/delete/{tweetid}/{userid}")
    public Response removeLikeFromTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        for (Tweet tweet : mockingBean.getDb().getTweetList()) {
            if (tweet.getId() == tweetId) {
                for (Like like : tweet.getLikes()) {
                    if (like.getUserId() == userId) {
                        tweet.getLikes().remove(like);
                        return Response.ok(tweet).build();
                    }
                }
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
