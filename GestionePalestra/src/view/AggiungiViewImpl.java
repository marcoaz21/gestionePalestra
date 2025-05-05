package view;

import model.CsvManager;
import presenter.AggiungiPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AggiungiViewImpl extends JPanel implements AggiungiView {
    private AggiungiPresenter presenter;
    private JFrame parentFrame;
    
    // Componenti UI
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceField;
    private JComboBox<String> tipoAbbComboBox;
    private JComboBox<String> categoriaAbbComboBox;
    private JLabel scadenzaValueLabel;
    private JButton salvaButton;
    private JButton backButton;

    public AggiungiViewImpl(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Titolo in alto
        JLabel titleLabel = new JLabel("Aggiungi iscritto", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);
        
        // Genera un nuovo codice univoco
        String nuovoCodice = CsvManager.generaCodiceUnivoco();
        
        // Form nel centro
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campi del form
        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);
        
        JLabel cognomeLabel = new JLabel("Cognome:");
        cognomeField = new JTextField(20);
        
        JLabel codiceLabel = new JLabel("Codice Univoco (generato automaticamente):");
        codiceField = new JTextField(20);
        codiceField.setText(nuovoCodice);
        codiceField.setEditable(false); // Rendi il campo non modificabile
        
        // Selezione del tipo di abbonamento
        JLabel tipoAbbLabel = new JLabel("Tipo Abbonamento:");
        tipoAbbComboBox = new JComboBox<>();
        
        // Selezione della categoria di abbonamento
        JLabel categoriaAbbLabel = new JLabel("Categoria Abbonamento:");
        categoriaAbbComboBox = new JComboBox<>();
        
        // Label per mostrare la data di scadenza calcolata
        JLabel scadenzaLabel = new JLabel("Data di Scadenza:");
        scadenzaValueLabel = new JLabel("Seleziona un tipo di abbonamento");
        
        // Aggiorna la data di scadenza quando cambia il tipo di abbonamento
        tipoAbbComboBox.addActionListener(e -> {
            String tipoSelezionato = (String) tipoAbbComboBox.getSelectedItem();
            LocalDate oggi = LocalDate.now();
            LocalDate scadenza;
            
            if (tipoSelezionato != null) {
                if (tipoSelezionato.equals("Mensile")) {
                    scadenza = oggi.plusMonths(1);
                } else { // Annuale
                    scadenza = oggi.plusYears(1);
                }
                
                scadenzaValueLabel.setText(scadenza.toString());
            }
        });
        
        // Aggiungi i componenti al form
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nomeLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(cognomeLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(cognomeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(codiceLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(codiceField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(tipoAbbLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(tipoAbbComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(categoriaAbbLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(categoriaAbbComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(scadenzaLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(scadenzaValueLabel, gbc);
        
        // Bottone per salvare
        salvaButton = new JButton("Salva");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(salvaButton, gbc);
        
        // Azione del bottone salva
        salvaButton.addActionListener(e -> {
            // Validazione dei campi
            if (nomeField.getText().isEmpty() || cognomeField.getText().isEmpty() || 
                codiceField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, 
                    "Tutti i campi sono obbligatori.", 
                    "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Ottieni i valori selezionati
            String tipoAbbonamento = (String) tipoAbbComboBox.getSelectedItem();
            String categoriaAbbonamento = (String) categoriaAbbComboBox.getSelectedItem();
            
            // Aggiungi l'iscritto tramite il presenter
            presenter.aggiungiIscritto(
                nomeField.getText(),
                cognomeField.getText(),
                codiceField.getText(),
                tipoAbbonamento,
                categoriaAbbonamento
            );
        });
        
        add(formPanel, BorderLayout.CENTER);
        
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
    public void mostraMessaggio(String messaggio, String titolo, int tipo) {
        JOptionPane.showMessageDialog(parentFrame, messaggio, titolo, tipo);
    }

    @Override
    public void pulisciForm() {
        nomeField.setText("");
        cognomeField.setText("");
        codiceField.setText(CsvManager.generaCodiceUnivoco());
        tipoAbbComboBox.setSelectedIndex(0);
        categoriaAbbComboBox.setSelectedIndex(0);
    }

    @Override
    public void setPresenter(AggiungiPresenter presenter) {
        this.presenter = presenter;
        
        // Carica i tipi di abbonamento
        tipoAbbComboBox.removeAllItems();
        for (String tipo : presenter.getTipiAbbonamento()) {
            tipoAbbComboBox.addItem(tipo);
        }
        
        // Carica le categorie di abbonamento
        categoriaAbbComboBox.removeAllItems();
        for (String categoria : presenter.getCategorieAbbonamento()) {
            categoriaAbbComboBox.addItem(categoria);
        }
    }
}