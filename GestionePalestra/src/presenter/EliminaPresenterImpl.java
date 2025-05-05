package presenter;

import model.CsvManager;
import model.iscritto;
import view.EliminaView;

import javax.swing.*;

public class EliminaPresenterImpl implements EliminaPresenter {
    private final EliminaView view;

    public EliminaPresenterImpl(EliminaView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void eliminaIscritto(iscritto iscritto) {
        int conferma = JOptionPane.showConfirmDialog(null,
                                                  "Sei sicuro di voler eliminare l'iscritto " + 
                                                  iscritto.getNome() + " " + iscritto.getCognome() + "?", 
                                                  "Conferma eliminazione", 
                                                  JOptionPane.YES_NO_OPTION);
        
        if (conferma == JOptionPane.YES_OPTION) {
            boolean eliminato = CsvManager.eliminaIscritto(iscritto.getCodice_Uni());
            
            if (eliminato) {
                view.mostraMessaggio("Iscritto eliminato con successo!", 
                                   "Successo", JOptionPane.INFORMATION_MESSAGE);
                view.pulisciForm();
            } else {
                view.mostraMessaggio("Errore durante l'eliminazione dell'iscritto.", 
                                   "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}