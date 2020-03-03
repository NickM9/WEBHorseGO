package by.horsego.dao;

import by.horsego.bean.Entity;

import java.util.List;

/**
 * Basic interface for Dao layer classes.
 *
 * @see Entity
 * @param <K>
 * @param <E> inheritor of the entity abstract class
 *
 * @author Mikita Masukhranau
 * @version 1.0
 */

public interface Dao <K, E extends Entity> {

    List<E> findAll() throws DaoException;
    E findByID(K id) throws DaoException;
    boolean delete(K id) throws DaoException;
    boolean create(E entity) throws DaoException;
    boolean update(E entity) throws DaoException;

}
