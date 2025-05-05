package presenter;

import java.util.List;

public interface AggiungiPresenter {
    void aggiungiIscritto(String nome, String cognome, String codice, 
                        String tipoAbbonamento, String categoriaAbbonamento);
    
    List<String> getTipiAbbonamento();
    
    List<String> getCategorieAbbonamento();
}