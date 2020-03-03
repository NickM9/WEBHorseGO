package by.horsego.dao;

/**
 * Class factory for getting instances of classes that implement the Dao interface.
 * A static method {@link #getInstance()} is used to get an instance of this class.
 *
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class DaoFactory {
	private final static DaoFactory instance = new DaoFactory();
	private final static UserDao userDao = new UserDao();
	private final static HorseDao horseDao = new HorseDao();
	private final static BetDao betDao = new BetDao();
	private final static GameDao gameDao = new GameDao();
	private final static GameBetsDao gameBetsDao = new GameBetsDao();
	private final static GameHorsesDao gameHorsesDao = new GameHorsesDao();
	
	private DaoFactory() {}
	
	public static DaoFactory getInstance() {
		return instance;
	}
	
	public  UserDao getUserDao() {
		return userDao;
	}
	
	public  HorseDao getHorseDao() {
		return horseDao;
	}
	
	public  BetDao getBetDao() {
		return betDao;
	}

	public  GameDao getGameDao() {
		return gameDao;
	}

	public  GameBetsDao getGameBetsDao() {
		return gameBetsDao;
	}

	public GameHorsesDao getGameHorsesDao() {
		return gameHorsesDao;
	}
}
