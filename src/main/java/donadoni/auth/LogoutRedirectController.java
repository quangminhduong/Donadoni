package donadoni.auth;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;


public class LogoutRedirectController implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        AccessManager.logout(ctx);
        ctx.redirect(ctx.formParam("URL"));
    }
}
