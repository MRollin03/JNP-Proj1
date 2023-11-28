package MainFolder;
import java.util.Scanner;

public class scan {
    private int size;
    private int grass;
    private int rabbit;
    private int burrow;

    public scan() {
        scanner();
    }

    private void scanner() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter amount and size in this order. Size, Grass, Rabbit and Burrow. seperated with nothing but a comma");
        String input = scanner.nextLine();

        // splits into parts with , as splitter
        String[] parts = input.split(",");


        // assigns the parts that were splitted into variable
        this.size = Integer.parseInt(parts[0].trim());
        this.grass = Integer.parseInt(parts[1].trim());
        this.rabbit = Integer.parseInt(parts[2].trim());
        this.burrow = Integer.parseInt(parts[3].trim());


        // used for debugging
        System.out.println(size);
        System.out.println(grass);
        System.out.println(rabbit);
        System.out.println(burrow);
    }
        // Getter for size
        public int getSize() {
            return size;
        }
    
        // Getter for grass
        public int getGrass() {
            return grass;
        }
    
        // Getter for rabbit
        public int getRabbit() {
            return rabbit;
        }
    
        // Getter for burrow
        public int getBurrow() {
            return burrow;
        }
}
