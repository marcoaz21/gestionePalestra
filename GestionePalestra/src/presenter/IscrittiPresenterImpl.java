package presenter;

import model.CsvManager;
import model.iscritto;
import view.IscrittiView;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;


public class IscrittiPresenterImpl implements IscrittiPresenter {
    private final IscrittiView view;
    private List<iscritto> iscritti;

    public IscrittiPresenterImpl(IscrittiView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void caricaIscritti() {
        iscritti = CsvManager.leggiIscritti();
        view.mostraIscritti(iscritti);
    }

    @Override
    public void aggiungiIscritto(String nome, String cognome, String codice, 
                               String tipoAbbonamento, String categoriaAbbonamento) {
        // Verifica se il codice è già utilizzato
        if (CsvManager.cercaIscritto(codice) != null) {
            view.mostraMessaggio("Il codice " + codice + " è già utilizzato.", 
                               "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        iscritto nuovoIscritto = new iscritto(nome, cognome, codice, "SI", "");

        nuovoIscritto.aggiungiAbbonamento(tipoAbbonamento, categoriaAbbonamento);
        
        boolean salvato = CsvManager.salvaIscritto(nuovoIscritto);
        
        if (salvato) {
            view.mostraMessaggio("Iscritto aggiunto con successo!", 
                               "Successo", JOptionPane.INFORMATION_MESSAGE);
            caricaIscritti(); // Ricarica la lista
            view.pulisciForm();
        } else {
            view.mostraMessaggio("Errore durante il salvataggio dell'iscritto.", 
                               "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void modificaIscritto(iscritto iscritto, String nome, String cognome, 
                               String tipoAbbonamento, String categoriaAbbonamento) {
        iscritto.setNome(nome);
        iscritto.setCognome(cognome);
        
        if (!iscritto.getTipoAbbonamento().equals(tipoAbbonamento) ||
            !iscritto.getCategoriaAbbonamento().equals(categoriaAbbonamento)) {
            iscritto.setTipoAbbonamento(tipoAbbonamento);
            iscritto.setCategoriaAbbonamento(categoriaAbbonamento);
        }
        
        boolean salvato = CsvManager.modificaIscritto(iscritto);
        
        if (salvato) {
            view.mostraMessaggio("Iscritto modificato con successo!", 
                               "Successo", JOptionPane.INFORMATION_MESSAGE);
            caricaIscritti(); // Ricarica la lista
        } else {
            view.mostraMessaggio("Errore durante la modifica dell'iscritto.", 
                               "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void eliminaIscritto(iscritto iscritto) {
        int conferma = JOptionPane.showConfirmDialog(null,
                                                  "Sei sicuro di voler eliminare l'iscritto " + 
                                                  iscritto.getNome() + " " + iscritto.getCognome() + "?", 
                                                  "Conferma eliminazione", 
                                                  JOptionPane.YES_NO_OPTION);
        
        if (conferma == JOptionPane.YES_OPTION) {
            boolean eliminato = CsvManager.eliminaIscritto(iscritto.getCodice_Uni());
            
            if (eliminato) {
                view.mostraMessaggio("Iscritto eliminato con successo!", 
                                   "Successo", JOptionPane.INFORMATION_MESSAGE);
                caricaIscritti(); // Ricarica la lista
                view.pulisciForm();
            } else {
                view.mostraMessaggio("Errore durante l'eliminazione dell'iscritto.", 
                                   "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void cercaIscritto(String codice) {
        iscritto trovato = CsvManager.cercaIscritto(codice);
        
        if (trovato != null) {
            view.mostraDettagliIscritto(trovato);
        } else {
            view.mostraMessaggio("Nessun iscritto trovato con il codice " + codice, 
                               "Ricerca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void aggiungiAbbonamento(iscritto iscritto, String tipoAbbonamento, String categoriaAbbonamento) {
        iscritto.aggiornaStorico();
        
        iscritto.aggiungiAbbonamento(tipoAbbonamento, categoriaAbbonamento);
        
        boolean salvato = CsvManager.modificaIscritto(iscritto);
        
        if (salvato) {
            List<iscritto.Abbonamento> abbonamentiAttivi = iscritto.getAbbonamentiAttivi();
            
            StringBuilder messaggioAbbonamenti = new StringBuilder();
            messaggioAbbonamenti.append("Nuovo abbonamento aggiunto con successo!\n\n");
            messaggioAbbonamenti.append("Abbonamenti attivi:\n");
            
            for (iscritto.Abbonamento abb : abbonamentiAttivi) {
                messaggioAbbonamenti.append("- Tipo: ").append(abb.getTipo())
                                   .append(", Categoria: ").append(abb.getCategoria())
                                   .append(", Scadenza: ").append(abb.getDataScadenza())
                                   .append("\n");
            }
            
            view.mostraMessaggio(messaggioAbbonamenti.toString(), 
                               "Successo", JOptionPane.INFORMATION_MESSAGE);
            
            iscritto aggiornato = CsvManager.cercaIscritto(iscritto.getCodice_Uni());
            if (aggiornato != null) {
                view.mostraDettagliIscritto(aggiornato);
            }
            
            caricaIscritti();
        } else {
            view.mostraMessaggio("Errore durante l'aggiunta del nuovo abbonamento.", 
                               "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<String> getTipiAbbonamento() {
        return CsvManager.getTipiAbbonamento();
    }

    @Override
    public List<String> getCategorieAbbonamento() {
        return CsvManager.getCategorieAbbonamento();
    }
}