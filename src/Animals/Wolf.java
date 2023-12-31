package Animals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public int mate_CD = 18 + rand.nextInt(8);      //this is public only so it can be tested
    private Wolfpack wolfPack;
    private int packnr;

    public Wolf(int packnr, Location packCenter, Wolfpack wolfPack) {
        super();
        this.wolfPack = wolfPack;
        this.packnr = packnr;
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
        if (mate_CD > 0) {
            mate_CD--;
        }
        super.act(world);
    }

    public void handleNightBehavior(World world) {

        Location currentLocation = world.getCurrentLocation();

        // if no location dont run.
        if (currentLocation == null) {
            if (mate_CD == 0){
                mateWolf();
            }
            return;
        }

        List<Location> emptyTiles = new ArrayList<>(world.getEmptySurroundingTiles());
        if (emptyTiles.isEmpty()) {
            return;
        }
        Location newLocation = Utils.diff(wolfPack.packCenter, currentLocation);
        if (world.isTileEmpty(newLocation)) {
            world.move(this, newLocation);
        }

        if (Utils.checkNonBlocking(currentLocation)) {
            if (world.getNonBlocking(currentLocation) instanceof Wolfden) {
                Wolfden hole = (Wolfden) world.getNonBlocking(wolfPack.packCenter);
                currentWolfden = hole;
                hole.addToHole(this, hole);
            }
        }
    }

    private void handleDayBehavior(World world) { // handle day behaviour
        if (world.getCurrentLocation() == null) { // Proceed only if world.getCurrentLocation() is not null
            if (currentWolfden != null) {
                currentWolfden.removeFromHole();
            }
            return;
        }

        wolfPack.updatePackCenter(this.packnr);

        Location currentLocation = world.getCurrentLocation();
        Set<Location> surroundings;
        surroundings = world.getSurroundingTiles(currentLocation);

        if (wolfPack.gethome()){
            for (Location tile : surroundings) { // delete wolfdens - could be moved to a seperate function
                wolfPack.homeSet = true;
                if (world.getTile(tile) instanceof Wolfden) {
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
     * if Rabbit or crow, delete the rabbit or crow, move to its location and gain 10 energy
     * if Bear, deal 5 damage to the bear through 'bear.damage(5)'
     * if Carcass, delete the Carcass and gain 4 energy
     * if Wolf (of other packnr) deal 5 damage to it.
     */
    private boolean canAttack() {
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
                return true;
            }
            if (world.getTile(spot) instanceof Wolf) {
                Wolf wolf = (Wolf) world.getTile(spot);
                if (wolf.getPacknr() != this.getPacknr()) {
                    wolf.damage(5); // Bear gives wolf 8 in damages.
                    return true;
                }
            }
            if (world.getTile(spot) instanceof Crow) {
                Crow crow = (Crow) world.getTile(spot);
                crow.die();
                world.move(this, spot);
                this.energy = this.energy + 10;
                return true;
            }
        }

        return false;
    }

    /**
     * Checks all other wolves in existence for suitable mates that are in same pack and hole as itself.
     * spawns a new wolfcub in the den and resets both this wolf and the other wolfs mate cooldown timer.
     */
    public void mateWolf(){     //public only so that it can be tested
        for (Wolf wolf : Wolfpack.WolvesInPacks){
            if (wolf.getmate_CD() == 0 && wolf.getPacknr() == this.getPacknr() && wolf != this){
                this.wolfPack.spawnWolf(1);
                resetmateCD(this);
                resetmateCD(wolf);
                System.out.println("A wolf is born!");
                return;
            }
        }
    }
    /**
     * resets the mate cooldown timer for the wolf in the input
     * returns nothing
     */
    protected void resetmateCD(Wolf wolf){
        wolf.mate_CD = 18 + rand.nextInt(8);
    }

    /**
     * return the mating cooldown period of a wolf.
     */
    public int getmate_CD() {
        return this.mate_CD;
    }

    /**
     * returns pack number
     */
    public int getPacknr() {
        return wolfPack.packnr;
    }

    public Wolfpack getPack(){
        return wolfPack;
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
    public  void updatePackCenter(Location l) {
        wolfPack.packCenter = l;
    }

    /**
     * returns the current wolfs wolfden
     * return type Homes
     */
    public Homes getCurrentWolfden() {
        return currentWolfden;
    }

    /**
     * sets the wolfs current wolfden to the input
     * @param wolfden of type Homes
     */
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
            return new DisplayInformation(Color.GREEN, "wolf");
        } else {
            return new DisplayInformation(Color.ORANGE, "wolf-small");
        }
    }
}