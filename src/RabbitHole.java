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
    RabbitHole(){
        super(ObjectType.hole);
    }
    
    @Override
    public void act(World world){
        super.act(world); 
    }

    //-------Code that Spreads grass around ----//
    public void place(Location l, World world){
        
        if(placeable(l, world)){
            
            world.setTile(l, this);
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

    public Boolean placeable(Location l , World world){
        if(!world.containsNonBlocking(l)){
            return true;
        }
        return false;
    }
    
}