package by.horsego.service;

import by.horsego.bean.Horse;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class HorseServiceTest {

    private static final Logger logger = Logger.getLogger(HorseServiceTest.class);
    private static final HorseService horseService = ServiceFactory.getInstance().getHorseService();
    private static Horse expected = new Horse();

    @BeforeClass
    public static void init(){
        expected.setId(1);
        expected.setName("Jasper");
    }

    @Test
    public void findHorseByIdTest(){
        Horse actual = null;

        try {
            actual = horseService.findHorseById(expected.getId());
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isHorseCreatedTest(){
        boolean created = false;

        try {
            created = horseService.isHorseCreated("Test Horse Name");
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(created);
    }

    @Test
    public void isHorseUpdatedTest(){
        boolean updated = false;

        try {
            updated = horseService.isHorseUpdated(expected.getId(), "Horse test name");
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(updated);
    }

    @Test
    public void isHorseDeletedTest(){
        boolean deleted = false;

        try {
            deleted = horseService.isHorseDeleted(expected.getId());
        } catch (ServiceException e) {
            logger.error(e);
        }

        Assert.assertTrue(deleted);
    }

}
