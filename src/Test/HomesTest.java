package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import Animals.Rabbit;
import Animals.Wolf;
import Animals.Wolfpack;
import EnviormentObjects.*;
import MainFolder.Utils;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

public class HomesTest {

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
    public void TestAddToDen() {
        Wolfpack pack = new Wolfpack(world, 1, new Location(5, 5));
        world.step();
        pack.spawnWolf(1);
        world.step();
        Wolf currentWolf = pack.getWolfsInPack().get(0);

        Wolfden wolfDen = new Wolfden(world);
        world.setTile(new Location(1, 1), wolfDen);

        currentWolf.setCurrentWolfden(wolfDen);

        wolfDen.addToHole(pack.getWolfsInPack().get(0), new Wolfden(world));

        assertTrue(wolfDen.getAnimalsInHole().contains(currentWolf));

        // Test that getting the location of the rabbit fails
        try {
            world.getLocation(currentWolf);
            fail("Expected an exception but didn't get one");
        } catch (Exception e) {
        }

    }

    @Test
    public void testAddToHole() {
        RabbitHole homes = new RabbitHole();
        Rabbit rabbit = new Rabbit();
        world.setTile(new Location(1, 1), rabbit);
        world.setTile(new Location(1, 1), homes);

        // Test adding a rabbit to a RabbitHole
        homes.addToHole(rabbit, Rabbit.class);
        assertTrue(homes.getAnimalsInHole().contains(rabbit));

        // Test that getting the location of the rabbit fails
        try {
            world.getLocation(rabbit);
            fail("Expected an exception but didn't get one");
        } catch (Exception e) {
        }
    }

    @Test
    public void testRemoveFromHole() {
        RabbitHole hole = new RabbitHole();

        world.setTile(new Location(1, 1), hole);

        Rabbit rabbit1 = new Rabbit();
        Rabbit rabbit2 = new Rabbit();

        world.setTile(new Location(1, 1), rabbit1);
        world.setTile(new Location(1, 2), rabbit2);

        // Add animals to the home
        hole.addToHole(rabbit1, RabbitHole.class);
        hole.addToHole(rabbit2, RabbitHole.class);

        // Test removing animals from the home
        hole.removeFromHole();

        // Check if animals are removed from the home and added to the world
        assertFalse(hole.getAnimalsInHole().contains(rabbit1));
        assertFalse(hole.getAnimalsInHole().contains(rabbit2));
        assertTrue(world.contains(rabbit1) || world.contains(rabbit2));
    }
}
