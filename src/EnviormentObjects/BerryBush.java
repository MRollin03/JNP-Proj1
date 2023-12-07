package EnviormentObjects;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.util.*;
import java.awt.*;


public class BerryBush extends EnvObject implements Actor, DynamicDisplayInformationProvider{

    private boolean berries = false;

    public BerryBush(World world){
        super(ObjectType.berryBush, world);
    }

    public void act(World world) {
        super.act(world);
        grow();
    }

    //-------Code that Spreads grass around ----//
    public void grow(){
        Random rand = new Random();
        int num = rand.nextInt(11);
        
        if(num == 10){
            berries = true;
        }
    }

    public boolean hasBerries(){
        return berries;
    }

    public void berriesToggle(){
        berries = false;
    }

    public DisplayInformation getInformation() {
        if(berries){
            return new DisplayInformation(Color.BLUE, "bush-berries");
        }
        else{
            return new DisplayInformation(Color.BLUE, "bush");
        }
    }
}