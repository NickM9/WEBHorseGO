package by.horsego.service;

import by.horsego.bean.*;
import by.horsego.dao.*;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Class for working with games services.
 *
 * Implements the interface {@link Service}
 * To get an instance of this class call the method {@link ServiceFactory#getGameService()}
 * on an object of the ServiceFactory class.
 *
 * @see Service
 * @see ServiceFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class GameService implements Service {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private GameDao gameDao = daoFactory.getGameDao();
    private GameHorsesDao gameHorsesDao = daoFactory.getGameHorsesDao();
    private GameBetsDao gameBetsDao = daoFactory.getGameBetsDao();
    private HorseDao horseDao = daoFactory.getHorseDao();
    private static BetService betService = new BetService();
    private final static Logger logger = Logger.getLogger(GameService.class);

    //package-private
    GameService(){}

    public Map<Integer, Horse> startGame(int gameId) throws ServiceException {

        Random generator = new Random();

        Game game = null;
        try {
            game = gameDao.findByID(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        buildGame(game);
        List<Bet> betList = betService.getAllGameBets(gameId);

        Map<Integer, Horse> gameResult = new LinkedHashMap<>();
        List<Horse> players = game.getHorses();

        int size = players.size();
        for(int i = 0; i < size; i++){
            int number = generator.nextInt(players.size());
            Horse horse = players.get(number);
            players.remove(horse);
            gameResult.put(i+1, horse);
        }

        game.setGamePlayed(true);

        try {
            gameDao.update(game);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        for(Bet bet : betList){
            betService.gameBetsResults(gameResult, bet);
        }

        return gameResult;
    }

    public List<Game> getAllNotPlayedGames() throws ServiceException {

        List<Game> allNotPlayedGames = null;
        try {
            allNotPlayedGames = gameDao.findAllNotPlayedGames();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        for(Game game : allNotPlayedGames){
            buildGame(game);
        }

        Collections.reverse(allNotPlayedGames);

        return allNotPlayedGames;
    }

    /**
     * Returns a list of games and set values of participant and bet types.
     *
     * Checks whether all types of bets are set for participants and if not, does not enter the game in the list.
     *
     * @return list of games
     * @throws ServiceException
     */
    public List<Game> getGamesForTable() throws ServiceException {

        List<Game> allNotPlayedGames = null;
        try {
            allNotPlayedGames = gameDao.findAllNotPlayedGames();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        List<Game> gamesForTable = new ArrayList<>();

        for(Game game : allNotPlayedGames){

            buildGame(game);

            boolean validHorseBets = isHorseBetsValidForTable(game);

            if (validHorseBets) {
                gamesForTable.add(game);
            }
        }

        return gamesForTable;
    }

    public List<Game> getAllGames() throws ServiceException {

        List<Game> allGames = null;
        try {
            allGames = gameDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        for (Game game : allGames){
            buildGame(game);
        }

        Collections.reverse(allGames);

        return allGames;
    }

    /**
     * The method returns the game.
     *
     * If the user role is USER the method returns the game only if it contains all types of bets for participants.
     * If the user is null or its role is not USER, it will return the game in any case.
     *
     * @param gameId
     * @param user can be null
     * @return Game
     * @throws ServiceException
     */
    public Game findGameById(int gameId, User user) throws ServiceException {

        Game game = null;
        try {
            game = gameDao.findByID(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        if (game == null){
            return null;
        }

        if (user == null || !user.getRole().equals(Role.USER)){
            buildGame(game);
            return game;
        }

        buildGame(game);
        boolean validHorseBets = isHorseBetsValidForTable(game);

        if(!validHorseBets){
            return null;
        }

        return game;
    }

    public boolean isGameCreated(String[] horsesId) throws ServiceException {

        List<Horse> horses = parseHorses(horsesId);

        Game game = new Game();
        game.setHorses(horses);
        game.setGamePlayed(false);

        boolean gameCreate = false;
        try {
            gameCreate = gameDao.create(game);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        boolean horsesCreated = isGameHorsesCreated(game);

        return gameCreate && horsesCreated;
    }

    public boolean isGameUpdated(int gameId, boolean gamePlayed, String[] horsesId) throws ServiceException {

        Game game = null;
        try {
            game = gameDao.findByID(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        game.setGamePlayed(gamePlayed);
        boolean gameUpdated = false;
        try {
            gameUpdated = gameDao.update(game);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        if (gamePlayed){
            return gameUpdated;
        }

        buildGame(game);

        List<Horse> oldHorses = null;
        try {
            oldHorses = gameHorsesDao.findAll(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        List<Horse> horses = parseHorses(horsesId);
        game.setHorses(horses);

        boolean gameHorseBetTypesUpdated = isGameHorseBetTypesUpdated(game, oldHorses);

        boolean gameHorsesReSaved = isGameHorsesReSaved(game);

        List<Bet> oldBetsList = betService.getAllGameBets(game.getId());
        boolean gameBetsReSaved = isGameBetsReSaved(game, oldBetsList);


        return gameUpdated && gameHorsesReSaved && gameBetsReSaved && gameHorseBetTypesUpdated;
    }

    /**
     * The method delete the game.
     *
     * Delete bets on the game from users and
     * returns them money in the method {@link BetService#isGameBetsDeletedByGameId(int)} (bets table),
     * delete the types of bets on game participants
     * in the method {@link GameBetsDao#deleteAllByGameId(int)} (game_bets table),
     * delete participants for the game in the method {@link GameHorsesDao#deleteAll(Integer)} (game_horses table),
     * delete the game (games table).
     *
     *
     * @see BetService#isGameBetsDeletedByGameId(int) 
     * @see GameBetsDao#deleteAllByGameId(int) 
     * @see GameHorsesDao#deleteAll(Integer)
     * @param gameId
     * @return true, if the game is deleted
     * @throws ServiceException
     */
    public boolean isGameDeleted(int gameId) throws ServiceException {

        boolean betsDeleted = betService.isGameBetsDeletedByGameId(gameId);
        boolean gameBetTypesDeleted = false;
        try {
            gameBetTypesDeleted = gameBetsDao.deleteAllByGameId(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        boolean gameHorsesDeleted = false;
        try {
            gameHorsesDeleted = gameHorsesDao.deleteAll(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        boolean gameDeleted = false;
        try {
            gameDeleted = gameDao.delete(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return betsDeleted && gameBetTypesDeleted && gameHorsesDeleted && gameDeleted;
    }

    public boolean isGameBetsUpdated(int gameId, int horseId, String[] betCoefficients) throws ServiceException {

        Game game = null;
        try {
            game = gameDao.findByID(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        if (game.isGamePlayed()){
            return false;
        }
        buildGame(game);

        Horse horse = null;
        try {
            horse = gameHorsesDao.findEntity(gameId, horseId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        Map<Horse, List<BetType>> horsesBets = game.getHorseBetTypes();
        List<BetType> betTypes = parseHorseBets(betCoefficients);

        if (horsesBets.get(horse) == null || horsesBets.get(horse).isEmpty()){
            horsesBets.replace(horse, betTypes);
            boolean gameBetsCreated = isGameBetsCreated(game, horse, betTypes);
            if(!gameBetsCreated){
                return false;
            }
        }

        horsesBets.replace(horse, betTypes);
        game.setHorseBetTypes(horsesBets);

        boolean gameBetsUpdated = isGameBetsUpdated(game, horse);

        return gameBetsUpdated;
    }

    public boolean isGameBetsDeleted(Game game, Horse horse) throws ServiceException {

        if(game.getHorseBetTypes().get(horse) == null){
            return true;
        }

        for (BetType betType : game.getHorseBetTypes().get(horse)){

            boolean betTypeDeleted = false;
            try {
                betTypeDeleted = gameBetsDao.delete(game.getId(), horse.getId(), betType);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
            if(!betTypeDeleted){
                return false;
            }

        }

        return true;
    }

    public boolean isGameHorseDeleted(int gameId, int horseId) throws ServiceException {

        Game game = null;
        try {
            game = gameDao.findByID(gameId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        buildGame(game);

        Horse horse = null;
        try {
            horse = gameHorsesDao.findEntity(gameId, horseId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        boolean gameHorseDeleted = false;
        try {
            gameHorseDeleted = gameHorsesDao.delete(game.getId(), horse);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return gameHorseDeleted;
    }


    //helpers

    // set horses and bet types in game
    private void buildGame (Game game) throws ServiceException {

        List<Horse> horses = null;
        try {
            horses = gameHorsesDao.findAll(game.getId());
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        game.setHorses(horses);

        Map<Horse, List<BetType>> horseBetTypes = new LinkedHashMap<>();

        for (Horse horse : horses){

            try {
                if (gameBetsDao.findAllByHorseId(game.getId(), horse.getId()) == null){
                    horseBetTypes.put(horse, new ArrayList<>());
                } else {
                    List<BetType> betTypes = gameBetsDao.findAllByHorseId(game.getId(), horse.getId());
                    horseBetTypes.put(horse, betTypes);
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }

        game.setHorseBetTypes(horseBetTypes);
    }

    // check for every horse has bet types
    private boolean isHorseBetsValidForTable(Game game) throws ServiceException {

        Map<Horse, List<BetType>> horsesBets = game.getHorseBetTypes();
        for (Map.Entry<Horse, List<BetType>> horseBets : horsesBets.entrySet()){

            if (horseBets.getValue() == null || horseBets.getValue().isEmpty()){
                return false;
            }
        }
        return true;
    }

    // create horses from id array
    private List<Horse> parseHorses(String[] horsesId) throws ServiceException {

        List<Horse> horses = new ArrayList<>();
        for (String stringId : horsesId){
            int id = Integer.parseInt(stringId);
            Horse horse = null;
            try {
                horse = horseDao.findByID(id);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
            horses.add(horse);
        }

        return horses;
    }

    private boolean isGameHorsesCreated(Game game) throws ServiceException {

        for (Horse horse : game.getHorses()){
            boolean horseCreated = false;
            try {
                horseCreated = gameHorsesDao.create(game.getId(), horse);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }

            if (!horseCreated){
                return false;
            }
        }

        return true;
    }

    private boolean isGameHorsesReSaved(Game game) throws ServiceException {

        boolean gameHorsesDeleted = isGameHorsesDeleted(game);
        boolean gameHorsesCreated = isGameHorsesCreated(game);

        return gameHorsesDeleted && gameHorsesCreated;
    }

    private boolean isGameHorsesDeleted(Game game) throws ServiceException {

        List<Horse> horsesInDataBase = null;
        try {
            horsesInDataBase = gameHorsesDao.findAll(game.getId());
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        for (Horse horse : horsesInDataBase){
            boolean horseDeleted = false;
            try {
                horseDeleted = gameHorsesDao.delete(game.getId(), horse);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
            if(!horseDeleted){
                return false;
            }
        }

        return true;
    }

    // create bet types from coefficient array
    private List<BetType> parseHorseBets(String[] betCoefficients) throws ServiceException {

        List<BetType> betTypes = new ArrayList<>();
        BetType.TypeEnum[] betEnum = BetType.TypeEnum.values();

        for (int i = 0; i < betEnum.length; i++) {
            BetType betType = new BetType();
            betType.setType(betEnum[i]);
            double betCoefficient = Double.parseDouble(betCoefficients[i]);
            betType.setCoefficient(betCoefficient);
            betTypes.add(betType);
        }

        return betTypes;
    }

    private boolean isGameBetsCreated(Game game, Horse horse, List<BetType>betTypes) throws ServiceException {

        for (BetType betType : betTypes){

            boolean gameBetCreated = false;
            try {
                gameBetCreated = gameBetsDao.create(game.getId(), horse.getId(), betType);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
            if (!gameBetCreated){
                return false;
            }
        }

        return true;
    }

    private boolean isGameBetsUpdated(Game game, Horse horse) throws ServiceException {

        for (BetType betType : game.getHorseBetTypes().get(horse)){

            boolean betTypeUpdated = false;
            try {
                betTypeUpdated = gameBetsDao.update(game.getId(), horse.getId(), betType);
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
            if(!betTypeUpdated){
                return false;
            }

        }

        return true;
    }

    private boolean isGameBetsReSaved(Game game, List<Bet> oldBetsList) throws ServiceException {

        for (Bet bet : oldBetsList){
            boolean horseExist = false;

            for (Horse horse : game.getHorses()){
                if(bet.getHorseId() == horse.getId()){
                    horseExist = true;
                }
            }

            if(!horseExist){
                boolean betDeleted = betService.isBetDeleted(bet);
                if(!betDeleted){
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isGameHorseBetTypesUpdated(Game game, List<Horse> oldHorses) throws ServiceException {

        for (Horse oldHorse : oldHorses){
            boolean horseExist = false;
            //remove
            System.out.println(oldHorse);
            System.out.println(game.getHorseBetTypes().get(oldHorse));

            for(Horse horse : game.getHorses()){
                if (oldHorse.getId() == horse.getId()){
                    horseExist = true;
                }
            }

            if(!horseExist){
                boolean betTypeDeleted = isGameBetsDeleted(game, oldHorse);
                if(!betTypeDeleted){
                    return false;
                }
            }
        }

        return true;
    }

}
