package presenter;

import model.iscritto;
import java.time.LocalDate;


public interface IscrittiPresenter {
    void caricaIscritti();
    
    void aggiungiIscritto(String nome, String cognome, String codice,
                         String tipoAbbonamento, String categoriaAbbonamento);
    
    void modificaIscritto(iscritto iscritto, String nome, String cognome,
                         String tipoAbbonamento, String categoriaAbbonamento);
    
    void eliminaIscritto(iscritto iscritto);
    
    void cercaIscritto(String codice);
    
    void aggiungiAbbonamento(iscritto iscritto, String tipoAbbonamento, String categoriaAbbonamento);
    
    java.util.List<String> getTipiAbbonamento();
    
    java.util.List<String> getCategorieAbbonamento();
}