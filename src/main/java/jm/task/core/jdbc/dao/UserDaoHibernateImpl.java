package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Util util;

    public UserDaoHibernateImpl(Util util) {
        this.util = util;
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                    "name VARCHAR(45) NOT NULL, " +
                    "lastName VARCHAR(45) NOT NULL, " +
                    "age TINYINT NOT NULL);").addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                e.printStackTrace();
                transaction.rollback();
            }
            System.out.println("Таблица не создана");
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                e.printStackTrace();
                transaction.rollback();
            }
            System.out.println("Таблица не удалена");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.printf("User с именем — %s добавлен в базу данных\n", user.getName());
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                e.printStackTrace();
                transaction.rollback();
            }
            System.out.println("Пользователь не добавлен");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("Пользователь успешно удален");
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                e.printStackTrace();
                transaction.rollback();
            }
            System.out.println("Пользователь не удален");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            for (User user : userList) {
                System.out.println(user);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                e.printStackTrace();
                transaction.rollback();
            }
            System.out.println("Пользователи не получены");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM users");
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                e.printStackTrace();
                transaction.rollback();
            }
            System.out.println("Пользователи не получены");
        }
    }
}
