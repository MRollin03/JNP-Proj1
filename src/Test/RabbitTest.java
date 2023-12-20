package Test;

import itumulator.executable.*;
import itumulator.world.*;
import EnviormentObjects.*;

import org.junit.*;

import static org.junit.Assert.*;
import Animals.*;
import MainFolder.*;

public class RabbitTest {

    public static World world;
    public static Utils m;
    public static Program p;

    @Before
    public void setup() { // useless? :/
        m = new Utils();
        world = new World(10);
        m.world = world;
    }

    // Test if Rabbit gets spawned and world contains it
    @Test
    public void testSpawning() {
        Rabbit rabbit = new Rabbit();
        world.setTile(new Location(1, 1), rabbit);
        world.contains(rabbit);
        assertTrue(world.contains(rabbit));
    }

    // checks i Rabbit eats the rabbit if its beside the Rabbit.
    @Test
    public void eat() {
        Rabbit rabbit = new Rabbit();
        Grass grass = new Grass(world);
        world.setTile(new Location(1, 1), rabbit);
        world.setTile(new Location(2, 1), grass);
        rabbit.act(world);
        assertTrue(world.contains(grass));

    }

    // checks if rabbit dies when called.
    @Test
    public void die() {
        Rabbit rabbit = new Rabbit();
        world.setTile(new Location(1, 1), rabbit);
        assertTrue(world.contains(rabbit));
        rabbit.die();
        assertFalse(world.contains(rabbit));
    }

    // checks if the rabbits default is 15 when reset.
    @Test
    public void resetmateCD() {
        Rabbit rabbit = new Rabbit();
        world.setTile(new Location(1, 1), rabbit);

        world.step();
        rabbit.act(world);
        rabbit.setMate_CD(10);
        rabbit.resetmateCD(world.getLocation(rabbit));

        world.step();
        rabbit.act(world);
        Assert.assertEquals(rabbit.getmate_CD(), 15);
    }

}
