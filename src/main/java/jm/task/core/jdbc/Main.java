package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("ilya", "Lapshinov", (byte) 29);
        userService.saveUser("dima", "Ivanov", (byte) 30);
        userService.saveUser("koly", "Sidorov", (byte) 31);
        userService.saveUser("tolik", "Efimov", (byte) 32);

        List<User> user = userService.getAllUsers();
        for (User u: user) {
            System.out.println(u);
        }

        userService.cleanUsersTable();;
        userService.dropUsersTable();




    }
}
