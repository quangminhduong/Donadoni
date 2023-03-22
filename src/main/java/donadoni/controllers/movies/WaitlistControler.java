package donadoni.controllers.movies;

import donadoni.dao.ReservationDao;
import donadoni.dao.SessionDao;
import donadoni.dao.WaitlistDao;
import donadoni.models.Session;
import donadoni.models.Waitlist;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class WaitlistControler implements Handler {


    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Long session_id = Long.valueOf(Objects.requireNonNull(ctx.formParam("session_id")));
        Long user_id = Long.valueOf(Objects.requireNonNull(ctx.formParam("user_id")));
        Waitlist wait = new Waitlist(session_id, user_id);
        wait = WaitlistDao.INSTANCE.addWaitList(wait);
        Map<String, Object> model = Views.baseModel(ctx);
        ctx.render("/views/booking/waitlist.html",model);
        }


}
