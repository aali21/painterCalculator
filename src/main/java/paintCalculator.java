import java.util.Hashtable;
import java.util.Scanner;


// class to hold quantity and price of each can
class PaintCanInfo {
    int quantity;
    double price;

    public PaintCanInfo(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }
}

public class paintCalculator {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        String room_type = "";
        String ceiling = "";
        Scanner reader = new Scanner(System.in);
        System.out.println("Is the room rectangular (r) or cylindrical (c) ");

        // error handling
        while (true) {
            room_type = reader.next().toLowerCase();
            if (room_type.equals("r") || room_type.equals("rectangular") || room_type.equals("c") || room_type.equals("cylindrical")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'r' for rectangular or 'c' for cylindrical.");
            }
        }

        if (room_type.equals("r") || room_type.equals("rectangular")) {


            // Prompt for length until valid input is provided
            int length = promptForPositiveInt(reader, "Using your tape measure horizontally across the walls of the room," +
                    " what is the entire length of the room (in mm)?");
            // Prompt for height of wall until valid input is provided
            int height = promptForPositiveInt(reader, "What is the height of the wall (in mm)?");
            ;

            // Asking the lengths and heights of windows
            int windowsLength = promptForPositiveInt(reader, "What is the total length of the windows in the room (mm)?");
            int windowsHeight = promptForPositiveInt(reader, "What is the total height of the windows in the room (mm)?");

            // Asking the lengths and heights of doors
            int doorsLength = promptForPositiveInt(reader, "What is the total length of the doors in the room (mm)?");
            int doorsHeight = promptForPositiveInt(reader, "What is the total height of the doors in the room (mm)?");

            int surf_area = length * height;
            // area that needs to be removed (as its doors and windows)
            int deduct_area = (doorsLength * doorsHeight) + (windowsHeight * windowsLength);

            // actual wall surface area needing paint
            int wall_surf_area = surf_area - deduct_area;

            System.out.println("Are you going to be painting the ceiling as well? (y/n)");
            while (true) {
                ceiling = reader.next().toLowerCase();
                if (ceiling.equals("y") || ceiling.equals("yes") || ceiling.equals("n") || ceiling.equals("no")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
                }
            }

            // If the ceiling is being painted, ask for dimensions and calculate its area
            int ceiling_area = 0;
            if (ceiling.equals("yes") || ceiling.equals("y")) {
                int ceilingLength = promptForPositiveInt(reader, "What is the length of the ceiling (in mm)?");
                int ceilingWidth =promptForPositiveInt(reader, "What is the width of the ceiling (in mm)?");
                ceiling_area = ceilingLength * ceilingWidth;
            }

            // Calculate the total surface area that needs painting
            int total_surf_area = wall_surf_area + ceiling_area;


            System.out.println("You have " + total_surf_area + "mm of surface area that actually needs painting (exclusive of windows and doors).");

            // Asking how many coats of paint user is doing
            int coats =promptForPositiveInt(reader, "How many coats of paint are you doing?");

            // Assuming a litre of paint covers 10m^2 of wall
            double coveragePerLitre = 10000; // Coverage per litre in mm
            System.out.println(total_surf_area);
            // Calculate the volume of paint needed
            double volumeOfPaintNeeded = total_surf_area*coats / coveragePerLitre;

            System.out.println("Volume of paint needed: " + volumeOfPaintNeeded + " litres");

            System.out.println("This will cost you £" + volumeOfPaintNeeded*5 + " considering you " +
                    "buy the exact amount of paint and at a price of £5/litre.");


            // Calculate the number of each paint can size needed
            Hashtable<Double, PaintCanInfo> neededCans = calculatePaintCansNeeded(volumeOfPaintNeeded);

            // loop over each of the sizes and output what sizes are needed, the quantity & the price
            neededCans.forEach((size, info) -> {
                System.out.println("Size: " + size + "L, Quantity: " + info.quantity + ", Price per can: £" + info.price);
                System.out.println("Total cost for " + size + "L cans: £" + (info.price * info.quantity));
            });

            }
        }



        //else if (room_type.equals("c")|| room_type.equals("cylindrical")) {

        //}

        // Methods

        // Method for ensuring user enters correct data type when asked to input integers
        private static int promptForPositiveInt(Scanner scanner, String prompt) {
            int result;
            do {
                System.out.println(prompt);
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter an integer value:");
                    scanner.next(); // Discard the invalid input
                }
                result = scanner.nextInt();
                if (result <= 0) {
                    System.out.println("Please enter a positive integer value:");
                }
            } while (result <= 0);
            return result;
        }

        // Method for hashtable
    private static Hashtable<Double, PaintCanInfo> calculatePaintCansNeeded(double volumeOfPaintNeeded) {
        // Define the prices for each can size
        Hashtable<Double, Double> prices = new Hashtable<>();
        prices.put(5.0, 20.0); // £20 for a 5-litre can
        prices.put(2.5, 11.0); // £11 for a 2.5-litre can
        prices.put(1.0, 5.5);  // £5.5 for a 1-litre can
        prices.put(0.5, 3.0);  // £3 for a 0.5-litre can


        // Initialize a Hashtable to store the resulting quantities of each paint can size needed.
        Hashtable<Double, PaintCanInfo> paintCanSizes = new Hashtable<>();
        double[] keys = {5.0, 2.5, 1.0, 0.5}; // Can sizes

        for (double key : keys) {
            int cansNeeded = (int) (volumeOfPaintNeeded / key);
            volumeOfPaintNeeded -= cansNeeded * key;
            double pricePerCan = prices.get(key);
            if (cansNeeded > 0) {
                paintCanSizes.put(key, new PaintCanInfo(cansNeeded, pricePerCan));
            }
        }

//        // After allocating larger cans, check if there's a remaining volume that's more than 0
//        // and less than the smallest can size (0.5 litres), then add one 0.5-litre can.
//        if (volumeOfPaintNeeded > 0 && volumeOfPaintNeeded <= 0.5 ) {
//            // Check if there's already an entry for 0.5L cans and increment, or create a new entry.
//            PaintCanInfo existingHalfLitreInfo = paintCanSizes.getOrDefault(0.5, new PaintCanInfo(0, prices.get(0.5)));
//            existingHalfLitreInfo.quantity += 1; // Add an additional 0.5L can
//            paintCanSizes.put(0.5, existingHalfLitreInfo);
//        }

        return paintCanSizes;
    }


    }

