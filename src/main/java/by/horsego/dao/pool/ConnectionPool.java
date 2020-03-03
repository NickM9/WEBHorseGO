package by.horsego.dao.pool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Connection pool implementation class.
 *
 * Uses thread-safe queues for free and busy connections.
 * The connection object uses its own implementation in the class {@link ProxyConnection}.
 * To get an instance of a class, use the method {@link #getInstance()}
 * {@link #defaultPoolSize} value that sets the size (number of connections) of queues.
 *
 * @see ProxyConnection
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class ConnectionPool {

    private final static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private static Lock instanceLock = new ReentrantLock();

    private String dbDriver;
    private String dbUrl;
    private String dbLogin;
    private String dbPassword;
    private int defaultPoolSize;

    private BlockingQueue<Connection> freeConnections;
    private BlockingQueue<Connection> givenAwayConnections;

    private ConnectionPool() {

        //init param

        dbDriver = DataBaseManager.getProperty("driver");
        dbUrl = DataBaseManager.getProperty("url");
        dbLogin = DataBaseManager.getProperty("login");
        dbPassword = DataBaseManager.getProperty("password");
        defaultPoolSize = Integer.parseInt(DataBaseManager.getProperty("pool_size"));


        freeConnections = new ArrayBlockingQueue<Connection>(defaultPoolSize);
        givenAwayConnections = new ArrayBlockingQueue<Connection>(defaultPoolSize);

        //register driver
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            logger.error("Driver not connected");
        }

        //init connections
        try {
            for (int i = 0; i < defaultPoolSize; i++) {
                Connection connection = DriverManager.getConnection(dbUrl, dbLogin, dbPassword);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            }
        } catch (SQLException e) {
            logger.error("SQL connection error");
        }

        logger.info("Register done, no connection errors");
    }

    public static ConnectionPool getInstance() {

        if (instance == null) {

            instanceLock.lock();

            if (instance == null) {
                instance = new ConnectionPool();
            }

            instanceLock.unlock();

        }

        return instance;
    }

    /**
     * The method takes the connection object from the free queue and puts it in the queue of busy connections.
     *
     * @return Connection object
     * @throws ConnectionPoolException
     */
    public Connection getConnection() throws ConnectionPoolException {

        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error(e);
            throw new ConnectionPoolException(e);
        }

        return connection;
    }

    /**
     * This method removes a connection from the queue of busy connections and places it in free ones.
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) {

        givenAwayConnections.remove(connection);
        freeConnections.offer(connection);

    }

    /**
     * Method destroys the connection pool.
     *
     * Closes all free connections, and deregister drivers.
     *
     * @throws ConnectionPoolException
     */
    public void destroyPool() throws ConnectionPoolException {

        for (int i = 0; i < defaultPoolSize; i++) {
            try {
                freeConnections.take().close();
            } catch (InterruptedException | SQLException e) {
                logger.error(e);
                throw new ConnectionPoolException(e);
            }
        }

        deregisterDrivers();
        logger.info("Pool destroyed");
    }

    private void deregisterDrivers() throws ConnectionPoolException {

        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error(e);
                throw new ConnectionPoolException(e);
            }
        }

        logger.info("Drivers deregister");
    }
}
