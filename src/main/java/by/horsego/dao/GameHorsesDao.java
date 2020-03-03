package by.horsego.dao;

import by.horsego.bean.Horse;
import by.horsego.dao.pool.ConnectionPool;
import by.horsego.dao.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for accessing the DB table game_horses.
 *
 * To get an instance of this class call the method {@link DaoFactory#getGameHorsesDao()}
 * on an object of the DaoFactory class.
 *
 * @see Dao
 * @see DaoFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class GameHorsesDao {

    private static final String SQL_SELECT_ALL_GAME_HORSES = "SELECT horse_id FROM game_horses WHERE game_id=?;";
    private static final String SQL_SELECT_GAME_HORSE = "SELECT horse_id FROM game_horses WHERE game_id=? AND horse_id=?";
    private static final String SQL_DELETE_ALL_GAME_HORSES = "DELETE FROM game_horses WHERE game_id=?;";
    private static final String SQL_DELETE_GAME_HORSE = "DELETE FROM game_horses WHERE game_id=? AND horse_id=?;";
    private static final String SQL_CREATE_GAME_HORSE = "INSERT INTO game_horses (game_id, horse_id) VALUES (?, ?);";
    private static final String SQL_UPDATE_GAME_HORSE = "UPDATE game_horses SET horse_id=? WHERE game_id=? AND horse_id=?;";
    private static final String SQL_SELECT_ALL_HORSE_GAMES = "SELECT game_id FROM game_horses WHERE horse_id=?;";
    private static final String SQL_SELECT_ALL_NOT_PLAYED_HORSE_GAMES =
            "SELECT game_horses.game_id, games.game_played\n" +
            "FROM game_horses\n" +
            "INNER JOIN games ON game_horses.game_id = games.id\n" +
            "WHERE game_horses.horse_id=? AND games.game_played=false;";

    private static final Logger logger = Logger.getLogger(GameHorsesDao.class);
    private ConnectionPool connectionPool;
    private HorseDao horseDao = DaoFactory.getInstance().getHorseDao();

    //package-private
    GameHorsesDao(){
        connectionPool = ConnectionPool.getInstance();
    }

    public List<Horse> findAll(Integer gameId) throws DaoException {

        List<Horse> horses = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_GAME_HORSES);) {

            statement.setInt(1, gameId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Horse horse = horseDao.findByID(resultSet.getInt("horse_id"));

                if (horse == null){
                    horse = new Horse();
                    horse.setId(resultSet.getInt("horse_id"));
                }

                horses.add(horse);
            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return horses;

    }

    public Horse findEntity(Integer gameId, int horseId) throws DaoException  {

        Horse horse = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_GAME_HORSE);) {

            statement.setInt(1, gameId);
            statement.setInt(2, horseId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                horse = horseDao.findByID(resultSet.getInt("horse_id"));
            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return horse;

    }

    public boolean deleteAll(Integer gameId) throws DaoException  {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ALL_GAME_HORSES)) {

            statement.setInt(1, gameId);
            statement.executeUpdate();


        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        if (findAll(gameId) != null && !findAll(gameId).isEmpty()){
            return false;
        }

        return true;

    }

    public boolean delete(Integer gameId, Horse entity) throws DaoException  {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_GAME_HORSE);) {

            statement.setInt(1, gameId);
            statement.setInt(2, entity.getId());
            statement.executeUpdate();


        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        if (findEntity(gameId, entity.getId()) != null){
            return false;
        }

        return true;

    }

    public boolean create(Integer gameId, Horse entity) throws DaoException  {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_GAME_HORSE)) {

            statement.setInt(1, gameId);
            statement.setInt(2, entity.getId());
            statement.executeUpdate();


        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        if (findEntity(gameId, entity.getId()) == null){
            return false;
        }

        return true;

    }

    public boolean update(Integer gameId, Horse horseToUpdate, Horse entity) throws DaoException  {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_GAME_HORSE);) {

            statement.setInt(1, entity.getId());
            statement.setInt(2, gameId);
            statement.setInt(3, horseToUpdate.getId());
            statement.executeUpdate();

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        if (findEntity(gameId, entity.getId()) == null){
            return false;
        }

        return true;

    }

    /**
     * This method returns a list of the IDs of all games that the participant participated in
     *
     * @param horseId
     * @return list of participant's game IDs
     * @throws DaoException
     */
    public List<Integer> findHorseGames(int horseId) throws DaoException {

        List<Integer> horseGames = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_HORSE_GAMES);) {

            statement.setInt(1, horseId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int gameId = resultSet.getInt("game_id");
                horseGames.add(gameId);

            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return horseGames;

    }

    /**
     * This method returns a list of the IDs of not played games in which the participant participated in.
     *
     * @param horseId
     * @return list of participant's game IDs
     * @throws DaoException
     */
    public List<Integer> findNotPlayedHorseGames(int horseId) throws DaoException {

        List<Integer> horseGames = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_NOT_PLAYED_HORSE_GAMES)) {

            statement.setInt(1, horseId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int gameId = resultSet.getInt("game_id");
                horseGames.add(gameId);

            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return horseGames;

    }

}
