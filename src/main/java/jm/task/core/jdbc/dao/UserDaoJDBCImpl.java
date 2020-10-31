package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        Util util = new Util();
        this.connection = util.getConnection();
    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS `new_schema`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT(3) NOT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8";
        try {
            connection.prepareStatement(query).execute();
        } catch (SQLException e) {
            System.out.println("some sheep with creating table");
            e.printStackTrace(System.out);
        }
    }

    public void dropUsersTable() {
        String query = "drop table if exists `new_schema`.`users`";
        try {
            connection.prepareStatement(query).execute();
        } catch (SQLException e) {
            System.out.println("some sheep with deleting table");
            e.printStackTrace(System.out);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into new_schema.users (name, lastName, age) values(?, ?, ?);";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("some sheep with adding user");
            e.printStackTrace(System.out);
        }
    }

    public void removeUserById(long id) {
        String query = "delete from new_schema.users where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("some sheep with deleting user");
            e.printStackTrace(System.out);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "select * from new_schema.users";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getNString(2), resultSet.getNString(3), resultSet.getByte(4));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("some sheep with deleting user");
            e.printStackTrace(System.out);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String query = "truncate table new_schema.users";
        try {
            connection.prepareStatement(query).execute();
        } catch (SQLException e) {
            System.out.println("some sheep with cleaning user table");
            e.printStackTrace(System.out);
        }
    }
}
