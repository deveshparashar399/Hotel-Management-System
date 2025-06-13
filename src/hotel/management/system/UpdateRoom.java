package hotel.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UpdateRoom extends JFrame {
    
    private JPanel contentPane;
    private JTextField txtAvailability;
    private JTextField txtCleanStatus;
    private JTextField txtRoom;
    private Choice customerChoice;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UpdateRoom frame = new UpdateRoom();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UpdateRoom() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(530, 200, 1000, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Update Room Status");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setBounds(85, 20, 250, 30);
        contentPane.add(lblTitle);

        JLabel lblGuestID = new JLabel("Guest ID:");
        lblGuestID.setBounds(30, 80, 100, 25);
        contentPane.add(lblGuestID);

        customerChoice = new Choice();
        customerChoice.setBounds(160, 80, 150, 25);
        contentPane.add(customerChoice);

        try {
            conn c = new conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                customerChoice.add(rs.getString("number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblRoomNumber = new JLabel("Room Number:");
        lblRoomNumber.setBounds(30, 130, 100, 25);
        contentPane.add(lblRoomNumber);

        txtRoom = new JTextField();
        txtRoom.setBounds(160, 130, 150, 25);
        contentPane.add(txtRoom);

        JLabel lblAvailability = new JLabel("Availability:");
        lblAvailability.setBounds(30, 180, 100, 25);
        contentPane.add(lblAvailability);

        txtAvailability = new JTextField();
        txtAvailability.setBounds(160, 180, 150, 25);
        contentPane.add(txtAvailability);

        JLabel lblCleanStatus = new JLabel("Clean Status:");
        lblCleanStatus.setBounds(30, 230, 100, 25);
        contentPane.add(lblCleanStatus);

        txtCleanStatus = new JTextField();
        txtCleanStatus.setBounds(160, 230, 150, 25);
        contentPane.add(txtCleanStatus);

        // Check Button
        JButton btnCheck = new JButton("Check");
        btnCheck.setBounds(50, 300, 100, 30);
        btnCheck.setBackground(Color.BLACK);
        btnCheck.setForeground(Color.WHITE);
        btnCheck.addActionListener(e -> checkRoomStatus());
        contentPane.add(btnCheck);

        // Update Button
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(170, 300, 100, 30);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.addActionListener(e -> updateRoomStatus());
        contentPane.add(btnUpdate);

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(110, 350, 100, 30);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(e -> {
            new Reception().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnBack);

        ImageIcon imgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/seventh.jpg"));
        Image img = imgIcon.getImage().getScaledInstance(550, 250, Image.SCALE_DEFAULT);
        ImageIcon imgResized = new ImageIcon(img);
        JLabel lblImage = new JLabel(imgResized);
        lblImage.setBounds(400, 80, 600, 250);
        contentPane.add(lblImage);
    }

    private void checkRoomStatus() {
        String guestId = customerChoice.getSelectedItem();
        try {
            conn c = new conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE number = '" + guestId + "'");
            if (rs.next()) {
                txtRoom.setText(rs.getString("room")); // use 'room', not 'roomnumber'
            }

            rs = c.s.executeQuery("SELECT * FROM room WHERE roomnumber = '" + txtRoom.getText() + "'");
            if (rs.next()) {
                txtAvailability.setText(rs.getString("availability"));
                txtCleanStatus.setText(rs.getString("cleaning_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateRoomStatus() {
        String roomNumber = txtRoom.getText();
        String availability = txtAvailability.getText();
        String cleaningStatus = txtCleanStatus.getText();
        try {
            conn c = new conn();
            String query = "UPDATE room SET availability = '" + availability + "', cleaning_status = '" + cleaningStatus + "' WHERE roomnumber = '" + roomNumber + "'";
            c.s.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Room Status Updated Successfully");
            new Reception().setVisible(true);
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
