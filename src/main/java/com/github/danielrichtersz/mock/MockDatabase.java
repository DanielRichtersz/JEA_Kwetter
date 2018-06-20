package mock;

import entity.Email;
import entity.Tweet;
import entity.TweetType;
import entity.User;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class MockDatabase {

    private static MockDatabase db;

    List<Tweet> tweetList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<Email> emailList = new ArrayList<>();

    @PostConstruct
    private void initialize() {
        User follower;
        User following;
        User user;
        Tweet tweet;
        Tweet tweet2;
        Tweet tweet3;
        Email email;

        email = new Email();
        email.setEmail("test@mail.com");
        email.setConfirmed(false);
        email.setId(this.emailList.size() + 1);
        this.emailList.add(email);

        Email email1 = new Email();
        email1.setEmail("test1@mail.com");
        email1.setConfirmed(true);
        email1.setId(this.emailList.size() + 1);
        this.emailList.add(email1);
        Email email2 = new Email();
        email2.setEmail("test2@mail.com");
        email2.setConfirmed(false);
        email2.setId(this.emailList.size() + 1);
        this.emailList.add(email2);

        List<String> interests = new ArrayList<>();
        interests.add("Food");
        interests.add("Something interesting");

        follower = new User();
        follower.setEmail(email1);
        follower.setPhonenumber("0612345678");
        follower.setFirstName("Follow");
        follower.setLastName("Er");
        follower.setPassword("Password1");
        follower.setProfilePictureURL("./url/another/folder/picture2.jpeg");
        follower.setInterests(interests);

        following = new User();
        following.setEmail(email2);
        following.setPhonenumber("0687654321");
        following.setFirstName("Follow");
        following.setLastName("Ing");
        following.setPassword("Password2");
        following.setProfilePictureURL("./url/another/folder/picture3.jpeg");
        following.setInterests(interests);

        List<User> followers = new ArrayList<>();
        follower.setId((long) userList.size() + 1);
        followers.add(follower);
        userList.add(follower);


        List<User> followings = new ArrayList<>();
        following.setId((long) userList.size() + 1);
        followings.add(following);
        userList.add(following);

        user = new User();
        user.setEmail(email);
        user.setPhonenumber("0621374675");
        user.setFirstName("Dan");
        user.setLastName("Iel");
        user.setPassword("Password");
        user.setProfilePictureURL("./url/another/folder/picture.jpeg");
        user.setInterests(interests);
        user.setFollowers(followers);
        user.setFollowing(followings);
        user.setId((long)userList.size() + 1);
        userList.add(user);

        // Tweets
        tweet = new Tweet();
        tweet.setMessage("First tweet");
        tweet.setOwner(user);
        tweet.setDateCreated(new Date());
        tweet.setType(TweetType.InititalTweet);

        tweet2 = new Tweet();
        tweet2.setMessage('"' + tweet.getMessage() + '"' + " - Such nice tweet.");
        tweet2.setOwner(following);
        tweet2.setDateCreated(new Date());
        tweet2.setType(TweetType.ReTweet);


        tweet3 = new Tweet();
        tweet3.setMessage("I am following " + user.getFirstName() + " " + user.getLastName() + "! ");
        tweet3.setOwner(follower);
        tweet3.setDateCreated(new Date());
        tweet3.setType(TweetType.InititalTweet);

        tweet.setId((long)tweetList.size() + 1);
        tweet.addLike(following.getId());
        tweetList.add(tweet);

        tweet2.setId((long)tweetList.size() + 1);
        tweet2.addLike(follower.getId());
        tweet2.addLike(user.getId());
        tweetList.add(tweet2);

        tweet3.setId((long)tweetList.size() + 1);
        tweet2.addLike(following.getId());
        tweetList.add(tweet3);
    }

    public List<Tweet> getTweetList() {
        return tweetList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Email> getEmailList() {
        return emailList;
    }


}
