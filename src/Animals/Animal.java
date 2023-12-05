package Animals;

import java.util.ArrayList;

import itumulator.world.*;



public class Animal{
    private int age;
    protected int foodmax;
    protected int energy;
    protected World world;
    protected ArrayList<Wolf> Wolves = new ArrayList<>();



    Animal(World world){
        this.age = 0;
        this.foodmax = 40+age*4;
        this.energy = 40;
        this.world = world;
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
        if (energy <= 0) {
            System.out.println(this);
            this.die(world);
        }

    }

    // Main die method
    protected void die(World world){
        System.out.println("test");
        world.delete(this);
    }

    public void damage(int damageValue){
        energy = energy - damageValue;
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
