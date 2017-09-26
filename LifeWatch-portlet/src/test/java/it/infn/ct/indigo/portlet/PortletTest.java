package it.infn.ct.indigo.portlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass; 
import org.junit.Test; 
import it.infn.ct.indigo.portlet.GenericPortlet;

public class PortletTest {
    private static GenericPortlet portlet;
    
    @BeforeClass
    public static void setUp() {
        portlet = new GenericPortlet();
    }

    @Test
    public void testIsAlive() {

        assertEquals(0, 0);
    }
}
