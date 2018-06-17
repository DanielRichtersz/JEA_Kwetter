package com.github.danielrichtersz.dao;

import com.github.danielrichtersz.entity.Like;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.services.MockDatabaseService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.NotFoundException;
import java.util.*;

@ApplicationScoped
public class TweetDAOLocal implements TweetDAO {

    @Inject
    MockDatabaseService mockDatabaseService;

    @Override
    public Tweet getByID(Long id) {
        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()) {
            if (tweet.getId() == id) {
                return tweet;
            }
        }
        throw new NotFoundException("The specified tweet could not be found");
    }

    @Override
    public void create(Tweet entity) {
        mockDatabaseService.getDb().getTweetList().add(entity);
    }

    @Override
    public void edit(Tweet entity) {
        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()) {
            if (tweet.getId() == entity.getId()) {
                remove(tweet);
                create(entity);
                return;
            }
        }
        throw new NotFoundException("The specified tweet could not be found");
    }

    @Override
    public void remove(Tweet entity) {
        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()) {
            if (tweet == entity) {
                mockDatabaseService.getDb().getTweetList().remove(tweet);
            }
        }
    }

    public long getNewTweetID() {
        return mockDatabaseService.getDb().getTweetList().size() + 1;
    }

    @Override
    public List<Tweet> getTweetsByUserID(long userId) {
        // Find all Tweets belonging to this userId
        List<Tweet> foundTweets = new ArrayList<Tweet>();
        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()) {
            if (tweet.getOwner().getId() == userId) {
                foundTweets.add(tweet);
            }
        }

        // Sort tweets
        // TODO: Check if the sorting works correctly
        foundTweets.sort(Tweet.tweetComparator);

        //Sort the list by dates
        //Collections.sort(foundTweets, new Comparator<Tweet>() {
        //    public int compare(Tweet o1, Tweet o2) {
        //        return o1.getDateCreated().compareTo(o2.getDateCreated());
        //    }
        //});

        return foundTweets;
    }

    @Override
    public List<Tweet> getTweetsByUserIDBetweenDates(
            Date start,
            Date end,
            long userId) {
        // Find all Tweets belonging to this userId
        List<Tweet> foundTweets = new ArrayList<Tweet>();
        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()) {
            if (tweet.getOwner().getId() == userId) {
                if (tweet.getDateCreated().before(end) && tweet.getDateCreated().after(start))
                {
                    foundTweets.add(tweet);
                }
            }
        }

        // Sort the tweets
        foundTweets.sort(Tweet.tweetComparator);

        return foundTweets;
    }

    @Override
    public Like addLikeToTweet(long tweetId, long userId) throws InstanceAlreadyExistsException {
        Tweet tweet = this.getByID(tweetId);
        for (Like like : tweet.getLikes()) {
            if (like.getUserId() == userId) {
                throw new InstanceAlreadyExistsException("This user has already liked this tweet");
            }
        }
        return tweet.addLike(userId);
    }

    @Override
    public void removeLikeFromTweet(long tweetId, long userId) {
        getByID(tweetId).removeLike(userId);
    }

    @Override
    public void removeLikesByUserID(long userID) {

        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()) {
            Iterator<Like> i = tweet.getLikes().iterator();
            while (i.hasNext()) {
                Like deleteLike = i.next();
                if (deleteLike.getUserId() == userID)
                {
                    tweet.removeLike(userID);
                }
            }
        }
    }
}
