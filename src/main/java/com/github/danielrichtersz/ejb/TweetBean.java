package ejb;

import entity.Like;
import entity.Tweet;
import services.impl.TweetServiceImpl;
import services.interfaces.LoginService;
import services.interfaces.TweetService;
import util.JWTAuth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.util.List;

@Path("/tweets")
@ApplicationScoped
public class TweetBean implements TweetBeanRemote, Serializable {

    @Inject
    TweetService tweetService;

    @Inject
    LoginService loginService;

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create/{userid}")
    public Tweet createTweet(@HeaderParam("token") String token, @HeaderParam("userid") String userId,
                             @FormParam("message") String message) throws ParseException, AccessDeniedException {
        if (loginService.validateToken(token, userId)) {
            return tweetService.createTweet(Long.parseLong(userId), message);
        } else {
            throw new AccessDeniedException("You do not have the rights to perform this action");
        }
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/gbtid/{tweetid}")
    public Tweet getTweetByTweetID(@PathParam("tweetid") long tweetId) {
        return tweetService.getTweetByTweetID(tweetId);
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/gbuid/{userid}")
    public List<Tweet> getTweetsByUserID(@PathParam("userid") long userId) {
        return tweetService.getTweetsByUserID(userId);
    }

    @Override
    @POST
    @Path("/btwn/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tweet> getTweetsByUserIDBetweenDates(
            @FormParam("startdate") String startdate,
            @FormParam("enddate") String enddate,
            @PathParam("userid") long userId) {
        return tweetService.getTweetsByUserIDBetweenDates(startdate, enddate, userId);
    }

    @Override
    @DELETE
    @Path("/delete/{tweetid}")
    public void removeTweet(@HeaderParam("token") String token, @HeaderParam("userid") String userId,
                            @PathParam("tweetid") long tweetId) throws AccessDeniedException {
        if (loginService.validateToken(token, userId)) {
            tweetService.removeTweet(tweetId, Long.valueOf(userId));
        } else {
            throw new AccessDeniedException("You do not have the rights to delete this tweet");
        }
    }

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/{tweetid}")
    public Like addLikeToTweet(@HeaderParam("token") String token, @HeaderParam("userid") String userId,
                               @PathParam("tweetid") long tweetId) throws InstanceAlreadyExistsException, AccessDeniedException {
        if (loginService.validateToken(token, userId)) {
            return tweetService.addLikeToTweet(tweetId, Long.valueOf(userId));
        } else {
            throw new AccessDeniedException("You need to be logged in to like a tweet");
        }
    }

    @Override
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/delete/{tweetid}")
    public void removeLikeFromTweet(@HeaderParam("token") String token, @HeaderParam("userid") String userId,
                                    @PathParam("tweetid") long tweetId) throws AccessDeniedException {
        if (loginService.validateToken(token, userId)) {
            tweetService.removeLikeFromTweet(tweetId, Long.valueOf(userId));
        } else {
            throw new AccessDeniedException("You need to be logged in to like a tweet");
        }
    }
}
