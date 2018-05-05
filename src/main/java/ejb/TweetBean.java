package ejb;

import entity.App;
import entity.Like;
import entity.Tweet;
import entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TweetBean {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tweet/create")
    public Response createTweet(@FormParam("datecreated") String dateCreated,
                                @FormParam("userid") long userId,
                                @FormParam("message") String message) {
        Tweet tweet = new Tweet();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        for (User user : App.db.getUserList()) {
            if (user.getId() == userId) {
                tweet.setOwner(user);
            }
        }

        //Check if an owner was found
        if (tweet.getOwner() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Lsst, add the message to the tweet
        if (message != null && !message.isEmpty()) {
            tweet.setMessage(message);
        }
        else {
            return Response.status(500, "No message entered").build();
        }

        //Add the Tweet to the database
        tweet.setId((long)App.db.getTweetList().size() + 1);
        App.db.getTweetList().add(tweet);
        return Response.ok(tweet).build();
    }

    //Should be in UserBean?
    @DELETE
    @Path("/tweet/delete/{userid}/{tweetid}")
    public Response removeTweet(@PathParam("userid")long userId, @PathParam("tweetid")long tweetId) {
        for (Tweet tweet : App.db.getTweetList()) {
            if (tweet.getId() == tweetId && tweet.getOwner().getId() == userId) {
                App.db.getTweetList().remove(tweet);
                return Response.status(Response.Status.FOUND).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/tweet/addlike/{tweetid}/{userid}")
    public Response addLikeToTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        //Find the corresponding tweet and user
        //Create the like and add to the tweet
        //With JPA this will be done only with the ID's, however using a mockdatabase this is the fastest solution
        //Event though this is far from pretty coding
        for (Tweet tweet : App.db.getTweetList()) {
            if (tweet.getId() == tweetId) {
                for (User user : App.db.getUserList()) {
                    if (user.getId() == userId) {
                        Like like = new Like(user, tweet, new Date());
                        //Set the id of the like. This is a far from perfect (or programming wise, pretty) solution
                        //however it fits the needs and will function as needed
                        like.setId((long)tweet.getLikes().size() + 1);

                        tweet.addLike(like);
                        return Response.ok().build();
                    }
                }
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //Find method to have userid protected so a user can only delete its own tweets
    @DELETE
    @Path("/tweet/remove/{tweetid}/{userid}")
    public Response removeLikeFromTweet(@PathParam("tweetid") long tweetId, @PathParam("userid") long userId) {
        for (Tweet tweet : App.db.getTweetList()) {
            if (tweet.getId() == tweetId) {
                for (Like like : tweet.getLikes()) {
                    if (like.getUser().getId() == userId) {
                        tweet.getLikes().remove(like);
                        return Response.ok().build();
                    }
                }
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
