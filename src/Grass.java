import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import itumulator.world.NonBlocking;
import itumulator.world.World;

import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;

public class Grass extends EnvObject{

    private double spreadChance = 25;

=======
public class Grass extends EnvObject implements Actor{
    private Set<Location> neighbours;
    private int spreadChance = 5-1; //15% chance to spread
    private Random rand = new Random();

    Grass(){
        super(ObjectType.grass);
    }

    
>>>>>>> Stashed changes
    public void act(World world) {
        super.act(world);
        spread(world);
        
    }
    //-------Code that Spreads grass around ----//
    public void spread(World world){
        int rand_int = 0;

        neighbours = world.getEmptySurroundingTiles();  //gets surrounding empty tiles
        ArrayList<Location> list = new ArrayList<>(neighbours);
        
        for (Location neighbor:list){
            rand_int = rand.nextInt(100);
            if (rand_int < spreadChance){               
                try{                                        //
                    world.setTile(neighbor,new Grass());    //insert new grass at location
                } catch (IllegalArgumentException e){
                    //System.out.println("test");
                }
            }
        }
    }

    public static boolean isTileGrass (World world, Location location){
        try{
        if (world.getNonBlocking(location) instanceof Grass){
            return true;
        }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }
        

    
    //---------------------- get methods----------------------//
}