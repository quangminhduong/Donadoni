package donadoni.dao;

import donadoni.models.Role;
import donadoni.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sebastian Rodriguez, 2020. email: sebastian.rodriguez@rmit.edu.au
 */
public class UserDao {

    private static final String SELECT_PWD_BY_EMAIL = "SELECT password FROM users WHERE email = ?" ;
    private static final String SELECT_BY_EMAIL = "SELECT email, name, role, user_id FROM users WHERE email = ?" ;
    public static UserDao INSTANCE = new UserDao();
    private static final String SELECT_BY_ID = "SELECT * FROM users WHERE user_id = ?" ;
    private static final String INSERT = "INSERT INTO users(email, name, password, role) VALUES(?,?,?,?)";
    private static String SELECT_ALL_CUSTOMER = "SELECT * FROM users";

    private UserDao(){}

    public List<User> getAll() throws SQLException {
        Connection connection = DBUtils.getConnection();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(SELECT_ALL_CUSTOMER);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(mapUser(rs));
        }
        connection.close();
        return users;
    }

    public User get(Long id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_ID);
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getLong(1));
            user.setEmail(rs.getString(2));
            user.setName(rs.getString(3));
            user.setRole(Role.valueOf(rs.getString(5)));

            return user;
        }
        connection.close();
        throw new SQLException("No User with id = "+id);
    }

    public String getUserPasswordHash(String email) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_PWD_BY_EMAIL);
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        connection.close();
        throw new SQLException("No User with email = " + email);
    }

    public User getByEmail(String email) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(SELECT_BY_EMAIL);
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setEmail(rs.getString(1));
            user.setName(rs.getString(2));
            user.setRole(Role.valueOf(rs.getString(3)));
            user.setId(rs.getLong(4));
            return user;
        }
        connection.close();
        throw new SQLException("No User with email = " + email);
    }
    public User create(User user) throws SQLException {
        Connection connection = DBUtils.getConnection();
        PreparedStatement stm = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, user.getEmail());
        stm.setString(2, user.getName());
        stm.setString(3,"Password");
        stm.setString(4, String.valueOf(user.getRole()));
        stm.executeUpdate();
        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()) {
            user.setId(generatedKeys.getLong(1));
        } else {
            connection.close();
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        connection.close();
        return user;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setName(rs.getString(3));
        user.setEmail(rs.getString(2));
        user.setId(rs.getLong(1));
        return user;
    }


}

