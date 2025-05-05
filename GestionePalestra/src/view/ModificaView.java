package view;

import model.iscritto;
import presenter.ModificaPresenter;

public interface ModificaView {
    void mostraDettagliIscritto(iscritto iscritto);
    
    void mostraMessaggio(String messaggio, String titolo, int tipo);
    
    void pulisciForm();
    
    void setPresenter(ModificaPresenter presenter);
}