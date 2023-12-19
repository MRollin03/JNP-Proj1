package EnviormentObjects;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import itumulator.world.*;
import itumulator.simulator.*;

public class Grass extends EnvObject implements Actor {
    private double spreadChance = 1; // 15% chance to spread
    private Random rand = new Random();

    public Grass(World world) {
        super(ObjectType.grass, world);
    }

    public void act(World world) {
        super.act(world);
        spread(world);
    }

    /**
     * spreads grass to nearby tiles based on a random number based chance. Ignores
     * Exceptions.
     * 
     * @param world of type World
     */
    public void spread(World world) {
        Set<Location> neighbours = world.getEmptySurroundingTiles(); // gets surrounding empty tiles
        if (neighbours.isEmpty()) { // stop if no neighbors are spreadable
            return;
        }
        int rand_int = 0;
        ArrayList<Location> list = new ArrayList<>(neighbours);

        for (Location neighbor : list) {
            rand_int = rand.nextInt(1000);
            if (rand_int < spreadChance) {
                try { // have to use try, since setTile will eventually fail.
                    world.setTile(neighbor, new Grass(world)); // insert new grass at location
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }
}