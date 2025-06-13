package hotel.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UpdateCheck extends JFrame {
    Connection conn = null;
    private JPanel contentPane;
    private JTextField txt_ID;
    private JTextField txt_Name;
    private JTextField txt_CheckinTime;
    private JTextField txt_Deposit;
    private JTextField txt_PendingAmount;
    Choice c1;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UpdateCheck frame = new UpdateCheck();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UpdateCheck() throws SQLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 200, 950, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Check-In Details");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTitle.setBounds(124, 11, 222, 25);
        contentPane.add(lblTitle);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/nine.jpg"));
        JLabel l1 = new JLabel(i1);
        l1.setBounds(450, 70, 476, 270);
        add(l1);

        JLabel lblID = new JLabel("ID:");
        lblID.setBounds(25, 88, 46, 14);
        contentPane.add(lblID);

        c1 = new Choice();
        try {
            conn c = new conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                c1.add(rs.getString("number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        c1.setBounds(248, 85, 140, 20);
        contentPane.add(c1);

        JLabel lblRoomNumber = new JLabel("Room Number:");
        lblRoomNumber.setBounds(25, 129, 107, 14);
        contentPane.add(lblRoomNumber);

        txt_ID = new JTextField();
        txt_ID.setBounds(248, 126, 140, 20);
        contentPane.add(txt_ID);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(25, 174, 97, 14);
        contentPane.add(lblName);

        txt_Name = new JTextField();
        txt_Name.setBounds(248, 171, 140, 20);
        contentPane.add(txt_Name);

        JLabel lblCheckin = new JLabel("Checked-in Time:");
        lblCheckin.setBounds(25, 216, 107, 14);
        contentPane.add(lblCheckin);

        txt_CheckinTime = new JTextField();
        txt_CheckinTime.setBounds(248, 216, 140, 20);
        contentPane.add(txt_CheckinTime);

        JLabel lblDeposit = new JLabel("Amount Paid (Rs):");
        lblDeposit.setBounds(25, 261, 107, 14);
        contentPane.add(lblDeposit);

        txt_Deposit = new JTextField();
        txt_Deposit.setBounds(248, 258, 140, 20);
        contentPane.add(txt_Deposit);

        JLabel lblPending = new JLabel("Pending Amount (Rs):");
        lblPending.setBounds(25, 302, 150, 14);
        contentPane.add(lblPending);

        txt_PendingAmount = new JTextField();
        txt_PendingAmount.setBounds(248, 299, 140, 20);
        contentPane.add(txt_PendingAmount);

        JButton btnCheck = new JButton("Check");
        btnCheck.addActionListener(e -> {
            try {
                String id = c1.getSelectedItem();
                conn c = new conn();
                ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE number = '" + id + "'");
                if (rs.next()) {
                    txt_ID.setText(rs.getString("room"));
                    txt_Name.setText(rs.getString("name"));
                    txt_CheckinTime.setText(rs.getString("checkintime"));
                    txt_Deposit.setText(rs.getString("deposit"));
                }

                // Calculate pending amount
                String room = txt_ID.getText();
                ResultSet rs2 = c.s.executeQuery("SELECT * FROM room WHERE roomnumber = '" + room + "'");
                if (rs2.next()) {
                    String price = rs2.getString("price");
                    int pending = Integer.parseInt(price) - Integer.parseInt(txt_Deposit.getText());
                    txt_PendingAmount.setText(Integer.toString(pending));
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        });
        btnCheck.setBounds(56, 378, 89, 23);
        btnCheck.setBackground(Color.BLACK);
        btnCheck.setForeground(Color.WHITE);
        contentPane.add(btnCheck);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> {
            try {
                conn c = new conn();
                String id = c1.getSelectedItem();
                String room = txt_ID.getText();
                String name = txt_Name.getText();
                String checkin = txt_CheckinTime.getText();
                String deposit = txt_Deposit.getText();

                c.s.executeUpdate("UPDATE customer SET room = '" + room + "', name = '" + name + "', checkintime = '" + checkin + "', deposit = '" + deposit + "' WHERE number = '" + id + "'");

                JOptionPane.showMessageDialog(null, "Data Updated Successfully");
                new Reception().setVisible(true);
                setVisible(false);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        });
        btnUpdate.setBounds(168, 378, 89, 23);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        contentPane.add(btnUpdate);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            new Reception().setVisible(true);
            setVisible(false);
        });
        btnBack.setBounds(281, 378, 89, 23);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        contentPane.add(btnBack);

        getContentPane().setBackground(Color.WHITE);
    }
}
