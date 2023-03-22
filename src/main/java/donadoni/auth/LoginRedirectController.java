package donadoni.auth;

import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class LoginRedirectController implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        model.put("URL", ctx.formParam("URL"));
        ctx.render("/views/auth/login.html", model);
    }
}
