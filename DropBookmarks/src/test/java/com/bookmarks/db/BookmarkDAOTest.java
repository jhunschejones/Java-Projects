package com.bookmarks.db;

import com.bookmarks.core.Bookmark;
import com.bookmarks.core.User;
import com.google.common.base.Optional;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.junit.*;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookmarkDAOTest {
    private static final SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();
    private static Liquibase liquibase = null;
    private Session session;
    private Transaction tx;
    private BookmarkDAO dao;
    private UserDAO userDAO;

    @BeforeClass
    public static void setUpClass() throws LiquibaseException, SQLException {
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) SESSION_FACTORY;
        DriverManagerConnectionProviderImpl provider = (DriverManagerConnectionProviderImpl) sessionFactoryImpl.getConnectionProvider();
        Connection connection = provider.getConnection();
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        liquibase = new Liquibase("migrations.xml", new ClassLoaderResourceAccessor(), database);
    }

    @AfterClass
    public static void tearDownClass() {
        SESSION_FACTORY.close();
    }

    @Before
    public void setUp() throws LiquibaseException {
        liquibase.update("DEV");
        session = SESSION_FACTORY.openSession();
        dao = new BookmarkDAO(SESSION_FACTORY);
        userDAO = new UserDAO(SESSION_FACTORY);
        tx = null;
    }

    @After
    public void tearDown() throws DatabaseException, LockException {
        liquibase.dropAll();
    }

    @Test
    public void findById() {
        Optional<Bookmark> bookmark = null;
        try {
            ManagedSessionContext.bind(session);
            tx = session.beginTransaction();

            bookmark = dao.findById(1);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            ManagedSessionContext.unbind(SESSION_FACTORY);
            session.close();
        }

        Assert.assertNotNull(bookmark);
        Assert.assertTrue(bookmark.isPresent());
        Assert.assertEquals("Liquibase", bookmark.get().getName());
    }

    @Test
    public void testSave() {
        String expectedName = "Save Test Bookmark";

        Optional<Bookmark> bookmark;

        //First
        try {
            ManagedSessionContext.bind(session);
            tx = session.beginTransaction();

            Bookmark saveBookmark = new Bookmark(expectedName, "test.com", "A bookmark for the save test", userDAO.findByUserNamePassword("carl", "super_secure").get());
            dao.save(saveBookmark);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            ManagedSessionContext.unbind(SESSION_FACTORY);
            session.close();
        }

        //Reopen session
        session = SESSION_FACTORY.openSession();
        tx = null;

        //Second
        try {
            ManagedSessionContext.bind(session);
            tx = session.beginTransaction();

            bookmark = dao.findById(4);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            ManagedSessionContext.unbind(SESSION_FACTORY);
            session.close();
        }

        Assert.assertNotNull(bookmark);
        Assert.assertTrue(bookmark.isPresent());
        Assert.assertEquals(expectedName, bookmark.get().getName());
    }

    @Test
    public void testFindForUser() {
        List<Bookmark> bookmarks;

        //First
        try {
            ManagedSessionContext.bind(session);
            tx = session.beginTransaction();

            bookmarks = dao.findForUser(1);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            ManagedSessionContext.unbind(SESSION_FACTORY);
            session.close();
        }

        Assert.assertNotNull(bookmarks);
        Assert.assertFalse(bookmarks.isEmpty());
        Assert.assertEquals("Liquibase", bookmarks.get(0).getName());
    }
}
