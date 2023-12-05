package EnviormentObjects;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.util.*;
import java.awt.*;


public class Carcass extends EnvObject implements NonBlocking, DynamicDisplayInformationProvider{

    private boolean big = false;
    

    public Carcass(World world){
        super(ObjectType.carcass, world);
    }

    public void act(World world) {
        super.act(world);
    }

    public boolean isBig(){
        return big;
    }

    public DisplayInformation getInformation() {
        if(big){
            return new DisplayInformation(Color.BLUE, "carcass");
        }
        else{
            return new DisplayInformation(Color.BLUE, "carcass-small");
        }
    }
}