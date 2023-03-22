package donadoni.dao;

import donadoni.models.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReviewDao {

    public static ReviewDao INSTANCE = new ReviewDao();

    private static String SELECT_BY_USER = "SELECT * FROM reviews WHERE user_id=?";
    private static String SELECT_BY_MOVIE = "SELECT * FROM reviews WHERE movie_id=?";
    private static String SELECT_BY_ID = "SELECT * FROM reviews WHERE review_id=?";
    private static String SELECT_ALL = "SELECT * FROM reviews";
    private static String INSERT = "INSERT INTO reviews (movie_id, user_id, rating, comments) VALUES (?,?,?,?)";
    private static String DELETE = "DELETE FROM reviews WHERE review_id=?";
    private static String UPDATE = "UPDATE reviews SET user_id = ?, movie_id = ?, rating = ?,  comments = ?, likes = ? WHERE review_id = ?";

    private ReviewDao() {
    }

    public List<Review> getAll() throws SQLException {
        Connection connection = DBUtils.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(SELECT_ALL);
        List<Review> reviews = new ArrayList<>();
        while (rs.next()) {
            reviews.add(mapReview(rs));
        }
        connection.close();
        return reviews;
    }

    public void delete(Review review) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(DELETE);
        stm.setLong(1, review.getReview_id());
        stm.executeUpdate();
        connection.close();

    }

    public List<Review> getByUser(Long user_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_USER);
        stm.setLong(1, user_id);
        ResultSet rs = stm.executeQuery();
        List<Review> reviews = new ArrayList<>();
        while (rs.next()) {
            reviews.add(mapReview(rs));
        }
        connection.close();
        return reviews;
    }

    public List<Review> getByMovie(Long movie_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_MOVIE);
        stm.setLong(1, movie_id);
        ResultSet rs = stm.executeQuery();
        List<Review> reviews = new ArrayList<>();
        while (rs.next()) {
            reviews.add(mapReview(rs));
        }
        connection.close();
        return reviews;
    }

    public Review get(Long review_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_ID);
        stm.setLong(1, review_id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            Review m = mapReview(rs);
            return m;
        }
        connection.close();
        throw new SQLException("No Review with id = " + review_id);
    }

    public Review addReview(Review review) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        stm.setLong(1, review.getMovie_id());
        stm.setLong(2, review.getUser_id());
        stm.setInt(3, review.getRating());
        stm.setString(4, review.getComments());
        stm.executeUpdate();
        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()) {
            review.setReview_id(generatedKeys.getLong(1));
        } else {
            connection.close();
            ;
            throw new SQLException("Adding review failed, no ID obtained.");
        }
        connection.close();
        return review;
    }

    public int update(Review review) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(UPDATE);
        stm.setLong(1, review.getUser_id());
        stm.setLong(2, review.getMovie_id());
        stm.setInt(3, review.getRating());
        stm.setString(4, review.getComments());
        stm.setInt(5,review.getLikes());
        stm.setLong(6, review.getReview_id());
        return stm.executeUpdate();
    }

    private Review mapReview(ResultSet rs) throws SQLException {
        Review review = new Review(rs.getLong(2), rs.getLong(3), rs.getInt(4), rs.getString(5), rs.getInt(6));
        review.setReview_id(rs.getLong(1));
        return review;
    }

}


