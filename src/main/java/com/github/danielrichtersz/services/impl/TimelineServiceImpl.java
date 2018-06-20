package services.impl;


import dao.TweetDAOLocal;
import dao.UserDAOLocal;
import entity.Tweet;
import entity.User;
import services.interfaces.TimelineService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TimelineServiceImpl implements TimelineService {

    @Inject
    UserDAOLocal userDAOLocal;

    @Inject
    TweetDAOLocal tweetDAOLocal;

    @Override
    public List<Tweet> getTimelineByUserID(long userID, String startdate, String enddate) {
        // Parse the time
        Calendar calendar = Calendar.getInstance();
        Date start;
        Date end;

        try {

            // Parse the dates between tweets will be retrieved
            calendar.setTimeInMillis(Long.valueOf(startdate));
            start = calendar.getTime();
            calendar.setTimeInMillis(Long.valueOf(enddate));
            end = calendar.getTime();
        } catch (Exception e) {
            throw new BadRequestException("The dates could not be parsed");
        }

        // Get the user by this userid
        User user = userDAOLocal.getByID(userID);

        // Get all tweets that belong to the users that this user follows
        List<Tweet> timeline = new ArrayList<Tweet>();
        if (!user.getFollowing().isEmpty()) {
            for (User following : user.getFollowing()) {
                timeline.addAll(tweetDAOLocal.getTweetsByUserIDBetweenDates(start, end, following.getId()));
            }
        }
        // Add all own tweets
        List<Tweet> ownTweets = tweetDAOLocal.getTweetsByUserIDBetweenDates(start, end, user.getId());
        timeline.addAll(ownTweets);

        // Sort the timeline
        timeline.sort(Tweet.tweetComparator);
        return timeline;
    }
}
