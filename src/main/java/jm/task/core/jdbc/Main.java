package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("IVAN", "IVANOV", (byte) 1);
        userService.saveUser("Пётр", "Петров", (byte) 8);
        userService.saveUser("Лавр", "Lavrov", (byte) 666);
        userService.saveUser("%32о2джф", "Soba4ka", (byte) 99);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
