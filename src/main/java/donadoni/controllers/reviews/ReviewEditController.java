package donadoni.controllers.reviews;

import donadoni.dao.ReviewDao;
import donadoni.models.Review;
import donadoni.models.User;
import donadoni.utils.Views;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ReviewEditController implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = Views.baseModel(ctx);
        User user = (User) model.get("user");
        Review review = ReviewDao.INSTANCE.get(ctx.formParam("review_id", Long.class).get());

        review.setComments(ctx.formParam("review"));
        review.setRating(ctx.formParam("rating", Integer.class).get());
        ReviewDao.INSTANCE.update(review);
        ctx.redirect("/movie/" + ctx.formParam("cinema_id", Long.class).get() + "/" + ctx.formParam("movie_id", Long.class).get());


    }

}
