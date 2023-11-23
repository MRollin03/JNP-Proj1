import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;

public class RabbitHole extends EnvObject{
    protected ArrayList<Rabbit> rabbitsInHole= new ArrayList<Rabbit>();
    
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

    // adds rabbits to hole
    public void addToHole(Rabbit rabbit){
        rabbitsInHole.add(rabbit);
    }

    public void removeFromHole(){
        //Randomly selects a hole from hole list
        

        //Spawns rabbits on surround empty tiles
    }


}
