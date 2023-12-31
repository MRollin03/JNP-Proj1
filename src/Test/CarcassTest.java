package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import EnviormentObjects.Carcass;
import MainFolder.Utils;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.executable.Program;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.util.*;
import java.awt.*;

public class CarcassTest {

    public static World world;
    public static Utils m;
    public static Program p;
    private Carcass carcassBig, carcassSmall;
    private final Location location = new Location(2,2);
    @Before
    public void setup() { 
        m = new Utils();
        world = new World(10);
        m.world = world;
        carcassBig = new Carcass(world, true);
        carcassSmall = new Carcass(world, false);
    }

    @Test
    public void testCarcassSize() {
        assertTrue("Carcass should be big", carcassBig.isBig());
        assertFalse("Carcass should be small", carcassSmall.isBig());
    }

    @Test
    public void testCarcassInfestation() {
        carcassBig.setInfested(true);
        carcassSmall.setInfested(false);

        assertTrue(carcassBig.isInfested());
        assertFalse(carcassSmall.isInfested());
    }

    @Test
    public void testCarcassAct() {
        world.setCurrentLocation(location);
        world.setTile(location, carcassSmall);
        assertNotNull(world.getTile(location));
    
        for (int i = 0; i < 25; i++) {
            if (!Utils.checkNonBlocking(location)) {
                break;
            }
            carcassSmall.act(world);
        }
        if (Utils.checkNonBlockingType(location, getClass())){
            assertTrue(!Utils.checkNonBlocking(location));}
    }
}
