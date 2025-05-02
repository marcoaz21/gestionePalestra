package view;

import model.iscritto;
import java.util.List;


public interface IscrittiView {
    void mostraIscritti(List<iscritto> iscritti);
    
    void mostraMessaggio(String messaggio, String titolo, int tipo);
    
    void mostraDettagliIscritto(iscritto iscritto);
    
    void pulisciForm();
    
    void setPresenter(presenter.IscrittiPresenter presenter);
}