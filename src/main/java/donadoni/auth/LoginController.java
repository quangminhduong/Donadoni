package donadoni.auth;

import donadoni.dao.UserDao;
import donadoni.models.User;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Map;


public class LoginController implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        model.put("URL", ctx.formParam("URL"));
        String TEMPLATE = ctx.path();
        String email = ctx.formParam("email");
        String pw = ctx.formParam("password");

        if (BCrypt.checkpw(pw, UserDao.INSTANCE.getUserPasswordHash(email))) {
            User user = UserDao.INSTANCE.getByEmail(email);
            AccessManager.loginUser(ctx,user);
            ctx.redirect(ctx.formParam("URL"));
        }else {
            ctx.render("/views/auth/loginfailed.html", model);
        }

    }
}
