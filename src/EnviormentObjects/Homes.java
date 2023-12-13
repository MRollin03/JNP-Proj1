package EnviormentObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import MainFolder.*;
import Animals.Animal;
import Animals.Rabbit;
import Animals.Wolf;
import itumulator.world.*;

public class Homes extends EnvObject {
    protected ArrayList<Animal> animalsInHome = new ArrayList<>();
    // protected ArrayList<ArrayList<Wolf>> homes = new ArrayList<>();
    ObjectType objType;

    public Homes() {
        super(ObjectType.hole, Utils.world);
    }

    /**
     * Checks if it is daytime, if yes, start the process to evacuate residents of
     * their homes.
     */
    public void activate() {
        if (world.isDay()) {
            removeFromHole();
        }
    }

    /**
     * adds a wolf/rabbit to their respective Wolfden/burrow and removes them from
     * the world
     * 
     * @param animal input of type Object but only works if is of type animal.
     * @param hole   input of type Object but only works if is of type hole.
     *               (homes?)
     **/
    public void addToHole(Animal animal, Object hole) {
        Animal tempAnimal = (Animal) animal;
        animalsInHome.add(tempAnimal);
        world.remove(animal);

        // Make sure the animal is removed from the world
        if (world.contains(animal)) {
            System.out.println("Animal added to the hole successfully.");
        } else {
            System.out.println("Error: Animal could not be removed from the world.");
        }
    }
    
    /**
    * adds a wolfcub to their respective Wolfden/burrow
    * @param animal input of type Object but only works if is of type animal.
    * @param hole input of type Object but only works if is of type hole. (homes?)
    **/
    public void addCubToHole(Object animal, Object hole){
        if (hole instanceof Wolfden) {
            Wolf tempWolf = (Wolf) animal;
            Wolfden.add(tempWolf);
        }
    }

    /**
     * removes all animals from their respective homes.
     */
    public void removeFromHole() {
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

    public ArrayList<Animal> getAnimalsInHole() {
        return animalsInHome;
    }
}
