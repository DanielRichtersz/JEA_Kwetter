package com.github.danielrichtersz.services.impl;

import com.github.danielrichtersz.dao.TweetDAOLocal;
import com.github.danielrichtersz.dao.UserDAOLocal;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.entity.User;
import com.github.danielrichtersz.services.interfaces.TimelineService;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimelineServiceImpl implements TimelineService {

    @Inject
    UserDAOLocal userDAOLocal;

    @Inject
    TweetDAOLocal tweetDAOLocal;

    @Override
    public List<Tweet> getTimelineByUserID(long userID, String startdate, String enddate) {
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
