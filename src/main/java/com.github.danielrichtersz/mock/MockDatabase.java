package com.github.danielrichtersz.mock;

import com.github.danielrichtersz.entity.Email;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.entity.TweetType;
import com.github.danielrichtersz.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockDatabase {

    private static MockDatabase db;

    public static MockDatabase getInstance() {
        if (db == null) {
            db = new MockDatabase();
        }
        return db;
    }

    List<Tweet> tweetList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<Email> emailList = new ArrayList<>();

    private MockDatabase(){
        this.initialize();
    }

    private void initialize() {
        User follower;
        User following;
        User user;
        Tweet tweet;
        Tweet tweet2;
        Email email;

        email = new Email();
        email.setEmail("test@mail.com");
        email.setConfirmed(false);
        Email email1 = new Email();
        email1.setEmail("test1@email.com");
        email1.setConfirmed(true);
        Email email2 = new Email();
        email2.setEmail("test2@email.com");
        email2.setConfirmed(false);

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


        tweet = new Tweet();
        tweet.setMessage("First tweet");
        tweet.setOwner(user);
        tweet.setDateCreated(new Date());
        tweet.setType(TweetType.InititalTweet);

        tweet2 = new Tweet();
        tweet2.setMessage('"' + tweet.getMessage() + '"' + " - Such nice tweet");
        tweet2.setOwner(following);
        tweet2.setDateCreated(new Date());
        tweet2.setType(TweetType.ReTweet);

        tweet.setId((long)tweetList.size() + 1);
        tweetList.add(tweet);

        tweet2.setId((long)tweetList.size() + 1);
        tweetList.add(tweet2);

        emailList.add(email);
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
