package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.dao.TweetDAOLocal;
import com.github.danielrichtersz.dao.UserDAOLocal;
import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Path("/timeline")
@ApplicationScoped
public class TimelineBean implements TimelineBeanRemote {

    @Inject
    UserDAOLocal userDAOLocal;

    @Inject
    TweetDAOLocal tweetDAOLocal;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}")
    @Override
    public List<Tweet> getTimelineByUserID(@PathParam("userid") long userID, @FormParam("startdate") String startdate,
                                           @FormParam("enddate") String enddate) {
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
