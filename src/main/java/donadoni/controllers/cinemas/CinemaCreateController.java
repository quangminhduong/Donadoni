package donadoni.controllers.cinemas;

import donadoni.dao.CinemaDao;
import donadoni.models.Cinema;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class CinemaCreateController implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {

        Cinema cinema = new Cinema(ctx.formParam("cinema_name", String.class).get(),ctx.formParam("address", String.class).get(),ctx.formParam("phone", String.class).get());
        cinema = CinemaDao.INSTANCE.create(cinema);
        ctx.redirect("/cinemas/list");
    }
}
