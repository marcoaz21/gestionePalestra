package presenter;

import model.iscritto;
import java.util.List;

public interface ModificaPresenter {
    void modificaIscritto(iscritto iscritto, String nome, String cognome, 
                         String tipoAbbonamento, String categoriaAbbonamento);
    
    List<String> getTipiAbbonamento();
    
    List<String> getCategorieAbbonamento();
    
    void aggiungiAbbonamento(iscritto iscritto, String tipoAbbonamento, String categoriaAbbonamento);
}