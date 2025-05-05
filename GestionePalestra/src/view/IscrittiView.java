package view;

import model.iscritto;
import presenter.IscrittiPresenter;

import java.util.List;

public interface IscrittiView {
    void setPresenter(IscrittiPresenter presenter);
    
    void mostraIscritti(List<iscritto> iscritti);
    
    void mostraDettagliIscritto(iscritto iscritto);
    
    void mostraMessaggio(String messaggio, String titolo, int tipo);
    
    void pulisciForm();
}