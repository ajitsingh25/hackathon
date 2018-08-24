package conn;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

/** @see http://stackoverflow.com/a/3002830/230513 */
public class NewClass extends JOptionPane{
    
    public NewClass(String originalUrl, String redirectedUrl, int numberOfParams, ArrayList<String> params, boolean isRedirect){
        this.originalUrl = originalUrl;
        this.redirectedUrl = redirectedUrl;
        this.numberOfParams = numberOfParams;
        this.params = params;
        this.isRedirect = isRedirect;
    }
    
    String originalUrl, redirectedUrl;
    ArrayList<String> params;
    int numberOfParams;
    boolean isRedirect = false;
    public ArrayList<String> display() {
        JLabel message;
        ArrayList<JTextField> fields = new ArrayList<>();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        if(isRedirect){
            message= new JLabel("Your page "+originalUrl+" is being redirected to "+redirectedUrl+"\nAnd this page requires login details.\nPlease provive the same.");
            
        }
        else{
             message= new JLabel("Your page requires login details.\nPlease provive the same.");
        }
        panel.add(message);
        for(String param : params){
            JTextField field = new JTextField();
            fields.add(field);
            panel.add(new JLabel(param));
            panel.add(field);
        }
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        ArrayList<String> paramValues = null;
        if (result == JOptionPane.OK_OPTION) {
            paramValues = new ArrayList<String>();
            for(JTextField jt : fields){
                paramValues.add(jt.getText().trim());
            }
            
        } else {
            System.out.println("Cancelled");
        }
        return paramValues;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                 ArrayList<String> al = new ArrayList<String>();
                 al.add("uname");al.add("password");
                new NewClass("abc", "xyz", 2, al, true).display();
            }
        });
    }
}