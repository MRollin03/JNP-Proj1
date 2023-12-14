package Test;

import itumulator.executable.*;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.*;
import Animals.*;
import MainFolder.*;

public class BearTest {

    public static World world;
    public static Utils m;
    public static Program p;

    @Before
    public void setup() { // useless? :/
        m = new Utils();
        world = new World(10);
        m.world = world;
    }

    // Test if bear gets spawned and world contains it
    @Test
    public void testSpawning() {
        Bear bear = new Bear();
        world.setTile(new Location(1, 1), bear);
        world.contains(bear);
        assertTrue(world.contains(bear));
    }

    // checks i bear eats the bear if its beside the bear.
    @Test
    public void eat() {
        Bear bear = new Bear();
        Grass grass = new Grass(world);
        world.setTile(new Location(1, 1), bear);
        world.setTile(new Location(2, 1), grass);
        bear.act(world);
        assertTrue(world.contains(grass));

    }

    // checks if bear dies when called.
    @Test
    public void die() {
        Bear bear = new Bear();
        world.setTile(new Location(1, 1), bear);
        assertTrue(world.contains(bear));
        bear.die();
        assertFalse(world.contains(bear));
    }

}
