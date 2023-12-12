package Test;

import itumulator.executable.*;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;

import org.junit.*;

import static org.junit.Assert.assertTrue;

import java.util.*;
import Animals.*;
import MainFolder.*;   


    
public class BearTest {

    public static World world;
    public static Utils m;
    public static Program p;
    @Before
    public void setup(){    //useless? :/
        m = new Utils();
        world = new World(10);
        m.world = world;
    }
    

    // Test if bear gets spawned and world contains it
    public void testSpawning(){
        Bear bear = new Bear();
        world.contains(bear);
        assertTrue(world.contains(bear));
    }

    public void eat(){
        Bear bear = new Bear();
        Rabbit rabbit = new Rabbit();
        world.setTile(new Location(1, 1), bear);
        world.setTile(new Location(2, 1), rabbit);
        
    }
    


}
    
