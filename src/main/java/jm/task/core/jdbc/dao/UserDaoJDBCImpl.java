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
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("some sheep with creating table");
            e.printStackTrace(System.out);
            rollback("createUsersTable");
        }
    }

    public void dropUsersTable() {
        String query = "drop table if exists `new_schema`.`users`";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("some sheep with deleting table");
            e.printStackTrace(System.out);
            rollback("dropUsersTable");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into new_schema.users (name, lastName, age) values(?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("some sheep with adding user");
            e.printStackTrace(System.out);
            rollback("saveUser");
        }
    }

    public void removeUserById(long id) {
        String query = "delete from new_schema.users where id=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("some sheep with deleting user");
            e.printStackTrace(System.out);
            rollback("removeUserById");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "select * from new_schema.users";
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getNString(2), resultSet.getNString(3), resultSet.getByte(4));
                userList.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println("some sheep with getting all users");
            e.printStackTrace(System.out);
            rollback("getAllUsers");
        }
        return userList;
    }

    public void cleanUsersTable() {
        String query = "truncate table new_schema.users";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.execute();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("some sheep with cleaning user table");
            e.printStackTrace(System.out);
            rollback("cleanUsersTable");
        }
    }

    private void rollback(String methodName) {
        try {
            connection.rollback();
        } catch (SQLException e2) {
            System.out.println("rollback_error in " + methodName);
            e2.printStackTrace(System.out);
        }
    }
}
