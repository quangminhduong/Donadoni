package donadoni.controllers.sessions;

import donadoni.dao.MovieDao;
import donadoni.dao.SessionDao;
import donadoni.models.Movie;
import donadoni.models.Session;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class SessionUpdateController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {

        String showingStr = ctx.formParam("showing");
        LocalDateTime showing = LocalDateTime.parse(showingStr);

        Session session = SessionDao.INSTANCE.get(ctx.formParam("session_id", Long.class).get());
        Movie movie = MovieDao.INSTANCE.get(session.getMovie_id());
        session.setShowing(showing);
        session.setAvailable(ctx.formParam("available",Integer.class).get());
        session.setStatus(ctx.formParam("status"));
        SessionDao.INSTANCE.update(session);
        ctx.redirect("/movie/" + session.getCinema_id() +"/"+ session.getMovie_id());
    }
}
