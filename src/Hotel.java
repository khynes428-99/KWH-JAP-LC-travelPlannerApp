
    
import java.io.*;
import java.util.ArrayList;

public class Hotel {
    private int id;
    private String name;
    private String location;
    private double price;
    private double rating;

    private static final String FILE_PATH = "data/hotels.csv";

    // Constructor
    public Hotel(int id, String name, String location, double price, double rating) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.rating = rating;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    // READ: Load hotels from CSV
    public static ArrayList<Hotel> loadHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            reader.readLine(); // Skip CSV header

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String location = data[2];
                double price = Double.parseDouble(data[3]);
                double rating = Double.parseDouble(data[4]);

                hotels.add(new Hotel(id, name, location, price, rating));
            }

        } catch (IOException e) {
            System.out.println("Error reading hotels: " + e.getMessage());
        }

        return hotels;
    }

    // CREATE: Add a hotel to CSV
    public static void addHotel(Hotel hotel) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(hotel.id + "," + hotel.name + "," + hotel.location + "," 
                    + hotel.price + "," + hotel.rating);
            writer.newLine();

            System.out.println("Hotel added successfully!");

        } catch (IOException e) {
            System.out.println("Error adding hotel: " + e.getMessage());
        }
    }

    // UPDATE: Update an existing hotel
    public static void updateHotel(Hotel updatedHotel) {
        ArrayList<Hotel> hotels = loadHotels();

        for (int i = 0; i < hotels.size(); i++) {
            if (hotels.get(i).id == updatedHotel.id) {
                hotels.set(i, updatedHotel);
                break;
            }
        }

        saveHotels(hotels);
    }

    // DELETE: Remove a hotel by ID
    public static void deleteHotel(int id) {
        ArrayList<Hotel> hotels = loadHotels();

        hotels.removeIf(hotel -> hotel.id == id);

        saveHotels(hotels);
    }

    // Save all hotels back to CSV
    private static void saveHotels(ArrayList<Hotel> hotels) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("id,name,location,price,rating");
            writer.newLine();

            for (Hotel hotel : hotels) {
                writer.write(hotel.id + "," + hotel.name + "," + hotel.location + "," 
                        + hotel.price + "," + hotel.rating);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving hotels: " + e.getMessage());
        }
    }
}


