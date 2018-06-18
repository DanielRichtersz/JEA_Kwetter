package services.impl;

import dao.TweetDAOLocal;
import dao.UserDAOLocal;
import entity.Like;
import entity.Tweet;
import entity.User;
import services.interfaces.TweetService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAllowedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TweetServiceImpl implements TweetService {

    // Doesn't call the interface. The reason for this is that calling the interface gives an exception which we cannot resolve.
    // Temporary solution
    @Inject
    UserDAOLocal udl;

    // Doesn't call the interface. The reason for this is that calling the interface gives an exception which we cannot resolve.
    // Temporary solution
    @Inject
    TweetDAOLocal tdl;

    @Override
    public Tweet createTweet(long userId, String dateCreated, String message) throws ParseException {
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

    @Override
    public Tweet getTweetByTweetID(long tweetId) {
        return tdl.getByID(tweetId);
    }

    @Override
    public List<Tweet> getTweetsByUserID(long userId) {
        return tdl.getTweetsByUserID(userId);
    }

    @Override
    public List<Tweet> getTweetsByUserIDBetweenDates(String startdate, String enddate, long userId) {
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

    @Override
    public void removeTweet(long tweetId, long userId) {
        Tweet tweet = tdl.getByID(tweetId);
        if (tweet.getOwner().getId() == userId) {
            tdl.remove(tweet);
        } else {
            throw new NotAllowedException("The specified user is not the owner of this tweet");
        }
    }

    @Override
    public Like addLikeToTweet(long tweetId, long userId) throws InstanceAlreadyExistsException {
        return tdl.addLikeToTweet(tweetId, userId);
    }

    @Override
    public void removeLikeFromTweet(long tweetId, long userId) {
        tdl.removeLikeFromTweet(tweetId, userId);
    }
}
