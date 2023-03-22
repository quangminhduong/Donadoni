package donadoni.dao;


import donadoni.models.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SessionDao {

    public static SessionDao INSTANCE = new SessionDao();

    private static String SELECT_BY_MOVIE = "SELECT * FROM sessions WHERE movie_id=?";
    private static String SELECT_BY_CINEMA = "SELECT * FROM sessions WHERE cinema_id=?";
    private static String SELECT_BY_CINEMA_AND_MOVIE = "SELECT * FROM sessions WHERE movie_id =? AND cinema_id=?";
    private static String SELECT_BY_ID = "SELECT * FROM sessions WHERE session_id=?";
    private static String SELECT_DISTINCT_CINEMA = "SELECT DISTINCT movie_id FROM sessions WHERE cinema_id=?";
    private static String UPDATE = "UPDATE sessions SET movie_id = ?, available = ?,  showing = ?, status = ?, cinema_id = ? WHERE session_id=?";
    private static String INSERT = "INSERT INTO sessions(movie_id, cinema_id, available, showing, status) VALUES(?,?,?,?,?)";
    private static String DELETE = "DELETE FROM sessions WHERE session_id=?";
    private static String SELECT_BY_USER = "SELECT * FROM sessions WHERE session_id IN (SELECT DISTINCT session_id FROM reservations WHERE user_id = ?)";

    private SessionDao() {
    }

    public ArrayList<Long> getDistictMovies(Long id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_DISTINCT_CINEMA);
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        ArrayList<Long> movie_ids = new ArrayList<>();
        while (rs.next()) {
            movie_ids.add(rs.getLong(1));
        }
        connection.close();
        return movie_ids;
    }

    public List<Session> getByUser(Long id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_USER);
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        List<Session> sessions = new ArrayList<>();
        while (rs.next()) {
            sessions.add(mapSession(rs));
        }
        connection.close();
        return sessions;
    }

    public List<Session> getByMovie(Long id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_MOVIE);
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        List<Session> sessions = new ArrayList<>();
        while (rs.next()) {
            sessions.add(mapSession(rs));
        }
        connection.close();
        return sessions;
    }

    public List<Session> getByCinema(Long id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_CINEMA);
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        List<Session> sessions = new ArrayList<>();
        while (rs.next()) {
            sessions.add(mapSession(rs));
        }
        connection.close();
        return sessions;
    }
    public List<Session> getByCinemaAndMovie(Long movie_id, Long cinema_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_CINEMA_AND_MOVIE);
        stm.setLong(1, movie_id);
        stm.setLong(2,cinema_id);
        ResultSet rs = stm.executeQuery();
        List<Session> sessions = new ArrayList<>();
        while (rs.next()) {
            sessions.add(mapSession(rs));
        }
        connection.close();
        return sessions;
    }

    public Session create(Session session) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        stm.setLong(1, session.getMovie_id());
        stm.setLong(2, session.getCinema_id());
        stm.setInt(3, session.getAvailable());
        stm.setString(4, String.valueOf(session.getShowing()));
        stm.setString(5, session.getStatus());
        stm.executeUpdate();
        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()) {
            session.setSession_id(generatedKeys.getLong(1));
        } else {
            connection.close();
            throw new SQLException("Creating session failed, no ID obtained.");
        }
        connection.close();
        return session;
    }

    public void delete(Session session) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(DELETE);
        stm.setLong(1, session.getSession_id());
        stm.executeUpdate();

    }

    public Session get(Long id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_ID);
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return mapSession(rs);
        }
        connection.close();
        throw new SQLException("No Session with id = " + id);
    }

    public int update(Session session) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(UPDATE);
        stm.setLong(1, session.getMovie_id());
        stm.setLong(2, session.getAvailable());
        stm.setString(3, String.valueOf(session.getShowing()));
        stm.setString(4, session.getStatus());
        stm.setLong(5, session.getCinema_id());
        stm.setLong(6, session.getSession_id());
        return stm.executeUpdate();
    }


    private Session mapSession(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(5);
        Session session = new Session(rs.getLong(2), rs.getLong(3), rs.getInt(4), timestamp.toLocalDateTime());
        session.setSession_id(rs.getLong(1));
        session.setStatus(rs.getString(6));
        return session;
    }

}
