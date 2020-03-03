package by.horsego.service;

import by.horsego.bean.*;
import by.horsego.dao.BetDao;
import by.horsego.dao.DaoException;
import by.horsego.dao.DaoFactory;
import by.horsego.dao.GameBetsDao;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Class for working with bets services.
 *
 * Implements the interface {@link Service}
 * To get an instance of this class call the method {@link ServiceFactory#getBetService()}
 * on an object of the ServiceFactory class.
 *
 * @see Service
 * @see ServiceFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class BetService implements Service {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private BetDao betDao = daoFactory.getBetDao();
    private GameBetsDao gameBetsDao = daoFactory.getGameBetsDao();
    private static UserService userService = new UserService();
    private static GameService gameService = new GameService();
    private final static Logger logger = Logger.getLogger(BetService.class);

    //package-private
    BetService(){}

    public Map<Bet, Game> getAllUserBets(User user) throws ServiceException{

        Map<Bet, Game> betsMap = new LinkedHashMap<>();

        List<Bet> betList = null;

        try {
            betList = betDao.findAllUserBets(user.getId());
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        Collections.reverse(betList);

        for (Bet bet : betList){
            Game game = gameService.findGameById(bet.getGameId(), user);
            betsMap.put(bet, game);
        }

        return betsMap;
    }

    public boolean isBetCreated(User user, int gameId, int horseId, String strBetType, double betAmount) throws ServiceException {

        boolean decreaseWallet = userService.isWalletDecrease(user, betAmount);

        if(!decreaseWallet){
            return false;
        }

        BetType betType = null;

        try {
            betType = gameBetsDao.findEntity(gameId, horseId, strBetType);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        Bet bet = new Bet();
        bet.setUserId(user.getId());
        bet.setHorseId(horseId);
        bet.setGameId(gameId);
        bet.setBetAmount(betAmount);
        bet.setBetType(betType);

        boolean betCreated = false;

        try {
            betCreated = betDao.create(bet);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return betCreated;
    }

    public List<Bet> getAllGameBets(int gameId) throws ServiceException {

        List<Bet> gameBetsList = null;

        try {
            gameBetsList = betDao.findAllBetsByGameId(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return gameBetsList;
    }

    public List<Bet> getAllBetsByHorseIdAndGameId(int gameId, int horseId) throws ServiceException {

        List<Bet> allBetsByHorseIdAndGameId = null;

        try {
            allBetsByHorseIdAndGameId = betDao.findAllBetsByHorseIdAndGameId(gameId, horseId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return allBetsByHorseIdAndGameId;
    }

    /**
     * The method delete bets on a participant in the list of games that came in.
     *
     * Deletes bets on a participant in the list of games, and returns money to the user
     * in the method {@link #isBetDeleted(Bet)}. (bets table)
     *
     * @see #isBetDeleted(Bet)
     * @param horse
     * @param horseGames
     * @return true if the bets deleted
     * @throws ServiceException
     */
    public boolean isHorseBetsDeleted(Horse horse, List<Game> horseGames) throws ServiceException {

        for (Game game : horseGames){

            List<Bet> horseGameBets = getAllBetsByHorseIdAndGameId(game.getId(), horse.getId());

            for (Bet bet : horseGameBets){
                boolean betDeleted = isBetDeleted(bet);
                if (!betDeleted){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * The method deletes the bet.
     *
     * Returns money to the user in the method {@link #isUserMoneyReturned(User, Bet)}, delete a bet.
     *
     * @see #isUserMoneyReturned(User, Bet)
     * @param bet
     * @return true if the bet deleted
     * @throws ServiceException
     */
    public boolean isBetDeleted(Bet bet) throws ServiceException {

        User user = userService.findUserById(bet.getUserId());
        boolean usersMoneyReturned = isUserMoneyReturned(user, bet);

        if (!usersMoneyReturned){
            return false;
        }

        boolean betDeleted = false;

        try {
            betDeleted = betDao.delete(bet.getId());
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        if (!betDeleted){
            return false;
        }

        return true;
    }

    /**
     * This method delete all bets on the game.
     *
     * Delete each bet and return the user money in the method {@link #isBetDeleted(Bet)}
     *
     * @see #isBetDeleted(Bet)
     * @param gameId
     * @return true if all bets deleted
     * @throws ServiceException
     */
    public boolean isGameBetsDeletedByGameId(int gameId) throws ServiceException {

        List<Bet> gameBetsList = null;

        try {
            gameBetsList = betDao.findAllBetsByGameId(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        for (Bet bet : gameBetsList){
            boolean betDeleted = isBetDeleted(bet);
            if(!betDeleted){
                return false;
            }
        }

        return true;
    }

    public void gameBetsResults(Map<Integer, Horse> gameResult, Bet bet) throws ServiceException {

        if (bet.getBetType().getType().equals(BetType.TypeEnum.VICTORY)){
            victoryResult(gameResult, bet);
        } else if (bet.getBetType().getType().equals(BetType.TypeEnum.FIRST_THREE)){
            firstThreeResult(gameResult, bet);
        } else if (bet.getBetType().getType().equals(BetType.TypeEnum.OUTSIDER)){
            outsiderResult(gameResult, bet);
        } else {
            lastThreeResult(gameResult, bet);
        }

    }

    //helpers

    private boolean isUserMoneyReturned(User user, Bet userBet) throws ServiceException {

        boolean moneyReturned = userService.isWalletIncrease(user, userBet.getBetAmount());
        if(!moneyReturned){
            return false;
        }

        return true;
    }

    private void victoryResult(Map<Integer, Horse> gameResult, Bet bet) throws ServiceException {

        User user = userService.findUserById(bet.getUserId());
        int horseId = bet.getHorseId();

        if (horseId == gameResult.get(1).getId()){

            double userAmount = bet.getBetAmount() * bet.getBetType().getCoefficient();
            user.setWallet(user.getWallet() + userAmount);
            userService.isUserUpdated(user);
            bet.setUserWin(true);

            try {
                betDao.update(bet);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }

        }
    }

    private void firstThreeResult(Map<Integer, Horse> gameResult, Bet bet) throws ServiceException {

        User user = userService.findUserById(bet.getUserId());
        int horseId = bet.getHorseId();

        if (horseId == gameResult.get(1).getId() ||
            horseId == gameResult.get(2).getId() ||
            horseId == gameResult.get(3).getId()){

            double userAmount = bet.getBetAmount() * bet.getBetType().getCoefficient();
            user.setWallet(user.getWallet() + userAmount);
            userService.isUserUpdated(user);
            bet.setUserWin(true);

            try {
                betDao.update(bet);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }

        }
    }

    private void outsiderResult(Map<Integer, Horse> gameResult, Bet bet) throws ServiceException {

        User user = userService.findUserById(bet.getUserId());
        int horseId = bet.getHorseId();

        if (horseId == gameResult.get(gameResult.size()).getId()){

            double userAmount = bet.getBetAmount() * bet.getBetType().getCoefficient();
            user.setWallet(user.getWallet() + userAmount);
            userService.isUserUpdated(user);
            bet.setUserWin(true);

            try {
                betDao.update(bet);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }

        }
    }

    private void lastThreeResult(Map<Integer, Horse> gameResult, Bet bet) throws ServiceException {

        User user = userService.findUserById(bet.getUserId());
        int horseId = bet.getHorseId();

        if (horseId == gameResult.get(gameResult.size()).getId() ||
                horseId == gameResult.get(gameResult.size()-1).getId() ||
                horseId == gameResult.get(gameResult.size()-2).getId()){

            double userAmount = bet.getBetAmount() * bet.getBetType().getCoefficient();
            user.setWallet(user.getWallet() + userAmount);
            userService.isUserUpdated(user);
            bet.setUserWin(true);
            try {
                betDao.update(bet);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }

        }

    }


}
