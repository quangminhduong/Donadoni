package donadoni.controllers.cinemas;

import donadoni.dao.CinemaDao;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class CinemaEditController implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        model.put("cinema", CinemaDao.INSTANCE.get(ctx.pathParam("cinema_id", Long.class).get()));
        ctx.render("/views/cinemas/edit.html", model);
    }
}
