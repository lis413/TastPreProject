package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;

    public UserDaoHibernateImpl() {

    }

    public UserDaoHibernateImpl(Session session){
        this.session = session;
    }




    @Override
    public void createUsersTable() {
        String hql = "create table if not exists users (id bigint auto_increment, name varchar(256), lastName varchar(256), age int, primary key (id))";
        Query query = session.createQuery(hql);
        query.executeUpdate();

    }

    @Override
    public void dropUsersTable() {
        Query query = session.createQuery("drop table users");
        query.executeUpdate();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.save(new User(name, lastName, age));
    }

    @Override
    public void removeUserById(long id) {
        String hql = "delete from users where id = '" + id + "'";
        Query query = session.createQuery(hql);
        query.executeUpdate();
    }

    @Override
    public List<User> getAllUsers() {

        String hql = "from users";
        Query query = session.createQuery(hql);
        List<User> list = query.list();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Query query = session.createQuery("DELETE FROM users");
        query.executeUpdate();
    }
}
