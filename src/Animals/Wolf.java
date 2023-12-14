package Animals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.Set;

import EnviormentObjects.*;
import MainFolder.*;
import java.util.Set;

import EnviormentObjects.*;
import MainFolder.Utils;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.awt.*;
import itumulator.executable.*;

public class Wolf extends Animal implements DynamicDisplayInformationProvider, Actor {
    private Homes currentWolfden = null;
    private Random rand = new Random();
    private int mate_CD = 18 + rand.nextInt(8);
    private Wolfpack wolfPack;

    public Wolf(int packnr, Location packCenter, Wolfpack wolfPack) {
        super();
        this.wolfPack = wolfPack;
        this.wolfPack.WolvesInPacks.add(this);
    }

    @Override
    public void act(World world) {
        if (world.isNight()) {
            handleNightBehavior(world);
            if (world.getCurrentTime() == 10) {
                wolfPack.newHome(this);
            }
        } else {
            handleDayBehavior(world);
            if (world.getCurrentTime() == 8 && currentWolfden != null) {
                if (!world.contains(currentWolfden)) {
                    return;
                }
                world.delete(currentWolfden);
            }
        }
        if (mate_CD > 0){
            mate_CD--;
        }
        super.act(world);
    }

    private void handleNightBehavior(World world) {

        Location currentLocation = world.getCurrentLocation();

        // if no location dont run.
        if (currentLocation == null) {
            System.out.println("CD = " + this.getmate_CD());
            if (this.getmate_CD() == 0){
                for (Wolf wolf : Wolfpack.WolvesInPacks){
                    if (wolf.getmate_CD() == 0 && wolf.getPacknr() == this.getPacknr() && !(world.isOnTile(wolf))){
                        Wolf cub = new Wolf(this.getPacknr(), this.getPackCenter(), this.wolfPack);
                        world.add(cub);     //spawn cub without adding it to world
                        Wolfden hole = (Wolfden) world.getNonBlocking(wolfPack.packCenter);
                        cub.currentWolfden = currentWolfden;
                        hole.addCubToHole(cub, hole);   //add wolfcub to wolfden so it will spawn with pack in the morning
                        resetmateCD(this);
                        resetmateCD(wolf);
                        System.out.println("A wolf is born!");
                        return;
                    }
                }
            }
            return;
        }

        //nearby empty tiles
        List<Location> emptyTiles = new ArrayList<>(world.getEmptySurroundingTiles());
        if (emptyTiles.isEmpty()) {
            return;
        }
        
        //randommove
        Location newLocation = Utils.diff(wolfPack.packCenter, currentLocation);
        if (world.isTileEmpty(newLocation)) {
            world.move(this, newLocation);
        }

        //go towards wolfden
        //System.out.println(world.getNonBlocking(currentLocation) instanceof Wolfden);
        System.out.println("world.getLocation(this)");
        System.out.println(world.getLocation(this));
        if (Utils.checkNonBlocking(currentLocation)){
            if (world.getNonBlocking(currentLocation) instanceof Wolfden) {
                Wolfden hole = (Wolfden) world.getNonBlocking(wolfPack.packCenter);
                currentWolfden = hole;
                hole.addToHole(this,hole);
            return;
            }
        } 

    private void handleDayBehavior(World world) { // handle day behaviour
        if (world.getCurrentLocation() == null) { // Proceed only if world.getCurrentLocation() is not null
            if (currentWolfden != null) {
                currentWolfden.removeFromHole();
            return;
        }
        if (world.getLocation(this) == null ){

        }

        wolfPack.updatePackCenter();

        Location currentLocation = world.getCurrentLocation();
        Set<Location> surroundings;
        surroundings = world.getSurroundingTiles(currentLocation);

        if (wolfPack.gethome()) {
            for (Location tile : surroundings) {        //delete wolfdens - could be moved to a seperate function
                wolfPack.homeSet = true;
                if (world.getTile(tile) instanceof Wolfden){
                    EnvObject.deleteObj(world, world.getNonBlocking(tile));
                }
            }
        }
        

        try {
            if (!world.getSurroundingTiles(wolfPack.packCenter, 2).contains(currentLocation)) {
                Location newLocation = Utils.diff(wolfPack.packCenter, currentLocation);
                if (world.isTileEmpty(newLocation)) {
                    world.move(this, newLocation);
                    return;
                }
            }
        } catch (NullPointerException e) { // for ignoring out-of-bounds checking
            System.out.println(e.getLocalizedMessage());
        }

        if (canAttack()) {
            System.out.println("Attack Successful!");
        } else {
            try {
                world.move(this, Utils.randomMove(currentLocation));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * the entire attack functionality of a wolf.
     * searches nearby tiles for enemies to attack.
     * if Rabbit, delete the rabbit, move to its location and gain 10 energy
     * if Bear, deal 5 damage to the bear through 'bear.damage(5)'
     * if Carcass, delete the Carcass and gain 4 energy
     * if Wolf (of other packnr) deal 5 damage to it.
     */
    private boolean canAttack(){
        Set<Location> nearby = world.getSurroundingTiles();
        for (Location spot : nearby) {
            if (world.getTile(spot) instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) world.getTile(spot);
                rabbit.die();
                world.move(this, spot);
                this.energy = this.energy + 10;
                return true;
            }
            if (world.getTile(spot) instanceof Bear) {
                Bear bear = (Bear) world.getTile(spot);
                bear.damage(5); // Gives bear 5 in energy damage.
                return true;
            }
            if (world.getTile(spot) instanceof Carcass) {
                Carcass carcass = (Carcass) world.getTile(spot);
                world.delete(carcass);
                this.energy = this.energy + 8;
                return true;
            }
            if (world.getTile(spot) instanceof Wolf) {
                Wolf wolf = (Wolf) world.getTile(spot);
                if (wolf.getPacknr() != this.getPacknr()) {
                    wolf.damage(5); // Bear gives wolf 8 in damages.
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * return the mating cooldown period of a wolf.
     */
    private int getmate_CD() {
        return this.mate_CD;
    }

    /**
     * Resets the mating cooldown timer for the wolf given in the input
     * @param wolf of type Wolf
     */
    private void resetmateCD(Wolf wolf){
        wolf.mate_CD = 18 + rand.nextInt(8);
    }

    /**
     * returns pack number
     */
    public int getPacknr() {
        return wolfPack.packnr;
    }

    /**
     * returns the location of the packCenter of the pack correlating with the
     * packnr input.
     * 
     * @param packnr of type int
     *               returns null if the specified wolfpack doesn't exist
     */
    public Location getPackCenter() {
        return wolfPack.packCenter;
    }

    /**
     * updates the wolfs current packCenter based on the input
     * 
     * @param l of type Location changes the packCenter.
     */
    protected void updatePackCenter(Location l) {
        wolfPack.packCenter = l;
    }

    public Homes getCurrentWolfden() {
        return currentWolfden;
    }

    public void setCurrentWolfden(Homes wolfden) {
        this.currentWolfden = wolfden;
    }

    @Override
    public void die() {
        wolfPack.remove(this);
        try { // wierd code
            world.delete(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public DisplayInformation getInformation() {
        if (super.getAge() > 1) {
            return new DisplayInformation(Color.BLUE, "wolf");
        } else {
            return new DisplayInformation(Color.BLUE, "wolf-small");
        }
    }
}