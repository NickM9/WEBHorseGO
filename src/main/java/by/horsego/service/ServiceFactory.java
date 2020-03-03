package by.horsego.service;

/**
 * Factory class for getting instances of service layer classes.
 * A static method {@link #getInstance()} is used to get an instance of this class.
 *
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class ServiceFactory {

    private final static ServiceFactory instance = new ServiceFactory();
    private final static GameService gameService = new GameService();
    private final static UserService userService = new UserService();
    private final static BetService betService = new BetService();
    private final static HorseService horseService = new HorseService();

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance;
    }

    public  GameService getGameService() {
        return gameService;
    }

    public HorseService getHorseService(){return horseService;}

    public UserService getUserService(){return userService;}

    public BetService getBetService(){return betService;}
}
