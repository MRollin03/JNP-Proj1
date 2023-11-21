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

public class Kanin extends Animal implements Actor{

    Kanin(){
        super();
    }

    @Override
    public void act(World world) {

        // Nat og dags Behavior 
        if(world.isNight()){

            // Edit: Her skal der kodes Nat behavoir -------

            Set<Location> neighbours = world.getEmptySurroundingTiles();
            ArrayList<Location> list = new ArrayList<>(neighbours);

            Random rand = new Random();

            if(list != null){
                Location l = list.get(rand.nextInt( list.size()-1)); // Linje 2 og 3 kan erstattes af neighbours.toArray()[0]
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

    }


    public boolean isGrassNear(){
        boolean res = false;

        // ---- Implement code that check if grass is  on neighboring tiles --- 

        return res;
    }
    
}
