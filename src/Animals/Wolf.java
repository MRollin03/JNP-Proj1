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

public class Wolf extends Animal implements Actor, DynamicDisplayInformationProvider{
    private Homes currentWolfden = null;
    private int packnr;
    protected ArrayList<Wolf> WolvesInPacks = new ArrayList<>();
    private Location home;

    public Wolf(World world, int packnr){
        super(world);
        this.packnr = packnr;
        //this.packCenter = new Location(x, y);
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
        Location currentLocation = world.getCurrentLocation();

        if (canAttack()){
            System.out.println("Attack Successful!");
        } else {
            try {
                Utils.randomMove(currentLocation, this);
                //Location newLocation = world.getLocation(this);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }

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

    private Location makehome(){                                //make so 
        if (!Wolfden.getexists(packnr)){                       //not sure if this works
            if (world.containsNonBlocking(world.getCurrentLocation())){
                world.delete(world.getNonBlocking(world.getCurrentLocation()));
            }
            Utils.spawnIn("Wolfden", world.getLocation(this));                          //spawn wolfden if none exists
        }
        return world.getCurrentLocation();
    }

    private void updatePackCenter(World world, int packnr){
        
    }

    public DisplayInformation getInformation() {
        if(super.getAge() > 1){
            return new DisplayInformation(Color.BLUE, "wolf-large");
        }
        else{
            return new DisplayInformation(Color.BLUE, "wolf-small");
        }
    }
}
