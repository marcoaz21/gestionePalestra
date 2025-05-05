package view;

import model.iscritto;
import presenter.ModificaPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class ModificaViewImpl extends JPanel implements ModificaView {
    private ModificaPresenter presenter;
    private JFrame parentFrame;
    
    // Componenti UI
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceField;
    private JComboBox<String> tipoAbbComboBox;
    private JComboBox<String> categoriaAbbComboBox;
    private JLabel scadenzaValueLabel;
    private JLabel statoValueLabel;
    private JTextArea storicoField;
    private JButton salvaButton;
    private JButton backButton;
    
    // Iscritto corrente
    private iscritto currentIscritto;

    public ModificaViewImpl(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Titolo in alto
        JLabel titleLabel = new JLabel("Modifica iscritto", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);
        
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
        
        JLabel codiceLabel = new JLabel("Codice Univoco:");
        codiceField = new JTextField(20);
        codiceField.setEditable(false); // Rendi il campo non modificabile
        
        JLabel statoLabel = new JLabel("Stato Abbonamento:");
        statoValueLabel = new JLabel();
        
        // Selezione del tipo di abbonamento
        JLabel tipoAbbLabel = new JLabel("Tipo Abbonamento:");
        tipoAbbComboBox = new JComboBox<>();
        
        // Selezione della categoria di abbonamento
        JLabel categoriaAbbLabel = new JLabel("Categoria Abbonamento:");
        categoriaAbbComboBox = new JComboBox<>();
        
        // Label per mostrare la data di scadenza
        JLabel scadenzaLabel = new JLabel("Data di Scadenza:");
        scadenzaValueLabel = new JLabel();
        
        // Campo per lo storico
        JLabel storicoLabel = new JLabel("Storico Abbonamenti:");
        storicoField = new JTextArea(5, 20);
        storicoField.setEditable(false);
        JScrollPane storicoScrollPane = new JScrollPane(storicoField);
        
        // Aggiorna la data di scadenza quando cambia il tipo di abbonamento
        tipoAbbComboBox.addActionListener(e -> {
            if (currentIscritto != null) {
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
        formPanel.add(statoLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(statoValueLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(tipoAbbLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(tipoAbbComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(categoriaAbbLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(categoriaAbbComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(scadenzaLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(scadenzaValueLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(storicoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(storicoScrollPane, gbc);
        
        // Pannello per i bottoni di azione
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        salvaButton = new JButton("Salva Modifiche");
        
        actionPanel.add(salvaButton);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(actionPanel, gbc);
        
        // Azione del bottone salva
        salvaButton.addActionListener(e -> {
            if (currentIscritto != null) {
                // Validazione dei campi
                if (nomeField.getText().isEmpty() || cognomeField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, 
                        "Nome e cognome sono obbligatori.", 
                        "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Ottieni i valori selezionati
                String tipoAbbonamento = (String) tipoAbbComboBox.getSelectedItem();
                String categoriaAbbonamento = (String) categoriaAbbComboBox.getSelectedItem();
                
                // Modifica l'iscritto tramite il presenter
                presenter.modificaIscritto(
                    currentIscritto,
                    nomeField.getText(),
                    cognomeField.getText(),
                    tipoAbbonamento,
                    categoriaAbbonamento
                );
            }
        });
        
        // Il pulsante "Aggiungi Abbonamento" è stato rimosso
        
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
    public void mostraDettagliIscritto(iscritto iscritto) {
        this.currentIscritto = iscritto;
        
        // Aggiorna i campi con i dati dell'iscritto
        nomeField.setText(iscritto.getNome());
        cognomeField.setText(iscritto.getCognome());
        codiceField.setText(iscritto.getCodice_Uni());
        
        // Aggiorna lo stato dell'abbonamento
        statoValueLabel.setText(iscritto.getAbb_Attivo().equals("SI") ? "Attivo" : "Non attivo");
        statoValueLabel.setForeground(iscritto.getAbb_Attivo().equals("SI") ? Color.GREEN.darker() : Color.RED);
        
        // Seleziona il tipo di abbonamento
        for (int i = 0; i < tipoAbbComboBox.getItemCount(); i++) {
            if (tipoAbbComboBox.getItemAt(i).equals(iscritto.getTipoAbbonamento())) {
                tipoAbbComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        // Seleziona la categoria di abbonamento
        for (int i = 0; i < categoriaAbbComboBox.getItemCount(); i++) {
            if (categoriaAbbComboBox.getItemAt(i).equals(iscritto.getCategoriaAbbonamento())) {
                categoriaAbbComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        // Aggiorna la data di scadenza
        scadenzaValueLabel.setText(iscritto.getDataScadenzaFormattata());
        
        // Aggiorna lo storico
        storicoField.setText(iscritto.getStorico());
        
        // Mostra gli abbonamenti attivi
        List<iscritto.Abbonamento> abbonamentiAttivi = iscritto.getAbbonamentiAttivi();
        if (!abbonamentiAttivi.isEmpty()) {
            StringBuilder abbAttivi = new StringBuilder("Abbonamenti attivi:\n");
            for (iscritto.Abbonamento abb : abbonamentiAttivi) {
                abbAttivi.append("- Tipo: ").append(abb.getTipo())
                         .append(", Categoria: ").append(abb.getCategoria())
                         .append(", Scadenza: ").append(abb.getDataScadenza())
                         .append("\n");
            }
            
            // Aggiungi gli abbonamenti attivi allo storico
            storicoField.setText(abbAttivi.toString() + "\n\nStorico:\n" + iscritto.getStorico());
        }
    }

    @Override
    public void mostraMessaggio(String messaggio, String titolo, int tipo) {
        JOptionPane.showMessageDialog(parentFrame, messaggio, titolo, tipo);
    }

    @Override
    public void pulisciForm() {
        nomeField.setText("");
        cognomeField.setText("");
        codiceField.setText("");
        statoValueLabel.setText("");
        scadenzaValueLabel.setText("");
        storicoField.setText("");
        currentIscritto = null;
    }

    @Override
    public void setPresenter(ModificaPresenter presenter) {
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