package EnviormentObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import MainFolder.*;
import Animals.Rabbit;
import Animals.Wolf;
import itumulator.world.*;


public class Homes extends EnvObject {
    protected ArrayList<Rabbit> animalsInHome = new ArrayList<>();
    protected ArrayList<Wolf> Wolfden = new ArrayList<>();
    //protected ArrayList<ArrayList<Wolf>> homes = new ArrayList<>();
    ObjectType objType;

    Homes(){
        super(ObjectType.hole, Utils.world);
    }
    
    /**
     * Checks if it is daytime, if yes, start the process to evacuate residents of their homes.
     */
    public void activate() {
        if (world.isDay()) {
            removeFromHole();
        }
    }
    
     /**
    * adds a wolf/rabbit to their respective Wolfden/burrow and removes them from the world
    * @param animal input of type Object but only works if is of type animal.
    * @param hole input of type Object but only works if is of type hole. (homes?)
    **/
    public void addToHole(Object animal, Object hole) {
        //System.out.println("1 : Rabbits in hole size " + animalsInHome.size() );
        if (hole instanceof Wolfden) {
            Wolf tempWolf = (Wolf) animal;
            Wolfden.add(tempWolf);
        } else if (hole instanceof RabbitHole) {
            Rabbit tempRabbit = (Rabbit) animal;
            animalsInHome.add(tempRabbit);
        }
        world.remove(animal);
    }

    /**
     * removes all animals from their respective homes.
     */
    public void removeFromHole() {
        removeFromDen();
        if (animalsInHome.isEmpty()) {
            return;
        }
        Location l = world.getLocation(this);
    
        Set<Location> neighbours = world.getEmptySurroundingTiles(l);
        ArrayList<Location> list = new ArrayList<>(neighbours);
    
        Collections.shuffle(list);
    
        int animalsReleased = 0;
    
        for (int i = 0; i < list.size(); i++) {
            try {
                if (i >= animalsInHome.size()) {
                    break;
                }
                world.setTile(list.get(i), animalsInHome.get(i));
                animalsReleased++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    
        for (int index = 0; index < animalsReleased; index++) {
            animalsInHome.remove(0);
        }
        
    }
    /**
     * Removes The Animals in the home.
     */
    public void removeFromDen(){

        if (Wolfden.isEmpty()) {
            return;
        }
        if(!world.contains(this)){return;}
        Location l = world.getLocation(this);
    
        Set<Location> neighbours = world.getEmptySurroundingTiles(l);
        ArrayList<Location> list = new ArrayList<>(neighbours);
    
        Collections.shuffle(list);
    
        int animalsReleased = 0;
        for (int i = 0; i < list.size(); i++) {
            try {
                if (i >= Wolfden.size()) {
                    break;
                }
                world.setTile(list.get(i), Wolfden.get(i));
                animalsReleased++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    
        for (int index = 0; index < animalsReleased; index++) {
            Wolfden.remove(0);
        }
        
    }

}
