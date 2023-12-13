package Animals;

import MainFolder.Utils;
import java.util.ArrayList;

import itumulator.world.*;



public class Animal{
    private int age;
    protected int foodmax;
    protected int energy;
    protected World world;
    protected ArrayList<Wolf> Wolves = new ArrayList<>();



    Animal(){
        this.age = 0;
        this.foodmax = 40 + age * 4;
        this.energy = 40;
        this.world = Utils.world;
    }

    /**
     * The Animal act-method controls the animals age and energy comsumtion, throughout the steps
     * @param world
     */
    public void act(World world){
        if (!world.isNight()){
            energy--;              //whenever they move, spend 1 energy, except at night
            
        }
        if (world.getCurrentTime() == 0) {      //animals grow older each day
            age++;
        }
        if(this.getEnergy() < 1 || this.age == 12){     //animals die if they have no energy or get too old
            die();
            System.out.println(this.getClass() + " Died");
        }
        if (energy > foodmax) {     //makes sure animals cannot overeat
            energy = foodmax;
        }
        if (energy <= 0) {
            if(world.contains(this)){this.die();}
        }

    }

     /**
      * Die function deletes the entetie from the world.
      */
    protected void die(){
        if(!world.contains(this)){return;}
        world.delete(this);
    }

    /**
     * This gives the Animal damage and reduces the overall energy of the entitie
     * @param damageValue
     */
    public void damage(int damageValue){
        energy = energy - damageValue;
    }

    /**
     * SpawnCarcasses, spawns Carcasses at given position.
     * @param size  Size parameter determine size of carcass, 1 for small 2 for large.
     * @param l l is the location of the carcass indtended location.
     */
    public void spawnCarcass(int size, Location l){
        if(size == 1){
            Utils.spawnIn("carcass-small", l);
        }
        if(size == 2){
            Utils.spawnIn("carcass", l);
        }
        
    }


    /**
     * Gets the Animals age.
     * @return Returns Age of animal in int.
     */
    public int getAge(){
        return age;
    }

    /**
     * Get the Animal maxFoodLevel.
     * @return Returns the maximum food/energy of the given animal.
     */
    public int getFoodMax(){
        return foodmax;
    }

    /**
     * Get the Animals energy-level
     * @return return energy in int-value
     */
    public int getEnergy(){
        return energy;
    }
}
