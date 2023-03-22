package donadoni.controllers.users;

import com.sendgrid.*;
import donadoni.dao.MovieDao;
import donadoni.dao.ReservationDao;
import donadoni.dao.SessionDao;
import donadoni.dao.UserDao;
import donadoni.models.Movie;
import donadoni.models.Reservation;
import donadoni.models.Session;
import donadoni.models.User;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class TicketCancelController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Reservation reservation = ReservationDao.INSTANCE.get(ctx.pathParam("reservation_id", Long.class).get());
        Long user_id = reservation.getUser_id();
        String user_email = UserDao.INSTANCE.get(user_id).getEmail();
        ReservationDao.INSTANCE.delete(reservation);

        Email from = new Email("admin@donadonicinema.com");
        String subject = "Ticket Cancellation";
        Email to = new Email("");
        Content content = new Content("text/plain", "Tickets canceled");
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        to.setEmail(user_email);
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

        ctx.redirect("/");
    }
}
