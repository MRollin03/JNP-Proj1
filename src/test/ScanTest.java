package Test;
import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import MainFolder.Scan;
import MainFolder.Utils;
import itumulator.world.*;

public class ScanTest {

    private Scan scan;
    private String testFilePath = "data\\test1.txt";
    @Before
   public void setup() {
        int size = 15;
        int delay = 800; // forsi   nkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 600; // sk�rm opl�sningen (i px)
        Utils.newProgram(size, display_size, delay);
        scan = new Scan(testFilePath);
    }

    @Test
    public void testFileProcessing() {
        
        int expectedGrassMin = 8;
        int expectedGrassMax = 15;

        assertTrue(scan.getGrass() >= expectedGrassMin && scan.getGrass() <= expectedGrassMax);
        assertEquals(3, scan.getRabbit());
        assertEquals(2, scan.getBears().size());
        assertEquals(6, scan.getHash().values().stream().mapToInt(Integer::intValue).sum());
        assertEquals(3, scan.getCarcass());
        assertEquals(3, scan.getBerryBush());
        assertEquals(2, scan.getFungi());
    }

    @Test
    public void testRandomRangeData() {

        boolean withinRange = true;
        for (int i = 0; i < 10; i++) {
            Scan scan = new Scan(testFilePath);
            if (scan.getGrass() < 8 || scan.getGrass() > 15) {
                withinRange = false;
                break;
            }
        }
        assertTrue(withinRange);
    }
    @Test
    public void testEmptyFile() {
        Scan emptyScan = new Scan("data\\empy.txt");
        assertEquals(0, emptyScan.getRabbit());
        assertEquals(0, emptyScan.getBears().size());
        assertEquals(0, emptyScan.getHash().size());
        assertEquals(0, emptyScan.getCarcass());
        assertEquals(0, emptyScan.getBerryBush());
        assertEquals(0, emptyScan.getFungi());
    }


    @Test
    public void testwolf() {
        Scan emptyScan = new Scan("data\\test1.txt");

        assertTrue(emptyScan.getHash().containsKey(1));
        assertTrue(emptyScan.getHash().containsKey(2));
        assertTrue(emptyScan.getHash().containsValue(3));
        assertTrue(emptyScan.getHash().containsValue(4));
        //test if hashmap contains value
    }

            @Test
    public void testbear() {
        Scan emptyScan = new Scan("data\\test1.txt");
        assertTrue(emptyScan.getBears().contains(new Location(1, 4)));
        assertTrue(emptyScan.getBears().contains(null));
        //test if list contains value
    }
    
    
    
}
