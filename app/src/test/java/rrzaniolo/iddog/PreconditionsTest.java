package rrzaniolo.iddog;

/*
 * Created by rrzaniolo on 30/04/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import org.junit.Test;

import rrzaniolo.iddog.utils.Preconditions;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class PreconditionsTest {

    @Test
    public void checkNotNull(){
        String object = "";

        String result = Preconditions.checkNotNull(object);

        assertEquals(object, result);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullException(){
        Preconditions.checkNotNull(null);
    }

    @Test
    public void isNotNullNorEmpty(){
        String object = "notNullNorEmpty";

        assertTrue(Preconditions.isNotNullNorEmpty(object));
    }

    @Test
    public void isNotNullNorEmpty_Empty(){
        String object = "";

        assertFalse(Preconditions.isNotNullNorEmpty(object));
    }

    @Test
    public void isNotNullNorEmpty_Null(){
        assertFalse(Preconditions.isNotNullNorEmpty(null));
    }
}
