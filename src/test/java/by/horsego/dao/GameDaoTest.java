package by.horsego.dao;

import by.horsego.bean.Game;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameDaoTest {

    private static final Logger logger = Logger.getLogger(GameDaoTest.class);
    private static final GameDao gameDao = DaoFactory.getInstance().getGameDao();
    private static Game expected = new Game();

    @BeforeClass
    public static void init(){
        expected.setGamePlayed(true);
    }

    @Test
    public void findByIdTest(){
        Game actual = null;

        try {
            actual = gameDao.findByID(12);
        } catch (DaoException e) {
            logger.error(e);
        }
        expected.setId(actual.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createTest(){

        boolean created = false;

        try {
            created = gameDao.create(expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void updateTest(){
        expected.setGamePlayed(false);
        expected.setId(20);

        boolean updated = false;

        try {
            updated = gameDao.update(expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(updated);
    }

    @Test
    public void deleteTest(){
        expected.setId(20);

        boolean deleted = false;

        try {
            deleted = gameDao.delete(expected.getId());
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }

}
