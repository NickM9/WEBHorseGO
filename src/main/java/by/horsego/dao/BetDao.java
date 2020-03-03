package by.horsego.dao;

import by.horsego.bean.Bet;
import by.horsego.bean.BetType;
import by.horsego.dao.pool.ConnectionPool;
import by.horsego.dao.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for accessing the DB table bets.
 * Implements interface {@link Dao}.
 *
 * To get an instance of this class call the method {@link DaoFactory#getBetDao()}
 * on an object of the DaoFactory class.
 *
 * @see Dao
 * @see DaoFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class BetDao implements Dao<Integer, Bet> {

    private static final String SQL_SELECT_ALL_BETS = "SELECT id, user_id, game_id, horse_id,  bet_amount, bet_type, bet_coefficient, user_win FROM bets;";
    private static final String SQL_SELECT_BET_BY_ID = "SELECT id, user_id, game_id, horse_id, bet_amount, bet_type, bet_coefficient, user_win FROM bets WHERE id=?;";
    private static final String SQL_SELECT_BET_BY_USER_ID = "SELECT id, user_id, game_id, horse_id, bet_amount, bet_type, bet_coefficient, user_win FROM bets WHERE user_id=?;";
    private static final String SQL_SELECT_BET_BY_GAME_ID = "SELECT id, user_id, game_id, horse_id, bet_amount, bet_type, bet_coefficient, user_win FROM bets WHERE game_id=?";
    private static final String SQL_SELECT_BET_BY_HORSE_ID_AND_GAME_ID = "SELECT id, user_id, game_id, horse_id, bet_amount, bet_type, bet_coefficient, user_win FROM bets WHERE game_id=? AND horse_id=?;";
    private static final String SQL_DELETE_BET_BY_ID = "DELETE FROM bets WHERE id=?;";
    private static final String SQL_CREATE_BET = "INSERT INTO bets (user_id, game_id, horse_id, bet_amount, bet_type, bet_coefficient, user_win) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE_BET = "UPDATE bets  SET user_id=?, game_id=?, horse_id=?, bet_amount=?, bet_type=?, bet_coefficient=?, user_win=? WHERE id=?;";

    private static final Logger logger = Logger.getLogger(BetDao.class);
    private ConnectionPool connectionPool;

    //package-private
    BetDao() {
        connectionPool = ConnectionPool.getInstance();
    }

    /**
     * Method that returns a list of all bets
     *
     * @return list of all bets
     * @throws DaoException
     */
    @Override
    public List<Bet> findAll() throws DaoException {

        List<Bet> bets = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();) {

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_BETS);

            while (resultSet.next()) {
                Bet bet = new Bet();
                bet.setId(resultSet.getInt("id"));
                bet.setUserId(resultSet.getInt("user_id"));
                bet.setGameId(resultSet.getInt("game_id"));
                bet.setHorseId(resultSet.getInt("horse_id"));
                bet.setBetAmount(resultSet.getDouble("bet_amount"));

                BetType betType = new BetType();
                betType.setType(BetType.TypeEnum.valueOf(resultSet.getString("bet_type")));
                betType.setCoefficient(resultSet.getDouble("bet_coefficient"));

                bet.setBetType(betType);
                bet.setUserWin(resultSet.getBoolean("user_win"));
                bets.add(bet);
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return bets;

    }

    /**
     * Method that returns a list of all bets for a specific user
     *
     * @param userId user id to search for all his bets
     * @return list of all user bets
     * @throws DaoException
     */
    public List<Bet> findAllUserBets(Integer userId) throws DaoException{

        List<Bet> allUserBetsList = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BET_BY_USER_ID);) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Bet bet = new Bet();
                bet.setId(resultSet.getInt("id"));
                bet.setUserId(resultSet.getInt("user_id"));
                bet.setGameId(resultSet.getInt("game_id"));
                bet.setHorseId(resultSet.getInt("horse_id"));
                bet.setBetAmount(resultSet.getDouble("bet_amount"));

                BetType betType = new BetType();
                betType.setType(BetType.TypeEnum.valueOf(resultSet.getString("bet_type")));
                betType.setCoefficient(resultSet.getDouble("bet_coefficient"));

                bet.setBetType(betType);
                bet.setUserWin(resultSet.getBoolean("user_win"));

                allUserBetsList.add(bet);
            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return allUserBetsList;

    }

    /**
     * Method that returns a list of all bets on a particular game
     *
     * @param gameId game id o search for all it bets
     * @return список всех ставок на игру
     * @throws DaoException
     */
    public List<Bet> findAllBetsByGameId(Integer gameId) throws DaoException{

        List<Bet> betList = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BET_BY_GAME_ID)) {

            statement.setInt(1, gameId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Bet bet = new Bet();
                bet.setId(resultSet.getInt("id"));
                bet.setUserId(resultSet.getInt("user_id"));
                bet.setGameId(resultSet.getInt("game_id"));
                bet.setHorseId(resultSet.getInt("horse_id"));
                bet.setBetAmount(resultSet.getDouble("bet_amount"));

                BetType betType = new BetType();
                betType.setType(BetType.TypeEnum.valueOf(resultSet.getString("bet_type")));
                betType.setCoefficient(resultSet.getDouble("bet_coefficient"));

                bet.setBetType(betType);
                bet.setUserWin(resultSet.getBoolean("user_win"));

                betList.add(bet);
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return betList;

    }

    /**
     * The method returns a list of all bets on a particular participant in the game
     *
     * @param gameId
     * @param horseId
     * @return list of bets of participant in the game
     * @throws DaoException
     */
    public List<Bet> findAllBetsByHorseIdAndGameId(Integer gameId, Integer horseId) throws DaoException{

        List<Bet> betList = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BET_BY_HORSE_ID_AND_GAME_ID)) {

            statement.setInt(1, gameId);
            statement.setInt(2, horseId);;
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Bet bet = new Bet();
                bet.setId(resultSet.getInt("id"));
                bet.setUserId(resultSet.getInt("user_id"));
                bet.setGameId(resultSet.getInt("game_id"));
                bet.setHorseId(resultSet.getInt("horse_id"));
                bet.setBetAmount(resultSet.getDouble("bet_amount"));

                BetType betType = new BetType();
                betType.setType(BetType.TypeEnum.valueOf(resultSet.getString("bet_type")));
                betType.setCoefficient(resultSet.getDouble("bet_coefficient"));

                bet.setBetType(betType);
                bet.setUserWin(resultSet.getBoolean("user_win"));

                betList.add(bet);
            }

        } catch (ConnectionPoolException | SQLException e){
            logger.error(e);
            throw new DaoException(e);
        }

        return betList;

    }

    @Override
    public Bet findByID(Integer id) throws DaoException {

        Bet bet = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BET_BY_ID);) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                bet = new Bet();
                bet.setId(resultSet.getInt("id"));
                bet.setUserId(resultSet.getInt("user_id"));
                bet.setGameId(resultSet.getInt("game_id"));
                bet.setHorseId(resultSet.getInt("horse_id"));
                bet.setBetAmount(resultSet.getDouble("bet_amount"));

                BetType betType = new BetType();
                betType.setType(BetType.TypeEnum.valueOf(resultSet.getString("bet_type")));
                betType.setCoefficient(resultSet.getDouble("bet_coefficient"));
                bet.setBetType(betType);

                bet.setUserWin(resultSet.getBoolean("user_win"));
            }

        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }

        return bet;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BET_BY_ID);) {

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
    public boolean create(Bet entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_BET, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, entity.getUserId());
            statement.setInt(2, entity.getGameId());
            statement.setInt(3, entity.getHorseId());
            statement.setDouble(4, entity.getBetAmount());
            statement.setString(5, entity.getBetType().getType().toString());
            statement.setDouble(6, entity.getBetType().getCoefficient());;
            statement.setBoolean(7, entity.isUserWin());
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
    public boolean update(Bet entity) throws DaoException {

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BET);) {

            statement.setInt(1, entity.getUserId());
            statement.setInt(2, entity.getGameId());
            statement.setInt(3, entity.getHorseId());
            statement.setDouble(4, entity.getBetAmount());
            statement.setString(5, entity.getBetType().getType().toString());
            statement.setDouble(6, entity.getBetType().getCoefficient());;
            statement.setBoolean(7, entity.isUserWin());

            statement.setInt(8, entity.getId());
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
