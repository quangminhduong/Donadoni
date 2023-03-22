package donadoni.controllers.movies;

import donadoni.dao.CinemaDao;
import donadoni.dao.MovieDao;
import donadoni.dao.SessionDao;
import donadoni.models.Cinema;
import donadoni.models.Movie;
import donadoni.models.Session;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class MovieListControllerCinema implements Handler {

    static final String TEMPLATE = Views.templatePath("movies/list.html");

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        List<Movie> movies = new ArrayList<>();
        Long cinema_id = ctx.pathParam("cinema_id", Long.class).get();
        Cinema cinema = CinemaDao.INSTANCE.get(cinema_id);
        ArrayList<Long> movie_ids = SessionDao.INSTANCE.getDistictMovies(cinema_id);
        Movie movie;
        for (Long movie_id : movie_ids) {
            movie = MovieDao.INSTANCE.get(movie_id);
            movies.add(movie);
            }

        model.put("movies", movies);
        model.put("cinema", cinema);
        ctx.render(TEMPLATE, model);
    }
}
