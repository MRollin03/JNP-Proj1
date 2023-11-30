package Animals;
import java.util.ArrayList;

import itumulator.simulator.Actor;
import itumulator.world.*;

public class Wolf extends Animal implements Actor{
    private int packnr;
    protected ArrayList<Wolf> WolvesInPacks = new ArrayList<>();
    private Location packCenter = null;
    
    public Wolf(World world, int packnr, int x, int y){
        super(world);
        this.packnr = packnr;
        this.packCenter = new Location(x, y);
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

    private void handleNightBehavior(World world) {
        
    }


    private void handleDayBehavior(World world) {

    }


    private void updatePackCenter(World world, int packnr){
        
    }
}
