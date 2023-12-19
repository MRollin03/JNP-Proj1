package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import EnviormentObjects.Carcass;
import EnviormentObjects.Fungi;
import MainFolder.Utils;
import itumulator.executable.Program;
import itumulator.world.*;
import java.awt.Color;

public class fungiTest {

    public static World world;
    public static Utils m;
    public static Program p;
    private Fungi fungiBig, fungiSmall;
    private Carcass carcass;
    private final Location fungiLocation = new Location(5, 5);
    private final Location carcassLocation = new Location(5, 6); // Assuming this is within spread range

    @Before
    public void setup() {
        m = new Utils();
        world = new World(10);
        m.world = world;
        fungiBig = new Fungi(true);
        fungiSmall = new Fungi(false);
        carcass = new Carcass(world, false);
        
        world.setTile(carcassLocation, carcass);
    }

    @Test
    public void testFungiSpread() {
        fungiBig.act(world);
        if (Utils.checkNonBlockingType(fungiLocation, getClass())){
            assertTrue("Carcass should be infested after fungi act", carcass.isInfested());
        }
        
    }

    @Test
    public void testFungidie() {
        world.setCurrentLocation(fungiLocation);
        world.setTile(fungiLocation, fungiSmall);
        assertNotNull(world.getTile(fungiLocation));

        for (int i = 0; i < 25; i++) {
            if (!Utils.checkNonBlocking(fungiLocation)) {
                break; // Exit the loop once the carcass is removed
            }
            fungiSmall.act(world);
        }
        if (Utils.checkNonBlockingType(fungiLocation, getClass())){
            assertTrue(!Utils.checkNonBlocking(fungiLocation));}
        }
    

    @Test
    public void testFungiSize() {
        assertTrue("Fungi should be big", fungiBig.isBig());
        assertFalse("Fungi should be small", fungiSmall.isBig());
    }

}