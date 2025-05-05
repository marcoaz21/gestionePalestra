package view;

import model.iscritto;
import presenter.StampaPresenter;

import java.util.List;

public interface StampaView {
    void mostraMessaggio(String messaggio, String titolo, int tipo);

    void setPresenter(StampaPresenter presenter);
    
    void mostraIscritti(List<iscritto> iscritti);
}