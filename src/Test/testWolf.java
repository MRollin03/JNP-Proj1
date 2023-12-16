package Test;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Set;
import MainFolder.Utils;
import itumulator.executable.Program;
import itumulator.world.*; 
import EnviormentObjects.Wolfden;
import Animals.Animal;
import Animals.Rabbit;
import Animals.Bear;
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
    public void setup(){ 
        world = new World(10);
        Utils m = new Utils();
        m.world = world;
    }

    @Test
    public void test_packmovement(){
        Location l1 = new Location(5, 5);
        Location l2 = new Location(5, 4);
        int wolves = 2;
            
        Wolfpack wolfpack = new Wolfpack(world, wolves, l1);
        Wolf wolf1 = new Wolf(1, l1, wolfpack);
        Wolf wolf2 = new Wolf(1, l2, wolfpack);
        world.setTile(new Location(4, 5), wolf2);
        world.setTile(l2, wolf1);

        int iterations = 10;
        for (int i = 0; i <= iterations; i++){
            int counter = 0;
            world.step();
            world.setCurrentLocation(world.getLocation(wolf1));
            wolf1.act(world);
            world.setCurrentLocation(world.getLocation(wolf2));
            wolf2.act(world);

            Set<Location> packArea = world.getSurroundingTiles(wolfpack.getPackCenter(1), 3);
            for (Location place : packArea){
                if (world.getTile(place) instanceof Wolf){
                    counter++;
                }
            }
            if (world.getTile(wolfpack.getPackCenter(1)) instanceof Wolf) {
                counter++;
            }
            //System.out.println(counter);
            if (world.isDay()) {
                Assert.assertTrue(counter == wolves);
            }


        }
    }

    @Test
    public void test_mating(){
        Location l1 = new Location(5, 5);
        Location l2 = new Location(5, 4);
        Wolfpack wolfpack = new Wolfpack(m.world, 1, l1);
        Wolf wolf1 = new Wolf(1, l1, wolfpack);
        Wolf wolf2 = new Wolf(1, l1, wolfpack);
        wolf1.mate_CD = 0;
        wolf2.mate_CD = 0;

        world.setTile(l1, wolf1);
        world.setTile(l2, wolf2);

        world.setCurrentLocation(world.getLocation(wolf1));
        
        while (!(world.isNight())){
            world.step();
            if (world.isOnTile(wolf1)){
                world.setCurrentLocation(world.getLocation(wolf1));
                wolf1.act(world);
                System.out.println(world.getLocation(wolf1));
            }
            if (world.isOnTile(wolf2)){
                world.setCurrentLocation(world.getLocation(wolf2));
                wolf2.act(world);
            }
        }
        

        while ((world.isNight())){
            world.step();
            if (world.isOnTile(wolf1)){
                world.setCurrentLocation(world.getLocation(wolf1));
                wolf1.handleNightBehavior(world);
            }
            
            if (world.isOnTile(wolf2)){
                world.setCurrentLocation(world.getLocation(wolf2));
                wolf2.handleNightBehavior(world);
            }
            
            world.setCurrentLocation(null);

            if (!(world.isOnTile(wolf2)) && !(world.isOnTile(wolf1))){
                System.out.println(wolf1.getmate_CD() + " " + wolf2.getmate_CD());
                wolf1.mateWolf();
            }
        }

        wolf1.act(world);

        for (Object obj : world.getEntities().keySet()){
            if (obj instanceof Wolf) {
                Wolf Wolf = (Wolf) obj ;
                Wolf.act(world);
            }
        }
        wolf1.act(world);
        wolf2.act(world);
        

        int counter_max = 3;
        int counter = 0;
        Set<Location> sur = world.getSurroundingTiles(l1, 5);
        if (world.getTile(l1) instanceof Wolf) {
            counter++;
        }
        for (Location place : sur){
            if (!(world.isTileEmpty(place))) {
                if(world.getTile(place) instanceof Wolf){
                    counter++;
                    
                }
            }
        }
        System.out.println(counter);
        if (counter == counter_max){
            return;
        }
        Assert.fail();
    }

    @Test
    public void test_attack_rabbbit(){
        Location l1 = new Location(5, 5);
        Location l2 = new Location(5, 4);
        Wolfpack wolfpack = new Wolfpack(m.world, 1, l1);
        Wolf wolf1 = new Wolf(1, l1, wolfpack);
        world.setTile(l1, wolf1);

        Rabbit rabbit = new Rabbit();
        world.setTile(l2, rabbit);

        world.setCurrentLocation(world.getLocation(wolf1));
        world.step();
        wolf1.act(world);

        System.out.println(world.getLocation(wolf1));
        System.out.println(world.getTile(l2));
        Assert.assertTrue(world.getTile(l2) instanceof Wolf);

    }

    @Test
    public void test_attack_bear(){
        Location l1 = new Location(5, 5);
        Location l2 = new Location(5, 4);
        Wolfpack wolfpack = new Wolfpack(m.world, 1, l1);
        Wolf wolf1 = new Wolf(1, l1, wolfpack);
        world.setTile(l1, wolf1);

        Bear bear = new Bear();
        world.setTile(l2, bear);

        Assert.assertEquals(40,bear.getEnergy());       //pre-attack health of bear should be standard of 40

        world.setCurrentLocation(world.getLocation(wolf1));
        world.step();
        wolf1.act(world);

        Assert.assertEquals(35,bear.getEnergy());       //post-attack health of bear should be 35

    }

    @Test
    public void test_attack_wolf(){
        Location l1 = new Location(5, 5);
        Location l2 = new Location(5, 4);
        Wolfpack wolfpack = new Wolfpack(m.world, 1, l1);
        Wolf wolf1 = new Wolf(1, l1, wolfpack);
        world.setTile(l1, wolf1);

        Wolfpack wolfpack2 = new Wolfpack(m.world, 2, l2);
        Wolf wolf2 = new Wolf(1, l2, wolfpack2);
        world.setTile(l2, wolf2);

        Assert.assertEquals(40,wolf2.getEnergy());       //pre-attack health of wolf2 should be standard of 40

        world.setCurrentLocation(world.getLocation(wolf1));
        world.step();
        wolf1.act(world);

        Assert.assertEquals(35,wolf2.getEnergy());       //post-attack health of wolf2 should be 35

    }

    /**
     * the spawning of wolves is special since it is combined with the 'Main.java' file.
     * Testing it will not call main, but we will use a copy of that code for all tests concerning wolves
     */
    @Test
    public void test_spawning(){
        Location l = new Location(5,5);
        int wolves = 2;
        int counter_max = wolves;
        int counter = 0;
            
        Wolfpack wolfpack = new Wolfpack(m.world, wolves, l);
        wolfpack.spawnWolf(wolves);

        Set<Location> sur = world.getSurroundingTiles(l, 5);
        if (world.getTile(l) instanceof Wolf) {
            counter++;
        }
        for (Location place : sur){
            if (!(world.isTileEmpty(place))) {
                if(world.getTile(place) instanceof Wolf){
                    counter++;
                    
                }
            }
        }
        if (!(counter == counter_max)){
            Assert.fail();
        }
            
    }

    @Test
    public void test_deletehome(){
        Location l = new Location(5,5);
            
        Wolfpack wolfpack = new Wolfpack(world, 1, l);

        Wolf wolf1 = new Wolf(1, l, wolfpack);
        world.setTile(l, wolf1);

        world.setTile(l, new Wolfden(world));

        wolf1.act(world);
        world.step();

        Set<Location> sur = world.getSurroundingTiles(l, 5);
        for (Location place : sur){
            if (world.containsNonBlocking(place)) {
                if(world.getNonBlocking(place) instanceof Wolfden){
                    Assert.fail();
                }
            }
        }
        
    }

    @Test
    public void test_makeHome(){
        Location l = new Location(5,5);
            
        Wolfpack wolfpack = new Wolfpack(world, 1, l);

        Wolf wolf1 = new Wolf(1, l, wolfpack);
        world.setTile(l, wolf1);

        world.setCurrentLocation(world.getLocation(wolf1));
        
        while (!(world.isNight())){
            world.step();
            wolf1.act(world);
        }
        
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
        