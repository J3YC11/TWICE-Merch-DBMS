import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class Customer extends JFrame {
    public JPanel Customer;
    private JTextField txtTransid;
    private JTextField txtCustomid;
    private JTextField txtFirstname;
    private JTextField txtLastname;
    private JTextField txtAdd;
    private JTextField txtContact;
    private JTextField txtEmail;
    private JTextField txtDop;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JTextField txtSearch2;
    private JTable table1;
    private JTextField txtSearch1;
    private JButton btnSearch;
    private JTextField txtProdid;

    Connection con;
    PreparedStatement pst;

    private void TXT_nameKeyReleased(java.awt.event.KeyEvent evt){
        String searchString = txtSearch1.getText();
        search(searchString);
    }

    private void search(String searchString){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/twice_dbms","root","");
            String sql = "SELECT transactionid, customerid, firstname, lastname, address, contactno, email, dateofpurchase, product_ID FROM customer_tbl WHERE transactionid LIKE ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,"%" + searchString + "%");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            ((DefaultTableModel)model).setRowCount(0);

            model.setRowCount(0);
            while (rs.next()){
                String transacID = rs.getString("transactionid");
                String cusname = rs.getString("customerid");
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String add = rs.getString("address");
                String connum = rs.getString("contactno");
                String email = rs.getString("email");
                String dop = rs.getString("dateofpurchase");
                String prodid = rs.getString("product_ID");

                model.addRow(new Object[]{transacID, cusname, fname, lname, add, connum, email, dop, prodid});
            }
            con.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    void table_load(){
        try{
            pst = con.prepareStatement("select transactionid as 'Transaction ID', customerid as 'Customer ID', firstname as 'First Name', lastname as 'Last Name', address as 'Address', contactno as 'Contact No.', email as 'Email', dateofpurchase as 'Date of Purchase', product_ID as 'Product ID' from customer_tbl");
            ResultSet rs=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Customer() {

        Connect1();
        table_load();

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String transID, customID, fname, lname, add, contact, email, dop, prodid;
                transID = txtTransid.getText();
                customID = txtCustomid.getText();
                fname = txtFirstname.getText();
                lname = txtLastname.getText();
                add = txtAdd.getText();
                contact = txtContact.getText();
                email = txtEmail.getText();
                dop = txtDop.getText();
                prodid = txtProdid.getText();

                try {
                    if (txtTransid.getText().equals("") || txtCustomid.getText().equals("") || txtFirstname.getText().equals("") || txtLastname.getText().equals("") || txtAdd.getText().equals("") || txtContact.getText().equals("") || txtEmail.getText().equals("") || txtDop.getText().equals("") || txtProdid.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please Enter your Data . . .");
                    } else {
                        pst = con.prepareStatement("insert into customer_tbl(transactionid, customerid, firstname, lastname, address, contactno, email, dateofpurchase, product_ID) values (?,?,?,?,?,?,?,?,?)");
                        pst.setString(1, transID);
                        pst.setString(2, customID);
                        pst.setString(3, fname);
                        pst.setString(4, lname);
                        pst.setString(5, add);
                        pst.setString(6, contact);
                        pst.setString(7, email);
                        pst.setString(8, dop);
                        pst.setString(9, prodid);

                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Record Added!");
                        table_load();
                        txtTransid.setText("");
                        txtCustomid.setText("");
                        txtFirstname.setText("");
                        txtLastname.setText("");
                        txtAdd.setText("");
                        txtContact.setText("");
                        txtEmail.setText("");
                        txtDop.setText("");
                        txtProdid.setText("");
                        txtTransid.requestFocus();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String transID, customID, fname, lname, add, contact, email, dop, prodid;
                transID = txtTransid.getText();
                customID = txtCustomid.getText();
                fname = txtFirstname.getText();
                lname = txtLastname.getText();
                add = txtAdd.getText();
                contact = txtContact.getText();
                email = txtEmail.getText();
                dop = txtDop.getText();
                prodid = txtProdid.getText();

                try {
                    pst = con.prepareStatement("Update customer_tbl set firstname = ?, lastname = ?, address = ?, contactno = ?, email = ?, dateofpurchase = ?, product_ID = ? where transactionid = ?");

                    pst.setString(1, transID);
                    pst.setString(2, customID);
                    pst.setString(3, fname);
                    pst.setString(4, lname);
                    pst.setString(5, add);
                    pst.setString(6, contact);
                    pst.setString(7, email);
                    pst.setString(8, dop);
                    pst.setString(9, prodid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update!");
                    table_load();
                    txtTransid.setText("");
                    txtCustomid.setText("");
                    txtFirstname.setText("");
                    txtLastname.setText("");
                    txtAdd.setText("");
                    txtContact.setText("");
                    txtEmail.setText("");
                    txtDop.setText("");
                    txtProdid.setText("");
                    txtTransid.requestFocus();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deleteID;
                deleteID = txtSearch2.getText();
                btnSave.setEnabled(true);
                try{
                    pst=con.prepareStatement("delete from customer_tbl where transactionid = ?");
                    pst.setString(1,deleteID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Update!");
                    table_load();
                    txtTransid.setText("");
                    txtCustomid.setText("");
                    txtFirstname.setText("");
                    txtLastname.setText("");
                    txtAdd.setText("");
                    txtContact.setText("");
                    txtEmail.setText("");
                    txtDop.setText("");
                    txtProdid.setText("");
                    txtSearch2.setText("");
                    txtTransid.requestFocus();

                } catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
        txtSearch1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                super.keyReleased(e);

                String searchString = txtSearch1.getText();
                search(searchString);
                try{
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/twice_dbms","root","");
                    String sql = "SELECT * FROM customer_tbl WHERE transactionid LIKE ? OR customerid LIKE ? OR firstname LIKE ? OR lastname LIKE ? OR address LIKE ? OR contactno LIKE ? OR email LIKE ? OR dateofpurchase LIKE ? OR product_ID LIKE ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1,"%" + searchString + "%");
                    pst.setString(2,"%" + searchString + "%");
                    pst.setString(3,"%" + searchString + "%");
                    pst.setString(4,"%" + searchString + "%");
                    pst.setString(5,"%" + searchString + "%");
                    pst.setString(6,"%" + searchString + "%");
                    pst.setString(7,"%" + searchString + "%");
                    pst.setString(8,"%" + searchString + "%");
                    pst.setString(9,"%" + searchString + "%");
                    pst.setString(1,txtSearch1.getText());
                    ResultSet rs = pst.executeQuery();
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    ((DefaultTableModel)model).setRowCount(0);

                    table1.setModel(DbUtils.resultSetToTableModel(rs));
                    con.close();

                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String Search = txtSearch2.getText();
                    pst=con.prepareStatement("SELECT transactionid, customerid, firstname, lastname, address, contactno, email, dateofpurchase, product_ID FROM customer_tbl WHERE transactionid = ?");
                    pst.setString(1,Search);
                    ResultSet rs = pst.executeQuery();
                    btnSave.setEnabled(false);

                    if (rs.next()==true){

                        String transid = rs.getString(1);
                        String customid = rs.getString(2);
                        String fname = rs.getString(3);
                        String lname = rs.getString(4);
                        String add = rs.getString(5);
                        String connum = rs.getString(6);
                        String email = rs.getString(7);
                        String dop = rs.getString(8);
                        String prodid = rs.getString(9);

                        txtTransid.setText(transid);
                        txtCustomid.setText(customid);
                        txtFirstname.setText(fname);
                        txtLastname.setText(lname);
                        txtAdd.setText(add);
                        txtContact.setText(connum);
                        txtEmail.setText(email);
                        txtDop.setText(dop);
                        txtProdid.setText(prodid);
                        txtSearch2.setText(Search);
                    }
                    else{

                        txtTransid.setText("");
                        txtCustomid.setText("");
                        txtFirstname.setText("");
                        txtLastname.setText("");
                        txtAdd.setText("");
                        txtContact.setText("");
                        txtEmail.setText("");
                        txtDop.setText("");
                        txtProdid.setText("");
                        txtSearch2.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Supplier ID...");
                    }
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public void Connect1(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/twice_dbms","root","");
            System.out.println("success . . .");

        } catch (ClassNotFoundException ex ){
            ex.printStackTrace();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Customer");
        frame.setContentPane(new Customer().Customer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
