package donadoni.controllers.booking;

import donadoni.dao.MovieDao;
import donadoni.dao.ReservationDao;
import donadoni.dao.SeatDao;
import donadoni.dao.SessionDao;
import donadoni.models.Movie;
import donadoni.models.Reservation;
import donadoni.models.Session;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class BookingController implements Handler {

    static final String TEMPLATE = Views.templatePath("booking/booking.html");

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Long session_id = ctx.pathParam("session_id",Long.class).get();
        Long movie_id = ctx.pathParam("id", Long.class).get();

        Session session = SessionDao.INSTANCE.get(session_id);
        List<Reservation> reservations = ReservationDao.INSTANCE.getBySession(session_id);
        Movie movie = MovieDao.INSTANCE.get(movie_id);
        Integer seats = ReservationDao.INSTANCE.countSeatsbySession(session_id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = session.getShowing().format(formatter);

        Map<String, Object> model = Views.baseModel(ctx);
        model.put("this_session", session);
        model.put("reservations", reservations);
        model.put("movie", movie);
        model.put("seats", seats);
        model.put("time", time);
        ctx.render(TEMPLATE,model);
    }
}
