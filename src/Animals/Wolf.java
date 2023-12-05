package Animals;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import EnviormentObjects.*;
import MainFolder.Main;
import MainFolder.Utils;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.awt.*;
import itumulator.executable.*;

public class Wolf extends Animal implements DynamicDisplayInformationProvider, Actor{
    private Homes currentWolfden = null;
    Random rand = new Random();
    int packnr;
    Location packCenter;
    int mate_CD = 18 + rand.nextInt(8);
    
    private Wolfpack wolfPack;
    private Location home = packCenter;

    public Wolf(World world, int packnr, Location packCenter, Wolfpack wolfPack){
        super(world);
        this.packnr = packnr;
        this.packCenter = packCenter;
        this.wolfPack = wolfPack;
        this.wolfPack.WolvesInPacks.add(this);
    }

    @Override
    public void act(World world){
        if (world.isNight()) {
            handleNightBehavior(world);
        } else{
            handleDayBehavior(world);
        }
        super.act(world);
    }


    private void handleNightBehavior(World world) {             //handle night behaviour
        if (world.getCurrentLocation() == null) {
            return;
        }

        if (Wolfpack.getHasHome(packnr) == false){
            if (world.containsNonBlocking(packCenter)){
                world.delete(world.getNonBlocking(packCenter));
            }
            home = packCenter;
            Utils.spawnIn("Wolfden", packCenter);
            Wolfpack.changeHasHome(packnr,true);
        }
  
        //home = packCenter;      //change home to packCenter
        Location currentLocation = world.getCurrentLocation();
        List<Location> emptyTiles = new ArrayList<>(world.getEmptySurroundingTiles());
        //System.out.println(home);
   
        home = packCenter;
        if (!emptyTiles.isEmpty()) {    //move towards home
            Location newLocation = Utils.diff(home, currentLocation);
            if(world.isTileEmpty(newLocation)){
                world.move(this, newLocation);
            }
        }

        //Location currentLocation = world.getCurrentLocation();      //change to fit wolves (old rabbit code for now)

        if (Utils.checkNonBlocking(currentLocation)){
            if (world.getNonBlocking(currentLocation) instanceof Wolfden) {
                Wolfden hole = (Wolfden) world.getNonBlocking(home);
                currentWolfden = hole;
                hole.addToHole(this,hole);
                if (this.getmate_CD() == 0 && hole.exists(this)){
                    for (Wolf wolf : Wolfpack.WolvesInPacks){
                        if (wolf.getmate_CD() == 0){
                            hole.addToHole(new Wolf(world, packnr, home, this.wolfPack),home);
                            System.out.println("A wolf is born!");
                        }
                    }
                }
            return;
            }
        }
       /* 
        if (hole != null) {
        if (this.getmate_CD() == 0 && hole.exists(this)){
            for (Wolf wolf : WolvesInPacks){
                if (wolf.getmate_CD() == 0){
                    hole.addToHole(new Wolf(world, packnr, home),home);
                    System.out.println("A wolf is born!");
                }
            }
        }
        }*/

        

    }


    private void handleDayBehavior(World world) {               //handle day behaviour
        if (world.getCurrentLocation() == null) {       // Proceed only if world.getCurrentLocation() is not null
            if (currentWolfden != null) {
                currentWolfden.removeFromHole();
            }
            return;
        }
        updatePackCenter(world, packnr);

        Location currentLocation = world.getCurrentLocation();
        Set<Location> surroundings;
        surroundings = world.getSurroundingTiles(currentLocation);
        for (Location tile : surroundings) {        //delete wolfdens - could be moved to a seperate function
            Wolfpack.changeHasHome(packnr,false);
            if (world.getTile(tile) instanceof Wolfden){
                EnvObject.deleteObj(world, world.getNonBlocking(tile));
            }
        }

        try {
            if (!world.getSurroundingTiles(packCenter,2).contains(currentLocation)){
                Location newLocation = Utils.diff(packCenter,currentLocation);
                if(world.isTileEmpty(newLocation)){
                    world.move(this, newLocation);
                    return;
                }
            } 
        } catch (NullPointerException e) {      //for ignoring out-of-bounds checking
            //System.out.println("not working");
        }

        if (canAttack()){
            System.out.println("Attack Successful!");
        } else {
            try {
                world.move(this, Utils.randomMove(currentLocation, this));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 
     */
    private boolean canAttack(){
        Set<Location> nearby = world.getSurroundingTiles();
        for (Location spot : nearby){
            if (world.getTile(spot) instanceof Rabbit){
                Rabbit rabbit = (Rabbit) world.getTile(spot);
                rabbit.die(world);
                world.move(this, spot);
                this.energy = this.energy + 10;
                return true;
            }
            if (world.getTile(spot) instanceof Bear){
                Bear bear = (Bear) world.getTile(spot);
                bear.damage(5); // Gives bear 5 in energy damage.
                return true;
            }
            if (world.getTile(spot) instanceof Wolf){
                Wolf wolf = (Wolf) world.getTile(spot);
                if(wolf.getPacknr() != this.getPacknr()){
                    wolf.damage(5); // Bear gives wolf 8 in damages.
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * updates the center of a pack based on the current locations of all wolves in that pack
     * @param world of type World, used to manipulate the world
     * @param packnr indicates which pack is being updated
     */
    private void updatePackCenter(World world, int packnr){
        ArrayList<Location> accumulator = new ArrayList<>();
        int accumulatorX = 0, accumulatorY = 0, counter = 0;

        for (Wolf wolf : Wolfpack.WolvesInPacks){                    //first, collect all locations for wolves in pack number: packnr
            if (wolf.getPacknr() == packnr){
                accumulator.add(world.getLocation(wolf));
            }
        }
        for (Location place : accumulator) {                //second, accumulate value of ALL locations from wolves in the pack
            accumulatorX += place.getX();
            accumulatorY += place.getY();
            counter++;
        }
        accumulatorX = accumulatorX/counter;                //third, compute average of ALL locations, and set it to packCenter
        accumulatorY = accumulatorY/counter;
        Location tempLocation = new Location(accumulatorX, accumulatorY);
        updatePackCenter(tempLocation);
        
    }

    /**
     * return the mating cooldown period of a wolf.
     */
    private int getmate_CD(){
        return this.mate_CD;
    }

    /**
     * returns pack number
     */
    public int getPacknr() {
        return packnr;
    }

    /**
     * returns the location of  the packCenter of the pack correlating with the packnr input.
     * @param packnr of type int
     * returns null if the specified wolfpack doesn't exist
     */
    public static Location getPackCenter(int packnr){
        for (Wolf wolf : Wolfpack.WolvesInPacks){
            if (wolf.getPacknr() == packnr){
                return wolf.packCenter;
            }
        }
        return null;
    }
    /**
     * updates the wolfs current packCenter based on the input
     * @param l of type Location changes the packCenter.
     */
    protected void updatePackCenter(Location l){
        this.packCenter = l;
    }

    public void setWolfPack(Wolfpack wolfPack){
        this.wolfPack = wolfPack;
    }

    
    @Override
    public void die(World world){
        wolfPack.remove(this);
        try {                               //wierd code
        world.delete(this);
        } catch (Exception e){
            System.out.println("fejlslet");
        }
    }

    public DisplayInformation getInformation() {
        if(super.getAge() > 1){
            return new DisplayInformation(Color.BLUE, "wolf");
        }
        else{
            return new DisplayInformation(Color.BLUE, "wolf-small");
        }
    }
}