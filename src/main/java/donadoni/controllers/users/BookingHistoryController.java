package donadoni.controllers.users;

import donadoni.dao.CinemaDao;
import donadoni.dao.MovieDao;
import donadoni.dao.ReservationDao;
import donadoni.dao.SessionDao;
import donadoni.models.Cinema;
import donadoni.models.Movie;
import donadoni.models.Reservation;
import donadoni.models.Session;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class BookingHistoryController implements Handler {

    static final String TEMPLATE = Views.templatePath("users/bookinghistory.html");


    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Long user_id = ctx.pathParam("id", Long.class).get();

        List<Session> sessions = SessionDao.INSTANCE.getByUser(user_id);
        HashMap map = ReservationDao.INSTANCE.countSeatsbyUserPerSession(user_id);


        List<Movie> movies = MovieDao.INSTANCE.getAll();
        Map<String, Object> model = Views.baseModel(ctx);
        model.put("sessions", sessions);
        model.put("amount", map);
        model.put("movies", movies);
        ctx.render(TEMPLATE,model);
    }
}
