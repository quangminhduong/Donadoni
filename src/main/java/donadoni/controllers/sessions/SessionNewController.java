package donadoni.controllers.sessions;

import donadoni.dao.MovieDao;
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
public class SessionNewController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        model.put("this_session", new Session());
        model.put("movies", MovieDao.INSTANCE.getAll());
        ctx.render("/views/sessions/new.html", model);
    }
}
