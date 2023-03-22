package donadoni.controllers.reviews;

import donadoni.dao.ReviewDao;
import donadoni.models.Review;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class ReviewCreateController implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Review review = new Review(
                ctx.formParam("user_id", Long.class).get(), ctx.formParam("movie_id", Long.class).get(),
                ctx.formParam("rating", Integer.class).get(), ctx.formParam("review"), 1);

        review = ReviewDao.INSTANCE.addReview(review);
        ctx.redirect("/movie/" + ctx.formParam("cinema_id", Long.class).get() + "/" + ctx.formParam("movie_id", Long.class).get());

    }

}
