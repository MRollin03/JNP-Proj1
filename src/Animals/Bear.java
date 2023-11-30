package Animals;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.*;
import EnviormentObjects.*;
import MainFolder.*;
import java.util.*;
import java.awt.*;

public class Bear extends Animal implements Actor, DynamicDisplayInformationProvider{

    private Location centrum;

    public Bear(World world){
        super(world);
        this.centrum = Utils.getRandomLocation(world.getSize());
    }
    public Bear(Location centrum, World world){
        super(world);
        this.centrum = centrum;
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

    private void handleNightBehavior(World world) {
        if(world.getCurrentLocation() == null){
            return;
        }
    }

    private void handleDayBehavior(World world) {

        // check if bear has location
        if (world.getCurrentLocation() == null) {
            return;
        }

        
        Location currentLocation = world.getCurrentLocation();
        
        // Gets a random move location and checks if theres grass on the tiles.
        try {
            Utils.randomMove(currentLocation, this);
            Location newLocation = world.getLocation(this);

            ArrayList<Class<?>> foods = new ArrayList<>();
            foods.add(Grass.class);
            foods.add(Wolf.class);
            foods.add(Rabbit.class);


            for (Class foodType : foods) {
                if (Utils.checkNonBlockingType(newLocation, foodType)) {
                    EnvObject.deleteObj(world, world.getNonBlocking(newLocation));
                    energy += 5;
                    System.err.println(foodType + " eaten");
                }
            }

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
