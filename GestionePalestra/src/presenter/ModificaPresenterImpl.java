package presenter;

import model.CsvManager;
import model.iscritto;
import view.ModificaView;

import javax.swing.*;
import java.util.List;

public class ModificaPresenterImpl implements ModificaPresenter {
    private final ModificaView view;

    public ModificaPresenterImpl(ModificaView view) {
        this.view = view;
        this.view.setPresenter(this);
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
        } else {
            view.mostraMessaggio("Errore durante la modifica dell'iscritto.", 
                               "Errore", JOptionPane.ERROR_MESSAGE);
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