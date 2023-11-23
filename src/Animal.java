import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;

public class Animal {
    private int age;
    private int foodPoint;
    private int energy;


    Animal(){
        this.age = 0;
        this.foodPoint = 1;
        this.energy = 1;
    }

    enum type{
        Rabbit,
        Person,
    }

    

    /*  ----- Standard Act behavoir -------
    
    public void act(World world) {
        
        Set<Location> neighbours = world.getEmptySurroundingTiles();
        ArrayList<Location> list = new ArrayList<>(neighbours);

        Random rand = new Random();

        if(list != null){
            Location l = list.get(rand.nextInt( list.size()-1)); // Linje 2 og 3 kan erstattes af neighbours.toArray()[0]
            world.move(this,l);
        }
    }*/


    // Main die method
    public void die(World world){
        world.delete(this);
    }





    // get methods

    public int getAge(){
        return age;
    }

    public int getFoodPoints(){
        return foodPoint;
    }

    public int getEnergy(){
        return energy;
    }
}
