package services.interfaces;

import entity.Tweet;

import java.util.List;

public interface TimelineService {
    List<Tweet> getTimelineByUserID(long userID, String startdate, String enddate);
}
