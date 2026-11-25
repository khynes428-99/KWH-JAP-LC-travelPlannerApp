import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class FindHotels {

    public static void showHotels() {

        JFrame frame = new JFrame("Find Hotels");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(15, 15));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(35, 75, 105));
        header.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Find Hotels");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        header.add(title, BorderLayout.WEST);

        // Search form
        JPanel searchPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane resultsScroll = new JScrollPane(resultsArea);

        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Destination
        JLabel destinationLabel = new JLabel("Destination:");
        JTextField destinationField = new JTextField();

        // Check-in date
        JLabel checkInLabel = new JLabel("Check-in Date:");
        JTextField checkInField = new JTextField();
        checkInField.setToolTipText("Example: 2026-12-15");

        // Check-out date
        JLabel checkOutLabel = new JLabel("Check-out Date:");
        JTextField checkOutField = new JTextField();
        checkOutField.setToolTipText("Example: 2026-12-20");

        // Number of guests
        JLabel guestsLabel = new JLabel("Number of Guests:");
        JSpinner guestsSpinner = new JSpinner(
                new SpinnerNumberModel(2, 1, 20, 1));

        searchPanel.add(destinationLabel);
        searchPanel.add(destinationField);

        searchPanel.add(checkInLabel);
        searchPanel.add(checkInField);

        searchPanel.add(checkOutLabel);
        searchPanel.add(checkOutField);

        searchPanel.add(guestsLabel);
        searchPanel.add(guestsSpinner);

        // Search button
        JPanel bottomPanel = new JPanel();

        JButton searchButton = new JButton("Search Hotels");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));

        bottomPanel.add(searchButton);

        // Search action
        searchButton.addActionListener(e -> {

            String destination = destinationField.getText().trim();
            String checkIn = checkInField.getText().trim();
            String checkOut = checkOutField.getText().trim();

            int guests = (int) guestsSpinner.getValue();

            if (destination.isEmpty() ||
                    checkIn.isEmpty() ||
                    checkOut.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please fill in all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }

            JOptionPane.showMessageDialog(
                    frame,
                    "Hotel Search Details:\n\n" +
                            "Destination: " + destination +
                            "\nCheck-in: " + checkIn +
                            "\nCheck-out: " + checkOut +
                            "\nGuests: " + guests ++);
        });
        searchButton.addActionListener(e -> {

            String destination = destinationField.getText().trim();
            String checkIn = checkInField.getText().trim();
            String checkOut = checkOutField.getText().trim();

            int guests = (int) guestsSpinner.getValue();

            if (destination.isEmpty() ||
                    checkIn.isEmpty() ||
                    checkOut.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Please fill in all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }

            // Load hotels from CSV
            ArrayList<Hotel> hotels = Hotel.loadHotels();

            // Clear previous search results
            resultsArea.setText("");

            boolean found = false;

            for (Hotel hotel : hotels) {

                if (hotel.getLocation().toLowerCase()
                        .contains(destination.toLowerCase())) {

                    resultsArea.append(
                            "Hotel: " + hotel.getName() +
                                    "\nLocation: " + hotel.getLocation() +
                                    "\nPrice: $" + hotel.getPrice() +
                                    "\nRating: " + hotel.getRating() +
                                    "\n-----------------------------\n");

                    found = true;
                }
            }

            if (!found) {
                resultsArea.setText("No hotels found in " + destination);
            }
        });

        // Add components
// Add components
frame.add(header, BorderLayout.NORTH);
frame.add(searchPanel, BorderLayout.WEST);
frame.add(resultsScroll, BorderLayout.CENTER);
frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
