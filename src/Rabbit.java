import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import itumulator.simulator.Actor;
import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;

public class Rabbit extends Animal implements Actor{

    Rabbit(){
        super();
    }

    @Override
    // kode til den specefikke Behavior
    public void act(World world) {
        // Nat og dags Behavior 
        if(world.isNight()){

            // Edit: Her skal der kodes Nat behavoir -------

                /**If statement for mulig død.
                 * Foodpoints 0 
                */
                if(true){
                    // Kode ker ---- 
                }

                


            

            Set<Location> neighbours = world.getEmptySurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);

            Random rand = new Random();

            if(list != null){
                Location l = list.get(rand.nextInt( list.size()-1)); 
                world.move(this,l);
            }

        }
        else{

            // Edit: Her skal der kodes dags behavoir -------

            Set<Location> neighbours = world.getEmptySurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);

            Random rand = new Random();

            if(list != null){
                Location l = list.get(rand.nextInt( list.size()-1)); // Linje 2 og 3 kan erstattes af neighbours.toArray()[0]
                world.move(this,l);
            }

        }

        super.act(world);
    }

    // Checks if grass are near
    public boolean isGrassNear(){
        boolean res = false;

        // ---- Implement code that check if grass is  on neighboring tiles --- 

        return res;
    }
    
    // Die funktion kalder remove via 'Animal' superclass
    public void die(World world){
        super.die(world);
    }
    
}
