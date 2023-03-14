package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        //User user1 = new User()
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.dropUsersTable();
        userService.cleanUsersTable();
        userService.saveUser("test1", "test11", (byte) 11);
        userService.saveUser("test2", "test22", (byte) 22);
        userService.saveUser("test3", "test33", (byte) 33);
        userService.saveUser("test4", "test44", (byte) 44);
        userService.removeUserById(3);
        userService.getAllUsers();
    }

}
