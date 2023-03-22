package donadoni;

import donadoni.auth.AccessManager;
import donadoni.auth.LoginController;
import donadoni.auth.LoginRedirectController;
import donadoni.auth.LogoutRedirectController;
import donadoni.controllers.booking.*;
import donadoni.controllers.cinemas.*;
import donadoni.controllers.movies.*;
import donadoni.controllers.reviews.ReviewCreateController;
import donadoni.controllers.reviews.ReviewEditController;
import donadoni.controllers.sessions.*;
import donadoni.controllers.users.BookingDetailsController;
import donadoni.controllers.users.BookingHistoryController;
import donadoni.controllers.users.TicketCancelConfirmationController;
import donadoni.controllers.users.TicketCancelController;
import donadoni.controllers.welcome.WelcomeController;
import donadoni.models.Role;
import donadoni.utils.Views;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import static io.javalin.core.security.SecurityUtil.roles;

/**
 * Main Application Class.
 * <p>
 * Running this class as regular java application will start the HTTP Server and configure our web application.
 *
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class App {


    public static void main(String[] args) {

        //Create our HTTP server and listen in port 7000
        Javalin app = Javalin.create(config -> {
            config.enableDevLogging();
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));
            config.addStaticFiles("public/");
            config.accessManager(new AccessManager());
        }).start(7000);

        //Register the engine to process html views
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

        //Configure Web Routes
        configureRoutes(app);

    }

    public static void configureRoutes(Javalin app) {

        app.get(WelcomeController.URL, new WelcomeController());

        //Paths should be in Variables, but kept here for learning purposes.

        app.get("/movies", new MovieListController());
        app.get("/movie/:cinema_id/:id", new MovieShowController());
        app.get("movies/cinema/:cinema_id", new MovieListControllerCinema());
        app.get("/movies/new", new MovieNewController(), roles(Role.ADMIN)); //Secured for ADMINs only
        app.post("/movies", new MovieCreateController(), roles(Role.ADMIN));//Secured for ADMINs only
        app.get("/movies/:id/edit", new MovieEditController(), roles(Role.ADMIN));//Secured for ADMINs only
        app.post("movies/:id", new MovieUpdateController(), roles(Role.ADMIN));//Secured for ADMINs only

        app.post("/review/new", new ReviewCreateController());
        app.post("/review/edit", new ReviewEditController());

        app.get("/cinemas/:cinema_id/edit", new CinemaEditController(), roles(Role.ADMIN));//Secured for ADMINs only
        app.post("/cinemas/:cinema_id", new CinemaUpdateController(), roles(Role.ADMIN));//Secured for ADMINs only
        app.post("/cinemas", new CinemaCreateController(), roles(Role.ADMIN));
        app.get("/cinemas/new", new CinemaNewController(), roles(Role.ADMIN));
        app.get("/cinemas/list", new CinemaListController());
        app.get("/cinemas/:cinema_id/delete", new CinemaDeleteController(), roles(Role.ADMIN));


        app.get("/session/:id/:session_id/booking", new BookingController());
        app.get("/session/:session_id/edit", new SessionEditController(), roles(Role.ADMIN));
        app.post("/session/update", new SessionUpdateController(), roles(Role.ADMIN));
        app.post("/session", new SessionCreateController(), roles(Role.ADMIN));
        app.get("/session/new", new SessionNewController(), roles(Role.ADMIN));
        app.get("/session/:session_id/cancel", new SessionCancelConfirmationController(), roles(Role.ADMIN));
        app.post("/session/delete", new SessionCancelController(), roles(Role.ADMIN));

        app.post("check", new AvailabilityController());
        app.post("waitlist", new WaitlistControler());
        app.post("/booking/payment", new PaymentController());
        app.post("/booking/confirm", new BookingConfirmationController());
        app.post("/booking/register", new BookingConfirmationControllerNewUser());
        app.get("/booking/payment", new PaymentController());

        app.get("/user/:id/bookings", new BookingHistoryController(),roles(Role.REGISTERED));
        app.get("/user/:id/bookings/:session_id", new BookingDetailsController(),roles(Role.REGISTERED));
        app.get("/reservation/:reservation_id/delete", new TicketCancelController(),roles(Role.REGISTERED));
        app.get("/reservation/:reservation_id/confirm", new TicketCancelConfirmationController(),roles(Role.REGISTERED));


        //Auth
        app.post("/login", new LoginRedirectController());
        app.get("/login", ctx -> ctx.render("/views/auth/login.html", Views.baseModel(ctx)));

        app.post("auth", new LoginController());
        app.post("/logout", new LogoutRedirectController());

    }


}
