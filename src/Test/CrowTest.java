package Test;
import org.junit.Test;

import Animals.Crow;
import EnviormentObjects.*;
import MainFolder.Utils;
import itumulator.executable.Program;
import itumulator.world.*;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;    
    
public class CrowTest {

    
    public static World world;
    public static Utils m;
    public static Program p;

    @Before
    public void setup() { // useless? :/
        m = new Utils();
        world = new World(10);
        m.world = world;
    }

    // Test if crow gets spawned and world contains it
    @Test
    public void testSpawning() {
        Crow crow = new Crow();
        world.setTile(new Location(1, 1), crow);
        world.contains(crow);
        assertTrue(world.contains(crow));
    }

    // checks i crow eats the crow if its beside the crow.
    @Test
    public void eat() {
        Crow crow = new Crow();
        Grass grass = new Grass(world);
        world.setTile(new Location(1, 1), crow);
        world.setTile(new Location(2, 1), grass);

        world.setCurrentLocation(world.getLocation(crow));
        
        crow.act(world);
        assertTrue(world.contains(grass));

    }

    // checks if crow dies when called.
    @Test
    public void die() {
        Crow crow = new Crow();
        world.setTile(new Location(1, 1), crow);
        assertTrue(world.contains(crow));
        crow.die();
        assertFalse(world.contains(crow));
    }
}
    