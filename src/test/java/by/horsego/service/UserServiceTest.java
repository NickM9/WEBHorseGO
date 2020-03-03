package by.horsego.service;

import by.horsego.bean.Role;
import by.horsego.bean.User;
import by.horsego.dao.DaoException;
import by.horsego.dao.DaoFactory;
import by.horsego.dao.UserDao;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserServiceTest {

    private static final Logger logger = Logger.getLogger(UserServiceTest.class);
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static User expected;

    @BeforeClass
    public static void init() {
        try {
            expected = userDao.findByID(3);
        } catch (DaoException e) {
            logger.error(e);
        }
    }

    @Test
    public void findUserByIdTest(){
        User actual = null;

        try {
            actual = userService.findUserById(expected.getId());
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findUserByLoginTest(){
        User actual = null;

        try {
            actual = userService.findUserByLogin(expected.getLogin());
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void logInTest(){
        User actual = null;

        try {
            actual = userService.logIn(expected.getLogin(), "Vano9Password");
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isSignUpTest(){
        boolean created = false;

        try {
            created = userService.isSignUp("Nikola", "Tesla", "NokolaT", "Nikola9Password", Role.USER.toString());
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void isLoginExistTest(){
        boolean exist = false;

        try {
            exist = userService.isLoginExist(expected.getLogin());
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(exist);
    }

}
