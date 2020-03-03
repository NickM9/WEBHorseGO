package by.horsego.dao;

import by.horsego.bean.Role;
import by.horsego.bean.User;
import by.horsego.dao.pool.ConnectionPool;
import by.horsego.dao.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for accessing the DB table users.
 * Implements interface {@link Dao}
 *
 * To get an instance of this class call the method {@link DaoFactory#getUserDao()}
 * on an object of the DaoFactory class.
 *
 * @see Dao
 * @see DaoFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class UserDao implements Dao<Integer, User> {

    private static final String SQL_SELECT_ALL_USERS = "SELECT id, name, surname, login, password, wallet, role  FROM users;";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id, name, surname, login, password, wallet, role  FROM users WHERE id=?;";
    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT id, name, surname, login, password, wallet, role  FROM users WHERE login=? AND password=?;";
    private static final String SQL_SELECT_USER_BY_NAME_AND_SURNAME = "SELECT id, name, surname, login, password, wallet, role  FROM users WHERE name=? AND surname=?;";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT id, name, surname, login, password, wallet, role  FROM users WHERE login=?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id=?;";
    private static final String SQL_CREATE_USER = "INSERT INTO users (name, surname, login, password, wallet, role) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE_USER = "UPDATE users  SET name=?, surname=?, login=?, password=?, wallet=?, role=? WHERE id=?;";

    private static final Logger logger = Logger.getLogger(UserDao.class);
    private ConnectionPool connectionPool;

    //package-private
    UserDao(){
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public List<User> findAll() throws DaoException {

        List<User> users = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();) {

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setWallet(resultSet.getDouble("wallet"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                users.add(user);
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return users;
    }

    @Override
    public User findByID (Integer id) throws DaoException {

        User user = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setWallet(resultSet.getDouble("wallet"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return user;
    }
    
    public User findByLoginAndPassword(String login, String password) throws DaoException {

        User user = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD)) {

            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setWallet(resultSet.getDouble("wallet"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return user;
    }

    public User findByLogin(String login) throws DaoException {

        User user = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN)) {

            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setWallet(resultSet.getDouble("wallet"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return user;
    }

    public List<User> findByNameAndSurname(String name, String surname) throws DaoException {

        List<User> users = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_NAME_AND_SURNAME)) {

            statement.setString(1, name);
            statement.setString(2, surname);;
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setWallet(resultSet.getDouble("wallet"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                users.add(user);
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return users;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);) {

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
    public boolean create(User entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getLogin());
            statement.setString(4, entity.getPassword());
            statement.setDouble(5, entity.getWallet());
            statement.setString(6, entity.getRole().toString());
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
    public boolean update(User entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER);) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getLogin());
            statement.setString(4, entity.getPassword());
            statement.setDouble(5, entity.getWallet());
            statement.setString(6, entity.getRole().toString());
            statement.setInt(7, entity.getId());
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
