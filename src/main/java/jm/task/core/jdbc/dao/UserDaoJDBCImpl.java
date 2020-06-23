package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl(Connection connection){
        this.connection = connection;
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.execute("create table if not exists users (id bigint auto_increment, name varchar(256), lastName varchar(256), age int, primary key (id))");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dropUsersTable() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS users");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String update = "insert into users (name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(update)){
            statement.setString (1,name);
            statement.setString (2,lastName);
            statement.setInt(3, age);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String update = "delete from users where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(update)){
            statement.setLong (1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String select = "select * from users";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while (!resultSet.isLast()){
                resultSet.next();
                list.add(new User(resultSet.getLong("id") , resultSet.getString("name"), resultSet.getString("lastName"), (byte) resultSet.getLong("age")));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public void cleanUsersTable() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("delete from users");
            stmt.close();
            System.out.println("table");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
