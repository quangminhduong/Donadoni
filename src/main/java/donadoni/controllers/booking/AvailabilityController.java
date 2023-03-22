package donadoni.controllers.booking;

import donadoni.dao.ReservationDao;
import donadoni.dao.SessionDao;
import donadoni.models.Session;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class AvailabilityController implements Handler {


    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Integer amount = Integer.valueOf(Objects.requireNonNull(ctx.formParam("amount")));
        Long session_id = Long.valueOf(Objects.requireNonNull(ctx.formParam("session_id")));
        Session session = SessionDao.INSTANCE.get(session_id);
        Integer seats = ReservationDao.INSTANCE.countSeatsbySession(session_id);
        Integer availableseats = session.getAvailable()-seats;
        Boolean accept = false;
        if (amount<=availableseats){
            accept = true;
        }
        Map<String, Object> model = Views.baseModel(ctx);
        model.put("this_session", session);
        model.put("amount", amount);
        model.put("accept", accept);
        ctx.render("/views/booking/ticketcheck.html", model);
        }


}
