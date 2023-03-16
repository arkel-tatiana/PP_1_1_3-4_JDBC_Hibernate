package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connect = null;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {//1
        String sql = "CREATE TABLE IF NOT EXISTS users" +
                "(id int not null auto_increment," +
                "name VARCHAR(45) not null," +
                "lastName VARCHAR(50) not null," +
                "age int," +
                "PRIMARY KEY (id))";
        connectStat(sql);

    }

    public void dropUsersTable() throws SQLException {//1
        String sql = "DROP TABLE IF EXISTS users";
        connectStat(sql);
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {//4
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try {
            connect = Util.getConnection();
            PreparedStatement preStat = connect.prepareStatement(sql);
            connect.setAutoCommit(false);
            preStat.setString(1, name);
            preStat.setString(2, lastName);
            preStat.setByte(3, age);
            preStat.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connect.rollback();
            //throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) throws SQLException {//5
        String sql = "DELETE FROM users where id = (?)";
        try {
            connect = Util.getConnection();
            PreparedStatement preStat = connect.prepareStatement(sql);
            connect.setAutoCommit(false);
            preStat.setLong(1, id);
            preStat.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connect.rollback();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List <User> listUser = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            connect = Util.getConnection();
            Statement stat = connect.createStatement();
            connect.setAutoCommit(false);
            ResultSet resSet = stat.executeQuery(sql);
            while (resSet.next()) {
                User user = new User();
                user.setId(resSet.getLong("id"));
                user.setName(resSet.getString("name"));
                user.setLastName(resSet.getString("lastName"));
                user.setAge(resSet.getByte("age"));
                listUser.add(user);
            }
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connect.rollback();
        }
        return listUser;
    }

    public void cleanUsersTable() throws SQLException {//3
        String sql = "TRUNCATE users";
        connectStat(sql);
    }

    private void connectStat(String sql) throws SQLException {
        try {
            connect = Util.getConnection();
            Statement stat = connect.createStatement();
            connect.setAutoCommit(false);
            stat.executeUpdate(sql);
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connect.rollback();
            //throw new RuntimeException(e);
        }
    }
}
