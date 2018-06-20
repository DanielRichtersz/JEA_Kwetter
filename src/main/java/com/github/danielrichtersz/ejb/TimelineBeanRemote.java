package ejb;

import entity.Tweet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface TimelineBeanRemote {

    List<Tweet> getTimelineByUserID(String token,
                                    String userId,
                                    String startdate,
                                    String enddate);
}
