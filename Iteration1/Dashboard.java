import javax.swing.*;
import java.awt.*;

public class Dashboard {
    public static void main(String[]args) {

        JFrame frame = new JFrame("Travel Planner");
        frame.setSize(700,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(15,15));

        JPanel header = new JPanel();
        header.setBackground(new Color(35, 75, 105));
        header.setLayout(new BorderLayout());
        header.setBorder( BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Travel Planner");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        header.add (title, BorderLayout.WEST);

            
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 15, 15));

        buttonPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        JButton  itineraryButton = new JButton("Create Itenerary");
        JButton  hotelButton = new JButton("Find Hotels");
        JButton  flightButton = new JButton("Find Flights");
        JButton  viewItineraryButton = new JButton("View Itenerary");

        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        itineraryButton.setFont(buttonFont);
        hotelButton.setFont(buttonFont);
        flightButton.setFont(buttonFont);
        viewItineraryButton.setFont(buttonFont);

        itineraryButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "iternerary button hit");

    });

        hotelButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "hotel button hit");

    });

        flightButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "flights button hit");

    });

        viewItineraryButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "View button hit");

    });

    buttonPanel.add(itineraryButton);
    buttonPanel.add(hotelButton);
    buttonPanel.add(flightButton);
    buttonPanel.add(viewItineraryButton);

    frame.add(header, BorderLayout.NORTH);
    frame.add(buttonPanel, BorderLayout.CENTER);

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    }


}
