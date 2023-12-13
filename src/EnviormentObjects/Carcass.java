package EnviormentObjects;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import itumulator.simulator.Actor;
import itumulator.world.*;
import java.util.*;

import MainFolder.Utils;

import java.awt.*;


public class Carcass extends EnvObject implements Actor, NonBlocking, DynamicDisplayInformationProvider{
    private boolean infested;
    private boolean big;
    private int state;
    
    public Carcass(World world, boolean isbig){
        super(ObjectType.carcass, world);
        Random rand = new Random();
        this.infested = rand.nextBoolean();
        this.state = 24;
        this.big = isbig;
    }

    
    public void act(World world) {
        super.act(world);

        if(state < 1 && infested && big){
            Utils.world.delete(this);
            Utils.spawnIn("fungi", world.getCurrentLocation());
        }

        if(state < 1 && infested && !big){
            Utils.world.delete(this);
            Utils.spawnIn("fungi-small", world.getCurrentLocation());
        }
        if(state < 1 && !infested){
            Utils.world.delete(this);
        }

        state--;
    }

    /**
     * Used to check if Carcass is infested with a fungus.
     * @param value
     */
    public void setInfested(boolean value){
        this.infested = value;
    }
    /**
     * Used for checking is its big Carcass
     * @return returns true if big, false if small
     */
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