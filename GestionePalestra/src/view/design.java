package view;

import model.CsvManager;
import model.iscritto;
import presenter.IscrittiPresenter;
import presenter.IscrittiPresenterImpl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class design implements IscrittiView {
    // Variabili per l'interfaccia principale
    private JPanel panel1;
    private JLabel titleLabel;
    private JButton aggiungiButton;
    private JButton stampaButton;
    private JButton cercaButton;
    private JButton eliminaButton;
    private JButton modificaButton;
    private JFrame frame;
    private IscrittiPresenter presenter;
    
    // Variabili per la visualizzazione dei dettagli dell'iscritto
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceField;
    private JTextArea storicoField;
    private JComboBox<String> tipoAbbComboBox;
    private JComboBox<String> categoriaAbbComboBox;
    private JLabel scadenzaValueLabel;
    private JLabel statoValueLabel;
    
    // Variabili per la gestione dei dati
    private static List<iscritto> iscritti;
    private iscritto selected;

    public design(JFrame frame) {
        this.frame = frame;
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        
        // Inizializza il presenter
        this.presenter = new IscrittiPresenterImpl(this);

        // Titolo in alto
        titleLabel = new JLabel("ELENCO PALESTRA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel1.add(titleLabel, BorderLayout.NORTH);

        // Pannello con bottoni
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 1, 10, 10));

        aggiungiButton = new JButton("Aggiungi iscritto");
        stampaButton = new JButton("Stampa iscritti");
        cercaButton = new JButton("Cerca iscritto");
        eliminaButton = new JButton("Elimina iscritto");
        modificaButton = new JButton("Modifica iscritto");

        buttonsPanel.add(aggiungiButton);
        buttonsPanel.add(stampaButton);
        buttonsPanel.add(cercaButton);
        buttonsPanel.add(eliminaButton);
        buttonsPanel.add(modificaButton);

        panel1.add(buttonsPanel, BorderLayout.CENTER);

        // Listeners
        aggiungiButton.addActionListener(e -> showAggiungiPanel());
        stampaButton.addActionListener(e -> showStampaPanel());
        cercaButton.addActionListener(e -> showPanel("Cerca iscritto"));
        eliminaButton.addActionListener(e -> showPanel("Elimina iscritto"));
        modificaButton.addActionListener(e -> showPanel("Modifica iscritto"));
    }
    
    // Pannello per aggiungere un nuovo iscritto
    private void showAggiungiPanel() {
        // Genera un nuovo codice univoco tramite il model
        String nuovoCodice = CsvManager.generaCodiceUnivoco();
        
        JPanel newPanel = new JPanel(new BorderLayout());
        
        // Titolo in alto
        JLabel titleLabel = new JLabel("Aggiungi iscritto", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Form nel centro
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campi del form
        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField(20);
        
        JLabel cognomeLabel = new JLabel("Cognome:");
        JTextField cognomeField = new JTextField(20);
        
        JLabel codiceLabel = new JLabel("Codice Univoco (generato automaticamente):");
        JTextField codiceField = new JTextField(20);
        codiceField.setText(nuovoCodice);
        codiceField.setEditable(false); // Rendi il campo non modificabile
        
        // Selezione del tipo di abbonamento
        JLabel tipoAbbLabel = new JLabel("Tipo Abbonamento:");
        JComboBox<String> tipoAbbComboBox = new JComboBox<>();
        for (String tipo : CsvManager.getTipiAbbonamento()) {
            tipoAbbComboBox.addItem(tipo);
        }
        
        // Selezione della categoria di abbonamento
        JLabel categoriaAbbLabel = new JLabel("Categoria Abbonamento:");
        JComboBox<String> categoriaAbbComboBox = new JComboBox<>();
        for (String categoria : CsvManager.getCategorieAbbonamento()) {
            categoriaAbbComboBox.addItem(categoria);
        }
        
        // Label per mostrare la data di scadenza calcolata
        JLabel scadenzaLabel = new JLabel("Data di Scadenza:");
        JLabel scadenzaValueLabel = new JLabel("Seleziona un tipo di abbonamento");
        
        // Aggiorna la data di scadenza quando cambia il tipo di abbonamento
        tipoAbbComboBox.addActionListener(e -> {
            String tipoSelezionato = (String) tipoAbbComboBox.getSelectedItem();
            LocalDate oggi = LocalDate.now();
            LocalDate scadenza;
            
            if (tipoSelezionato.equals("Mensile")) {
                scadenza = oggi.plusMonths(1);
            } else { // Annuale
                scadenza = oggi.plusYears(1);
            }
            
            scadenzaValueLabel.setText(scadenza.toString());
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
        JButton salvaButton = new JButton("Salva");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(salvaButton, gbc);
        
        // Azione del bottone salva
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validazione dei campi
                if (nomeField.getText().isEmpty() || cognomeField.getText().isEmpty() || 
                    codiceField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, 
                        "Tutti i campi sono obbligatori.", 
                        "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Verifica se esiste già un iscritto con lo stesso codice univoco
                String codiceUni = codiceField.getText();
                iscritto esistente = CsvManager.cercaIscritto(codiceUni);
                if (esistente != null) {
                    JOptionPane.showMessageDialog(frame, 
                        "Esiste già un iscritto con il codice univoco: " + codiceUni, 
                        "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Ottieni i valori selezionati
                String tipoAbbonamento = (String) tipoAbbComboBox.getSelectedItem();
                String categoriaAbbonamento = (String) categoriaAbbComboBox.getSelectedItem();
                
                // Crea un nuovo iscritto con i dati dell'abbonamento
                iscritto nuovoIscritto = new iscritto(
                    nomeField.getText(),
                    cognomeField.getText(),
                    codiceField.getText(),
                    "Si", // Abbonamento attivo di default
                    "", // Storico vuoto per i nuovi iscritti
                    tipoAbbonamento,
                    categoriaAbbonamento
                );
                
                // Salva l'iscritto nel file CSV
                boolean salvato = CsvManager.salvaIscritto(nuovoIscritto);
                
                if (salvato) {
                    JOptionPane.showMessageDialog(frame, 
                        "Iscritto salvato con successo!\nData di scadenza abbonamento: " + 
                        nuovoIscritto.getDataScadenzaFormattata(), 
                        "Successo", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Pulisci i campi
                    nomeField.setText("");
                    cognomeField.setText("");
                    codiceField.setText("");
                    tipoAbbComboBox.setSelectedIndex(0);
                    categoriaAbbComboBox.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(frame, 
                        "Errore durante il salvataggio dell'iscritto.", 
                        "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        newPanel.add(formPanel, BorderLayout.CENTER);
        
        // Bottone per tornare indietro
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Torna al menu");
        backButton.addActionListener(e -> {
            frame.setContentPane(panel1);
            frame.revalidate();
            frame.repaint();
        });
        bottomPanel.add(backButton);
        newPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Imposta il nuovo pannello
        frame.setContentPane(newPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    // Pannello per visualizzare tutti gli iscritti
    private void showStampaPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());
        
        // Titolo in alto
        JLabel titleLabel = new JLabel("Elenco Iscritti", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Tabella nel centro
        // Ricarica sempre la lista degli iscritti per assicurarci di avere i dati più aggiornati
        presenter.caricaIscritti();
        
        // Verifica che la lista degli iscritti sia stata caricata correttamente
        if (iscritti == null) {
            iscritti = CsvManager.leggiIscritti();
        }
        
        // Intestazioni della tabella
        String[] columnNames = {"Nome", "Cognome", "Codice Univoco", "Abbonamento Attivo", "Stato", "Storico"};
        
        // Dati della tabella
        Object[][] data = new Object[iscritti.size()][6];
        for (int i = 0; i < iscritti.size(); i++) {
            iscritto is = iscritti.get(i);
            data[i][0] = is.getNome();
            data[i][1] = is.getCognome();
            data[i][2] = is.getCodice_Uni();
            
            // Formatta i dettagli di tutti gli abbonamenti attivi in un formato più leggibile
            StringBuilder abbAttivo = new StringBuilder("<html>");
            
            // Aggiorna lo storico prima di visualizzare (sposta gli abbonamenti scaduti nello storico)
            is.aggiornaStorico();
            
            // Ottieni tutti gli abbonamenti attivi
            List<iscritto.Abbonamento> abbonamentiAttivi = is.getAbbonamentiAttivi();
            
            if (abbonamentiAttivi.isEmpty()) {
                // Se non ci sono abbonamenti attivi, mostra l'abbonamento principale per compatibilità
                abbAttivo.append("<b>Nessun abbonamento attivo</b>");
            } else {
                // Mostra tutti gli abbonamenti attivi con spaziatura migliorata e testo più piccolo
                abbAttivo.append("<div style='font-size: 10px; line-height: 1.3;'>");
                abbAttivo.append("<b>Abbonamenti attivi (").append(abbonamentiAttivi.size()).append("):</b><br>");
                
                // Crea una tabella per tutti gli abbonamenti
                abbAttivo.append("<table cellpadding='1' cellspacing='0' style='width: 100%; margin-top: 3px;'>");
                
                // Intestazione della tabella
                abbAttivo.append("<tr style='background-color: #f0f0f0;'>");
                abbAttivo.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>#</th>");
                abbAttivo.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>Tipo</th>");
                abbAttivo.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>Categoria</th>");
                abbAttivo.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>Scadenza</th>");
                abbAttivo.append("</tr>");
                
                // Righe della tabella per ogni abbonamento
                for (int j = 0; j < abbonamentiAttivi.size(); j++) {
                    iscritto.Abbonamento abb = abbonamentiAttivi.get(j);
                    
                    // Alterna i colori delle righe per maggiore leggibilità
                    String bgColor = (j % 2 == 0) ? "#ffffff" : "#f9f9f9";
                    
                    abbAttivo.append("<tr style='background-color: ").append(bgColor).append(";'>");
                    abbAttivo.append("<td style='padding: 2px;'>").append(j + 1).append("</td>");
                    abbAttivo.append("<td style='padding: 2px;'>").append(abb.getTipo()).append("</td>");
                    abbAttivo.append("<td style='padding: 2px;'>").append(abb.getCategoria()).append("</td>");
                    abbAttivo.append("<td style='padding: 2px;'>").append(abb.getDataScadenza()).append("</td>");
                    abbAttivo.append("</tr>");
                }
                
                abbAttivo.append("</table>");
                
                // Aggiungi dettagli aggiuntivi se necessario
                if (abbonamentiAttivi.size() > 0) {
                    abbAttivo.append("<div style='margin-top: 5px; font-size: 9px; color: #666;'>");
                    abbAttivo.append("Data inizio ultimo abbonamento: ").append(abbonamentiAttivi.get(0).getDataInizio());
                    abbAttivo.append("</div>");
                }
                
                abbAttivo.append("</div>");
            }
            
            abbAttivo.append("</html>");
            data[i][3] = abbAttivo.toString();
            
            // Stato dell'abbonamento (basato sulla presenza di abbonamenti attivi)
            data[i][4] = is.hasAbbonamentiAttivi() ? "ATTIVO" : "SCADUTO";
            
            // Formatta lo storico in un formato più leggibile
            if (is.getStorico() != null && !is.getStorico().isEmpty()) {
                // Dividi lo storico in singoli abbonamenti (separati da punto e virgola)
                String[] abbonamenti = is.getStorico().split(";");
                StringBuilder storicoFormatted = new StringBuilder("<html>");
                storicoFormatted.append("<div style='font-size: 11px; line-height: 1.3;'>");
                storicoFormatted.append("<b>Abbonamenti scaduti:</b><br>");
                
                for (int j = 0; j < abbonamenti.length; j++) {
                    String abbonamento = abbonamenti[j].trim();
                    if (!abbonamento.isEmpty()) {
                        // Aggiungi un separatore tra gli abbonamenti
                        if (j > 0) {
                            storicoFormatted.append("<div style='margin-top: 5px; border-top: 1px dotted #ddd; padding-top: 3px;'></div>");
                        }
                        storicoFormatted.append(abbonamento).append("<br>");
                    }
                }
                
                storicoFormatted.append("</div></html>");
                data[i][5] = storicoFormatted.toString();
            } else {
                data[i][5] = "<html><div style='color: #999; font-style: italic;'>Nessuno storico</div></html>";
            }
        }
        
        JTable table = new JTable(data, columnNames);
        
        // Personalizza la tabella
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(100); // Altezza delle righe adeguata al nuovo formato compatto
        
        // Imposta le larghezze preferite delle colonne
        table.getColumnModel().getColumn(0).setPreferredWidth(80); // Nome
        table.getColumnModel().getColumn(1).setPreferredWidth(80); // Cognome
        table.getColumnModel().getColumn(2).setPreferredWidth(60); // Codice
        table.getColumnModel().getColumn(3).setPreferredWidth(250); // Abbonamenti
        table.getColumnModel().getColumn(4).setPreferredWidth(60); // Stato
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // Storico
        
        // Colora le righe in base allo stato dell'abbonamento
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Ottieni lo stato dell'abbonamento (colonna 4)
                String stato = (String) table.getValueAt(row, 4);
                
                // Imposta il colore di sfondo in base allo stato
                if ("SCADUTO".equals(stato)) {
                    c.setBackground(new Color(255, 200, 200)); // Rosso chiaro per abbonamenti scaduti
                } else {
                    c.setBackground(new Color(200, 255, 200)); // Verde chiaro per abbonamenti attivi
                }
                
                // Se la cella è selezionata, usa un colore diverso
                if (isSelected) {
                    c.setBackground(new Color(184, 207, 229));
                }
                
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        newPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Pannello inferiore con statistiche e bottone indietro
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Statistiche a sinistra
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        int totaleIscritti = iscritti.size();
        int abbonatiAttivi = 0;
        
        for (iscritto is : iscritti) {
            if (is.hasAbbonamentiAttivi()) {
                abbonatiAttivi++;
            }
        }
        
        JLabel statsLabel = new JLabel(String.format("Totale iscritti: %d | Abbonamenti attivi: %d | Abbonamenti scaduti: %d", 
                                                    totaleIscritti, abbonatiAttivi, totaleIscritti - abbonatiAttivi));
        statsPanel.add(statsLabel);
        bottomPanel.add(statsPanel, BorderLayout.WEST);
        
        // Bottone per tornare indietro a destra
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Torna al menu");
        backButton.addActionListener(e -> {
            frame.setContentPane(panel1);
            frame.revalidate();
            frame.repaint();
        });
        buttonPanel.add(backButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        
        newPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Imposta il nuovo pannello
        frame.setContentPane(newPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void showPanel(String title) {
        switch (title) {
            case "Elimina iscritto":
                showEliminaPanel();
                break;
            case "Modifica iscritto":
                showModificaPanel();
                break;
            case "Cerca iscritto":
                showCercaPanel();
                break;
            default:
                showDefaultPanel(title);
                break;
        }
    }
    
    // Pannello per eliminare un iscritto
    private void showEliminaPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());
        
        // Titolo in alto
        JLabel titleLabel = new JLabel("Elimina iscritto", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Pannello centrale con la lista degli iscritti
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // Ottieni la lista degli iscritti
        List<iscritto> iscritti = CsvManager.leggiIscritti();
        
        // Crea un modello per la lista
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (iscritto i : iscritti) {
            listModel.addElement(i.getCodice_Uni() + " - " + i.getNome() + " " + i.getCognome());
        }
        
        // Crea la lista con gli iscritti
        JList<String> iscrittiList = new JList<>(listModel);
        iscrittiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(iscrittiList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Pannello per i dettagli dell'iscritto selezionato
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Dettagli iscritto"));
        
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel nomeValue = new JLabel();
        JLabel cognomeLabel = new JLabel("Cognome:");
        JLabel cognomeValue = new JLabel();
        JLabel codiceLabel = new JLabel("Codice Univoco:");
        JLabel codiceValue = new JLabel();
        JLabel tipoAbbLabel = new JLabel("Tipo Abbonamento:");
        JLabel tipoAbbValue = new JLabel();
        JLabel categoriaAbbLabel = new JLabel("Categoria Abbonamento:");
        JLabel categoriaAbbValue = new JLabel();
        JLabel scadenzaLabel = new JLabel("Data Scadenza:");
        JLabel scadenzaValue = new JLabel();
        JLabel statoLabel = new JLabel("Stato Abbonamento:");
        JLabel statoValue = new JLabel();
        
        detailsPanel.add(nomeLabel);
        detailsPanel.add(nomeValue);
        detailsPanel.add(cognomeLabel);
        detailsPanel.add(cognomeValue);
        detailsPanel.add(codiceLabel);
        detailsPanel.add(codiceValue);
        detailsPanel.add(tipoAbbLabel);
        detailsPanel.add(tipoAbbValue);
        detailsPanel.add(categoriaAbbLabel);
        detailsPanel.add(categoriaAbbValue);
        detailsPanel.add(scadenzaLabel);
        detailsPanel.add(scadenzaValue);
        detailsPanel.add(statoLabel);
        detailsPanel.add(statoValue);
        
        centerPanel.add(detailsPanel, BorderLayout.SOUTH);
        
        // Bottone per eliminare
        JButton eliminaButton = new JButton("Elimina iscritto selezionato");
        eliminaButton.setEnabled(false);
        
        // Listener per la selezione nella lista
        iscrittiList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = iscrittiList.getSelectedIndex();
                if (selectedIndex != -1) {
                    iscritto selected = iscritti.get(selectedIndex);
                    
                    // Aggiorna i dettagli
                    nomeValue.setText(selected.getNome());
                    cognomeValue.setText(selected.getCognome());
                    codiceValue.setText(selected.getCodice_Uni());
                    tipoAbbValue.setText(selected.getTipoAbbonamento());
                    categoriaAbbValue.setText(selected.getCategoriaAbbonamento());
                    scadenzaValue.setText(selected.getDataScadenzaFormattata());
                    
                    if (selected.isAbbonamentoScaduto()) {
                        statoValue.setText("SCADUTO");
                        statoValue.setForeground(Color.RED);
                    } else {
                        statoValue.setText("ATTIVO");
                        statoValue.setForeground(new Color(0, 128, 0)); // Verde
                    }
                    
                    // Abilita il bottone elimina
                    eliminaButton.setEnabled(true);
                } else {
                    // Resetta i dettagli
                    nomeValue.setText("");
                    cognomeValue.setText("");
                    codiceValue.setText("");
                    tipoAbbValue.setText("");
                    categoriaAbbValue.setText("");
                    scadenzaValue.setText("");
                    statoValue.setText("");
                    
                    // Disabilita il bottone elimina
                    eliminaButton.setEnabled(false);
                }
            }
        });
        
        // Azione del bottone elimina
        eliminaButton.addActionListener(e -> {
            int selectedIndex = iscrittiList.getSelectedIndex();
            if (selectedIndex != -1) {
                iscritto selected = iscritti.get(selectedIndex);
                
                // Chiedi conferma
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Sei sicuro di voler eliminare l'iscritto " + selected.getNome() + " " + selected.getCognome() + "?",
                    "Conferma eliminazione",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    // Elimina l'iscritto tramite il presenter
                    presenter.eliminaIscritto(selected);
                    
                    // Ricarica la lista degli iscritti tramite il presenter
                    presenter.caricaIscritti();
                    
                    // Aggiorna il modello della lista
                    listModel.clear();
                    // Usa la lista statica degli iscritti che è stata aggiornata dal presenter
                    for (iscritto i : design.iscritti) {
                        listModel.addElement(i.getNome() + " " + i.getCognome() + " (" + i.getCodice_Uni() + ")");
                    }
                    
                    // Resetta i dettagli
                    nomeValue.setText("");
                    cognomeValue.setText("");
                    codiceValue.setText("");
                    tipoAbbValue.setText("");
                    categoriaAbbValue.setText("");
                    scadenzaValue.setText("");
                    statoValue.setText("");
                    
                    // Disabilita il bottone elimina
                    eliminaButton.setEnabled(false);
                }
            }
        });
        
        // Aggiungi il bottone elimina
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(eliminaButton);
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        
        newPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Bottone per tornare indietro
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Torna al menu");
        backButton.addActionListener(e -> {
            frame.setContentPane(panel1);
            frame.revalidate();
            frame.repaint();
        });
        bottomPanel.add(backButton);
        newPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Imposta il nuovo pannello
        frame.setContentPane(newPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    // Pannello per modificare un iscritto
    private void showModificaPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());
        
        // Titolo in alto
        JLabel titleLabel = new JLabel("Modifica iscritto", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Pannello centrale diviso in due parti
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        
        // Pannello sinistro con la lista degli iscritti
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Seleziona iscritto"));
        
        // Ottieni la lista degli iscritti
        List<iscritto> iscritti = CsvManager.leggiIscritti();
        
        // Crea un modello per la lista
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (iscritto i : iscritti) {
            listModel.addElement(i.getCodice_Uni() + " - " + i.getNome() + " " + i.getCognome());
        }
        
        // Crea la lista con gli iscritti
        JList<String> iscrittiList = new JList<>(listModel);
        iscrittiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(iscrittiList);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Pannello destro con il form di modifica
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Modifica dati"));
        
        // Form nel pannello destro
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campi del form
        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField(20);
        
        JLabel cognomeLabel = new JLabel("Cognome:");
        JTextField cognomeField = new JTextField(20);
        
        JLabel codiceLabel = new JLabel("Codice Univoco:");
        JTextField codiceField = new JTextField(20);
        codiceField.setEditable(false); // Il codice univoco non può essere modificato
        
        JLabel storicoLabel = new JLabel("Storico:");
        JTextField storicoField = new JTextField(20);
        
        // Selezione del tipo di abbonamento
        JLabel tipoAbbLabel = new JLabel("Tipo Abbonamento:");
        JComboBox<String> tipoAbbComboBox = new JComboBox<>();
        for (String tipo : CsvManager.getTipiAbbonamento()) {
            tipoAbbComboBox.addItem(tipo);
        }
        
        // Selezione della categoria di abbonamento
        JLabel categoriaAbbLabel = new JLabel("Categoria Abbonamento:");
        JComboBox<String> categoriaAbbComboBox = new JComboBox<>();
        for (String categoria : CsvManager.getCategorieAbbonamento()) {
            categoriaAbbComboBox.addItem(categoria);
        }
        
        // Label per mostrare la data di scadenza calcolata
        JLabel scadenzaLabel = new JLabel("Data di Scadenza:");
        JLabel scadenzaValueLabel = new JLabel("");
        
        // Aggiorna la data di scadenza quando cambia il tipo di abbonamento
        tipoAbbComboBox.addActionListener(e -> {
            if (iscrittiList.getSelectedIndex() != -1) {
                iscritto selected = iscritti.get(iscrittiList.getSelectedIndex());
                String tipoSelezionato = (String) tipoAbbComboBox.getSelectedItem();
                LocalDate dataInizio = selected.getDataInizioAbbonamento();
                if (dataInizio == null) {
                    dataInizio = LocalDate.now();
                }
                
                LocalDate scadenza;
                if (tipoSelezionato.equals("Mensile")) {
                    scadenza = dataInizio.plusMonths(1);
                } else { // Annuale
                    scadenza = dataInizio.plusYears(1);
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
        formPanel.add(storicoLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(storicoField, gbc);
        
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
        
        // Bottone per aggiungere un nuovo abbonamento
        JButton nuovoAbbButton = new JButton("Aggiungi nuovo abbonamento");
        nuovoAbbButton.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(nuovoAbbButton, gbc);
        
        // Bottone per salvare le modifiche
        JButton salvaButton = new JButton("Salva modifiche");
        salvaButton.setEnabled(false);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(salvaButton, gbc);
        
        rightPanel.add(formPanel, BorderLayout.CENTER);
        
        // Listener per la selezione nella lista
        iscrittiList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = iscrittiList.getSelectedIndex();
                if (selectedIndex != -1) {
                    iscritto selected = iscritti.get(selectedIndex);
                    
                    // Aggiorna i campi del form
                    nomeField.setText(selected.getNome());
                    cognomeField.setText(selected.getCognome());
                    codiceField.setText(selected.getCodice_Uni());
                    storicoField.setText(selected.getStorico());
                    
                    // Seleziona il tipo e la categoria di abbonamento
                    if (selected.getTipoAbbonamento() != null && !selected.getTipoAbbonamento().isEmpty()) {
                        tipoAbbComboBox.setSelectedItem(selected.getTipoAbbonamento());
                    } else {
                        tipoAbbComboBox.setSelectedIndex(0);
                    }
                    
                    if (selected.getCategoriaAbbonamento() != null && !selected.getCategoriaAbbonamento().isEmpty()) {
                        categoriaAbbComboBox.setSelectedItem(selected.getCategoriaAbbonamento());
                    } else {
                        categoriaAbbComboBox.setSelectedIndex(0);
                    }
                    
                    // Mostra la data di scadenza
                    if (selected.getDataScadenzaAbbonamento() != null) {
                        scadenzaValueLabel.setText(selected.getDataScadenzaAbbonamento().toString());
                    } else {
                        scadenzaValueLabel.setText("Non impostata");
                    }
                    
                    // Abilita i bottoni
                    salvaButton.setEnabled(true);
                    nuovoAbbButton.setEnabled(true);
                } else {
                    // Resetta i campi
                    nomeField.setText("");
                    cognomeField.setText("");
                    codiceField.setText("");
                    storicoField.setText("");
                    tipoAbbComboBox.setSelectedIndex(0);
                    categoriaAbbComboBox.setSelectedIndex(0);
                    scadenzaValueLabel.setText("");
                    
                    // Disabilita i bottoni
                    salvaButton.setEnabled(false);
                    nuovoAbbButton.setEnabled(false);
                }
            }
        });
        
        // Azione del bottone per aggiungere un nuovo abbonamento
        nuovoAbbButton.addActionListener(e -> {
            int selectedIndex = iscrittiList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Ottieni l'iscritto selezionato
                iscritto selected = iscritti.get(selectedIndex);
                
                // Crea un pannello per il nuovo abbonamento
                JPanel nuovoAbbPanel = new JPanel(new GridLayout(0, 2, 5, 5));
                
                JLabel tipoLabel = new JLabel("Tipo Abbonamento:");
                JComboBox<String> tipoCombo = new JComboBox<>();
                for (String tipo : CsvManager.getTipiAbbonamento()) {
                    tipoCombo.addItem(tipo);
                }
                
                JLabel categoriaLabel = new JLabel("Categoria Abbonamento:");
                JComboBox<String> categoriaCombo = new JComboBox<>();
                for (String categoria : CsvManager.getCategorieAbbonamento()) {
                    categoriaCombo.addItem(categoria);
                }
                
                JLabel dataInizioLabel = new JLabel("Data Inizio:");
                JLabel dataInizioValue = new JLabel(LocalDate.now().toString());
                
                JLabel dataScadenzaLabel = new JLabel("Data Scadenza:");
                JLabel dataScadenzaValue = new JLabel();
                
                // Calcola la data di scadenza in base al tipo selezionato
                tipoCombo.addActionListener(event -> {
                    String tipoSelezionato = (String) tipoCombo.getSelectedItem();
                    LocalDate oggi = LocalDate.now();
                    LocalDate scadenza;
                    
                    if (tipoSelezionato.equals("Mensile")) {
                        scadenza = oggi.plusMonths(1);
                    } else { // Annuale
                        scadenza = oggi.plusYears(1);
                    }
                    
                    dataScadenzaValue.setText(scadenza.toString());
                });
                
                // Imposta il valore iniziale della data di scadenza
                tipoCombo.setSelectedIndex(0);
                
                nuovoAbbPanel.add(tipoLabel);
                nuovoAbbPanel.add(tipoCombo);
                nuovoAbbPanel.add(categoriaLabel);
                nuovoAbbPanel.add(categoriaCombo);
                nuovoAbbPanel.add(dataInizioLabel);
                nuovoAbbPanel.add(dataInizioValue);
                nuovoAbbPanel.add(dataScadenzaLabel);
                nuovoAbbPanel.add(dataScadenzaValue);
                
                // Mostra il pannello in un dialog
                int result = JOptionPane.showConfirmDialog(
                    frame,
                    nuovoAbbPanel,
                    "Aggiungi nuovo abbonamento",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                );
                
                if (result == JOptionPane.OK_OPTION) {
                    // Ottieni i dati del nuovo abbonamento
                    String nuovoTipo = (String) tipoCombo.getSelectedItem();
                    String nuovaCategoria = (String) categoriaCombo.getSelectedItem();
                    
                    // Aggiorna l'iscritto esistente direttamente
                    // Prima aggiorna lo storico per spostare gli abbonamenti scaduti
                    selected.aggiornaStorico();
                    
                    // Aggiungi il nuovo abbonamento mantenendo quelli esistenti
                    selected.aggiungiAbbonamento(nuovoTipo, nuovaCategoria);
                    
                    // Salva l'iscritto modificato
                    boolean salvato = CsvManager.modificaIscritto(selected);
                    
                    if (salvato) {
                        // Ottieni la lista degli abbonamenti attivi
                        List<iscritto.Abbonamento> abbonamentiAttivi = selected.getAbbonamentiAttivi();
                        
                        StringBuilder messaggioAbbonamenti = new StringBuilder();
                        messaggioAbbonamenti.append("Nuovo abbonamento aggiunto con successo!\n\n");
                        messaggioAbbonamenti.append("Abbonamenti attivi:\n");
                        
                        for (iscritto.Abbonamento abb : abbonamentiAttivi) {
                            messaggioAbbonamenti.append("- Tipo: ").append(abb.getTipo())
                                               .append(", Categoria: ").append(abb.getCategoria())
                                               .append(", Scadenza: ").append(abb.getDataScadenza())
                                               .append("\n");
                        }
                        
                        JOptionPane.showMessageDialog(
                            frame,
                            messaggioAbbonamenti.toString(),
                            "Successo",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        
                        // Aggiorna i campi del form con i nuovi dati
                        storicoField.setText(selected.getStorico());
                        tipoAbbComboBox.setSelectedItem(selected.getTipoAbbonamento());
                        categoriaAbbComboBox.setSelectedItem(selected.getCategoriaAbbonamento());
                        
                        if (selected.getDataScadenzaAbbonamento() != null) {
                            scadenzaValueLabel.setText(selected.getDataScadenzaAbbonamento().toString());
                        }
                        
                        // Ricarica l'iscritto dal file CSV per ottenere i dati aggiornati
                        iscritto aggiornato = CsvManager.cercaIscritto(selected.getCodice_Uni());
                        if (aggiornato != null) {
                            // Aggiorna l'iscritto nella lista
                            iscritti.set(selectedIndex, aggiornato);
                            selected = aggiornato;
                            
                            // Aggiorna la selezione nella lista
                            iscrittiList.setSelectedIndex(selectedIndex);
                            iscrittiList.ensureIndexIsVisible(selectedIndex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Errore durante l'aggiunta del nuovo abbonamento.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
        
        // Azione del bottone salva
        salvaButton.addActionListener(e -> {
            int selectedIndex = iscrittiList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Validazione dei campi
                if (nomeField.getText().isEmpty() || cognomeField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Nome e Cognome sono campi obbligatori.",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                
                // Ottieni l'iscritto selezionato
                iscritto selected = iscritti.get(selectedIndex);
                
                // Aggiorna i dati
                selected.setNome(nomeField.getText());
                selected.setCognome(cognomeField.getText());
                selected.setStorico(storicoField.getText());
                selected.setTipoAbbonamento((String) tipoAbbComboBox.getSelectedItem());
                selected.setCategoriaAbbonamento((String) categoriaAbbComboBox.getSelectedItem());
                
                // Salva le modifiche
                boolean modificato = CsvManager.modificaIscritto(selected);
                
                if (modificato) {
                    // Aggiorna la lista
                    listModel.setElementAt(
                        selected.getCodice_Uni() + " - " + selected.getNome() + " " + selected.getCognome(),
                        selectedIndex
                    );
                    
                    JOptionPane.showMessageDialog(
                        frame,
                        "Iscritto modificato con successo!",
                        "Successo",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Errore durante la modifica dell'iscritto.",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        
        // Aggiungi i pannelli al split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        
        newPanel.add(splitPane, BorderLayout.CENTER);
        
        // Bottone per tornare indietro
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Torna al menu");
        backButton.addActionListener(e -> {
            frame.setContentPane(panel1);
            frame.revalidate();
            frame.repaint();
        });
        bottomPanel.add(backButton);
        newPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Imposta il nuovo pannello
        frame.setContentPane(newPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    // Pannello per cercare un iscritto
    private void showCercaPanel() {
        JPanel newPanel = new JPanel(new BorderLayout());
        
        // Titolo in alto
        JLabel titleLabel = new JLabel("Cerca iscritto", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Pannello di ricerca
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel searchLabel = new JLabel("Cerca per Codice Univoco:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Cerca");
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Pannello per i risultati
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Risultato ricerca"));
        
        // Pannello per i dettagli dell'iscritto
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        
        JLabel nomeLabel = new JLabel("Nome:");
        JLabel nomeValue = new JLabel();
        JLabel cognomeLabel = new JLabel("Cognome:");
        JLabel cognomeValue = new JLabel();
        JLabel codiceLabel = new JLabel("Codice Univoco:");
        JLabel codiceValue = new JLabel();
        JLabel tipoAbbLabel = new JLabel("Tipo Abbonamento:");
        JLabel tipoAbbValue = new JLabel();
        JLabel categoriaAbbLabel = new JLabel("Categoria Abbonamento:");
        JLabel categoriaAbbValue = new JLabel();
        JLabel dataInizioLabel = new JLabel("Data Inizio:");
        JLabel dataInizioValue = new JLabel();
        JLabel scadenzaLabel = new JLabel("Data Scadenza:");
        JLabel scadenzaValue = new JLabel();
        JLabel statoLabel = new JLabel("Stato Abbonamento:");
        JLabel statoValue = new JLabel();
        JLabel storicoLabel = new JLabel("Storico:");
        JLabel storicoValue = new JLabel();
        
        detailsPanel.add(nomeLabel);
        detailsPanel.add(nomeValue);
        detailsPanel.add(cognomeLabel);
        detailsPanel.add(cognomeValue);
        detailsPanel.add(codiceLabel);
        detailsPanel.add(codiceValue);
        detailsPanel.add(tipoAbbLabel);
        detailsPanel.add(tipoAbbValue);
        detailsPanel.add(categoriaAbbLabel);
        detailsPanel.add(categoriaAbbValue);
        detailsPanel.add(dataInizioLabel);
        detailsPanel.add(dataInizioValue);
        detailsPanel.add(scadenzaLabel);
        detailsPanel.add(scadenzaValue);
        detailsPanel.add(statoLabel);
        detailsPanel.add(statoValue);
        detailsPanel.add(storicoLabel);
        detailsPanel.add(storicoValue);
        
        resultPanel.add(detailsPanel, BorderLayout.CENTER);
        
        // Messaggio iniziale
        JLabel initialMessage = new JLabel("Inserisci un codice univoco e premi Cerca", SwingConstants.CENTER);
        resultPanel.add(initialMessage, BorderLayout.NORTH);
        
        // Azione del bottone cerca
        searchButton.addActionListener(e -> {
            String codice = searchField.getText().trim();
            if (codice.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Inserisci un codice univoco da cercare.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            // Cerca l'iscritto
            iscritto trovato = CsvManager.cercaIscritto(codice);
            
            if (trovato != null) {
                // Rimuovi il messaggio iniziale
                resultPanel.remove(initialMessage);
                
                // Aggiorna i dettagli base
                nomeValue.setText(trovato.getNome());
                cognomeValue.setText(trovato.getCognome());
                codiceValue.setText(trovato.getCodice_Uni());
                
                // Aggiorna lo storico prima di visualizzare (sposta gli abbonamenti scaduti nello storico)
                trovato.aggiornaStorico();
                
                // Ottieni tutti gli abbonamenti attivi
                List<iscritto.Abbonamento> abbonamentiAttivi = trovato.getAbbonamentiAttivi();
                
                if (abbonamentiAttivi.isEmpty()) {
                    // Se non ci sono abbonamenti attivi, mostra i dati dell'abbonamento principale
                    tipoAbbValue.setText("Nessun abbonamento attivo");
                    categoriaAbbValue.setText("-");
                    dataInizioValue.setText("-");
                    scadenzaValue.setText("-");
                    storicoValue.setText(trovato.getStorico());
                } else {
                    // Mostra il primo abbonamento attivo nei campi principali
                    iscritto.Abbonamento primoAbb = abbonamentiAttivi.get(0);
                    String suffisso = abbonamentiAttivi.size() > 1 ? " (+" + (abbonamentiAttivi.size() - 1) + " altri)" : "";
                    tipoAbbValue.setText(primoAbb.getTipo() + suffisso);
                    categoriaAbbValue.setText(primoAbb.getCategoria());
                    dataInizioValue.setText(primoAbb.getDataInizio().toString());
                    scadenzaValue.setText(primoAbb.getDataScadenza().toString());
                    
                    // Se ci sono più abbonamenti, mostra i dettagli di tutti in formato tabella
                    if (abbonamentiAttivi.size() > 1) {
                        StringBuilder abbDetails = new StringBuilder("<html>");
                        abbDetails.append("<div style='font-size: 11px;'>");
                        abbDetails.append("<b>Tutti gli abbonamenti attivi:</b><br>");
                        
                        // Crea una tabella per tutti gli abbonamenti
                        abbDetails.append("<table cellpadding='1' cellspacing='0' style='width: 100%; margin-top: 3px;'>");
                        
                        // Intestazione della tabella
                        abbDetails.append("<tr style='background-color: #f0f0f0;'>");
                        abbDetails.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>#</th>");
                        abbDetails.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>Tipo</th>");
                        abbDetails.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>Categoria</th>");
                        abbDetails.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>Scadenza</th>");
                        abbDetails.append("</tr>");
                        
                        // Righe della tabella per ogni abbonamento
                        for (int i = 0; i < abbonamentiAttivi.size(); i++) {
                            iscritto.Abbonamento abb = abbonamentiAttivi.get(i);
                            
                            // Alterna i colori delle righe per maggiore leggibilità
                            String bgColor = (i % 2 == 0) ? "#ffffff" : "#f9f9f9";
                            
                            abbDetails.append("<tr style='background-color: ").append(bgColor).append(";'>");
                            abbDetails.append("<td style='padding: 2px;'>").append(i + 1).append("</td>");
                            abbDetails.append("<td style='padding: 2px;'>").append(abb.getTipo()).append("</td>");
                            abbDetails.append("<td style='padding: 2px;'>").append(abb.getCategoria()).append("</td>");
                            abbDetails.append("<td style='padding: 2px;'>").append(abb.getDataScadenza()).append("</td>");
                            abbDetails.append("</tr>");
                        }
                        
                        abbDetails.append("</table>");
                        
                        abbDetails.append("<br><b>Storico:</b><br>").append(trovato.getStorico());
                        abbDetails.append("</div></html>");
                        storicoValue.setText(abbDetails.toString());
                    } else {
                        storicoValue.setText(trovato.getStorico());
                    }
                }
                
                // Aggiorna lo stato in base alla presenza di abbonamenti attivi
                boolean hasAbbAttivi = trovato.hasAbbonamentiAttivi();
                statoValue.setText(hasAbbAttivi ? "ATTIVO" : "SCADUTO");
                statoValue.setForeground(hasAbbAttivi ? new Color(0, 128, 0) : Color.RED); // Verde o Rosso
                
                // Aggiorna il pannello
                resultPanel.revalidate();
                resultPanel.repaint();
            } else {
                // Resetta i dettagli
                nomeValue.setText("");
                cognomeValue.setText("");
                codiceValue.setText("");
                tipoAbbValue.setText("");
                categoriaAbbValue.setText("");
                dataInizioValue.setText("");
                scadenzaValue.setText("");
                statoValue.setText("");
                storicoValue.setText("");
                
                // Mostra messaggio di errore
                JOptionPane.showMessageDialog(
                    frame,
                    "Nessun iscritto trovato con il codice: " + codice,
                    "Iscritto non trovato",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        
        // Aggiungi i pannelli al pannello principale
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(resultPanel, BorderLayout.CENTER);
        
        newPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Bottone per tornare indietro
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Torna al menu");
        backButton.addActionListener(e -> {
            frame.setContentPane(panel1);
            frame.revalidate();
            frame.repaint();
        });
        bottomPanel.add(backButton);
        newPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Imposta il nuovo pannello
        frame.setContentPane(newPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    // Pannello generico per funzionalità non ancora implementate
    private void showDefaultPanel(String title) {
        JPanel newPanel = new JPanel(new BorderLayout());
        
        // Titolo in alto
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        newPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Contenuto nel centro
        JPanel contentPanel = new JPanel();
        contentPanel.add(new JLabel("Funzionalità in fase di sviluppo..."));
        newPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Bottone per tornare indietro
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Torna al menu");
        backButton.addActionListener(e -> {
            frame.setContentPane(panel1);
            frame.revalidate();
            frame.repaint();
        });
        bottomPanel.add(backButton);
        newPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Imposta il nuovo pannello
        frame.setContentPane(newPanel);
        frame.revalidate();
        frame.repaint();
    }

    public JPanel getPanel() {
        return panel1;
    }
    
    // Implementazione dei metodi dell'interfaccia IscrittiView
    
    /**
     * Crea una tabella con gli iscritti
     * @return La tabella creata
     */
    private JTable creaTabella() {
        // Definisci le colonne della tabella
        String[] columnNames = {"Nome", "Cognome", "Codice", "Abbonamenti", "Stato", "Storico"};
        
        // Crea i dati della tabella
        Object[][] data = new Object[iscritti.size()][6];
        
        for (int i = 0; i < iscritti.size(); i++) {
            iscritto is = iscritti.get(i);
            data[i][0] = is.getNome();
            data[i][1] = is.getCognome();
            data[i][2] = is.getCodice_Uni();
            
            // Formatta i dettagli di tutti gli abbonamenti attivi in un formato più leggibile
            StringBuilder abbAttivo = new StringBuilder("<html>");
            
            // Aggiorna lo storico prima di visualizzare (sposta gli abbonamenti scaduti nello storico)
            is.aggiornaStorico();
            
            // Ottieni tutti gli abbonamenti attivi
            List<iscritto.Abbonamento> abbonamentiAttivi = is.getAbbonamentiAttivi();
            
            if (abbonamentiAttivi.isEmpty()) {
                // Se non ci sono abbonamenti attivi, mostra l'abbonamento principale per compatibilità
                abbAttivo.append("<b>Nessun abbonamento attivo</b>");
            } else {
                // Mostra tutti gli abbonamenti attivi con spaziatura migliorata e testo più piccolo
                abbAttivo.append("<div style='font-size: 10px; line-height: 1.3;'>");
                abbAttivo.append("<b>Abbonamenti attivi (").append(abbonamentiAttivi.size()).append("):</b><br>");
                
                // Crea una tabella per tutti gli abbonamenti
                abbAttivo.append("<table cellpadding='1' cellspacing='0' style='width: 100%; margin-top: 3px;'>");
                
                // Intestazione della tabella
                abbAttivo.append("<tr style='background-color: #f0f0f0;'>");
                abbAttivo.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>#</th>");
                abbAttivo.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>Tipo</th>");
                abbAttivo.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>Categoria</th>");
                abbAttivo.append("<th style='text-align: left; padding: 2px; border-bottom: 1px solid #ccc;'>Scadenza</th>");
                abbAttivo.append("</tr>");
                
                // Righe della tabella per ogni abbonamento
                for (int j = 0; j < abbonamentiAttivi.size(); j++) {
                    iscritto.Abbonamento abb = abbonamentiAttivi.get(j);
                    
                    // Alterna i colori delle righe per maggiore leggibilità
                    String bgColor = (j % 2 == 0) ? "#ffffff" : "#f9f9f9";
                    
                    abbAttivo.append("<tr style='background-color: ").append(bgColor).append(";'>");
                    abbAttivo.append("<td style='padding: 2px;'>").append(j + 1).append("</td>");
                    abbAttivo.append("<td style='padding: 2px;'>").append(abb.getTipo()).append("</td>");
                    abbAttivo.append("<td style='padding: 2px;'>").append(abb.getCategoria()).append("</td>");
                    abbAttivo.append("<td style='padding: 2px;'>").append(abb.getDataScadenza()).append("</td>");
                    abbAttivo.append("</tr>");
                }
                
                abbAttivo.append("</table>");
                
                // Aggiungi dettagli aggiuntivi se necessario
                if (abbonamentiAttivi.size() > 0) {
                    abbAttivo.append("<div style='margin-top: 5px; font-size: 9px; color: #666;'>");
                    abbAttivo.append("Data inizio ultimo abbonamento: ").append(abbonamentiAttivi.get(0).getDataInizio());
                    abbAttivo.append("</div>");
                }
                
                abbAttivo.append("</div>");
            }
            
            abbAttivo.append("</html>");
            data[i][3] = abbAttivo.toString();
            
            // Stato dell'abbonamento
            data[i][4] = is.hasAbbonamentiAttivi() ? "ATTIVO" : "SCADUTO";
            
            // Formatta lo storico in un formato più leggibile
            String storico = is.getStorico();
            if (storico != null && !storico.isEmpty()) {
                StringBuilder storicoFormatted = new StringBuilder("<html><div style='font-size: 9px; line-height: 1.2;'>");
                String[] abbonamenti = storico.split("\\|");
                
                for (int j = 0; j < abbonamenti.length; j++) {
                    String abbonamento = abbonamenti[j].trim();
                    if (!abbonamento.isEmpty()) {
                        // Aggiungi un separatore tra gli abbonamenti
                        if (j > 0) {
                            storicoFormatted.append("<div style='margin-top: 5px; border-top: 1px dotted #ddd; padding-top: 3px;'></div>");
                        }
                        storicoFormatted.append(abbonamento).append("<br>");
                    }
                }
                
                storicoFormatted.append("</div></html>");
                data[i][5] = storicoFormatted.toString();
            } else {
                data[i][5] = "<html><div style='color: #999; font-style: italic;'>Nessuno storico</div></html>";
            }
        }
        
        JTable table = new JTable(data, columnNames);
        
        // Personalizza la tabella
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(100); // Altezza delle righe adeguata al nuovo formato compatto
        
        // Imposta le larghezze preferite delle colonne
        table.getColumnModel().getColumn(0).setPreferredWidth(80); // Nome
        table.getColumnModel().getColumn(1).setPreferredWidth(80); // Cognome
        table.getColumnModel().getColumn(2).setPreferredWidth(60); // Codice
        table.getColumnModel().getColumn(3).setPreferredWidth(250); // Abbonamenti
        table.getColumnModel().getColumn(4).setPreferredWidth(60); // Stato
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // Storico
        
        // Colora le righe in base allo stato dell'abbonamento
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Ottieni lo stato dell'abbonamento (colonna 4)
                String stato = (String) table.getValueAt(row, 4);
                
                // Imposta il colore di sfondo in base allo stato
                if ("SCADUTO".equals(stato)) {
                    c.setBackground(new Color(255, 200, 200)); // Rosso chiaro per abbonamenti scaduti
                } else {
                    c.setBackground(new Color(200, 255, 200)); // Verde chiaro per abbonamenti attivi
                }
                
                // Se la cella è selezionata, usa un colore di selezione più scuro
                if (isSelected) {
                    c.setBackground(new Color(51, 153, 255)); // Blu per la selezione
                    c.setForeground(Color.WHITE);
                }
                
                return c;
            }
        });
        
        // Aggiungi un listener per la selezione delle righe
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < iscritti.size()) {
                        // Mostra i dettagli dell'iscritto selezionato
                        selected = iscritti.get(selectedRow);
                        mostraDettagliIscritto(selected);
                    }
                }
            }
        });
        
        return table;
    }
    
    @Override
    public void mostraIscritti(List<iscritto> iscritti) {
        // Salva la lista degli iscritti
        design.iscritti = iscritti;
        
        // Se siamo nella schermata di stampa, aggiorniamo subito la tabella
        if (frame.getContentPane() != panel1 && frame.getTitle().equals("Gestione Palestra")) {
            showStampaPanel();
        }
        // Altrimenti la tabella verrà creata e mostrata quando l'utente clicca su "Stampa iscritti"
    }
    
    @Override
    public void mostraMessaggio(String messaggio, String titolo, int tipo) {
        JOptionPane.showMessageDialog(frame, messaggio, titolo, tipo);
    }
    
    @Override
    public void mostraDettagliIscritto(iscritto iscritto) {
        if (iscritto == null) {
            return;
        }
        
        // Salva l'iscritto selezionato
        this.selected = iscritto;
        
        // Verifica se i campi del form sono stati inizializzati
        if (nomeField != null && cognomeField != null && codiceField != null && 
            storicoField != null && tipoAbbComboBox != null && categoriaAbbComboBox != null &&
            scadenzaValueLabel != null && statoValueLabel != null) {
            
            try {
                // Aggiorna i campi del form con i dati dell'iscritto
                nomeField.setText(iscritto.getNome() != null ? iscritto.getNome() : "");
                cognomeField.setText(iscritto.getCognome() != null ? iscritto.getCognome() : "");
                codiceField.setText(iscritto.getCodice_Uni() != null ? iscritto.getCodice_Uni() : "");
                storicoField.setText(iscritto.getStorico() != null ? iscritto.getStorico() : "");
                
                if (iscritto.getTipoAbbonamento() != null) {
                    tipoAbbComboBox.setSelectedItem(iscritto.getTipoAbbonamento());
                } else {
                    tipoAbbComboBox.setSelectedIndex(0);
                }
                
                if (iscritto.getCategoriaAbbonamento() != null) {
                    categoriaAbbComboBox.setSelectedItem(iscritto.getCategoriaAbbonamento());
                } else {
                    categoriaAbbComboBox.setSelectedIndex(0);
                }
                
                if (iscritto.getDataScadenzaAbbonamento() != null) {
                    scadenzaValueLabel.setText(iscritto.getDataScadenzaAbbonamento().toString());
                } else {
                    scadenzaValueLabel.setText("N/A");
                }
                
                // Aggiorna lo stato dell'abbonamento
                if (iscritto.hasAbbonamentiAttivi()) {
                    statoValueLabel.setText("ATTIVO");
                    statoValueLabel.setForeground(new Color(0, 150, 0));
                } else {
                    statoValueLabel.setText("SCADUTO");
                    statoValueLabel.setForeground(Color.RED);
                }
            } catch (Exception e) {
                // In caso di errore, pulisci il form
                pulisciForm();
            }
        }
    }
    
    @Override
    public void pulisciForm() {
        // Pulisce i campi del form solo se sono stati inizializzati
        if (nomeField != null) nomeField.setText("");
        if (cognomeField != null) cognomeField.setText("");
        if (codiceField != null) codiceField.setText("");
        if (storicoField != null) storicoField.setText("");
        if (tipoAbbComboBox != null) tipoAbbComboBox.setSelectedIndex(0);
        if (categoriaAbbComboBox != null) categoriaAbbComboBox.setSelectedIndex(0);
        if (scadenzaValueLabel != null) scadenzaValueLabel.setText("N/A");
        if (statoValueLabel != null) statoValueLabel.setText("N/A");
    }
    
    @Override
    public void setPresenter(IscrittiPresenter presenter) {
        this.presenter = presenter;
    }
    
    /**
     * Restituisce il presenter associato a questa view
     * @return Il presenter
     */
    public IscrittiPresenter getPresenter() {
        return presenter;
    }
    
    /**
     * Ottiene i tipi di abbonamento tramite il presenter
     * @return Lista dei tipi di abbonamento
     */
    private List<String> getTipiAbbonamento() {
        return presenter.getTipiAbbonamento();
    }
    
    /**
     * Ottiene le categorie di abbonamento tramite il presenter
     * @return Lista delle categorie di abbonamento
     */
    private List<String> getCategorieAbbonamento() {
        return presenter.getCategorieAbbonamento();
    }
}
