package presenter;

import model.CsvManager;
import model.iscritto;
import view.CercaView;

import javax.swing.*;

public class CercaPresenterImpl implements CercaPresenter {
    private final CercaView view;

    public CercaPresenterImpl(CercaView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void cercaIscritto(String codice) {
        iscritto trovato = CsvManager.cercaIscritto(codice);
        
        if (trovato != null) {
            view.mostraDettagliIscritto(trovato);
        } else {
            view.mostraMessaggio("Nessun iscritto trovato con il codice " + codice, 
                               "Ricerca", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}