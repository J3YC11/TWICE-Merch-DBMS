import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class Supplier extends JFrame {
    public JPanel Supplier;
    private JTable table1;
    private JTextField txtSearch1;
    private JTextField txtID;
    private JTextField txtName;
    private JTextField txtLoc;
    private JTextField txtCon;
    private JTextField txtEmail;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JTextField txtSearch2;
    private JButton btnSearch;

    Connection con;
    PreparedStatement pst;

    private void TXT_nameKeyReleased(java.awt.event.KeyEvent evt){
        String searchString = txtSearch1.getText();
        search(searchString);
    }

    private void search(String searchString){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/twice_dbms","root","");
            String sql = "SELECT Sup_ID, suppName, location, contactNo, email FROM supplier_tbl WHERE sup_ID LIKE ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,"%" + searchString + "%");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            ((DefaultTableModel)model).setRowCount(0);

            model.setRowCount(0);
            while (rs.next()){
                String SupID = rs.getString("sup_ID");
                String SupName = rs.getString("suppName");
                String Location = rs.getString("location");
                String ConNo = rs.getString("contactNo");
                String Email = rs.getString("email");

                model.addRow(new Object[]{SupID, SupName, Location, ConNo, Email});
            }
            con.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    void table_load(){
        try{
            pst = con.prepareStatement("select sup_ID as 'Supplier ID', suppName as 'Supplier Name', location as 'Location', contactNo as 'Contact Number', email as 'Email' from supplier_tbl");
            ResultSet rs=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Supplier() {

        Connect1();
        table_load();

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SupID, SupName, Location, ConNo, Email;
                SupID = txtID.getText();
                SupName = txtName.getText();
                Location = txtLoc.getText();
                ConNo = txtCon.getText();
                Email = txtEmail.getText();

                try {
                    if (txtID.getText().equals("") || txtName.getText().equals("") || txtLoc.getText().equals("") || txtCon.getText().equals("") || txtEmail.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please Enter your Data . . .");
                    } else {
                        pst = con.prepareStatement("insert into supplier_tbl(sup_ID, suppName, location, contactNo, email) values (?,?,?,?,?)");
                        pst.setString(1, SupID);
                        pst.setString(2, SupName);
                        pst.setString(3, Location);
                        pst.setString(4, ConNo);
                        pst.setString(5, Email);

                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Record Added!");
                        table_load();
                        txtID.setText("");
                        txtName.setText("");
                        txtLoc.setText("");
                        txtCon.setText("");
                        txtEmail.setText("");
                        txtID.requestFocus();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Search, SupName, Location, ConNo, Email;
                Search = txtSearch2.getText();
                SupName = txtName.getText();
                Location = txtLoc.getText();
                ConNo = txtCon.getText();
                Email = txtEmail.getText();
                btnSave.setEnabled(true);
                try {
                    pst = con.prepareStatement("Update supplier_tbl set suppName = ?, location = ?, contactNo = ?, email = ? where sup_ID = ?");

                    pst.setString(1, SupName);
                    pst.setString(2, Location);
                    pst.setString(3, ConNo);
                    pst.setString(4, Email);
                    pst.setString(5, Search);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update!");
                    table_load();
                    txtID.setText("");
                    txtName.setText("");
                    txtLoc.setText("");
                    txtCon.setText("");
                    txtEmail.setText("");
                    txtSearch2.setText("");
                    txtID.requestFocus();

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
                    pst=con.prepareStatement("delete from supplier_tbl where sup_ID = ?");
                    pst.setString(1,deleteID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Update!");
                    table_load();
                    txtID.setText("");
                    txtName.setText("");
                    txtLoc.setText("");
                    txtCon.setText("");
                    txtEmail.setText("");
                    txtSearch2.setText("");
                    txtID.requestFocus();

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
                    String sql = "SELECT * FROM supplier_tbl WHERE sup_ID LIKE ? OR suppName LIKE ? OR location LIKE ? OR contactNo LIKE ? OR email LIKE ?";
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
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String Search = txtSearch2.getText();
                    pst=con.prepareStatement("SELECT sup_ID, suppName, location, contactNo, email FROM supplier_tbl WHERE sup_ID = ?");
                    pst.setString(1,Search);
                    ResultSet rs = pst.executeQuery();
                    btnSave.setEnabled(false);

                    if (rs.next()==true){

                        String SupID = rs.getString(1);
                        String SupName = rs.getString(2);
                        String Location = rs.getString(3);
                        String ConCo = rs.getString(4);
                        String Email = rs.getString(5);

                        txtID.setText(SupID);
                        txtName.setText(SupName);
                        txtLoc.setText(Location);
                        txtCon.setText(ConCo);
                        txtEmail.setText(Email);
                        txtSearch2.setText(Search);
                    }
                    else{

                        txtID.setText("");
                        txtName.setText("");
                        txtLoc.setText("");
                        txtCon.setText("");
                        txtEmail.setText("");
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
        JFrame frame = new JFrame("Supplier");
        frame.setContentPane(new Supplier().Supplier);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
