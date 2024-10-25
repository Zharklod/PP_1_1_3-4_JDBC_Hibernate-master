package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util;

    public UserDaoJDBCImpl(Util util) {
        this.util = util;
    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement =
                     util.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS users " +
                             "(id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                             "name VARCHAR(45) NOT NULL, " +
                             "lastName VARCHAR(45) NOT NULL, " +
                             "age TINYINT NOT NULL);")) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблицу не удалось создать");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("DROP TABLE users");
            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            System.out.println("Таблица не удалёна");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("INSERT INTO users(name, lastName, age) VALUES(\"" + name + "\", \"" + lastName + "\", " + age + ");");
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.out.println("Пользователя не удалось добавить");
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("DELETE FROM users WHERE id = " + id + ";");
            System.out.println("Пользователь успешно удален");
        } catch (SQLException e) {
            System.out.println("Пользователь не удалён");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("name"), resultSet.getString("lastName"), (byte) resultSet.getInt("age")));
            }
            System.out.println(userList);
        } catch (SQLException e) {
            System.out.println("Не удалось получить данные всех пользователей из таблицы");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("DELETE FROM users");
            System.out.println("Пользователи успешно удалены");
        } catch (SQLException e) {
            System.out.println("Пользователи не удалёны");
        }
    }
}
