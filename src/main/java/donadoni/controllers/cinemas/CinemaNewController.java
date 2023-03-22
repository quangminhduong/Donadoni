package donadoni.controllers.cinemas;

import donadoni.dao.MovieDao;
import donadoni.models.Cinema;
import donadoni.models.Session;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class CinemaNewController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        model.put("cinema", new Cinema());
        ctx.render("/views/cinemas/new.html", model);
    }
}
