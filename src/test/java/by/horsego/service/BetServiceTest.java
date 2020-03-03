package by.horsego.service;

import by.horsego.bean.Bet;
import by.horsego.bean.BetType;
import by.horsego.bean.User;
import by.horsego.dao.DaoException;
import by.horsego.dao.DaoFactory;
import by.horsego.dao.UserDao;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BetServiceTest {

    private static final Logger logger = Logger.getLogger(BetServiceTest.class);
    private static final BetService betService = ServiceFactory.getInstance().getBetService();
    private static final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static Bet bet = new Bet();
    private static User user;

    @BeforeClass
    public static void init() {
        try {
            user = userDao.findByID(3);
        } catch (DaoException e) {
            logger.error(e);
        }

        bet.setUserId(user.getId());
        bet.setGameId(12);
        bet.setHorseId(1);
        bet.setBetAmount(15);

        BetType betType = new BetType();
        betType.setType(BetType.TypeEnum.VICTORY);
        betType.setCoefficient(1.3);

        bet.setBetType(betType);
        bet.setUserWin(false);
    }

    @Test
    public void isBetCratedTest() {
        boolean created = false;

        try {
            created = betService.isBetCreated(user, bet.getGameId(), bet.getHorseId(),
                    bet.getBetType().getType().toString(), bet.getBetAmount());
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void isBetDeletedTest(){
        boolean deleted = false;

        try {
            deleted = betService.isBetDeleted(bet);
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }

    @Test
    public void isGameBetsDeletedByGameIdTest(){
        boolean deleted = false;

        try {
            deleted = betService.isGameBetsDeletedByGameId(bet.getGameId());
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }

}
