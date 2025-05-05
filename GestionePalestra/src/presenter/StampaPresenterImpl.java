package presenter;

import model.CsvManager;
import model.iscritto;
import view.StampaView;

import java.util.List;

public class StampaPresenterImpl implements StampaPresenter {
    private final StampaView view;
    private List<iscritto> iscritti;

    public StampaPresenterImpl(StampaView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void caricaIscritti() {
        // Leggi tutti gli iscritti dal file CSV
        iscritti = CsvManager.leggiIscritti();
        
        // Aggiorna lo storico di ogni iscritto prima di visualizzarlo
        for (iscritto is : iscritti) {
            is.aggiornaStorico();
        }
        
        // Mostra gli iscritti nella vista
        view.mostraIscritti(iscritti);
    }
}