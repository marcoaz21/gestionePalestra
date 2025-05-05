package view;

import presenter.AggiungiPresenter;

public interface AggiungiView {
    void setPresenter(AggiungiPresenter presenter);
    
    void mostraMessaggio(String messaggio, String titolo, int tipo);
    
    void pulisciForm();
}