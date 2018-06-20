package ejb;

import entity.Tweet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface TimelineBeanRemote {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/load")
    List<Tweet> getTimelineByUserID(@HeaderParam("token") String token,
                                    @HeaderParam("userid") String userId,
                                    @FormParam("startdate") String startdate,
                                    @FormParam("enddate") String enddate);
}
