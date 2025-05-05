package view;

import presenter.EliminaPresenter;

public interface EliminaView {
    void setPresenter(EliminaPresenter presenter);
    
    void mostraMessaggio(String messaggio, String titolo, int tipo);
    
    void pulisciForm();
}