package dao;

import entity.Tweet;
import mock.MockDatabase;

import javax.ejb.Stateful;
import java.util.List;

@Stateful
public class LocalTweetDaoImpl implements TweetDAO {

    private MockDatabase db = new MockDatabase();

    @Override
    public Tweet getTweet() {
        return db.getTweetList().get(0);
    }

    @Override
    public List<Tweet> getTweets() {
        return db.getTweetList();
    }

    @Override
    public void create(Tweet tweet) {
            db.getTweetList().add(tweet);
    }

    @Override
    public void delete(Tweet tweet) {
        List<Tweet> tweets = db.getTweetList();
        for (Tweet t : tweets) {
            if (t == tweet){
                db.getTweetList().remove(tweet);
            }
        }
    }
}
