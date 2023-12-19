package Animals;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import MainFolder.Utils;
import itumulator.simulator.Actor;
import itumulator.world.*;

public class Wolfpack {
    private World world;
    protected int packnr;
    protected Location packCenter;
    protected boolean homeSet = false;

    protected static ArrayList<Wolf> WolvesInPacks = new ArrayList<>();

    public Wolfpack(World world, int packnr, Location packCenter) { // I hate wolves....
        this.packnr = packnr;
        this.packCenter = packCenter;
        this.homeSet = false;
        this.world = world;
        Utils.Wolfpacks.add(this);
    }

    public void newHome(Wolf wolf) {
        if (Utils.checkNonBlocking(wolf.getPackCenter())) {
            world.delete(world.getNonBlocking(wolf.getPackCenter()));
        }
        Utils.spawnIn("Wolfden", wolf.getPackCenter());
        homeSet = true;
    }

    /**
     * updates the center of a pack based on the current locations of all wolves in
     * that pack
     * 
     * @param world  of type World, used to manipulate the world
     * @param packnr indicates which pack is being updated
     */
    protected void updatePackCenter(int packnr) {
        ArrayList<Location> accumulator = new ArrayList<>();
        int accumulatorX = 0, accumulatorY = 0, counter = 0;

        for (Wolf wolf : this.WolvesInPacks) { // first, collect all locations for wolves in pack number: packnr
            if (world.contains(wolf) && wolf.getPacknr() == packnr) {
                accumulator.add(world.getLocation(wolf));
            }
        }

        for (Location place : accumulator) { // second, accumulate value of ALL locations from wolves in the pack
            accumulatorX += place.getX();
            accumulatorY += place.getY();
            counter++;
        }

        accumulatorX = accumulatorX / counter; // third, compute average of ALL locations, and set it to packCenter
        accumulatorY = accumulatorY / counter;

        this.packCenter = new Location(accumulatorX, accumulatorY);
    }

    /**
     * Only used during initialization of the world, spawns wolves depending on
     * input with corresponding packnumber
     * 
     * @param wolves int value indicating how many wolves need to be spawned.
     */
    public void spawnWolf(int wolves) {
        Set<Location> neighbours = world.getSurroundingTiles(packCenter, 1);
        ArrayList<Location> list = new ArrayList<>(neighbours);

        for (int i = 0; i < wolves && i < list.size(); i++) {
            Wolf wolf = new Wolf(packnr, packCenter, this);
            if (world.isTileEmpty(list.get(i))) {
                world.setTile(list.get(i), wolf);
            }
            WolvesInPacks.add(wolf);
        }
    }

    /**
     * returns a boolean value of whether the given wolf has a home
     * true if wolf has a home
     */
    public boolean isHomeSet() {
        return homeSet;
    }

    /**
     * returns the Location constructor packCenter for a given packnr.
     */
    public Location getPackCenter(int packnr) {
        return packCenter;
    }

    public void setPackcenter(Location l){
        packCenter = l;
    }

    public void addToPack(Wolf wolf) {
        WolvesInPacks.add(wolf);
    }

    public void remove(Wolf wolf) {
        WolvesInPacks.remove(wolf);
    }
    public boolean gethome(){
        return this.homeSet;
    }

    public ArrayList<Wolf> getWolfsInPack() {
        return WolvesInPacks;
    }
}
