package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.*;

import java.util.TimeZone;

import javax.persistence.EntityExistsException;
import javax.validation.ValidationException;

import org.apache.lucene.queryParser.ParseException;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistencePreLoadedDataTest;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class UserManagerTest extends PersistencePreLoadedDataTest {
    @Inject
    Group groupFinder;

    @Test
    public void emailCorrect() {
        user = new User("test1", "test1 name", "test@example.com", "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    @Test(expected = ValidationException.class)
    public void emailEmpty() {
        user = new User("test1", "test1 name", null, "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    @Test(expected = ValidationException.class)
    public void emailIncorrect() {
        user = new User("test1", "test1 name", "falseEmail@", "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    @Test
    public void loginIncorrect() {
        final User result = userManager.login("test", "test");
        assertNull(result);
    }

    @Test
    public void loginWithEmailCorrect() {
        final User result = userManager.login(USER_EMAIL, USER_PASSWORD);
        assertNotNull(result.getId());
    }

    @Test
    public void loginWithNickCorrect() {
        final User result = userManager.login(USER_SHORT_NAME, USER_PASSWORD);
        assertNotNull(result.getId());
    }

    @Test(expected = ValidationException.class)
    public void passwdLengthIncorrect() {
        user = new User("test1", "test1 name", "test@example.com", "pass", english, gb, getTimeZone());
        persist(user);
    }

    /**
     * This is not working:
     * http://opensource.atlassian.com/projects/hibernate/browse/EJB-382
     */
    @Test(expected = EntityExistsException.class)
    public void testUserExist() throws I18nNotFoundException {
        final User user1 = userManager.createUser("test", "test 1 name", "test1@example.com", "some password", "en",
                "GB", "GMT");
        persist(user1);
        final User user2 = userManager.createUser("test", "test 1 name", "test1@example.com", "some password", "en",
                "GB", "GMT");
        persist(user2);
    }

    @Test(expected = ValidationException.class)
    public void userNameLengthIncorrect() {
        user = new User("test1", "te", "test@example.com", "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    @Test
    public void userSearch() throws Exception, ParseException {
        userManager.reIndex();
        final SearchResult<User> result = userManager.search(USER_SHORT_NAME);
        assertEquals(1, result.getSize());
        assertEquals(USER_SHORT_NAME, result.getList().get(0).getShortName());
        rollbackTransaction();
    }

    @Test(expected = ValidationException.class)
    public void userShortNameIncorrect() {
        user = new User("test1A", "test1 name", "test@example.com", "some passwd", english, gb, getTimeZone());
        persist(user);
    }

    private TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

}
