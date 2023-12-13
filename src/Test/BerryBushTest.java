package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import EnviormentObjects.BerryBush;
import MainFolder.Utils;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.executable.Program;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.util.*;
import java.awt.*;

public class BerryBushTest {

    public static World world;
    public static Utils m;
    public static Program p;

    @Before
    public void setup() { // useless? :/
        m = new Utils();
        world = new World(10);
        m.world = world;
    }

    @Test
    public void spawnBerryBushTest() {
        BerryBush bush = new BerryBush(world);
        world.setTile(new Location(1, 1), bush);

        assertFalse(null, bush.hasBerries());

        bush.berriesToggle();

        assertTrue(bush.hasBerries());

    }
}
