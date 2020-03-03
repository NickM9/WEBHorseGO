package by.horsego.dao;

import by.horsego.bean.Horse;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameHorsesDaoTest {

    private static final Logger logger = Logger.getLogger(GameHorsesDaoTest.class);
    private static final GameHorsesDao gameHorsesDao = DaoFactory.getInstance().getGameHorsesDao();
    private static Horse expected = new Horse();
    private int gameId = 12;

    @BeforeClass
    public static void init(){
        expected.setId(1);
        expected.setName("Jasper");
    }

    @Test
    public void findEntityTest(){
        Horse actual = null;

        try {
            actual = gameHorsesDao.findEntity(gameId, expected.getId());
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createTest(){

        boolean created = false;

        try {
            created = gameHorsesDao.create(gameId, expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void deleteTest(){
        expected.setId(15);

        boolean deleted = false;

        try {
            deleted = gameHorsesDao.delete(gameId, expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }

}
