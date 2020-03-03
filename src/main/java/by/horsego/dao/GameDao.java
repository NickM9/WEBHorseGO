package by.horsego.dao;

import by.horsego.bean.Game;
import by.horsego.dao.pool.ConnectionPool;
import by.horsego.dao.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for accessing the DB table games.
 * Implements interface {@link Dao}
 *
 * To get an instance of this class call the method {@link DaoFactory#getGameDao()}
 * on an object of the DaoFactory class.
 *
 * @see Dao
 * @see DaoFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class GameDao implements Dao<Integer, Game> {

    private static final String SQL_SELECT_ALL_GAMES = "SELECT id, game_played FROM games;";
    private static final String SQL_SELECT_GAME_BY_ID = "SELECT id, game_played FROM games WHERE id=?;";
    private static final String SQL_SELECT_GAME_BY_PLAYED = "SELECT id, game_played FROM games WHERE game_played=?;";
    private static final String SQL_DELETE_GAME_BY_ID = "DELETE FROM games WHERE id=?;";
    private static final String SQL_CREATE_GAME = "INSERT INTO games (game_played) VALUES (?);";
    private static final String SQL_UPDATE_GAME = "UPDATE games SET game_played=? WHERE id=?;";

    private final static Logger logger = Logger.getLogger(GameDao.class);
    private ConnectionPool connectionPool;

    //package-private
    GameDao(){
        connectionPool = ConnectionPool.getInstance();;
    }

    @Override
    public List<Game> findAll() throws DaoException {

        List<Game> games = new ArrayList<>();


        try(Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();) {

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_GAMES);

            while (resultSet.next()) {
                Game game = new Game();
                game.setId(resultSet.getInt("id"));
                game.setGamePlayed(resultSet.getBoolean("game_played"));
                games.add(game);
            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return games;

    }

    @Override
    public Game findByID(Integer id) throws DaoException {

        Game game = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_GAME_BY_ID)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                game = new Game();
                game.setId(resultSet.getInt("id"));
                game.setGamePlayed(resultSet.getBoolean("game_played"));
            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return game;

    }

    public List<Game> findAllNotPlayedGames() throws DaoException {

        List<Game> games = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_GAME_BY_PLAYED)) {

            statement.setBoolean(1, false);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Game game = new Game();
                game.setId(resultSet.getInt("id"));
                game.setGamePlayed(resultSet.getBoolean("game_played"));
                games.add(game);
            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return games;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_GAME_BY_ID);) {

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
    public boolean create(Game entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_GAME, Statement.RETURN_GENERATED_KEYS);) {

            statement.setBoolean(1, entity.isGamePlayed());;
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                int key = resultSet.getInt(1);
                entity.setId(key);
            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        if (findByID(entity.getId()) == null){
            return false;
        }

        return true;

    }

    @Override
    public boolean update(Game entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_GAME);) {

            statement.setBoolean(1, entity.isGamePlayed());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        if (!findByID(entity.getId()).equals(entity)){
            return false;
        }

        return true;

    }
}
