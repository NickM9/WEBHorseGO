package by.horsego.dao;

import by.horsego.bean.BetType;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameBetsDaoTest {

    private static final Logger logger = Logger.getLogger(GameBetsDaoTest.class);
    private static final GameBetsDao gameBetsDao = DaoFactory.getInstance().getGameBetsDao();
    private static BetType expected = new BetType();
    private int gameId = 12;
    private int horseId = 1;

    @BeforeClass
    public static void init(){
        expected.setType(BetType.TypeEnum.VICTORY);
        expected.setCoefficient(1.3);
    }

    @Test
    public void findEntityTest(){
        BetType actual = null;

        try {
            actual = gameBetsDao.findEntity(gameId, horseId, expected.getType().toString());
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createTest(){
        expected.setCoefficient(888);
        boolean created = false;

        try {
            created = gameBetsDao.create(gameId, horseId, expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void updateTest(){
        expected.setCoefficient(999);

        boolean updated = false;

        try {
            updated = gameBetsDao.update(gameId, horseId, expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(updated);
    }

    @Test
    public void deleteTest(){
        boolean deleted = false;

        try {
            deleted = gameBetsDao.delete(gameId, horseId, expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }
}
