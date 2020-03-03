package by.horsego.service;

import by.horsego.bean.Game;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameServiceTest {

    private static final Logger logger = Logger.getLogger(GameServiceTest.class);
    private static final GameService gameService = ServiceFactory.getInstance().getGameService();
    private String[] horsesId = new String[] {"1", "2", "3"};
    private static Game game = new Game();

    @BeforeClass
    public static void init(){
        game.setId(12);
        game.setGamePlayed(true);
    }

    @Test
    public void isGameCreatedTest(){
        boolean created = false;

        try {
            created = gameService.isGameCreated(horsesId);
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void isGameUpdatedTest(){
        boolean updated = false;

        try {
            updated = gameService.isGameUpdated(game.getId(), !game.isGamePlayed(), horsesId);
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(updated);
    }

    @Test
    public void isGameDeletedTest(){
        boolean deleted = false;

        try {
            deleted = gameService.isGameDeleted(game.getId());
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }

    @Test
    public void isGameHorseDeleted(){
        boolean deleted = false;

        try {
            deleted = gameService.isGameHorseDeleted(game.getId(), 1);
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }

}
