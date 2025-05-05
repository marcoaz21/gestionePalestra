package view;

import model.iscritto;
import presenter.CercaPresenter;

public interface CercaView {
    void mostraDettagliIscritto(iscritto iscritto);
    
    void mostraMessaggio(String messaggio, String titolo, int tipo);
    
    void setPresenter(CercaPresenter presenter);
}