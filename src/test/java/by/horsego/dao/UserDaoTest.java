package by.horsego.dao;

import by.horsego.bean.Role;
import by.horsego.bean.User;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDaoTest {

    private static final Logger logger = Logger.getLogger(UserDaoTest.class);
    private static final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static User expected = new User();

    @BeforeClass
    public static void init() {
        expected.setName("Ivan");
        expected.setSurname("Sokolov");
        expected.setLogin("Vano999");
        expected.setPassword("c589e2f9af657e1108a7410d748b704f");
        expected.setWallet(100.5);
        expected.setRole(Role.USER);
    }

    @Test
    public void findByIdTest() {
        User actual = null;

        try {
            actual = userDao.findByID(3);
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
            created = userDao.create(expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void updateTest(){
        expected.setId(10);
        expected.setWallet(1000);

        boolean updated = false;

        try {
            updated = userDao.update(expected);
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
            deleted = userDao.delete(expected.getId());
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }
}
