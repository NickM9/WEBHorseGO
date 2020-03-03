package by.horsego.service;

import by.horsego.bean.Game;
import by.horsego.bean.Horse;
import by.horsego.dao.DaoException;
import by.horsego.dao.DaoFactory;
import by.horsego.dao.GameHorsesDao;
import by.horsego.dao.HorseDao;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Class for working with horse services.
 *
 * Implements the interface {@link Service}
 * To get an instance of this class call the method {@link ServiceFactory#getHorseService()}
 * on an object of the ServiceFactory class.
 *
 * @see Service
 * @see ServiceFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class HorseService implements Service {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private HorseDao horseDao = daoFactory.getHorseDao();
    private GameHorsesDao gameHorsesDao = daoFactory.getGameHorsesDao();
    private BetService betService = new BetService();
    private GameService gameService = new GameService();
    private final static Logger logger = Logger.getLogger(HorseService.class);

    //package-private
    HorseService(){}

    /**
     * The method returns a map with the participant's key object and
     * a list of the id of not played games in which the participant participates in.
     *
     * For sorting use {@link HorsesTableComparator}, which sorts game numbers from larger to smaller.
     *
     * @see HorsesTableComparator
     * @return map with the participant's key object and a list of the id of not played games
     * in which the participant participates in
     *
     * @throws ServiceException
     */
    public Map<Horse, List<Integer>> getHorsesForTable() throws ServiceException {

        Map<Horse, List<Integer>> tableHorses = new LinkedHashMap<>();

        List<Horse> horses = null;
        try {
            horses = horseDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        for (Horse horse : horses){

            List<Integer> horseGames = null;
            try {
                horseGames = gameHorsesDao.findNotPlayedHorseGames(horse.getId());
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }

            Collections.sort(horseGames, new HorsesTableComparator());
            tableHorses.put(horse, horseGames);
        }

        return tableHorses;
    }

    public List<Horse> getAllHorses() throws ServiceException {

        List<Horse> horses = null;
        try {
            horses = horseDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return horses;
    }

    public Horse findHorseById(int horseId) throws ServiceException {

        Horse horse = null;
        try {
            horse = horseDao.findByID(horseId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return horse;
    }

    public List<Game> getHorseGames(Horse horse) throws ServiceException {

        List<Integer> horseGamesId = null;
        try {
            horseGamesId = gameHorsesDao.findHorseGames(horse.getId());
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        Collections.sort(horseGamesId, new HorsesTableComparator());
        List<Game> horseGames= new ArrayList<>();

        for (Integer i : horseGamesId){
            Game game = gameService.findGameById(i, null);
            horseGames.add(game);
        }

        return horseGames;
    }

    public boolean isHorseCreated (String horseName) throws ServiceException {

        Horse horse = new Horse();
        horse.setName(horseName);

        boolean horseCreated = false;
        try {
            horseCreated = horseDao.create(horse);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return horseCreated;
    }

    public boolean isHorseUpdated(int horseId, String horseName) throws ServiceException {

        Horse horse = null;
        try {
            horse = horseDao.findByID(horseId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        horse.setName(horseName);

        boolean horseUpdated = false;
        try {
            horseUpdated = horseDao.update(horse);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return horseUpdated;
    }

    /**
     * The method delete the participant.
     *
     * Delete the user bets on a participant in not played games and
     * returns them money in the method {@link BetService#isHorseBetsDeleted(Horse, List)} (bets table),
     * delete this participant bet types for not played games
     * in the method {@link GameService#isGameBetsDeleted(Game, Horse)} (game_bets table),
     * delete a participant from not played games
     * in the method {@link GameService#isGameHorseDeleted(int, int)} (game_horses table),
     * delete the participant (horses table).
     *
     *
     * @see BetService#isHorseBetsDeleted(Horse, List)
     * @see GameService#isGameBetsDeleted(Game, Horse)
     * @see GameService#isGameHorseDeleted(int, int)
     * @param horseId
     * @return true, if horse deleted
     * @throws ServiceException
     */
    public boolean isHorseDeleted(int horseId) throws ServiceException {

        Horse horse = null;
        try {
            horse = horseDao.findByID(horseId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        List<Game> notPayedHorseGames = getNotPlayedHorseGames(horse);

        boolean horseBetsDeleted = betService.isHorseBetsDeleted(horse, notPayedHorseGames);

        boolean gameBetsDeleted = true;
        for (Game game : notPayedHorseGames){
            horseBetsDeleted = gameService.isGameBetsDeleted(game, horse);
            if(!horseBetsDeleted){
                gameBetsDeleted = false;
                break;
            }
        }

        boolean horseFromGamesDeleted = true;
        for (Game game : notPayedHorseGames){
            horseFromGamesDeleted = gameService.isGameHorseDeleted(game.getId(), horse.getId());
            if(!horseFromGamesDeleted){
                horseFromGamesDeleted = false;
                break;
            }
        }

        boolean horseDeleted = false;
        try {
            horseDeleted = horseDao.delete(horseId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return horseBetsDeleted && gameBetsDeleted && horseFromGamesDeleted && horseDeleted;
    }

    private List<Game> getNotPlayedHorseGames(Horse horse) throws ServiceException {

        List<Integer> notPayedHorseGamesId = null;
        try {
            notPayedHorseGamesId = gameHorsesDao.findNotPlayedHorseGames(horse.getId());
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        List<Game> notPayedHorseGames = new ArrayList<>();

        for (Integer i : notPayedHorseGamesId){
            Game game = gameService.findGameById(i, null);
            notPayedHorseGames.add(game);
        }

        return notPayedHorseGames;
    }
}
