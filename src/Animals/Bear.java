package Animals;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;
import MainFolder.*;
import java.util.*;
import java.awt.*;
import java.util.ArrayList.*;;

public class Bear extends Animal implements Actor, DynamicDisplayInformationProvider{

    private Location centrum;
    private Set<Location> terrortories;
    ArrayList<Class<?>> foods = new ArrayList<>();


    //-----------Constructors for two different cases

    // random location
    public Bear(World world) {
        super(world);
        this.centrum = Utils.getWorldRandomLocation(world.getSize());
        world.setCurrentLocation(centrum);
        terrortories = world.getSurroundingTiles(1);
    }

    // dedicated location
    public Bear(Location centrum, World world) {
        super(world);
        this.centrum = centrum;
        world.setCurrentLocation(centrum);
        terrortories = world.getSurroundingTiles(1);
    }


    //act function
    @Override
    public void act(World world){
        if (world.isNight()) {
            handleNightBehavior(world);
        } else{
            handleDayBehavior(world);
        }

        super.act(world);
    }
   
    
    private void handleNightBehavior(World world) {
        Location currentLocation = world.getCurrentLocation();
        if(currentLocation == null){
            return;
        }
    }

    private void handleDayBehavior(World world) {

        // check if bear has location
        Location currentLocation = world.getCurrentLocation();

        if (world.getCurrentLocation() == null) {
            return;
        }

        //get a random location around the bear and sees if the location is inside of  the terrortorie
        Location newLocation = Utils.randomMove(currentLocation, world);
        while (!terrortories.contains(newLocation)) {
            currentLocation = world.getCurrentLocation();
            newLocation = Utils.randomMove(currentLocation, world);
            System.out.println(newLocation);
        }

        //Tries to find a food source on the next step
        if(world.containsNonBlocking(newLocation)){

            //Berry checker
            if(world.getNonBlocking(newLocation).getClass() == BerryBush.class){
                BerryBush bush = (BerryBush) world.getNonBlocking(newLocation);
                if(bush.hasBerries()){
                    bush.berriesToggle();
                    super.energy = super.getEnergy() + 5;
                    System.err.println(this.getEnergy());
                }
            }

            // Grass checker
            if(world.getNonBlocking(newLocation).getClass() == Grass.class){
                Grass grass = (Grass) world.getNonBlocking(newLocation);
                world.delete(grass);
                super.energy =+ 5;
            }
            
            if (canAttack()){
                System.out.println("Attack Successful!");
            } else {
                try {
                    world.move(this, Utils.randomMove(currentLocation, Utils.world));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }   

        world.move(this, newLocation);

    } 


    public Location getCentrum(){
        return centrum;
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
            if (world.getTile(spot) instanceof Wolf){
                Wolf wolf = (Wolf) world.getTile(spot);
                wolf.damage(8); // Bear gives wolf 8 in damages.
                return true;
            }
        }

        return false;
    }
    

    @Override
    public DisplayInformation getInformation() {

        if (world.isNight()){
            if(super.getAge() > 1){
                return new DisplayInformation(Color.BLUE, "bear-sleeping");
            }
            else{
                return new DisplayInformation(Color.BLUE, "bear-small-sleeping");
            }
            
        } else {
            if(super.getAge() > 1){
                return new DisplayInformation(Color.BLUE, "bear");
            }
            else{
                return new DisplayInformation(Color.BLUE, "bear-small");
            }
        }
    }

}
