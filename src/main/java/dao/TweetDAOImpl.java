package dao;

import entity.Tweet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class TweetDAOImpl implements TweetDAO {

    EntityManager em;

    public Tweet getByID(Long id) {
        return em.find(Tweet.class, id);
    }

    public void create(Tweet entity) {
        em.persist(entity);
    }

    public void edit(Tweet entity) {
        em.merge(entity);
    }

    public void remove(Tweet entity) {
        em.remove(entity);
    }
}
