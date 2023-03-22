package donadoni.dao;

import donadoni.models.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SeatDao {

    public static SeatDao INSTANCE = new SeatDao();

    private static String SELECT_ALL = "SELECT * FROM seat";


    private SeatDao() {
    }

    public List<Seat> getAll() throws SQLException {
        Connection connection = DBUtils.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(SELECT_ALL);
        List<Seat> seats = new ArrayList<>();
        while (rs.next()) {
            seats.add(mapSession(rs));
        }
        connection.close();
        return seats;
    }

    private Seat mapSession(ResultSet rs) throws SQLException {
        Seat seat = new Seat();
        seat.setSeat_id(rs.getLong(1));
        return seat;
    }
}
