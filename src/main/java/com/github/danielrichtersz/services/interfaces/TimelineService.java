package com.github.danielrichtersz.services.interfaces;

import com.github.danielrichtersz.entity.Tweet;

import java.util.List;

public interface TimelineService {
    List<Tweet> getTimelineByUserID(long userID, String startdate, String enddate);
}
