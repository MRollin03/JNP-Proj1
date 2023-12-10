package Test;

import org.junit.*;
import java.util.*;
import Animals.Rabbit;
import EnviormentObjects.Grass;
import MainFolder.Utils;    
import itumulator.world.*; 


public class testUtilities {
    public static World world;

    @Before
    public void setup(){    //useless? :/
        Utils m = new Utils();
        m.newProgram(5,500,500);
    }
        
    @Test
    public void test_checkNonBlocking() {
        //Main world = new Main();
        Utils m = new Utils();
        m.newProgram(5,500,500);

        Location test = new Location(1,1);
        Assert.assertEquals(m.checkNonBlocking(test),false);        //test false when world is empty
        m.spawnIn("Grass", test);
        Assert.assertEquals(m.checkNonBlocking(test),true);         //test when nonblocking object is inserted
    }

    @Test
    public void test_placeable(){
        Utils m = new Utils();
        m.newProgram(5,500,500);
        //same as checkNonBlocking?
        //delete?
    }

    @Test
    public void test_checkNonBlockingType(){
        Utils m = new Utils();
        m.newProgram(5,500,500);
        Location l1 = new Location(1,1);
        Location l2 = new Location(2,2);

        m.spawnIn("Grass", l1);
        m.spawnIn("Rabbit", l2);

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

        Assert.assertEquals(Utils.diff(end, start), expResult);     //correct result
        Assert.assertNotEquals(Utils.diff(end, start), wrgResult);     //wrong result

    }

    @Test
    public void test_getWorldRandomLocation(){

    }

    @Test
    public void test_randommove(){
        Utils m = new Utils();
        m.newProgram(10,500,500);
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
        //like checkNonBlockingType, will need to confer with groupmates to test this class
    }

    @Test
    public void test_isNonBlocktNear(){
        //like checkNonBlockingType, will need to confer with groupmates to test this class
    }

    @Test
    public void test_spawnIn(){
        Utils m = new Utils();
        m.newProgram(10,500,500);

        m.spawnIn("Grass", new Location(2, 2));
        m.spawnIn("Rabbit", new Location(2, 2));
        m.spawnIn("Rabbit", new Location(4, 4));
        m.spawnIn("Grass", new Location(2, 3));

        Assert.assertTrue(!(m.world.isTileEmpty(new Location(2, 2))));   //test blocking object
        Assert.assertTrue(!(m.world.isTileEmpty(new Location(4, 4))));   //test blocking object
        Assert.assertTrue(m.world.containsNonBlocking(new Location(2, 2)));   //test nonblocking object
        Assert.assertTrue(m.world.containsNonBlocking(new Location(2, 3)));   //test nonblocking object
        Assert.assertFalse(m.world.containsNonBlocking(new Location(5, 3)));   //test nonblocking object


        //doesnt compile
        //Assert.AssertThrows(IllegalArgumentException.class,(m.spawnIn("Grass", new Location(15, 15))));

    }

    @Test
    public void test_newProgram(){
        //do we need?
    }
    
}
    