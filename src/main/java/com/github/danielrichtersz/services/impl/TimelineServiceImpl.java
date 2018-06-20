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
            calendar.setTimeInMillis(Long.valueOf(startdate));
            start = calendar.getTime();
            calendar.setTimeInMillis(Long.valueOf(enddate));
            end = calendar.getTime();
        } catch (Exception e) {
            throw new BadRequestException("The dates could not be parsed");
        }

        User user = userDAOLocal.getByID(userID);

        List<Tweet> timeline = new ArrayList<Tweet>();
        if (!user.getFollowing().isEmpty()) {
            for (User following : user.getFollowing()) {
                timeline.addAll(tweetDAOLocal.getTweetsByUserIDBetweenDates(start, end, userID));
            }
        }

        return timeline;
    }
}
