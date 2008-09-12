package org.ourproject.kune.platf.client.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateToken;

public class StateTokenTest {

    @Test
    public void checkAllEmpty() {
	final StateToken token = new StateToken("");
	assertNull(token.getGroup());
	assertNull(token.getTool());
	assertNull(token.getFolder());
	assertNull(token.getDocument());
    }

    @Test
    public void checkEquals() {
	final StateToken token1 = new StateToken("abc", "da", "1", "1");
	final StateToken token2 = new StateToken("abc", "da", "1", "1");
	assertEquals(token1, token2);
    }

    @Test
    public void checkEqualsEncoded() {
	final StateToken token1 = new StateToken("abc.da.1.1");
	final StateToken token2 = new StateToken("abc.da.1.1");
	assertEquals(token1, token2);
    }

    @Test
    public void checkNoEquals() {
	final StateToken token1 = new StateToken("abc", "da", "1", "1");
	final StateToken token2 = new StateToken("abc", "da", 1l);
	assertFalse(token1.equals(token2));
    }
}
