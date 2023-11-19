import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.util.*;

import java.util.Random;

public class Person implements Actor {
    @Override
    public void act(World world) {
        
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        ArrayList<Location> list = new ArrayList<>(neighbours);
        Random rand = new Random();
        Location l = list.get(rand.nextInt(0, list.size()-1)); // Linje 2 og 3 kan erstattes af neighbours.toArray()[0]
        world.move(this,l);
    }


}