package Animals;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import EnviormentObjects.*;
import MainFolder.Utils;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.awt.*;
import itumulator.executable.*;

public class Wolf extends Wolfpack implements DynamicDisplayInformationProvider{
    private Homes currentWolfden = null;
    int packnr;
    int wolves;
    
    private Location home;

    public Wolf(World world, int packnr, int wolves){
        super(world, packnr, wolves);
        WolvesInPacks.add(this);
        //this.packnr = packnr;
        //this.packCenter = new Location(x, y);

        //location = random location = packCenter -> spawn in territories
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


        
        home = packCenter;      //change home to packCenter

        Location currentLocation = world.getCurrentLocation();
        //Location holeLocation = Utils.isNonBlocktNear(currentLocation, RabbitHole.class);
            List<Location> emptyTiles = new ArrayList<>(world.getEmptySurroundingTiles());

            if (!emptyTiles.isEmpty()) {

                Location newLocation = Utils.diff(home, currentLocation);

                if(world.isTileEmpty(newLocation)){
                    world.move(this, newLocation);
                }
            }

        //Location currentLocation = world.getCurrentLocation();      //change to fit wolves (old rabbit code for now)
        if (Utils.checkNonBlocking(currentLocation, Wolfden.class)) {
            Homes hole = (Homes) world.getNonBlocking(currentLocation);
            currentWolfden = hole;
            hole.addToHole(this,hole);
            return;
        }
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
            hasHome = false;
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
                world.delete(world.getTile(spot));
                world.move(this, spot);
                //remember insert energy increase here
                return true;
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

        for (Wolf wolf : WolvesInPacks){                    //first, collect all locations for wolves in pack number: packnr
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
        packCenter = tempLocation;
        
    }

    /**
     * returns pack number
     */
    public int getPacknr() {
        return packnr;
    }

    @Override
    public void die(World world){
        WolvesInPacks.remove(this);
        world.delete(this);
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
