package Test;

import org.junit.*;

import static org.junit.Assert.assertThrows;

import java.util.*;
import Animals.Rabbit;
import EnviormentObjects.Grass;
import MainFolder.Utils;
import itumulator.executable.Program;
import itumulator.world.*; 


public class testUtilities {
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
    public void test_checkNonBlocking() {
        Location test = new Location(1,1);
        Assert.assertEquals(m.checkNonBlocking(test),false);        //test false when world is empty
        world.setTile(test, new Grass(world));
        Assert.assertEquals(m.checkNonBlocking(test),true);         //test when nonblocking object is inserted
    }

    @Test
    public void test_placeable(){
        //inverse of checkNonBlocking no reason to test.
    }

    @Test
    public void test_checkNonBlockingType(){
        Location l1 = new Location(1,1);
        Location l2 = new Location(2,2);

        world.setTile(l1, new Grass(world));
        world.setTile(l2, new Rabbit());

        Assert.assertTrue(m.checkNonBlockingType(l1, Grass.class));
        Assert.assertFalse(m.checkNonBlockingType(l2, Grass.class));
        Assert.assertFalse(m.checkNonBlockingType(l2, Rabbit.class));
    }

    @Test
    public void test_diff(){
        Location start = new Location(1, 1);
        Location end = new Location(3,1);
        Location expResult = new Location(2, 1);
        Location wrgResult = new Location(1, 2);

        Assert.assertEquals(Utils.diff(end, start), expResult);         //Positive test
        Assert.assertNotEquals(Utils.diff(end, start), wrgResult);      //Negative test

    }

    @Test
    public void test_getWorldRandomLocation(){
         for (int i = 0; i <= 99; i++){
            Location l = m.getWorldRandomLocation(10);
            world.setTile(l, new Rabbit());
            world.setTile(l, new Grass(world));
        }
        //stack overflow test
        assertThrows(StackOverflowError.class, () -> {
            for (int i = 0; i <= 100; i++){
                Location l = m.getWorldRandomLocation(10);
                world.setTile(l, new Rabbit());
                world.setTile(l, new Grass(world));
            }
        });
        
    }

    @Test
    public void test_randommove(){
        Location start = new Location(5, 5);
        ArrayList<Location> possiblemovements = new ArrayList<Location>();
        possiblemovements.add(new Location(4, 4)); //bottom left
        possiblemovements.add(new Location(5, 4)); //down
        possiblemovements.add(new Location(4, 5)); //left
        possiblemovements.add(new Location(6, 4)); //bottom right
        possiblemovements.add(new Location(4, 6)); //top left
        possiblemovements.add(new Location(6, 5)); //right
        possiblemovements.add(new Location(5, 6)); //up
        possiblemovements.add(new Location(6, 6)); //top right

        Location result = m.randomMove(start);
        Assert.assertTrue(possiblemovements.contains(result));
        result = m.randomMove(start);
        Assert.assertTrue(possiblemovements.contains(result));
        result = m.randomMove(start);
        Assert.assertTrue(possiblemovements.contains(result));
        result = m.randomMove(start);
        Assert.assertTrue(possiblemovements.contains(result));
        result = m.randomMove(start);
        Assert.assertTrue(possiblemovements.contains(result));
        result = m.randomMove(start);
        Assert.assertTrue(possiblemovements.contains(result));
        result = m.randomMove(start);
        Assert.assertTrue(possiblemovements.contains(result));
        result = m.randomMove(start);
        Assert.assertTrue(possiblemovements.contains(result));

    }

    @Test
    public void test_isBlockNear(){
        Location center = new Location(5, 5);
        Location test1 = new Location(2, 2);
        world.setTile(test1, new Rabbit());

        Assert.assertEquals(test1,m.isBlockNear(center, Rabbit.class, 100));  //Positive test

        Assert.assertEquals(null,m.isBlockNear(center, Rabbit.class, 1));  //negative test

        //testing if method finds objects at center
        world.setTile(center, new Rabbit());                           //negative test
        Assert.assertNotEquals(center,m.isBlockNear(center, Rabbit.class, 1));  //negative test

    }

    @Test
    public void test_isNonBlocktNear(){
        Location center = new Location(5, 5);
        Location test1 = new Location(2, 2);
        world.setTile(test1, new Grass(world));

        try {
        Assert.assertEquals(test1,m.isNonBlocktNear(center, Grass.class, 100));  //Positive test
        } catch (Exception e){
            Assert.fail("Something went wong with test_isNonBlocktNear");
        }

        assertThrows(IllegalArgumentException.class, () -> {
            m.isNonBlocktNear(center, Grass.class, 1);
        });

        world.setTile(center, new Grass(world));
        assertThrows(IllegalArgumentException.class, () -> {
            m.isNonBlocktNear(center, Grass.class, 1);
        });
    }

    @Test
    public void test_spawnIn(){
        Utils m = new Utils();
        m.newProgram(10,500,500);   //need to make new program since spawnIn uses displayinformation

        m.spawnIn("Grass", new Location(2, 2));
        m.spawnIn("Rabbit", new Location(2, 2));
        m.spawnIn("Rabbit", new Location(4, 4));
        m.spawnIn("Grass", new Location(2, 3));

        Assert.assertTrue(!(m.world.isTileEmpty(new Location(2, 2))));   //test blocking object
        Assert.assertTrue(!(m.world.isTileEmpty(new Location(4, 4))));   //test blocking object
        Assert.assertTrue(m.world.containsNonBlocking(new Location(2, 2)));   //test nonblocking object
        Assert.assertTrue(m.world.containsNonBlocking(new Location(2, 3)));   //test nonblocking object
        Assert.assertFalse(m.world.containsNonBlocking(new Location(5, 3)));   //test nonblocking object


        assertThrows(IllegalArgumentException.class, () -> {
            m.spawnIn("Grass", new Location(15, 15));
        });
    }
    
}
    