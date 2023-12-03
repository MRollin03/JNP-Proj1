package Animals;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import MainFolder.Main;
import MainFolder.Utils;
import itumulator.simulator.Actor;
import itumulator.world.*;


public class Wolfpack extends Animal implements Actor{
    protected int packnr;
    protected int wolves;
    protected static ArrayList<Wolf> WolvesInPacks = new ArrayList<>();
    protected ArrayList<Wolfpack> Wolfpacks = new ArrayList<>();
    /*static*/ protected Location packCenter;
    protected boolean hasHome;



    public Wolfpack (World world, int packnr, Location packCenter){
        super(world);
        this.packnr = packnr;
        this.packCenter = packCenter;
        Wolfpacks.add(this);
    }

    @Override
    public void act(World world){
        if (world.isNight() && hasHome == false){
            if (world.containsNonBlocking(packCenter)){
                world.delete(world.getNonBlocking(packCenter));
            }
            Utils.spawnIn("Wolfden", packCenter);
            hasHome = true;
        }
    }

    /**
     * Only used during initialization of the world, spawns wolves depending on input with corresponding packnumber
     * @param wolves int value indicating how many wolves need to be spawned.
     */
    public void spawnWolf(int wolves){
        Set<Location> neighbours = world.getEmptySurroundingTiles(packCenter);
        ArrayList<Location> list = new ArrayList<>(neighbours);

        for (int i = 0; i <= wolves;i++){
            Wolf wolf = new Wolf(world, packnr, packCenter);
            world.setTile(list.get(i), wolf);
        }
    }

    protected void updatePackCenter(Location l){
        this.packCenter = l;
    }


}
