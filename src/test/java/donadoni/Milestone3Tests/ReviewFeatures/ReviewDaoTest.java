package donadoni.Milestone3Tests.ReviewFeatures;

import donadoni.dao.DBUtils;
import donadoni.dao.ReviewDao;
import donadoni.models.Review;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReviewDaoTest {

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
        List<Review> reviews = ReviewDao.INSTANCE.getAll();
        assertEquals(7, reviews.size());
    }

    @Test
    void delete() throws SQLException {
        Review review = ReviewDao.INSTANCE.get(Long.valueOf(1));
        ReviewDao.INSTANCE.delete(review);
        assertThrows(SQLException.class, () -> ReviewDao.INSTANCE.get(Long.valueOf(1)));
    }

    @Test
    void getByUser() throws SQLException {
        List<Review> reviews = ReviewDao.INSTANCE.getByUser(Long.valueOf(3));
        assertEquals(3, reviews.size());
    }

    @Test
    void getByMovie() throws SQLException {
        List<Review> reviews = ReviewDao.INSTANCE.getByMovie(Long.valueOf(1));
        assertEquals(3, reviews.size());
    }

    @Test
    void get() throws SQLException {
        Review review = ReviewDao.INSTANCE.get(Long.valueOf(1));
        assertEquals("This is a great movie!1", review.getComments());
    }

    @Test
    void addReview() throws SQLException {
        Review new_review = new Review(Long.valueOf(3), Long.valueOf(1), 4, "This is Dope!", 0);
        ReviewDao.INSTANCE.addReview(new_review);
        assertEquals(8, new_review.getReview_id());
    }

    @Test
    void update() throws SQLException {
        Review review = ReviewDao.INSTANCE.get(Long.valueOf(1));
        assertNotNull(review);
        assertEquals("This is a great movie!1", review.getComments());
        review.setComments("I changed my mind");
        ReviewDao.INSTANCE.update(review);
        review = ReviewDao.INSTANCE.get(Long.valueOf(1));
        assertNotNull(review);
        assertEquals("I changed my mind", review.getComments());
    }

}