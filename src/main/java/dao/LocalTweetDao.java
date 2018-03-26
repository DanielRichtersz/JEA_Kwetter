package dao;

import entity.Tweet;
import mock.MockDatabase;

import java.util.List;

public class LocalTweetDao implements TweetDAO {

    private MockDatabase mdb = new MockDatabase();

    @Override
    public Tweet getTweet() {
        return mdb.getTweetList().get(0);
    }

    @Override
    public List<Tweet> getTweets() {
        return mdb.getTweetList();
    }

    @Override
    public boolean insertTweet(Tweet tweet) {
        try {
            mdb.getTweetList().add(tweet);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteTweet(Tweet tweet) {
        List<Tweet> tweets = mdb.getTweetList();
        for (Tweet t : tweets) {
            if (t == tweet){
                mdb.getTweetList().remove(tweet);
                return true;
            }
        }
        return false;
    }
}
