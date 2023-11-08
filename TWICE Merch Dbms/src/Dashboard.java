import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class Dashboard extends JFrame {
    public JPanel Dashboard;
    private JTable table1;
    private JButton btnCustomers;
    private JButton btnMerch;
    private JButton btnSuppliers;
    private JButton btnExit;
    private JTextField txtSearch;
    private JButton btnTranssup;
    private JButton btntranscus;

    Connection con;
    PreparedStatement pst;

    private void TXT_nameKeyReleased(java.awt.event.KeyEvent evt){
        String searchString = txtSearch.getText();
        search(searchString);
    }

    private void search(String searchString){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/twice_dbms","root","");
            String sql = "SELECT product_ID, productname, quantity, price, sup_ID FROM products_tbl WHERE product_ID LIKE ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,"%" + searchString + "%");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            ((DefaultTableModel)model).setRowCount(0);

            model.setRowCount(0);
            while (rs.next()){
                String prodID = rs.getString("product_ID");
                String prodName = rs.getString("productname");
                String qty = rs.getString("quantity");
                String price = rs.getString("price");
                String supID = rs.getString("sup_ID");
                model.addRow(new Object[]{prodID, prodName, qty, price, supID});
            }
            con.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    void table_load(){
        try{
            pst = con.prepareStatement("select product_ID as 'Product ID', productname as 'Product Name', quantity as 'Quantity', price as 'Price', sup_ID as 'Supplier ID' from products_tbl");
            /*pst = con.prepareStatement("SELECT * FROM products_tbl INNER JOIN supplier_tbl ON products_tbl.sup_ID = supplier_tbl.sup_ID");*/
            ResultSet rs=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Dashboard() {

        Connect1();
        table_load();

        btnMerch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setContentPane(new Merchandise().Merchandise);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        btnSuppliers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                JFrame frame = new JFrame();
                frame.setContentPane(new Supplier().Supplier);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        btnCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setContentPane(new Customer().Customer);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?","Please confirm",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        btnTranssup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    pst = con.prepareStatement("SELECT products_tbl.product_ID, products_tbl.productname, products_tbl.quantity, products_tbl.price, products_tbl.sup_ID, supplier_tbl.suppName, supplier_tbl.location, supplier_tbl.contactNo, supplier_tbl.email FROM products_tbl INNER JOIN supplier_tbl ON products_tbl.sup_ID = supplier_tbl.sup_ID");
                    ResultSet rs=pst.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));

                }catch(SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
        btntranscus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    pst = con.prepareStatement("select products_tbl.product_ID AS 'Product ID', products_tbl.productname AS 'Product Name', products_tbl.quantity AS 'Quantity', products_tbl.price AS 'Price', customer_tbl.transactionid AS 'Transaction ID', customer_tbl.customerid AS 'Customer ID', customer_tbl.firstname AS 'First Name', customer_tbl.lastname AS 'Last Name', customer_tbl.address AS 'Address', customer_tbl.contactno AS 'Contact No.', customer_tbl.email AS 'Email', customer_tbl.dateofpurchase AS 'Date of Purchase' from products_tbl INNER JOIN customer_tbl ON products_tbl.product_ID = customer_tbl.product_ID");
                    ResultSet rs=pst.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));

                }catch(SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                super.keyPressed(e);
                String searchString = txtSearch.getText();
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
                    pst.setString(1,txtSearch.getText());
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
    }

    public static void main(String[] args) {
        Dashboard h = new Dashboard();
        h.setContentPane(new Dashboard().Dashboard);
        h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        h.setExtendedState(JFrame.MAXIMIZED_BOTH);
        h.pack();
        h.setVisible(true);
        h.setLocationRelativeTo(null);
        h.setLayout(null);
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
}
