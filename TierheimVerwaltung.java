
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TierheimVerwaltung extends JFrame {

    private JTable tierTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, artField, alterField, statusField;
    private ArrayList<Tier> tierList;

    public TierheimVerwaltung() {
        tierList = new ArrayList<>();
        
        // Setze Layout und Grundparameter
        setTitle("Tierheim Verwaltung");
        setSize(600, 400);
        setLocationRelativeTo(null);  // Fenster zentrieren
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Erstelle die Tabelle
        String[] columnNames = { "Name", "Art", "Alter", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0);
        tierTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(tierTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Panel für Eingabeformulare
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Art:"));
        artField = new JTextField();
        inputPanel.add(artField);

        inputPanel.add(new JLabel("Alter:"));
        alterField = new JTextField();
        inputPanel.add(alterField);

        inputPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        inputPanel.add(statusField);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons für Aktionen
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tier hinzufügen");
        JButton deleteButton = new JButton("Tier löschen");
        JButton editButton = new JButton("Tier bearbeiten");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // ActionListener für Buttons
        addButton.addActionListener(e -> addTier());
        deleteButton.addActionListener(e -> deleteTier());
        editButton.addActionListener(e -> editTier());

        // Zeige die GUI
        setVisible(true);
    }

    private void addTier() {
        String name = nameField.getText();
        String art = artField.getText();
        String alter = alterField.getText();
        String status = statusField.getText();

        // Füge das Tier zur Liste hinzu
        Tier tier = new Tier(name, art, Integer.parseInt(alter), status);
        tierList.add(tier);

        // Aktualisiere die Tabelle
        updateTable();
        
        // Leere die Eingabefelder
        nameField.setText("");
        artField.setText("");
        alterField.setText("");
        statusField.setText("");
    }

    private void deleteTier() {
        int selectedRow = tierTable.getSelectedRow();
        if (selectedRow != -1) {
            tierList.remove(selectedRow);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Kein Tier ausgewählt!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editTier() {
        int selectedRow = tierTable.getSelectedRow();
        if (selectedRow != -1) {
            Tier tier = tierList.get(selectedRow);

            // Setze die Eingabefelder mit den Daten des Tiers
            nameField.setText(tier.getName());
            artField.setText(tier.getArt());
            alterField.setText(String.valueOf(tier.getAlter()));
            statusField.setText(tier.getStatus());
            
            // Lösche das ausgewählte Tier aus der Liste
            tierList.remove(selectedRow);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Kein Tier ausgewählt!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        // Lösche alle Zeilen in der Tabelle
        tableModel.setRowCount(0);

        // Füge jedes Tier als neue Zeile in die Tabelle ein
        for (Tier tier : tierList) {
            tableModel.addRow(new Object[] { tier.getName(), tier.getArt(), tier.getAlter(), tier.getStatus() });
        }
    }

    // Tier-Klasse zur Modellierung eines Tiers
    class Tier {
        private String name;
        private String art;
        private int alter;
        private String status;

        public Tier(String name, String art, int alter, String status) {
            this.name = name;
            this.art = art;
            this.alter = alter;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public String getArt() {
            return art;
        }

        public int getAlter() {
            return alter;
        }

        public String getStatus() {
            return status;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TierheimVerwaltung::new);
    }
}
