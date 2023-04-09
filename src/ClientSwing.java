import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClientSwing extends JFrame{
    private DefaultListModel<String> model; // modello della lista
    private JList<String> list; // lista degli oggetti

    public ClientSwing(ArrayList<String> categories) {
        super(); // titolo della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // inizializza il modello della lista con alcuni oggetti di esempio
        model = new DefaultListModel<>();
        model.addAll(categories);

        // crea la lista degli oggetti e la aggiunge alla finestra
        list = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(list);
        getContentPane().add(scrollPane);

        list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(e.getValueIsAdjusting()); 
            }
            
        });

        setSize(250, 200); // dimensioni della finestra
        setLocationRelativeTo(null); // posiziona la finestra al centro dello schermo
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/asta";
        String username = "root";
        String password = "leopoldodinarnia";

        Repository repo = new Repository(url, username, password);

        ArrayList<Item> items = new ArrayList<Item>();

        ArrayList<String> categories = repo.selectCategories();

        // crea e mostra la finestra con la lista di oggetti
        SwingUtilities.invokeLater(() -> new ClientSwing(categories).setVisible(true));
    }
}
