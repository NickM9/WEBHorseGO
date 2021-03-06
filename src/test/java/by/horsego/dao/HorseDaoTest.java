package by.horsego.dao;

import by.horsego.bean.Horse;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class HorseDaoTest {

    private static final Logger logger = Logger.getLogger(HorseDaoTest.class);
    private static final HorseDao horseDao = DaoFactory.getInstance().getHorseDao();
    private static Horse expected = new Horse();

    @BeforeClass
    public static void init(){
        expected.setId(1);
        expected.setName("Jasper");
    }

    @Test
    public void findByIdTest(){
        Horse actual = null;

        try {
            actual = horseDao.findByID(expected.getId());
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createTest(){

        boolean created = false;

        try {
            created = horseDao.create(expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void updateTest(){
        expected.setName("Jasper Test");

        boolean updated = false;

        try {
            updated = horseDao.update(expected);
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(updated);
    }

    @Test
    public void deleteTest(){
        expected.setId(15);

        boolean deleted = false;

        try {
            deleted = horseDao.delete(expected.getId());
        } catch (DaoException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }

}
