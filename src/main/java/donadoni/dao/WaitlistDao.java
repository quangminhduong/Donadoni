package donadoni.dao;

import donadoni.models.Reservation;
import donadoni.models.Waitlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class WaitlistDao {

    public static WaitlistDao INSTANCE = new WaitlistDao();

    private static String SELECT_BY_USER = "SELECT * FROM waitlist WHERE user_id=?";
    private static String SELECT_BY_SESSION = "SELECT * FROM waitlist WHERE session_id=?";
    private static String SELECT_BY_ID = "SELECT * FROM waitlist WHERE wait_id=?";
    private static String SELECT_ALL = "SELECT * FROM waitlist";
    private static String INSERT = "INSERT INTO waitlist (session_id, user_id) VALUES (?,?)";
    private static String DELETE = "DELETE FROM waitlist WHERE session_id=?";

    private WaitlistDao() {
    }

    public  List<Waitlist> getAll() throws SQLException {
        Connection connection = DBUtils.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(SELECT_ALL);
        List<Waitlist> waitlist = new ArrayList<>();
        while (rs.next()){
            waitlist.add(mapWait(rs));
        }
        connection.close();
        return waitlist;
    }
    public void delete(Waitlist waitlist) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(DELETE);
        stm.setLong(1, waitlist.getSession_id());
        stm.executeUpdate();
        connection.close();

    }

    public List<Waitlist> getByUser(Long user_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(SELECT_BY_USER);
        List<Waitlist> wait = new ArrayList<>();
        while (rs.next()){
            wait.add(mapWait(rs));
        }
        connection.close();
        return wait;
    }

    public List<Waitlist> getBySession(Long session_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_SESSION);
        stm.setLong(1, session_id);
        ResultSet rs = stm.executeQuery();
        List<Waitlist> wait = new ArrayList<>();
        while (rs.next()){
            wait.add(mapWait(rs));
        }
        connection.close();
        return wait;
    }

    public Waitlist get(Long wait_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_ID);
        stm.setLong(1, wait_id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            Waitlist m = mapWait(rs);
            return m;
        }
        connection.close();
        throw new SQLException("No Reservation with id = " + wait_id);
    }

    public Waitlist addWaitList(Waitlist wait) throws SQLException{
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
        stm.setLong(1, wait.getSession_id());
        stm.setLong(2, wait.getUser_id());
        stm.executeUpdate();
        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()){
            wait.setWait_id(generatedKeys.getLong(1));
        } else {
            connection.close();;
            throw new SQLException("Adding reservation failed, no ID obtained.");
        }
        connection.close();
        return wait;
    }

    private Waitlist mapWait(ResultSet rs) throws SQLException {
        Waitlist wait = new Waitlist(rs.getLong(2),rs.getLong(3));
        wait.setWait_id(rs.getLong(1));
        return wait;
    }

    }


