package by.horsego.dao;

import by.horsego.bean.Bet;
import by.horsego.bean.BetType;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BetDaoTest {

    private static final Logger logger = Logger.getLogger(BetDaoTest.class);
    private static final BetDao betDao = DaoFactory.getInstance().getBetDao();
    private static Bet expected = new Bet();

    @BeforeClass
    public static void init(){
        expected.setUserId(3);
        expected.setGameId(12);
        expected.setHorseId(15);
        expected.setBetAmount(20);

        BetType betType = new BetType();
        betType.setType(BetType.TypeEnum.VICTORY);
        betType.setCoefficient(1.3);

        expected.setBetType(betType);
        expected.setUserWin(false);
    }

    @Test
    public void findByIdTest(){
        Bet actual = null;

        try {
            actual = betDao.findByID(53);
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
            created = betDao.create(expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void updateTest(){
        expected.setBetAmount(100);
        expected.setId(62);

        boolean updated = false;

        try {
            updated = betDao.update(expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(updated);
    }

    @Test
    public void deleteTest(){
        expected.setId(62);

        boolean deleted = false;

        try {
            deleted = betDao.delete(expected.getId());
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }
}
