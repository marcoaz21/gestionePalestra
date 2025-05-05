package view;

import model.iscritto;
import presenter.StampaPresenter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class StampaViewImpl extends JPanel implements StampaView {
    private StampaPresenter presenter;
    private JFrame parentFrame;
    
    // Componenti UI
    private JTable iscrittiTable;
    private DefaultTableModel tableModel;
    private JButton backButton;

    public StampaViewImpl(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Titolo in alto
        JLabel titleLabel = new JLabel("Elenco Iscritti", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);
        
        // Tabella nel centro
        String[] columnNames = {"Nome", "Cognome", "Codice Univoco", "Abbonamento Attivo", "Tipo", "Categoria", "Scadenza"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendi la tabella non modificabile
            }
        };
        
        iscrittiTable = new JTable(tableModel);
        iscrittiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        iscrittiTable.setRowHeight(25);
        
        // Renderer personalizzato per le celle
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < iscrittiTable.getColumnCount(); i++) {
            iscrittiTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(iscrittiTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottone per tornare indietro
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Torna al menu");
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    @Override
    public void mostraIscritti(List<iscritto> iscritti) {
        // Pulisci la tabella
        tableModel.setRowCount(0);
        
        // Verifica che la lista degli iscritti non sia vuota
        if (iscritti == null || iscritti.isEmpty()) {
            return;
        }
        
        // Aggiungi gli iscritti alla tabella
        for (iscritto is : iscritti) {
            // Aggiorna lo storico prima di visualizzare
            is.aggiornaStorico();
            
            Object[] row = new Object[7];
            row[0] = is.getNome();
            row[1] = is.getCognome();
            row[2] = is.getCodice_Uni();
            row[3] = is.getAbb_Attivo();
            row[4] = is.getTipoAbbonamento();
            row[5] = is.getCategoriaAbbonamento();
            row[6] = is.getDataScadenzaFormattata();
            
            tableModel.addRow(row);
        }
    }

    @Override
    public void mostraMessaggio(String messaggio, String titolo, int tipo) {
        JOptionPane.showMessageDialog(parentFrame, messaggio, titolo, tipo);
    }

    @Override
    public void setPresenter(StampaPresenter presenter) {
        this.presenter = presenter;
    }
}