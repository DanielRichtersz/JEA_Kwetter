package ejb;

import entity.Like;
import entity.Tweet;

import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.util.List;

public interface TweetBeanRemote {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create/{userid}")
    Tweet createTweet(@HeaderParam("token") String token, @HeaderParam("userid") String userId,
                      @FormParam("message") String message) throws ParseException, AccessDeniedException;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/gbtid/{tweetid}")
    Tweet getTweetByTweetID(@PathParam("tweetid") long tweetId);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/gbuid/{userid}")
    List<Tweet> getTweetsByUserID(@PathParam("userid") long userId);

    @POST
    @Path("/btwn/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Tweet> getTweetsByUserIDBetweenDates(
            @FormParam("startdate") String startdate,
            @FormParam("enddate") String enddate,
            @PathParam("userid") long userId);

    @DELETE
    @Path("/delete/{tweetid}")
    void removeTweet(@HeaderParam("token") String token, @HeaderParam("userid") String userId,
                     @PathParam("tweetid") long tweetId) throws AccessDeniedException;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/{tweetid}")
    Like addLikeToTweet(@HeaderParam("token") String token, @HeaderParam("userid") String userId,
                        @PathParam("tweetid") long tweetId) throws InstanceAlreadyExistsException, AccessDeniedException;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/like/delete/{tweetid}")
    void removeLikeFromTweet(@HeaderParam("token") String token, @HeaderParam("userid") String userId,
                             @PathParam("tweetid") long tweetId) throws AccessDeniedException;
}
