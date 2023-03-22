package donadoni.controllers.sessions;

import donadoni.dao.SessionDao;
import donadoni.models.Session;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class SessionCreateController implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {

        String showingStr = ctx.formParam("showing");
        LocalDateTime showing = LocalDateTime.parse(showingStr);
        Session session = new Session(
                ctx.formParam("movie_id", Long.class).get(), ctx.formParam("cinema_id", Long.class).get(), ctx.formParam("available", Integer.class).get(),
                showing);
        session.setStatus(ctx.formParam("status"));

        session = SessionDao.INSTANCE.create(session);
        ctx.redirect("/movie/" + session.getCinema_id() +"/"+ session.getMovie_id());

    }
}
