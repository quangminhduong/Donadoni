package donadoni.controllers.sessions;

import donadoni.dao.CinemaDao;
import donadoni.dao.MovieDao;
import donadoni.dao.ReservationDao;
import donadoni.dao.SessionDao;
import donadoni.models.Movie;
import donadoni.models.Reservation;
import donadoni.models.Session;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import com.sendgrid.*;

import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class SessionCancelController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Session session = SessionDao.INSTANCE.get(ctx.formParam("session_id", Long.class).get());
        Movie movie = MovieDao.INSTANCE.get(session.getMovie_id());
        Integer seats = ReservationDao.INSTANCE.countSeatsbySession(session.getSession_id());
        session.updateStatus();
        Long cinema_id = session.getCinema_id();

        Email from = new Email("admin@donadonicinema.com");
        String subject = "Movie Session Cancellation";
        Email to = new Email("");
        Content content = new Content("text/plain", "Tickets canceled");

        if (!session.getStatus().equals("CLOSED")) {
            if (seats == 0) {
                SessionDao.INSTANCE.delete(session);

            } else {
                session.setStatus("CANCELED");
                SessionDao.INSTANCE.update(session);
                List<String> emails = ReservationDao.INSTANCE.getEmailsBySession(session.getSession_id());
                SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
                for (String email : emails) {
                    Request request = new Request();
                    to.setEmail(email);
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
                }
            }
        }

        ctx.redirect("/movie/"+cinema_id+"/"+movie.getId());
    }
}
