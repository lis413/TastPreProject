package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.id.uuid.StandardRandomStrategy;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory = createSessionFactory(Util.getMySqlConfiguration());
   // private Session session = sessionFactory.openSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {

            String hql = "create table if not exists users (id bigint auto_increment, name varchar(256), lastName varchar(256), age int, primary key (id))";
            SQLQuery query = session.createSQLQuery(hql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            session.close();
        }


    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            SQLQuery query = session.createSQLQuery("drop table users");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "delete from users where id = '" + id + "'";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
        }catch (Exception e) {
            transaction.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "from User";
            Query query = session.createQuery(hql);
            List<User> list = query.list();
            transaction.commit();
            return list;
        } catch (Exception e){
            transaction.rollback();
        } finally {
            session.close();
        }

        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
        }catch (Exception e) {
            transaction.rollback();
        }finally {
            session.close();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
