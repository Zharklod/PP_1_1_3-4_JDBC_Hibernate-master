package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
//    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sql = "CREATE TABLE users (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                                          "name VARCHAR(45) NOT NULL, " +
                                          "lastName VARCHAR(45) NOT NULL, " +
                                          "age INT NOT NULL);";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблицу не удалось создать");
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE users";

        try (Statement statement = getConnection().createStatement() ) {
            statement.execute(sql);
            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            System.out.println("Таблица не удалёна");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO users(name, lastName, age) VALUES(\"");
        sb.append(name);
        sb.append("\", \"");
        sb.append(lastName);
        sb.append("\", ");
        sb.append((byte) age);
        sb.append(");");
        String sqlresult = sb.toString();
        try (Statement statement = getConnection().createStatement()) {
//            statement.execute("INSERT INTO users(name, lastName, age) VALUES(" + name + ", " + lastName + ", " + age + ");");
            statement.execute(sqlresult);
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.out.println("Пользователя не удалось добавить");
        }
    }

    public void removeUserById(long id) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM users WHERE id = ");
        sb.append(id);
        sb.append(";");
        String sql = sb.toString();

        try (Statement statement = getConnection().createStatement() ) {
            statement.execute(sql);
            System.out.println("Пользователь успешно удален");
        } catch (SQLException e) {
            System.out.println("Пользователь не удалён");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Statement statement = getConnection().createStatement() ) {
            ResultSet resultSet = statement.executeQuery(sql);

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
        String sql = "DELETE FROM users";

        try (Statement statement = getConnection().createStatement() ) {
            statement.execute(sql);
            System.out.println("Пользователи успешно удалены");
        } catch (SQLException e) {
            System.out.println("Пользователи не удалёны");
        }
    }
}
