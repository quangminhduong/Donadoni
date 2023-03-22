package donadoni;

import donadoni.dao.DBUtils;
import donadoni.dao.ReservationDao;
import donadoni.models.Reservation;
import io.javalin.Javalin;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketCancelTest {
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
    void deleteTicket() throws SQLException {
        /* This will test if the reservation is successful removed from the database */
        List<Reservation> reservations = ReservationDao.INSTANCE.getAll();
        Reservation reservation = reservations.get(0);
        ReservationDao.INSTANCE.delete(reservation);
        assertThrows(SQLException.class, () -> ReservationDao.INSTANCE.get(reservation.getReservation_id()));

    }

    @Test
    void TicketAvailableUpdate() throws SQLException{
        /* This will test if the reservation is successful removed from the database */
        int ticketCount = ReservationDao.INSTANCE.countSeatsbySession((long)1);
        List<Reservation> reservations = ReservationDao.INSTANCE.getBySession((long)1);
        ReservationDao.INSTANCE.delete(reservations.get(0));
        int newTicketcount = ReservationDao.INSTANCE.countSeatsbySession((long)1);
        assertEquals(ticketCount-1,newTicketcount);
    }
}