package donadoni.controllers.booking;

import donadoni.dao.SessionDao;
import donadoni.dao.UserDao;
import donadoni.models.Session;
import donadoni.models.User;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class PaymentController implements Handler {

    static final String TEMPLATE = Views.templatePath("booking/payment.html");

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        User user = UserDao.INSTANCE.get(ctx.formParam("user_id", Long.class).get());
        int amount = ctx.formParam("amount", Integer.class).get();
        Session session = SessionDao.INSTANCE.get(ctx.formParam("session_id", Long.class).get());


        Map<String, Object> model = Views.baseModel(ctx);
        model.put("session1", session);
        model.put("amount", amount);
        model.put("user", user);
        model.put("total", String.valueOf(getTotal(12, amount)));
        ctx.render(TEMPLATE,model);
    }
    public double getTotal(double price, int amount){
        return (price*amount);
    }
}
