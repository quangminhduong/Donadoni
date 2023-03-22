package donadoni.controllers.movies;

import donadoni.dao.*;
import donadoni.models.Movie;
import donadoni.models.Review;
import donadoni.models.Session;
import donadoni.models.User;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class MovieShowController implements Handler {

    static final String TEMPLATE = Views.templatePath("movies/show.html");


    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        User user = (User) model.get("user");
        Long movie_id = ctx.pathParam("id", Long.class).get();
        Long cinema_id = ctx.pathParam("cinema_id", Long.class).get();
        List<User> users = UserDao.INSTANCE.getAll();
        Review user_review = new Review();

        Movie movie = MovieDao.INSTANCE.get(movie_id);
        List<Session> sessions = SessionDao.INSTANCE.getByCinemaAndMovie(movie_id, cinema_id);
        List<String> times = new ArrayList<>();
        for (Session session : sessions) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String time = session.getShowing().format(formatter);
            times.add(time);
            Integer booked = ReservationDao.INSTANCE.countSeatsbySession(session.getSession_id());
            if (session.getStatus() == null) {
                session.updateStatus();
            }
            if (session.getStatus().equals("OPEN")) {
                if (booked.equals(session.getAvailable())) {
                    session.setStatus("FULL");
                }
            }
        }
        int sum = 0;
        int count = 0;
        int avg_rating = 0;
        List<Review> reviews = ReviewDao.INSTANCE.getByMovie(movie_id);
        if (!reviews.isEmpty()) {
            reviews.sort(Comparator.comparing(Review::getLikes).reversed());
            for (Review review : reviews) {
                sum += review.getRating();
                count++;
                if (review.getUser_id().equals(user.getId())) {
                    user_review = review;
                }
                avg_rating = Integer.valueOf(sum / count);
            }
        }

        model.put("user_review", user_review);
        model.put("count", count);
        model.put("avg_rating", avg_rating);
        model.put("movie", movie);
        model.put("users", users);
        model.put("sessions", sessions);
        model.put("times", times);
        model.put("reviews", reviews);
        model.put("cinema_id", cinema_id);
        ctx.render(TEMPLATE, model);

    }


}
