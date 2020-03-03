package by.horsego.dao;

import by.horsego.bean.Horse;
import by.horsego.dao.pool.ConnectionPool;
import by.horsego.dao.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for accessing the DB table horses.
 * Implements interface {@link Dao}
 *
 * To get an instance of this class call the method {@link DaoFactory#getHorseDao()}
 * on an object of the DaoFactory class.
 *
 * @see Dao
 * @see DaoFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class HorseDao implements Dao<Integer, Horse>{

    private static final String SQL_SELECT_ALL_HORSES = "SELECT id, name  FROM horses;";
    private static final String SQL_SELECT_HORSE_BY_ID = "SELECT id, name  FROM horses WHERE id=?;";
    private static final String SQL_DELETE_HORSE_BY_ID = "DELETE FROM horses WHERE id=?;";
    private static final String SQL_CREATE_HORSE= "INSERT INTO horses (name) VALUES (?);";
    private static final String SQL_UPDATE_HORSE= "UPDATE horses  SET name=? WHERE id=?;";

    private static final Logger logger = Logger.getLogger(HorseDao.class);
    private ConnectionPool connectionPool;

    //package-private
    HorseDao(){
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public List<Horse> findAll() throws DaoException {

        List<Horse> horses = new ArrayList<>();


        try(Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();) {

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_HORSES);

            while (resultSet.next()) {
                Horse horse = new Horse();
                horse.setId(resultSet.getInt("id"));
                horse.setName(resultSet.getString("name"));
                horses.add(horse);
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return horses;

    }

    @Override
    public Horse findByID(Integer id) throws DaoException {

        Horse horse = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_HORSE_BY_ID);) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                horse = new Horse();
                horse.setId(resultSet.getInt("id"));
                horse.setName(resultSet.getString("name"));
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return horse;

    }

    @Override
    public boolean delete(Integer id) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_HORSE_BY_ID);) {

            statement.setInt(1, id);
            statement.executeUpdate();


        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        if (findByID(id) != null){
            return false;
        }

        return true;

    }

    @Override
    public boolean create(Horse entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_HORSE, Statement.RETURN_GENERATED_KEYS);) {

            statement.setString(1, entity.getName());;
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                int key = resultSet.getInt(1);
                entity.setId(key);
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        if (findByID(entity.getId()) == null){
            return false;
        }

        return true;

    }

    @Override
    public boolean update(Horse entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_HORSE);) {

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        if (!findByID(entity.getId()).equals(entity)){
            return false;
        }

        return true;

    }
}
