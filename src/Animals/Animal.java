package Animals;

import MainFolder.Utils;
import itumulator.world.*;



public class Animal{
    private int age;
    protected int foodmax;
    protected int energy;
    protected World world;


    Animal(World world){
        this.age = 0;
        this.foodmax = 30+age*2;
        this.energy = 30;
        this.world = world;
    }

    enum type{
        Rabbit,
        Person
    }

    public void act(World world){
        if (!world.isNight()){
            energy--;              //whenever they move, spend 1 energy, except at night
            
        }
        if (world.getCurrentTime() == 0) {      //animals grow older each day
            age++;
        }
        if(this.getEnergy() < 1 || this.age == 12){     //animals die if they have no energy or get too old
            die(world);
            System.out.println(this.getClass() + " Died");
        }
        if (energy > foodmax) {     //makes sure animals cannot overeat
            energy = foodmax;
        }

    }

    // Main die method
    public void die(World world){
        world.delete(this);
    }

    public void damage(int damageValue){
        energy = energy - damageValue;
    }

    public void  spawnCarcass(int size, Location l){
        if(size == 0){
            Utils.spawnIn("carcass-small", l);
        }
        if(size == 0){
            Utils.spawnIn("carcass", l);
        }
        
    }


    // get methods
    public int getAge(){
        return age;
    }

    public int getFoodMax(){
        return foodmax;
    }

    public int getEnergy(){
        return energy;
    }
}
