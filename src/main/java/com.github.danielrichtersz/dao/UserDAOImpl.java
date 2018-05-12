package com.github.danielrichtersz.dao;

import com.github.danielrichtersz.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(name="Kwetter")
    EntityManager em;

    @Override
    public User getByID(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void edit(User entity) {
        em.merge(entity);
    }

    @Override
    public void remove(User entity) {
        em.remove(entity);
    }

    @Override
    public User getByCredentials(String email, String password) {
        return null;
    }
}
