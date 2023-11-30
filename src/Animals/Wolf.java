package Animals;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import EnviormentObjects.*;
import MainFolder.Utils;
import itumulator.simulator.Actor;
import itumulator.world.*;

public class Wolf extends Animal implements Actor{
    private Homes currentWolfden = null;
    private int packnr;
    protected ArrayList<Wolf> WolvesInPacks = new ArrayList<>();
    private Location home;

    public Wolf(World world, int packnr){
        super(world);
        this.packnr = packnr;
    }

    @Override
    public void act(World world){
        if (world.isNight()) {
            handleNightBehavior(world);
        } else{
            handleDayBehavior(world);
        }
        updatePackCenter(world, packnr);
        super.act(world);
    }


    private void handleNightBehavior(World world) {             //handle night behaviour
        if (world.getCurrentLocation() == null) {
            return;
        }
        
        home = makehome();

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
        if (world.containsNonBlocking(currentLocation) && Utils.checkNonBlocking(currentLocation, Wolfden.class)) {
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
        Location currentLocation = world.getCurrentLocation();

        Set<Location> emptyTiles = world.getEmptySurroundingTiles(currentLocation);

        try {
            Utils.randomMove(currentLocation, this);
            //Location newLocation = world.getLocation(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private Location makehome(){
        if (!Wolfden.getexists()){                       //not sure if this works
            Utils.spawnIn("Wolfden", world.getLocation(this));                          //spawn wolfden if none exists
        }
        return world.getCurrentLocation();
    }

    private void updatePackCenter(World world, int packnr){
        
    }
}
