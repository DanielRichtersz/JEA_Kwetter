package dao;

import entity.Tweet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TweetDAOImpl implements TweetDAO {

    @PersistenceContext(name="Kwetter")
    EntityManager em;

    @Override
    public Tweet getByID(Long id) {
        return em.find(Tweet.class, id);
    }

    @Override
    public void create(Tweet entity) {
        em.persist(entity);
    }

    @Override
    public void edit(Tweet entity) {
        em.merge(entity);
    }

    @Override
    public void remove(Tweet entity) {
        em.remove(entity);
    }
}
