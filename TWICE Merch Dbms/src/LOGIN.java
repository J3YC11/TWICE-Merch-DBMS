import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LOGIN extends JFrame{
    private JPanel LOGIN;
    private JTextField txtUsername;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JButton btnCancel;
    private JLabel showPass;
    private JLabel hidePass;

    public LOGIN() {

        this.hidePass.setVisible(false);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userValue = txtUsername.getText();
                String passValue = txtPass.getText();

                if(userValue.equals("John Carl") && passValue.equals("Diala")){
                    JFrame frame = new JFrame();
                    frame.setContentPane(new Dashboard().Dashboard);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    dispose();}
                else{
                    JOptionPane.showMessageDialog(null,"Wrong Password, try again!");
                    txtPass.setText("");
                    txtUsername.setText("");
                    txtUsername.requestFocus();
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?","Please confirm",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        showPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                hidePass.setVisible(true);
                showPass.setVisible(false);
                txtPass.setEchoChar((char)0);

            }
        });
        hidePass.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                showPass.setVisible(true);
                hidePass.setVisible(false);
                txtPass.setEchoChar('*');
            }
        });
    }

    public static void main(String[] args) {
        LOGIN h = new LOGIN();

        h.setLayout(null);
        h.setVisible(true);
        h.setContentPane(h.LOGIN);
        h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        h.pack();
        h.setLocationRelativeTo(null);
        h.setVisible(true);
    }
}
