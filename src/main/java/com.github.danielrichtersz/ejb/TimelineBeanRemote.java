package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.entity.Tweet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface TimelineBeanRemote {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userid}")
    List<Tweet> getTimelineByUserID(@PathParam("userid") long userID, @FormParam("startdate") String startdate,
                                    @FormParam("enddate") String enddate);
}
