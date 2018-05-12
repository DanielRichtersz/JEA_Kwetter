package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.entity.Tweet;

import javax.ejb.Remote;
import javax.management.InstanceAlreadyExistsException;
import java.text.ParseException;

@Remote
public interface TweetBeanRemote {
    Tweet createTweet(long userId, String dateCreated, String message) throws ParseException;

    Tweet getTweet(long tweetId);

    void removeTweet(long tweetId, long userId);

    void addLikeToTweet(long tweetId, long userId) throws InstanceAlreadyExistsException;

    void removeLikeFromTweet(long tweetId, long userId);


}
