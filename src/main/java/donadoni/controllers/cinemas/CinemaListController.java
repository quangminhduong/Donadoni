package donadoni.controllers.cinemas;

import donadoni.dao.CinemaDao;
import donadoni.models.Cinema;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class CinemaListController implements Handler {

    static final String TEMPLATE = Views.templatePath("cinemas/list.html");

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        List<Cinema> cinemas = CinemaDao.INSTANCE.getAll();


        model.put("cinemas", cinemas);
        ctx.render(TEMPLATE, model);
    }
}
