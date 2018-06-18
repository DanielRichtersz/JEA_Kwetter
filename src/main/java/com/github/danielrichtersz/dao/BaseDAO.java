package dao;

public interface BaseDAO<T> {

    T getByID(Long id);

    void create(T entity);

    void edit(T entity);

    void remove(T entity);
}
