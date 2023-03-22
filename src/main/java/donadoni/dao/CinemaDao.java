package donadoni.dao;

import donadoni.models.Cinema;
import donadoni.models.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A Data Access Object for the Movie model.
 *
 * Implementation could be improved by students.
 *
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class CinemaDao {

    public static CinemaDao INSTANCE = new CinemaDao();

    private static String SELECT_ALL = "SELECT * FROM cinemas";
    private static String SELECT_BY_ID = "SELECT * FROM cinemas WHERE cinema_id=?";
    private static String INSERT = "INSERT INTO cinemas(cinema_name, cinema_address, cinema_phone) VALUES(?,?,?)";
    private static String UPDATE = "UPDATE cinemas SET cinema_name = ?, cinema_address = ?,  cinema_phone = ? WHERE cinema_id=?";
    private static String DELETE = "DELETE FROM cinemas WHERE cinema_id = ?";

    private CinemaDao() {
    }

    public List<Cinema> getAll() throws SQLException {
        Connection connection = DBUtils.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(SELECT_ALL);
        List<Cinema> cinemas = new ArrayList<>();
        while (rs.next()) {
            cinemas.add(mapCinema(rs));
        }
        connection.close();
        return cinemas;
    }

    public Cinema get(Long id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_ID);
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            Cinema m = mapCinema(rs);
            return m;
        }
        connection.close();
        throw new SQLException("No Cinema with id = " + id);
    }

    public Cinema create(Cinema cinema) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, cinema.getCinema_name());
        stm.setString(2, cinema.getCinema_address());
        stm.setString(3, cinema.getCinema_phone());
        stm.executeUpdate();
        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()) {
            cinema.setCinema_id(generatedKeys.getLong(1));
        } else {
            connection.close();
            throw new SQLException("Creating cinema failed, no ID obtained.");
        }
        connection.close();
        return cinema;
    }

    /**
     * Update an existing record.
     * @param cinema the record to update
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     *        or (2) 0 for SQL statements that return nothing
     * @throws SQLException
     */
    public int update(Cinema cinema) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(UPDATE);
        stm.setString(1, cinema.getCinema_name());
        stm.setString(2, cinema.getCinema_address());
        stm.setString(3, cinema.getCinema_phone());
        stm.setLong(4,cinema.getCinema_id());
        return stm.executeUpdate();
    }

    public void delete(Cinema cinema) throws SQLException{
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(DELETE);
        stm.setLong(1, cinema.getCinema_id());
        stm.executeUpdate();

    }


    private Cinema mapCinema(ResultSet rs) throws SQLException {
        Cinema cinema = new Cinema(rs.getString(2), rs.getString(3), rs.getString(4));
        cinema.setCinema_id(rs.getLong(1));
        return cinema;
    }

}
