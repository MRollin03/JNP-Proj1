package Animals;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import MainFolder.Main;
import MainFolder.Utils;
import itumulator.simulator.Actor;
import itumulator.world.*;


public class Wolfpack implements Actor{
    World world = Utils.world;
    protected int packnr;
    protected Location packCenter;
    protected boolean hasHome;

    protected static ArrayList<Wolf> WolvesInPacks = new ArrayList<>();
    
    public Wolfpack (World world, int packnr, Location packCenter){     //I hate wolves....
        this.packnr = packnr;
        this.packCenter = packCenter;
        this.hasHome = false;
        Main.Wolfpacks.add(this);
    }

    @Override
    public void act(World world){
        packCenter = Wolf.getPackCenter(packnr);
        System.out.println(packCenter);
    }

    public static boolean getHasHome(int packnr){
        for (Wolfpack wolfpack : Main.Wolfpacks){
            if (wolfpack.getPacknr() == packnr){
                return wolfpack.hasHome;
            }
        }
        return false;
    }

    public static void changeHasHome(int packnr, boolean value){
        for (Wolfpack wolfpack : Main.Wolfpacks){
            if (wolfpack.getPacknr() == packnr){
                wolfpack.hasHome = value;
            }
        }
    }

    private int getPacknr(){
        return packnr;
    }

    public static Location getPackCenter(int packnr){
        for (Wolf wolf : Wolfpack.WolvesInPacks){
            if (wolf.getPacknr() == packnr){
                return wolf.packCenter;
            }
        }
        return null;
    }



    /**
     * Only used during initialization of the world, spawns wolves depending on input with corresponding packnumber
     * @param wolves int value indicating how many wolves need to be spawned.
     */
    public void spawnWolf(int wolves){      //lav packcenter i midten
        Set<Location> neighbours = world.getSurroundingTiles(packCenter,2);
        ArrayList<Location> list = new ArrayList<>(neighbours);
        
        System.err.println("spawner wolfs");
        for (int i = 0; i <= wolves-1;i++){
            Wolf wolf = new Wolf(world, packnr, packCenter);
            if (world.isTileEmpty(list.get(i))){
                world.setTile(list.get(i), wolf);
            }
            WolvesInPacks.add(wolf);
        }
        
    }




}
