package Test;

import itumulator.executable.*;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;

import org.junit.*;
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
    


    public void testSpawning(){
        Bear bear = new Bear();
        world.contains(bear);
        
    }
}
    
