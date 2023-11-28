package Animals;

import itumulator.world.*;
import itumulator.simulator.*;

import java.util.*;

public class Person implements Actor {
    @Override
    public void act(World world) {
        
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        ArrayList<Location> list = new ArrayList<>(neighbours);
        Random rand = new Random();
        if(list != null){
          Location l = list.get(rand.nextInt( list.size()-1)); // Linje 2 og 3 kan erstattes af neighbours.toArray()[0]
          world.move(this,l);
        }

      
    }


}