package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.entity.Tweet;
import com.github.danielrichtersz.services.impl.TimelineServiceImpl;
import com.github.danielrichtersz.services.interfaces.TimelineService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/timeline")
@ApplicationScoped
public class TimelineBean implements TimelineBeanRemote {

    @Inject
    TimelineServiceImpl timelineService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}")
    @Override
    public List<Tweet> getTimelineByUserID(@PathParam("userid") long userID, @FormParam("startdate") String startdate,
                                           @FormParam("enddate") String enddate) {
        return timelineService.getTimelineByUserID(userID, startdate, enddate);
    }
}
