package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.createUsersTable();
        userServiceImpl.saveUser("Андрей", "Губин", (byte) 52);
        userServiceImpl.saveUser("Антон", "Камолов", (byte) 42);
        userServiceImpl.saveUser("Гарик", "Харламов", (byte) 45);
        userServiceImpl.saveUser("Сергей", "Жуков", (byte) 50);
        userServiceImpl.getAllUsers();
//        for (int i = 4; i > 0; i--) {
//            userDaoJDBCImpl.removeUserById(i);
//        }
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
        Util.closeConnection();
    }
}
