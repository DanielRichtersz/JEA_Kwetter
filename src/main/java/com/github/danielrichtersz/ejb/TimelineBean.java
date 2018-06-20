package ejb;

import entity.Tweet;
import services.impl.TimelineServiceImpl;
import services.interfaces.TimelineService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/timeline")
@ApplicationScoped
public class TimelineBean implements TimelineBeanRemote {

    @Inject
    TimelineService timelineService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/load")
    @Override
    public List<Tweet> getTimelineByUserID(@HeaderParam("token") String token,
                                           @HeaderParam("userid") String userId,
                                           @FormParam("startdate") String startdate,
                                           @FormParam("enddate") String enddate) {
        return timelineService.getTimelineByUserID(Long.valueOf(userId), startdate, enddate);
    }
}
