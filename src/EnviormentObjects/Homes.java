package EnviormentObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import MainFolder.*;
import Animals.*;
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
    }
    
    /**
     * removes all animals from their respective homes.
     */
    public void removeFromHole() {
        if( !world.contains(this)){return;}
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
