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

import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class TicketCancelConfirmationController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        Reservation reservation = ReservationDao.INSTANCE.get(ctx.pathParam("reservation_id", Long.class).get());
        Session session = SessionDao.INSTANCE.get(reservation.getSession_id());
        Movie movie = MovieDao.INSTANCE.get(session.getMovie_id());
        Cinema cinema = CinemaDao.INSTANCE.get(session.getCinema_id());

        model.put("session1", session);
        model.put("movie", movie);
        model.put("reservation", reservation);
        model.put("cinema", cinema);

        ctx.render("/views/users/cancelconfirmation.html", model);
    }
}
