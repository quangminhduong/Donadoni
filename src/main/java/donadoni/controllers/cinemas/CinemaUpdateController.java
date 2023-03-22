package donadoni.controllers.cinemas;

import donadoni.dao.CinemaDao;
import donadoni.dao.MovieDao;
import donadoni.models.Cinema;
import donadoni.models.Movie;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class CinemaUpdateController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {

        Cinema cinema = CinemaDao.INSTANCE.get(ctx.pathParam("cinema_id", Long.class).get());
        cinema.setCinema_name(ctx.formParam("cinema_name", String.class).get());
        cinema.setCinema_address(ctx.formParam("address", String.class).get());
        cinema.setCinema_phone(ctx.formParam("phone"));
        CinemaDao.INSTANCE.update(cinema);
        ctx.redirect("/movies/cinema/" + cinema.getCinema_id());
    }
}
