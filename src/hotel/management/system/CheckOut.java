package hotel.management.system;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckOut extends JFrame {
    Connection conn = null;
    PreparedStatement pst = null;
    private JPanel contentPane;
    private JTextField t1, t2, t3;
    Choice c1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CheckOut frame = new CheckOut();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CheckOut() throws SQLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(530, 200, 900, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/sixth.jpg"));
        Image i3 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i3);
        JLabel l1 = new JLabel(i2);
        l1.setBounds(400, 0, 500, 300);
        add(l1);

        JLabel lblCheckOut = new JLabel("Check Out");
        lblCheckOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblCheckOut.setBounds(70, 11, 140, 35);
        contentPane.add(lblCheckOut);

        JLabel lblName = new JLabel("Customer Number:");
        lblName.setBounds(20, 85, 120, 14);
        contentPane.add(lblName);

        c1 = new Choice();
        try {
            conn c = new conn();
            ResultSet rs = c.s.executeQuery("select * from customer");
            while (rs.next()) {
                c1.add(rs.getString("number"));
            }
        } catch (Exception e) { }
        c1.setBounds(150, 82, 150, 20);
        contentPane.add(c1);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/tick.png"));
        Image i5 = i4.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JButton l2 = new JButton(i6);
        l2.setBounds(310, 82, 20, 20);
        add(l2);

        l2.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
        try {
            conn c = new conn();
            String number = c1.getSelectedItem(); // selected number
            ResultSet rs = c.s.executeQuery("select * from customer where number = '"+number+"'");
            
            if (rs.next()) {
                t1.setText(rs.getString("room"));     // room number
                t2.setText(rs.getString("checkintime"));    // check-in time
                // set checkout time as now
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String currentTime = formatter.format(date);
                t3.setText(currentTime);                     // check-out time
            }
        } catch (Exception e) { 
            e.printStackTrace();
        }
        }
    });


        JLabel lblRoomNumber = new JLabel("Room Number:");
        lblRoomNumber.setBounds(20, 132, 120, 20);
        contentPane.add(lblRoomNumber);

        t1 = new JTextField();
        t1.setBounds(150, 132, 150, 20);
        contentPane.add(t1);

        JLabel lblCheckIn = new JLabel("Check-In Time:");
        lblCheckIn.setBounds(20, 172, 120, 20);
        contentPane.add(lblCheckIn);

        t2 = new JTextField();
        t2.setBounds(150, 172, 150, 20);
        contentPane.add(t2);

        JLabel lblCheckOutTime = new JLabel("Check-Out Time:");
        lblCheckOutTime.setBounds(20, 212, 120, 20);
        contentPane.add(lblCheckOutTime);

        t3 = new JTextField();
        t3.setBounds(150, 212, 150, 20);
        contentPane.add(t3);

        JButton btnCheckOut = new JButton("Check Out");
        btnCheckOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = c1.getSelectedItem();
                String room = t1.getText();
                String checkin = t2.getText();
                String checkout = t3.getText();

                conn c = new conn();

                try {
                    // Save checkout history
                    String insertHistory = "insert into checkout_history (customer_number, room_number, check_in_time, check_out_time) values ('"+id+"', '"+room+"', '"+checkin+"', '"+checkout+"')";
                    c.s.executeUpdate(insertHistory);

                    // Delete customer
                    String deleteCustomer = "delete from customer where number = '"+id+"'";
                    c.s.executeUpdate(deleteCustomer);

                    // Update room availability
                    String updateRoom = "update room set availability = 'Available' where roomnumber = '"+room+"'";
                    c.s.executeUpdate(updateRoom);

                    JOptionPane.showMessageDialog(null, "Check Out Successful");
                    new Reception().setVisible(true);
                    setVisible(false);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnCheckOut.setBounds(50, 270, 120, 30);
        btnCheckOut.setBackground(Color.BLACK);
        btnCheckOut.setForeground(Color.WHITE);
        contentPane.add(btnCheckOut);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Reception().setVisible(true);
                setVisible(false);
            }
        });
        btnBack.setBounds(190, 270, 120, 30);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        contentPane.add(btnBack);

        getContentPane().setBackground(Color.WHITE);
    }
}
