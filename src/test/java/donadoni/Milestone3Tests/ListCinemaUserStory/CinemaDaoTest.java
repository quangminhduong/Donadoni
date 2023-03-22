package donadoni.Milestone3Tests.ListCinemaUserStory;

import donadoni.dao.CinemaDao;
import donadoni.dao.DBUtils;
import donadoni.models.Cinema;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CinemaDaoTest {
    @BeforeEach
    void setup() {
        //Use a different DB for testing
        String DB = "jdbc:h2:file:./target/donadonitest";
        //Set the environment property so that DBUtils point to the same file DB.
        System.setProperty(DBUtils.DB_URL, DB);
        //Get Flyway instance
        Flyway flyway = Flyway.configure().dataSource(DB, "sa", "").load();
        //Clean testing DB before each test to make sure we have a consistent state
        flyway.clean();
        //Set up db
        flyway.migrate();
    }
    @Test
    void getAll() throws SQLException {
        List<Cinema> cinemas = CinemaDao.INSTANCE.getAll();
        assertEquals(3, cinemas.size());
    }

}