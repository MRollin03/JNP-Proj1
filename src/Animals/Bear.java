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

        Location newLocation = Utils.randomMove(currentLocation, world);
        int number = 0;
        while (!terrortories.contains(newLocation)) {
            newLocation = Utils.randomMove(currentLocation, world);
            if (terrortories.contains(newLocation)) {
                System.out.println("yay");
            }
            number++;
            System.out.println(newLocation);
        }

        world.move(this, newLocation);
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
