package Animals;
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
    protected int foodPoint;
    private int energy;
    protected World world;


    Animal(World world){
        this.age = 0;
        this.foodPoint = 10;
        this.energy = 1;
        this.world = world;
    }

    enum type{
        Rabbit,
        Person
    }

        
public void act(World world){
        if (world.getCurrentTime() == 0) {
            foodPoint --;
        }
        // hvis klokken er 11 og mad point er 0
        if(world.getCurrentTime() == 0 && this.getFoodPoints() < 1 ){
            die(world);
        }
    }

    


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
