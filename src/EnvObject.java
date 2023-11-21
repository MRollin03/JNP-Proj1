import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;

public class EnvObject implements NonBlocking {
    Set<Location> neighbours;
    ArrayList<Location> list;

    public void act(World world){
        
        neighbours = world.getEmptySurroundingTiles();
        list = new ArrayList<>(neighbours);

    }
    









    //---------------------- get methods----------------------//
    public ArrayList getLocation(){
        return list;
    }


}
