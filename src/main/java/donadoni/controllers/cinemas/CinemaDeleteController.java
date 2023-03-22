package donadoni.controllers.cinemas;

import com.sendgrid.*;
import donadoni.dao.*;
import donadoni.models.*;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class CinemaDeleteController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {

        Cinema cinema = CinemaDao.INSTANCE.get(ctx.pathParam("cinema_id", Long.class).get());
        List<Session> sessions = SessionDao.INSTANCE.getByCinema(cinema.getCinema_id());
        for (Session session : sessions) {
            for (Reservation reservation : ReservationDao.INSTANCE.getBySession(session.getSession_id())) {
                ReservationDao.INSTANCE.delete(reservation);
            }
            for (Waitlist wait : WaitlistDao.INSTANCE.getBySession(session.getSession_id())) {
                WaitlistDao.INSTANCE.delete(wait);
            }
            SessionDao.INSTANCE.delete(session);
        }
        CinemaDao.INSTANCE.delete(cinema);

        ctx.redirect("/movies");
    }
}
