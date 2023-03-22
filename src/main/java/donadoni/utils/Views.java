package donadoni.utils;

import donadoni.auth.AccessManager;
import donadoni.dao.CinemaDao;
import donadoni.dao.ReservationDao;
import donadoni.dao.SessionDao;
import donadoni.models.*;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Utility Class with useful methods for Views of our application
 *
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class Views {

    private static final String TEMPLATE_BASE = "/views/";


    public static String templatePath(String template) {
        return TEMPLATE_BASE.concat(template);
    }
    public static Map<String, Object> baseModel(Context ctx) throws SQLException {
        Map<String, Object> model = new HashMap<>();
        //add currentUser information
        User user = AccessManager.getSessionCurrentUser(ctx);
        String role = user.getRole().toString();
        List<Session> sessions = new ArrayList<>();
        List<Cinema> cinemas = CinemaDao.INSTANCE.getAll();
        if (user.getRole() != Role.ANONYMOUS){
            List<Reservation> reservations = ReservationDao.INSTANCE.getByUser(user.getId());
            for (Reservation reservation:reservations){
                Session session = SessionDao.INSTANCE.get(reservation.getSession_id());
                sessions.add(session);
            }
        }
        model.put("URL", ctx.path());
        model.put("user", user);
        model.put("role", role);
        model.put("user_logged_in", (user.getRole() == Role.ANONYMOUS ? false : true));
        model.put("sessions", sessions);
        model.put("cinemas", cinemas);
        return model;
    }
}
