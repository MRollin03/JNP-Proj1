package Animals;
import java.util.ArrayList;

import MainFolder.Main;
import MainFolder.Utils;
import itumulator.simulator.Actor;
import itumulator.world.*;


public class Wolfpack extends Animal implements Actor{
    protected int packnr;
    protected int wolves;
    protected static ArrayList<Wolf> WolvesInPacks = new ArrayList<>();
    protected ArrayList<Wolfpack> Wolfpacks = new ArrayList<>();
    static protected Location packCenter;
    protected boolean hasHome;



    Wolfpack (World world, int packnr, int wolves){
        super(world);
        this.packnr = packnr;
        this.wolves = wolves;
        Wolfpacks.add(this);
        
    }

    @Override
    public void act(World world){
        for (Wolfpack pack : Wolfpacks){

        }
        //System.out.println(packCenter + " " + packnr);
        if (world.isNight() && hasHome == false){
            if (world.containsNonBlocking(packCenter)){
                world.delete(world.getNonBlocking(packCenter));
            }
            Utils.spawnIn("Wolfden", packCenter);
            hasHome = true;
        }
    }

    public void createPack(){

    }

}
