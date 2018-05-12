package com.github.danielrichtersz.dao;

import com.github.danielrichtersz.entity.Like;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.services.MockDatabaseService;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.NotFoundException;

@Singleton(name = "TweetDAOLocal")
@DependsOn("MockDatabaseService")
public class TweetDAOLocal implements TweetDAO {

    @EJB
    MockDatabaseService mockDatabaseService;

    @Override
    public Tweet getByID(Long id) {
        for (Tweet tweet : mockDatabaseService.getDb().getTweetList()){
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
                tweet = entity;
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

    public long getNewTweetID(){
        return mockDatabaseService.getDb().getTweetList().size() + 1;
    }

    @Override
    public void addLikeToTweet(long tweetId, long userId) throws InstanceAlreadyExistsException {
        Tweet tweet = this.getByID(tweetId);
        if (tweet.getOwner().getId() == userId) {
            for (Like like : tweet.getLikes()) {
                if (like.getUserId() == userId) {
                    throw new InstanceAlreadyExistsException("This user has already liked this tweet");
                }
            }
            tweet.addLike(userId);
        }
    }

    @Override
    public void removeLikeFromTweet(long tweetId, long userId) {
        getByID(tweetId).removeLike(userId);
    }
}
