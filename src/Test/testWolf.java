package Test;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import EnviormentObjects.Grass;
import MainFolder.Utils;
import itumulator.executable.Program;
import itumulator.world.*; 
import EnviormentObjects.Wolfden;
import Animals.*;




public class testWolf {
    public static World world;
    public static Utils m;
    public static Program p;


    @Before
    public void setup(){    //useless? :/
        m = new Utils();
        world = new World(10);
        m.world = world;
    }

    @Test
    public void test_packmovement(){
        Location l = new Location(5,5);
        int wolves = 2;
            
        Wolfpack wolfpack = new Wolfpack(world, wolves, l);
        Wolf wolf1 = new Wolf(1, l, wolfpack);
        Wolf wolf2 = new Wolf(1, l, wolfpack);
        world.setTile(new Location(4, 5), wolf2);
        world.setTile(l, wolf1);


    }

    @Test
    public void test_mating(){
        
    }

    @Test
    public void test_attack(){
        
    }

    /**
     * the spawning of wolves is special since it is combiend with the 'Main.java' file.
     * Testing it will not call main, but we will use a copy of that code for all tests concerning wolves
     */
    @Test
    public void test_spawning(){
        Location l = new Location(5,5);
        int wolves = 2;
            
        Wolfpack wolfpack = new Wolfpack(m.world, wolves, l);
        wolfpack.spawnWolf(wolves);

        if ( m.isBlockNear(new Location(5, 5),Wolf.class,2) == null) {
            Assert.fail("Test Failed");
        }
            
    }

    @Test
    public void test_deletehome(){
        
    }

    @Test
    public void test_ejectFromHome(){
        
    }

    //ved ikke hvad der er galt her
    @Test
    public void test_makeHome(){
        Location l = new Location(5,5);
        int wolves = 2;
            
        Wolfpack wolfpack = new Wolfpack(world, wolves, l);
        wolfpack.spawnWolf(wolves);
        while (!(world.isNight())){
            world.step();
        }
        world.step();
        world.step();
        try {
        System.out.println(m.isNonBlocktNear(new Location(5, 5),Wolfden.class,2));
        } catch (Exception e) {
            Assert.fail("Test Failed");
        }

    }



}
        