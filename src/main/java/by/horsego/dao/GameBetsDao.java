package by.horsego.dao;

import by.horsego.bean.BetType;
import by.horsego.dao.pool.ConnectionPool;
import by.horsego.dao.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for accessing the DB table game_bets.
 *
 * To get an instance of this class call the method {@link DaoFactory#getGameBetsDao()}
 * on an object of the DaoFactory class.
 *
 * @see DaoFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class GameBetsDao {

    private static final String SQL_SELECT_ALL_BET_TYPES = "SELECT bet_type, bet_coefficient FROM game_bets WHERE game_id=?";
    private static final String SQL_SELECT_ALL_BET_TYPES_BY_HORSE_ID = "SELECT bet_type, bet_coefficient FROM game_bets WHERE game_id=? AND horse_id=?";
    private static final String SQL_SELECT_BET_TYPE_BY_TYPE = "SELECT bet_type, bet_coefficient FROM game_bets WHERE game_id=? AND horse_id=? AND bet_type=?;";
    private static final String SQL_DELETE_ALL_BET_TYPES = "DELETE FROM game_bets WHERE game_id=?";
    private static final String SQL_DELETE_BET_TYPE = "DELETE FROM game_bets WHERE game_id=? AND horse_id=? AND bet_type=?;";
    private static final String SQL_CREATE_BET_TYPE = "INSERT INTO game_bets (game_id, horse_id, bet_type, bet_coefficient) VALUES (?, ?, ?, ?);";
    private static final String SQL_UPDATE_BET_TYPE = "UPDATE game_bets SET bet_coefficient=? WHERE game_id=? AND horse_id=? AND bet_type=?;";

    private static final Logger logger = Logger.getLogger(GameBetsDao.class);
    private ConnectionPool connectionPool;

    //package-private
    GameBetsDao(){
        connectionPool = ConnectionPool.getInstance();
    }

    /**
     * This method returns a list of all types of bets on the game.
     *
     * @param gameId
     * @return a list of the types of bets
     * @throws DaoException
     */
    public List<BetType> findAll(int gameId) throws DaoException {

        List<BetType> betTypes = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BET_TYPES)) {

            statement.setInt(1, gameId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                BetType betType = new BetType();
                betType.setType(BetType.TypeEnum.valueOf(resultSet.getString("bet_type")));
                betType.setCoefficient(resultSet.getDouble("bet_coefficient"));

                betTypes.add(betType);
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return betTypes;

    }

    /**
     * Method delete all bet types on this game.
     *
     * @param gameId
     * @return true, if all types of bets on this game deleted
     * @throws DaoException
     */
    public boolean deleteAllByGameId(int gameId) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ALL_BET_TYPES)) {

            statement.setInt(1, gameId);
            statement.executeUpdate();


        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        if (findAll(gameId) != null && !findAll(gameId).isEmpty()){
            return false;
        }

        return true;

    }

    /**
     * This method returns a list of bet types of participant in the game.
     *
     * @param gameId
     * @param horseId
     * @return list of bet types for a particular participant in the gameе
     * @throws DaoException
     */
    public List<BetType> findAllByHorseId(int gameId, int horseId) throws DaoException {

        List<BetType> betTypes = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BET_TYPES_BY_HORSE_ID);) {

            statement.setInt(1, gameId);
            statement.setInt(2, horseId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                BetType betType = new BetType();
                betType.setType(BetType.TypeEnum.valueOf(resultSet.getString("bet_type")));
                betType.setCoefficient(resultSet.getDouble("bet_coefficient"));

                betTypes.add(betType);
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return betTypes;
    }

    /**
     * The method returns a BetType object by game, participant, and type.
     *
     * @param gameId
     * @param horseId
     * @param strBetType
     * @return type of bet
     * @throws DaoException
     */
    public BetType findEntity (int gameId, int horseId, String strBetType) throws DaoException {

        BetType betType = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BET_TYPE_BY_TYPE);) {

            statement.setInt(1, gameId);
            statement.setInt(2, horseId);
            statement.setString(3, strBetType);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                betType = new BetType();
                betType.setType(BetType.TypeEnum.valueOf(resultSet.getString("bet_type")));
                betType.setCoefficient(resultSet.getDouble("bet_coefficient"));
            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return betType;
    }

    public boolean delete(int gameId, int horseId, BetType entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BET_TYPE);) {

            statement.setInt(1, gameId);
            statement.setInt(2, horseId);
            statement.setString(3, entity.getType().toString());
            statement.executeUpdate();


        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        if (findEntity(gameId, horseId, entity.getType().toString()) != null){
            return false;
        }

        return true;
    }

    public boolean create(int gameId, int horseId, BetType entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_BET_TYPE)) {

            statement.setInt(1, gameId);
            statement.setInt(2, horseId);
            statement.setString(3, entity.getType().toString());
            statement.setDouble(4, entity.getCoefficient());
            statement.executeUpdate();


        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        if (findEntity(gameId, horseId, entity.getType().toString()) == null){
            return false;
        }

        return true;

    }

    public boolean update(int gameId, int horseId, BetType entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BET_TYPE);) {

            statement.setDouble(1, entity.getCoefficient());
            statement.setInt(2, gameId);
            statement.setInt(3,horseId);
            statement.setString(4, entity.getType().toString());
            statement.executeUpdate();

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        if (findEntity(gameId, horseId, entity.getType().toString()) == null){
            return false;
        }

        return true;

    }
}
