package donadoni;

import donadoni.dao.DBUtils;
import donadoni.dao.MovieDao;
import donadoni.models.Movie;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {
    class MovieDaoTest {
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
        void create() throws SQLException {
            Movie m = new Movie("Goodfellas", 1990);
            Movie m2 = MovieDao.INSTANCE.create(m);
            assertNotNull(m2.getId());
            assertEquals(3, m2.getId());

        }
    }
}