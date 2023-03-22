package donadoni.controllers.movies;

import donadoni.dao.MovieDao;
import donadoni.dao.SessionDao;
import donadoni.models.Cinema;
import donadoni.models.Movie;
import donadoni.models.Session;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class MovieListController implements Handler {

    static final String TEMPLATE = Views.templatePath("movies/list.html");

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        List<Movie> movies = MovieDao.INSTANCE.getAll();
        Cinema cinema = new Cinema("our Theaters", null, null);
        cinema.setCinema_id((long) 0);
        model.put("movies", movies);
        model.put("cinema", cinema);
        ctx.render(TEMPLATE, model);
    }
}
