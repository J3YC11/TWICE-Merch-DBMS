import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Merchandise extends JFrame {
    public JPanel Merchandise;
    private JTextField txtProid;
    private JTextField txtName;
    private JTextField txtPrice;
    private JTextField txtQty;
    private JButton btnSearch;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JTextField txtSearch2;
    private JButton btnSave;
    private JTable table1;
    private JTextField txtSearch1;
    private JTextField txtSupid;

    Connection con;
    PreparedStatement pst;

    private void TXT_nameKeyReleased(java.awt.event.KeyEvent evt) {
        String searchString = txtSearch1.getText();
        search(searchString);
    }

    private void search(String searchString) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/twice_dbms", "root", "");
            String sql = "SELECT product_ID, productname, quantity, price, sup_ID FROM products_tbl WHERE product_ID LIKE ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + searchString + "%");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            ((DefaultTableModel) model).setRowCount(0);

            model.setRowCount(0);
            while (rs.next()) {
                String prodID = rs.getString("product_ID");
                String prodName = rs.getString("productname");
                String qty = rs.getString("quantity");
                String price = rs.getString("price");
                String supID = rs.getString("sup_ID");
                model.addRow(new Object[]{prodID, prodName, qty, price, supID});
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void table_load() {
        try {
            pst = con.prepareStatement("select product_ID as 'Product ID', productname as 'Product Name', quantity as 'Quantity', price as 'Price', sup_ID as 'Supplier ID' from products_tbl");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Merchandise() {
        Connect1();
        table_load();

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prodID, prodName, qty, price, supID;
                prodID = txtProid.getText();
                prodName = txtName.getText();
                qty = txtQty.getText();
                price = txtPrice.getText();
                supID = txtSupid.getText();

                try {
                    if (txtProid.getText().equals("") || txtName.getText().equals("") || txtQty.getText().equals("") || txtPrice.getText().equals("") || txtSupid.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please Enter your Data . . .");
                    } else {
                        pst = con.prepareStatement("insert into products_tbl(product_ID, productname, quantity, price, sup_ID) values (?,?,?,?,?)");
                        pst.setString(1, prodID);
                        pst.setString(2, prodName);
                        pst.setString(3, qty);
                        pst.setString(4, price);
                        pst.setString(5,supID);

                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Record Added!");
                        table_load();
                        txtProid.setText("");
                        txtName.setText("");
                        txtQty.setText("");
                        txtPrice.setText("");
                        txtSupid.setText("");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        txtSearch1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                super.keyPressed(e);
                String searchString = txtSearch1.getText();
                search(searchString);
                try{
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/twice_dbms","root","");
                    String sql = "SELECT * FROM products_tbl WHERE product_ID LIKE ? OR productname LIKE ? OR quantity LIKE ? OR price LIKE ? OR sup_ID LIKE ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1,"%" + searchString + "%");
                    pst.setString(2,"%" + searchString + "%");
                    pst.setString(3,"%" + searchString + "%");
                    pst.setString(4,"%" + searchString + "%");
                    pst.setString(5,"%" + searchString + "%");
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
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search, prodName, qty, price, supID;
                search = txtSearch2.getText();
                prodName = txtName.getText();
                qty = txtQty.getText();
                price = txtPrice.getText();
                supID = txtSupid.getText();
                btnSave.setEnabled(true);
                try {
                    pst = con.prepareStatement("Update products_tbl set productname = ?, quantity = ?, price = ?, sup_ID = ? where product_ID = ?");
                    pst.setString(1, prodName);
                    pst.setString(2, qty);
                    pst.setString(3, price);
                    pst.setString(4, supID);
                    pst.setString(5, search);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update!");
                    table_load();
                    txtProid.setText("");
                    txtName.setText("");
                    txtQty.setText("");
                    txtPrice.setText("");
                    txtSupid.setText("");
                    txtSearch2.setText("");
                    txtProid.requestFocus();


                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String Search = txtSearch2.getText();
                    pst=con.prepareStatement("SELECT product_ID, productname, quantity, price, sup_ID FROM products_tbl WHERE product_ID = ?");
                    pst.setString(1,Search);
                    ResultSet rs = pst.executeQuery();
                    btnSave.setEnabled(false);

                    if (rs.next()==true){

                        String prodID = rs.getString(1);
                        String prodName = rs.getString(2);
                        String qty = rs.getString(3);
                        String price = rs.getString(4);
                        String supID = rs.getString(5);

                        txtProid.setText(prodID);
                        txtName.setText(prodName);
                        txtQty.setText(qty);
                        txtPrice.setText(price);
                        txtSupid.setText(supID);
                        txtSearch2.setText(Search);
                    }
                    else{

                        txtProid.setText("");
                        txtName.setText("");
                        txtQty.setText("");
                        txtPrice.setText("");
                        txtSupid.setText("");
                        txtSearch2.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID...");
                    }
                }catch (SQLException e1){
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
                    pst=con.prepareStatement("delete from products_tbl where product_ID = ?");
                    pst.setString(1,deleteID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Update!");
                    table_load();
                    txtProid.setText("");
                    txtName.setText("");
                    txtQty.setText("");
                    txtPrice.setText("");
                    txtSupid.setText("");
                    txtSearch2.setText("");
                    txtProid.requestFocus();

                } catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Merchandise");
        frame.setContentPane(new Merchandise().Merchandise);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
    public void Connect1() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/twice_dbms", "root", "");
            System.out.println("success . . .");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
