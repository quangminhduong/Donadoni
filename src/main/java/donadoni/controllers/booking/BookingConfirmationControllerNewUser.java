package donadoni.controllers.booking;

import donadoni.auth.AccessManager;
import donadoni.dao.MovieDao;
import donadoni.dao.SessionDao;
import donadoni.dao.UserDao;
import donadoni.models.Movie;
import donadoni.models.Role;
import donadoni.models.Session;
import donadoni.models.User;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class BookingConfirmationControllerNewUser implements Handler {


    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        User user = new User(ctx.formParam("email", String.class).get(),ctx.formParam("name", String.class).get(), Role.valueOf("REGISTERED"));
        user = UserDao.INSTANCE.create(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Session session = SessionDao.INSTANCE.get(ctx.formParam("session_id", Long.class).get());
        Integer amount = ctx.formParam("amount", Integer.class).get();
        Movie movie = MovieDao.INSTANCE.get(session.getMovie_id());
        String time = session.getShowing().format(formatter);


        Map<String, Object> model = Views.baseModel(ctx);
        model.put("session1", session);
        model.put("user", user);
        model.put("amount", amount);
        model.put("movie", movie);
        model.put("time", time);
        AccessManager.loginUser(ctx,user);
        ctx.render("/views/booking/payment.html", model);
    }


}
