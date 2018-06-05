package com.github.danielrichtersz.ejb;


import com.github.danielrichtersz.dao.TweetDAOLocal;
import com.github.danielrichtersz.dao.UserDAOLocal;
import com.github.danielrichtersz.entity.Like;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/tweets")
@ApplicationScoped
public class TweetBean implements TweetBeanRemote, Serializable {

    // Doesn't call the interface. The reason for this is that calling the interface gives an exception which we cannot resolve.
    // Temporary solution
    @Inject
    UserDAOLocal udl;

    // Doesn't call the interface. The reason for this is that calling the interface gives an exception which we cannot resolve.
    // Temporary solution
    @Inject
    TweetDAOLocal tdl;

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

        //Add the message to the tweet
        if (message != null && !message.isEmpty()) {
            tweet.setMessage(message);
        } else {
            throw new NullPointerException("No message input found");
        }

        //Get the owner by ID
        User owner = udl.getByID(userId);
        tweet.setOwner(owner);
        tweet.setId(tdl.getNewTweetID());
        tdl.create(tweet);
        return tweet;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{tweetid}")
    public Tweet getTweetByTweetID(@PathParam("tweetid") long tweetId) {
        return tdl.getByID(tweetId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}")
    public List<Tweet> getTweetsByUserID(@PathParam("userid") long userId) {
        return tdl.getTweetsByUserID(userId);
    }

    @POST
    @Path("/{userid}")
    @Override
    public List<Tweet> getTweetsByUserIDBetweenDates(
            @FormParam("startdate") String startdate,
            @FormParam("enddate") String enddate,
            @PathParam("userid") long userId) {
        // Parse the time
        SimpleDateFormat parse = new SimpleDateFormat("yyyy-MM-dd");
        Date start;
        Date end;

        try {
            start = parse.parse(startdate);
            end = parse.parse(enddate);
        } catch (ParseException e) {
            throw new BadRequestException();
        }

        return tdl.getTweetsByUserIDBetweenDates(start, end, userId);
    }

    //Should be in UserBean?
    @DELETE
    @Path("/delete/{tweetid}/{userid}")
    public void removeTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        Tweet tweet = tdl.getByID(tweetId);
        if (tweet.getOwner().getId() == userId) {
            tdl.remove(tweet);
        } else {
            throw new NotAllowedException("The specified user is not the owner of this tweet");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/{tweetid}/{userid}")
    public Like addLikeToTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) throws InstanceAlreadyExistsException {
        return tdl.addLikeToTweet(tweetId, userId);
    }

    //Todo: Find method to have userid protected so a user can only delete its own tweets
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/delete/{tweetid}/{userid}")
    public void removeLikeFromTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        tdl.removeLikeFromTweet(tweetId, userId);
    }
}
