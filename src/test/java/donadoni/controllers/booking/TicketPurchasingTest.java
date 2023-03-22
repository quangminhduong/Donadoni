package donadoni.controllers.booking;

import donadoni.App;
import donadoni.dao.DBUtils;
import io.javalin.Javalin;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class TicketPurchasingTest {
    private static final String URL = "http://localhost:1234/";
    static Javalin app = null;

    @BeforeAll
    public static void setup() {
        String DB = "jdbc:h2:file:./target/donadonitest";
        System.setProperty(DBUtils.DB_URL, DB);
        Flyway flyway = Flyway.configure().dataSource(DB, "sa", "").load();
        flyway.clean();
        flyway.migrate();
        app = Javalin.create();
        App.configureRoutes(app);
        app.start(1234);
    }

    @AfterAll
    static void tearDown() {
        app.stop();
    }

    @Test
    void updateAvailable() throws SQLException {

    }
}