package ejb;

import entity.Like;
import entity.Tweet;
import services.impl.TweetServiceImpl;
import services.interfaces.TweetService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

@Path("/tweets")
@ApplicationScoped
public class TweetBean implements TweetBeanRemote, Serializable {

    @Inject
    TweetService tweetService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create/{userid}")
    public Tweet createTweet(@PathParam("userid") long userId,
                             @FormParam("datecreated") String dateCreated,
                             @FormParam("message") String message) throws ParseException {
        return tweetService.createTweet(userId, dateCreated, message);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/gbtid/{tweetid}")
    public Tweet getTweetByTweetID(@PathParam("tweetid") long tweetId) {
        return tweetService.getTweetByTweetID(tweetId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/gbuid/{userid}")
    public List<Tweet> getTweetsByUserID(@PathParam("userid") long userId) {
        return tweetService.getTweetsByUserID(userId);
    }

    @POST
    @Path("/btwn/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Tweet> getTweetsByUserIDBetweenDates(
            @FormParam("startdate") String startdate,
            @FormParam("enddate") String enddate,
            @PathParam("userid") long userId) {
        return tweetService.getTweetsByUserIDBetweenDates(startdate, enddate, userId);
    }

    //Should be in UserBean?
    @DELETE
    @Path("/delete/{tweetid}/{userid}")
    public void removeTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        tweetService.removeTweet(tweetId, userId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/{tweetid}/{userid}")
    public Like addLikeToTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) throws InstanceAlreadyExistsException {
        return tweetService.addLikeToTweet(tweetId, userId);
    }

    //Todo: Find method to have userid protected so a user can only delete its own tweets
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/delete/{tweetid}/{userid}")
    public void removeLikeFromTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        tweetService.removeLikeFromTweet(tweetId, userId);
    }
}
