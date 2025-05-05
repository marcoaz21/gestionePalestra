package presenter;

import model.CsvManager;
import model.iscritto;
import view.AggiungiView;

import javax.swing.*;
import java.util.List;

public class AggiungiPresenterImpl implements AggiungiPresenter {
    private final AggiungiView view;

    public AggiungiPresenterImpl(AggiungiView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void aggiungiIscritto(String nome, String cognome, String codice, 
                               String tipoAbbonamento, String categoriaAbbonamento) {
        if (CsvManager.cercaIscritto(codice) != null) {
            view.mostraMessaggio("Il codice " + codice + " è già utilizzato.", 
                               "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        iscritto nuovoIscritto = new iscritto(nome, cognome, codice, "SI", "");

        nuovoIscritto.aggiungiAbbonamento(tipoAbbonamento, categoriaAbbonamento);
        
        boolean salvato = CsvManager.salvaIscritto(nuovoIscritto);
        
        if (salvato) {
            view.mostraMessaggio("Iscritto aggiunto con successo!", 
                               "Successo", JOptionPane.INFORMATION_MESSAGE);
            view.pulisciForm();
        } else {
            view.mostraMessaggio("Errore durante il salvataggio dell'iscritto.", 
                               "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<String> getTipiAbbonamento() {
        return CsvManager.getTipiAbbonamento();
    }

    @Override
    public List<String> getCategorieAbbonamento() {
        return CsvManager.getCategorieAbbonamento();
    }
}