package EnviormentObjects;
import itumulator.world.*;
import java.util.ArrayList;
import Animals.Wolf;

public class Wolfden extends Homes{
    protected ArrayList<Wolf> WolvesInHole = new ArrayList<>();
    protected static int packnr; 

    public Wolfden(World world) {
        //super(ObjectType.hole, world);
        this.packnr = packnr;
    }

    public static boolean getexists(int packnr){
        if (getpacknr() == packnr){
            return true;
        }
        return false;
    }

    private static int getpacknr(){
        return packnr;
    }

    @Override
    public ObjectType getObjectType() {
        return super.getObjectType();
    }

}
