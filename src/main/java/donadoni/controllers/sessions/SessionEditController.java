package donadoni.controllers.sessions;

import donadoni.dao.MovieDao;
import donadoni.dao.SessionDao;
import donadoni.models.Movie;
import donadoni.models.Session;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class SessionEditController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        Session session = SessionDao.INSTANCE.get(ctx.pathParam("session_id", Long.class).get());
        Movie movie = MovieDao.INSTANCE.get(session.getMovie_id());
        model.put("this_session", session);
        model.put("movie", movie);

        ctx.render("/views/sessions/edit.html", model);
    }
}
