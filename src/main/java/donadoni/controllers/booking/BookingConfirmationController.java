package donadoni.controllers.booking;

import com.sendgrid.*;
import donadoni.dao.MovieDao;
import donadoni.dao.ReservationDao;
import donadoni.dao.SessionDao;
import donadoni.dao.UserDao;
import donadoni.models.Movie;
import donadoni.models.Reservation;
import donadoni.models.Session;
import donadoni.models.User;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class BookingConfirmationController implements Handler {


    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Session session = SessionDao.INSTANCE.get(ctx.formParam("session_id", Long.class).get());
        User user = UserDao.INSTANCE.get(ctx.formParam("user_id", Long.class).get());
        Integer amount = ctx.formParam("amount", Integer.class).get();
        Movie movie = MovieDao.INSTANCE.get(session.getMovie_id());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = session.getShowing().format(formatter);


        List<Reservation> reservations = ReservationDao.INSTANCE.getBySession(session.getSession_id());
        List<Long> currentseats = new ArrayList<>();
        Long seat_id = Long.valueOf(1);
        for (Reservation reservation : reservations) {
            currentseats.add(reservation.getSeat_id());
        }
        int i = 1;
        while (i <= amount) {
            if (!currentseats.contains(seat_id)) {
                Reservation newreservation = new Reservation(session.getSession_id(), user.getId(), seat_id);
                newreservation = ReservationDao.INSTANCE.addReservation(newreservation);
                seat_id++;
                i++;
            } else {
                seat_id++;
            }
        }
        Email from = new Email("admin@donadonicinema.com");
        String subject = "Ticket confirmation";
        Email to = new Email("");
        Content content = new Content("text/plain", "Tickets booked");
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        to.setEmail(user.getEmail());
        Mail mail = new Mail(from, subject, to, content);
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }


        Map<String, Object> model = Views.baseModel(ctx);
        model.put("session1", session);
        model.put("amount", amount);
        model.put("movie", movie);
        model.put("time", time);
        ctx.render("/views/booking/bookingconfirmed.html", model);
    }


}
