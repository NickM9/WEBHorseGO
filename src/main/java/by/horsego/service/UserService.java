package by.horsego.service;

import by.horsego.bean.Role;
import by.horsego.bean.User;
import by.horsego.dao.DaoException;
import by.horsego.dao.DaoFactory;
import by.horsego.dao.UserDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for working with user services.
 *
 * Implements the interface {@link Service}
 * To get an instance of this class call the method {@link ServiceFactory#getUserService()}
 * on an object of the ServiceFactory class.
 *
 * @see Service
 * @see ServiceFactory
 * @author Mikita Masukhranau
 * @version 1.0
 */


public class UserService implements Service {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.getUserDao();
    private final static Logger logger = Logger.getLogger(UserService.class);

    //package-private
    UserService(){}

    public List<User> getAllUsers() throws ServiceException {

        List<User> users = null;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return users;
    }

    public User findUserById(int userId) throws ServiceException {

        User user = null;
        try {
            user = userDao.findByID(userId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return user;
    }

    public User findUserByLogin(String login) throws ServiceException {

        User user = null;
        try {
            user = userDao.findByLogin(login);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return user;
    }

    public List<User> findUsersByNameAndSurname(String name, String surname) throws ServiceException {

        List<User> users = null;
        try {
            users = userDao.findByNameAndSurname(name, surname);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return users;
    }

    public User logIn(String login, String password) throws ServiceException {

        password = encryption(password);

        User user = null;
        try {
            user = userDao.findByLoginAndPassword(login, password);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return user;
    }

    public boolean isSignUp(String name, String surname, String login, String password, String role) throws ServiceException {

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        password = encryption(password);
        user.setPassword(password);
        Role userRole = Role.valueOf(role);
        user.setRole(userRole);

        boolean created = false;
        try {
            created = userDao.create(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return created;
    }

    public boolean isLoginExist(String login) throws ServiceException {

        User user = null;
        try {
            user = userDao.findByLogin(login);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        if (user != null){
            return true;
        }

        return false;
    }

    public boolean isPasswordValid(String password, final String REG_EX) throws ServiceException {

        Pattern pattern = Pattern.compile(REG_EX);
        Matcher matcher = pattern.matcher(password);

        boolean passwordValid = matcher.matches();

        return passwordValid;
    }

    public boolean isWalletIncrease(User user, double sum) throws ServiceException {

        double userWallet = user.getWallet();
        userWallet += sum;
        user.setWallet(userWallet);

        boolean res = false;
        try {
            res = userDao.update(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return res;
    }

    public boolean isWalletDecrease(User user, double sum) throws ServiceException {

        double userWallet = user.getWallet();

        if (userWallet < sum){
            return false;
        }

        userWallet -= sum;
        user.setWallet(userWallet);
        boolean res = false;
        try {
            res = userDao.update(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return res;
    }

    public boolean isLoginUpdated(User user, String login, String password) throws ServiceException {

        password = encryption(password);
        if (!user.getPassword().equals(password)){
            return false;
        }

        user.setLogin(login);
        boolean userUpdate = false;
        try {
            userUpdate = userDao.update(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return userUpdate;
    }

    public boolean isPasswordUpdate(User user, String password, String newPassword) throws ServiceException {

        password = encryption(password);
        if (!(user.getPassword().equals(password))){
            return false;
        }

        newPassword = encryption(newPassword);
        user.setPassword(newPassword);

        boolean updateUser = false;
        try {
            updateUser = userDao.update(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return updateUser;
    }

    public boolean isNameUpdated(User user, String name, String surname, String password) throws ServiceException {

        password = encryption(password);
        if (!user.getPassword().equals(password)){
            return false;
        }

        user.setName(name);
        user.setSurname(surname);

        boolean nameUpdated = false;
        try {
            nameUpdated = userDao.update(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return nameUpdated;
    }

    public boolean isUserCreated(String name, String surname, String login, String password, double wallet, String role) throws ServiceException {

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        password = encryption(password);
        user.setPassword(password);
        user.setWallet(wallet);
        Role userRole = Role.valueOf(role);
        user.setRole(userRole);

        boolean created = false;
        try {
            created = userDao.create(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return created;
    }

    public boolean isUserUpdated(int userId, String name, String surname, String login, double wallet, String role) throws ServiceException {

        User user = null;
        try {
            user = userDao.findByID(userId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        user.setWallet(wallet);
        Role userRole = Role.valueOf(role);
        user.setRole(userRole);

        boolean updated = false;
        try {
            updated = userDao.update(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return updated;
    }

    public boolean isUserUpdated(User user) throws ServiceException {

        boolean userUpdated = false;
        try {
            userUpdated = userDao.update(user);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return userUpdated;
    }

    public boolean isUserDeleted(int userId) throws ServiceException {

        boolean userDeleted = false;
        try {
            userDeleted = userDao.delete(userId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return userDeleted;
    }

    private String encryption(String password) throws ServiceException {
        String md5Hex = DigestUtils.md5Hex(password);
        return md5Hex;
    }
}
