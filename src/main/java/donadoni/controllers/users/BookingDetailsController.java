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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class BookingDetailsController implements Handler {

    static final String TEMPLATE = Views.templatePath("users/bookingdetails.html");


    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Long user_id = ctx.pathParam("id", Long.class).get();
        Long session_id = ctx.pathParam("session_id", Long.class).get();

        Session session = SessionDao.INSTANCE.get(session_id);
        Movie movie = MovieDao.INSTANCE.get(session.getMovie_id());
        List<Reservation> reservations = ReservationDao.INSTANCE.getByUserAndSession(user_id, session_id);
        Cinema cinema = CinemaDao.INSTANCE.get(session.getCinema_id());


        Map<String, Object> model = Views.baseModel(ctx);
        model.put("reservations", reservations);
        model.put("session1", session);
        model.put("movie", movie);
        model.put("cinema", cinema);
        ctx.render(TEMPLATE, model);
    }
}
