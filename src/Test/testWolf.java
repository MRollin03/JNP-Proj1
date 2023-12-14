package Test;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.Set;

import MainFolder.Utils;
import itumulator.executable.Program;
import itumulator.world.*; 
import EnviormentObjects.Wolfden;
import Animals.Animal;
import Animals.Wolf;
import Animals.Wolfpack;
import itumulator.simulator.Actor;
import itumulator.*;
import itumulator.world.Location;
import itumulator.world.World;




public class testWolf {
    public World world;
    public Utils m;
    public Wolfden home;
    public Program p;


    @Before
    public void setup(){    //useless? :/
        world = new World(10);
        Utils m = new Utils();
        m.world = world;
        //home = new Wolfden(world);
        //m.p = new Program(10,800,800);
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
        Location l = new Location(5,5);
        int wolves = 2;
            
        Wolfpack wolfpack = new Wolfpack(m.world, wolves, l);
        wolfpack.spawnWolf(wolves);

        try {
            m.isNonBlocktNear(new Location(5, 5),Wolfden.class,2);
        } catch (Exception e) {
            Assert.fail();
        }
    }


    //ved ikke hvad der er galt her
    @Test
    public void test_makeHome(){
        Location l = new Location(5,5);
        int wolves = 1;
            
        Wolfpack wolfpack = new Wolfpack(world, 1, l);
        

        //wolfpack.spawnWolf(wolves);
        //Wolf wolf1 = wolfpack.getWolfsInPack().get(0);
        //world.setTile(l, wolf1);


        Wolf wolf1 = new Wolf(1, l, wolfpack);
        world.setTile(l, wolf1);

        world.setCurrentLocation(world.getLocation(wolf1));
        
        while (!(world.isNight())){
            world.step();
            //world.act();
            //System.out.println(world.getCurrentLocation());
            
            wolf1.act(world);
            System.out.println(world.getLocation(wolf1));
            //world.setCurrentLocation(new Location(4, 5));
            //wolf2.act(world);
            
        }
        
        //Wolfden test = new Wolfden(world);
        //world.setTile(new Location(wolves, wolves), home);

        Set<Location> sur = world.getSurroundingTiles(l, 5);
        for (Location place : sur){
            if (world.containsNonBlocking(place)) {
                Assert.assertTrue(world.getNonBlocking(place) instanceof Wolfden);
                System.out.println("Test Successful");
                return;
            }
        }
        Assert.fail();

    }



}
        