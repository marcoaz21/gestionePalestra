package view;

import model.iscritto;
import presenter.EliminaPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminaViewImpl implements EliminaView {
    private JPanel mainPanel;
    private JTextField codiceField;
    private JButton cercaButton;
    private JButton eliminaButton;
    private JButton indietroButton;
    private JLabel nomeLabel;
    private JLabel cognomeLabel;
    private JLabel abbonamentoLabel;
    private JFrame parentFrame;
    private EliminaPresenter presenter;
    private iscritto iscrittoSelezionato;

    public EliminaViewImpl(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        
        // Titolo
        JLabel titleLabel = new JLabel("Elimina Iscritto", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Pannello di ricerca
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel codiceLabel = new JLabel("Codice Univoco:");
        codiceField = new JTextField(10);
        cercaButton = new JButton("Cerca");
        
        searchPanel.add(codiceLabel);
        searchPanel.add(codiceField);
        searchPanel.add(cercaButton);
        
        // Pannello dettagli
        JPanel detailsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Dettagli Iscritto"));
        
        JLabel nomeTitleLabel = new JLabel("Nome:");
        nomeLabel = new JLabel("");
        JLabel cognomeTitleLabel = new JLabel("Cognome:");
        cognomeLabel = new JLabel("");
        JLabel abbonamentoTitleLabel = new JLabel("Abbonamento:");
        abbonamentoLabel = new JLabel("");
        
        detailsPanel.add(nomeTitleLabel);
        detailsPanel.add(nomeLabel);
        detailsPanel.add(cognomeTitleLabel);
        detailsPanel.add(cognomeLabel);
        detailsPanel.add(abbonamentoTitleLabel);
        detailsPanel.add(abbonamentoLabel);
        
        // Pannello bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        eliminaButton = new JButton("Elimina");
        indietroButton = new JButton("Indietro");
        
        buttonPanel.add(eliminaButton);
        buttonPanel.add(indietroButton);
        
        // Pannello centrale
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(detailsPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Disabilita il bottone elimina finché non viene trovato un iscritto
        eliminaButton.setEnabled(false);
        
        // Aggiungi listener
        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codice = codiceField.getText().trim();
                if (codice.isEmpty()) {
                    mostraMessaggio("Inserisci un codice univoco valido.", 
                                  "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Cerca l'iscritto tramite il model
                iscrittoSelezionato = model.CsvManager.cercaIscritto(codice);
                
                if (iscrittoSelezionato != null) {
                    // Mostra i dettagli dell'iscritto
                    nomeLabel.setText(iscrittoSelezionato.getNome());
                    cognomeLabel.setText(iscrittoSelezionato.getCognome());
                    
                    // Mostra i dettagli dell'abbonamento
                    if (iscrittoSelezionato.hasAbbonamentiAttivi()) {
                        abbonamentoLabel.setText("<html>" + iscrittoSelezionato.getAbbonamentiAttiviFormattati().replace("\n", "<br>") + "</html>");
                    } else {
                        abbonamentoLabel.setText("Nessun abbonamento attivo");
                    }
                    
                    // Abilita il bottone elimina
                    eliminaButton.setEnabled(true);
                } else {
                    mostraMessaggio("Nessun iscritto trovato con il codice " + codice, 
                                  "Ricerca", JOptionPane.INFORMATION_MESSAGE);
                    pulisciForm();
                }
            }
        });
        
        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iscrittoSelezionato != null) {
                    presenter.eliminaIscritto(iscrittoSelezionato);
                }
            }
        });
        
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Torna al menu principale
                parentFrame.getContentPane().removeAll();
                design mainDesign = new design(parentFrame);
                parentFrame.setContentPane(mainDesign.getPanel());
                
                // Carica gli iscritti nel presenter
                mainDesign.getPresenter().caricaIscritti();
                
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }
    
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void setPresenter(EliminaPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void mostraMessaggio(String messaggio, String titolo, int tipo) {
        JOptionPane.showMessageDialog(parentFrame, messaggio, titolo, tipo);
    }

    @Override
    public void pulisciForm() {
        codiceField.setText("");
        nomeLabel.setText("");
        cognomeLabel.setText("");
        abbonamentoLabel.setText("");
        eliminaButton.setEnabled(false);
        iscrittoSelezionato = null;
    }
}