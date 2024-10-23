package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Андрей", "Губин", (byte) 52);
        userService.saveUser("Антон", "Камолов", (byte) 42);
        userService.saveUser("Гарик", "Харламов", (byte) 45);
        userService.saveUser("Сергей", "Жуков", (byte) 50);
        userService.getAllUsers();
//        for (int i = 4; i > 0; i--) {
//            userDaoJDBCImpl.removeUserById(i);
//        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.closeConnection();
    }
}
