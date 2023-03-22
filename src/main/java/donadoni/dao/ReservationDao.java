package donadoni.dao;

import donadoni.models.Movie;
import donadoni.models.Reservation;
import donadoni.models.Seat;
import donadoni.models.Session;

import java.sql.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ReservationDao {

    public static ReservationDao INSTANCE = new ReservationDao();

    private static String SELECT_BY_USER = "SELECT * FROM reservations WHERE user_id=?";
    private static String SELECT_BY_SESSION = "SELECT * FROM reservations WHERE session_id=?";
    private static String SELECT_EMAIL_BY_SESSION = "SELECT email FROM users WHERE user_id IN (SELECT user_id FROM reservations WHERE session_id=?)";
    private static String SELECT_BY_ID = "SELECT * FROM reservations WHERE reservation_id=?";
    private static String SELECT_ALL = "SELECT * FROM reservations";
    private static String COUNT_TICKETS_BY_SESSION = "SELECT COUNT(seat_id) FROM reservations WHERE session_id=?";
    private static String INSERT = "INSERT INTO reservations (session_id, user_id, seat_id, cinema_id) VALUES (?,?,?,?)";
    private static String COUNT_TICKETS_BY_USER_PER_SESSION = "SELECT distinct  session_id, COUNT(seat_id) FROM reservations WHERE user_id=? GROUP BY session_id, user_id ";
    private static String SELECT_BY_USER_AND_SESSION = "SELECT * FROM reservations WHERE user_id=? AND session_id=?";
    private static String DELETE = "DELETE FROM reservations WHERE reservation_id=?";

    private ReservationDao() {
    }

    public Integer countSeatsbySession(Long session_id) throws SQLException{
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(COUNT_TICKETS_BY_SESSION);
        stm.setLong(1, session_id);
        ResultSet rs = stm.executeQuery();
        int seats = 0;
        if (rs.next()){
            seats = rs.getInt(1);
        }
        return seats;
    }
    public HashMap countSeatsbyUserPerSession(Long user_id) throws SQLException{
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(COUNT_TICKETS_BY_USER_PER_SESSION);
        stm.setLong(1, user_id);
        ResultSet rs = stm.executeQuery();
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        while (rs.next()){
            map.put(rs.getLong(1), rs.getInt(2));
        }
        connection.close();
        return map;
    }

    public  List<Reservation> getAll() throws SQLException {
        Connection connection = DBUtils.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(SELECT_ALL);
        List<Reservation> reservations = new ArrayList<>();
        while (rs.next()){
            reservations.add(mapReservation(rs));
        }
        connection.close();
        return reservations;
    }

    public List<Reservation> getByUserAndSession(Long user_id, Long session_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_USER_AND_SESSION);
        stm.setLong(1, user_id);
        stm.setLong(2, session_id);
        ResultSet rs = stm.executeQuery();
        List<Reservation> reservations = new ArrayList<>();
        while (rs.next()){
            reservations.add(mapReservation(rs));
        }
        connection.close();
        return reservations;
    }

    public List<Reservation> getByUser(Long user_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_USER);
        stm.setLong(1, user_id);
        ResultSet rs = stm.executeQuery();
        List<Reservation> reservationsByUser = new ArrayList<>();
        while (rs.next()){
            reservationsByUser.add(mapReservation(rs));
        }
        connection.close();
        return reservationsByUser;
    }

    public List<Reservation> getBySession(Long session_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_SESSION);
        stm.setLong(1, session_id);
        ResultSet rs = stm.executeQuery();
        List<Reservation> reservationsBySession = new ArrayList<>();
        while (rs.next()){
            reservationsBySession.add(mapReservation(rs));
        }
        connection.close();
        return reservationsBySession;
    }

    public List<String> getEmailsBySession(Long session_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_EMAIL_BY_SESSION);
        stm.setLong(1, session_id);
        ResultSet rs = stm.executeQuery();
        List<String> emails = new ArrayList<>();
        while (rs.next()){
            emails.add(rs.getString(1));
        }
        connection.close();
        return emails;
    }


    public Reservation get(Long reservation_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_ID);
        stm.setLong(1, reservation_id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return mapReservation(rs);
        }
        connection.close();
        throw new SQLException("No Reservation with id = " + reservation_id);
    }

    public Reservation addReservation(Reservation reservation) throws SQLException{
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
        stm.setLong(1, reservation.getSession_id());
        stm.setLong(2, reservation.getUser_id());
        stm.setLong(3,reservation.getSeat_id());
        stm.setLong(4,SessionDao.INSTANCE.get(reservation.getSession_id()).getCinema_id());
        stm.executeUpdate();
        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()){
            reservation.setReservation_id(generatedKeys.getLong(1));
        } else {
            connection.close();
            throw new SQLException("Adding reservation failed, no ID obtained.");
        }
        connection.close();
        return reservation;
    }

    public void delete(Reservation reservation) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(DELETE);
        stm.setLong(1, reservation.getReservation_id());
        stm.executeUpdate();
        connection.close();

    }


    private Reservation mapReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation(rs.getLong(2),rs.getLong(3),rs.getLong(4));
        reservation.setReservation_id(rs.getLong(1));
        return reservation;
    }

    }


