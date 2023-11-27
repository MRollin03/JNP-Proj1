import itumulator.executable.*;
import itumulator.world.*;
import itumulator.simulator.*;
import itumulator.display.*;
import java.awt.*;
import java.util.*;

public class EnvObject implements NonBlocking {
    Set<Location> neighbours;
    ArrayList<Location> list;
    ObjectType objType;
    protected World world;

    EnvObject(ObjectType objType, World world){
        this.objType = objType;
        this.world = world;
    }

    public void act(World world){
        neighbours = world.getEmptySurroundingTiles();
        list = new ArrayList<>(neighbours);
    }

    public static void deleteObj(World world, Object obj){      //used to remove grass
        world.delete(obj);
    }




    //---------------------- set methods----------------------//
    public void setObjecType(ObjectType type){
        this.objType = type;
    }



    //---------------------- get methods----------------------//
    public ArrayList getLocation(){
        return list;
    }

    public ObjectType getObjectType(){
        return objType;
    }


}
