package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class iscritto {
    private String Nome;
    private String Cognome;
    private String Codice_Uni;
    private String Abb_Attivo;
    private String Storico;
    
    // Abbonamento principale (per compatibilità con il codice esistente)
    private LocalDate DataInizioAbbonamento;
    private LocalDate DataScadenzaAbbonamento;
    private String TipoAbbonamento; // "Mensile" o "Annuale"
    private String CategoriaAbbonamento; // "Pesi", "Zumba", "Pilates"
    
    // Lista di abbonamenti attivi
    private List<Abbonamento> abbonamenti;

    // Classe interna per rappresentare un abbonamento
    public static class Abbonamento {
        private LocalDate dataInizio;
        private LocalDate dataScadenza;
        private String tipo;
        private String categoria;
        
        public Abbonamento(String tipo, String categoria, LocalDate dataInizio) {
            this.tipo = tipo;
            this.categoria = categoria;
            this.dataInizio = dataInizio;
            
            // Calcola la data di scadenza
            if (tipo.equals("Mensile")) {
                this.dataScadenza = dataInizio.plusMonths(1);
            } else if (tipo.equals("Annuale")) {
                this.dataScadenza = dataInizio.plusYears(1);
            }
        }
        
        public LocalDate getDataInizio() {
            return dataInizio;
        }
        
        public LocalDate getDataScadenza() {
            return dataScadenza;
        }
        
        public String getTipo() {
            return tipo;
        }
        
        public String getCategoria() {
            return categoria;
        }
        
        public boolean isScaduto() {
            // Un abbonamento è scaduto se la data di scadenza è passata
            return dataScadenza != null && LocalDate.now().isAfter(dataScadenza);
        }
        
        @Override
        public String toString() {
            return "Tipo: " + tipo + ", Categoria: " + categoria + ", Periodo: " + 
                   dataInizio + " - " + dataScadenza;
        }
    }

    public iscritto(String Nome, String Cognome, String Codice_Uni, String Abb_Attivo, String Storico) {
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Codice_Uni = Codice_Uni;
        this.Abb_Attivo = Abb_Attivo;
        this.Storico = Storico;
        this.DataInizioAbbonamento = LocalDate.now();
        this.DataScadenzaAbbonamento = null;
        this.TipoAbbonamento = "";
        this.CategoriaAbbonamento = "";
        this.abbonamenti = new ArrayList<>();
    }


    public iscritto(String Nome, String Cognome, String Codice_Uni, String Abb_Attivo, String Storico,
                   String TipoAbbonamento, String CategoriaAbbonamento) {
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.Codice_Uni = Codice_Uni;
        this.Abb_Attivo = Abb_Attivo;
        this.Storico = Storico;
        this.DataInizioAbbonamento = LocalDate.now();
        this.TipoAbbonamento = TipoAbbonamento;
        this.CategoriaAbbonamento = CategoriaAbbonamento;
        this.abbonamenti = new ArrayList<>();
        
        // Calcola la data di scadenza in base al tipo di abbonamento
        if (TipoAbbonamento.equals("Mensile")) {
            this.DataScadenzaAbbonamento = DataInizioAbbonamento.plusMonths(1);
        } else if (TipoAbbonamento.equals("Annuale")) {
            this.DataScadenzaAbbonamento = DataInizioAbbonamento.plusYears(1);
        }
        
        // Aggiungi l'abbonamento principale alla lista degli abbonamenti
        if (!TipoAbbonamento.isEmpty() && !CategoriaAbbonamento.isEmpty()) {
            this.abbonamenti.add(new Abbonamento(TipoAbbonamento, CategoriaAbbonamento, DataInizioAbbonamento));
        }
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public String getCodice_Uni() {
        return Codice_Uni;
    }

    public void setCodice_Uni(String codice_Uni) {
        Codice_Uni = codice_Uni;
    }

    public String getAbb_Attivo() {
        return Abb_Attivo;
    }

    public void setAbb_Attivo(String abb_Attivo) {
        Abb_Attivo = abb_Attivo;
    }

    public String getStorico() {
        return Storico;
    }

    public void setStorico(String storico) {
        Storico = storico;
    }

    public LocalDate getDataInizioAbbonamento() {
        return DataInizioAbbonamento;
    }

    public void setDataInizioAbbonamento(LocalDate dataInizioAbbonamento) {
        DataInizioAbbonamento = dataInizioAbbonamento;
    }

    public LocalDate getDataScadenzaAbbonamento() {
        return DataScadenzaAbbonamento;
    }

    public void setDataScadenzaAbbonamento(LocalDate dataScadenzaAbbonamento) {
        DataScadenzaAbbonamento = dataScadenzaAbbonamento;
    }

    public String getTipoAbbonamento() {
        return TipoAbbonamento;
    }

    public void setTipoAbbonamento(String tipoAbbonamento) {
        TipoAbbonamento = tipoAbbonamento;
        
        // Aggiorna la data di scadenza quando cambia il tipo di abbonamento
        if (DataInizioAbbonamento != null) {
            if (TipoAbbonamento.equals("Mensile")) {
                this.DataScadenzaAbbonamento = DataInizioAbbonamento.plusMonths(1);
            } else if (TipoAbbonamento.equals("Annuale")) {
                this.DataScadenzaAbbonamento = DataInizioAbbonamento.plusYears(1);
            }
        }
    }

    public String getCategoriaAbbonamento() {
        return CategoriaAbbonamento;
    }

    public void setCategoriaAbbonamento(String categoriaAbbonamento) {
        CategoriaAbbonamento = categoriaAbbonamento;
    }
    
    // Metodo per sincronizzare l'abbonamento principale con la lista degli abbonamenti
    public void sincronizzaAbbonamento() {
        // Se abbiamo un abbonamento principale valido ma non è nella lista, aggiungiamolo
        if (DataInizioAbbonamento != null && !TipoAbbonamento.isEmpty() && !CategoriaAbbonamento.isEmpty()) {
            // Controlla se l'abbonamento principale è già nella lista
            boolean trovato = false;
            for (Abbonamento abb : abbonamenti) {
                if (abb.getTipo().equals(TipoAbbonamento) && 
                    abb.getCategoria().equals(CategoriaAbbonamento) &&
                    abb.getDataInizio().equals(DataInizioAbbonamento)) {
                    trovato = true;
                    break;
                }
            }
            
            // Se non è nella lista, aggiungilo
            if (!trovato) {
                Abbonamento nuovoAbb = new Abbonamento(TipoAbbonamento, CategoriaAbbonamento, DataInizioAbbonamento);
                abbonamenti.add(nuovoAbb);
            }
        }
        
        // Aggiorna lo stato dell'abbonamento attivo
        // Verifica direttamente senza chiamare hasAbbonamentiAttivi() per evitare ricorsione
        boolean hasActive = false;
        for (Abbonamento abb : abbonamenti) {
            if (!abb.isScaduto()) {
                hasActive = true;
                break;
            }
        }
        
        if (hasActive) {
            Abb_Attivo = "SI";
        } else {
            Abb_Attivo = "NO";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        iscritto other = (iscritto) obj;
        return Codice_Uni != null && Codice_Uni.equals(other.Codice_Uni);
    }
    
    // Metodo per clonare un iscritto (utile per preservare i dati durante le modifiche)
    public iscritto clone() {
        iscritto clone = new iscritto(Nome, Cognome, Codice_Uni, Abb_Attivo, Storico);
        clone.setDataInizioAbbonamento(DataInizioAbbonamento);
        clone.setDataScadenzaAbbonamento(DataScadenzaAbbonamento);
        clone.setTipoAbbonamento(TipoAbbonamento);
        clone.setCategoriaAbbonamento(CategoriaAbbonamento);
        
        // Clona anche tutti gli abbonamenti
        for (Abbonamento abb : abbonamenti) {
            Abbonamento abbClone = new Abbonamento(
                abb.getTipo(), 
                abb.getCategoria(), 
                abb.getDataInizio()
            );
            clone.abbonamenti.add(abbClone);
        }
        
        return clone;
    }
    
    // Metodo per verificare se l'abbonamento principale è scaduto
    public boolean isAbbonamentoScaduto() {
        if (DataScadenzaAbbonamento == null) {
            return true; // Se non c'è data di scadenza, consideriamo l'abbonamento scaduto
        }
        return LocalDate.now().isAfter(DataScadenzaAbbonamento);
    }
    
    // Metodo per ottenere la data di scadenza formattata come stringa
    public String getDataScadenzaFormattata() {
        if (DataScadenzaAbbonamento == null) {
            return "Non impostata";
        }
        return DataScadenzaAbbonamento.toString();
    }
    
    // Metodo per aggiungere un nuovo abbonamento con la data corrente
    public void aggiungiAbbonamento(String tipo, String categoria) {
        aggiungiAbbonamento(tipo, categoria, LocalDate.now());
    }
    
    // Metodo per aggiungere un nuovo abbonamento con una data specifica
    public void aggiungiAbbonamento(String tipo, String categoria, LocalDate dataInizio) {
        // Prima sincronizza per assicurarsi che tutti gli abbonamenti esistenti siano nella lista
        sincronizzaAbbonamento();
        
        // Crea e aggiungi il nuovo abbonamento
        Abbonamento nuovoAbb = new Abbonamento(tipo, categoria, dataInizio);
        abbonamenti.add(nuovoAbb);
        
        // Aggiorna anche l'abbonamento principale per compatibilità
        // ma solo se non ci sono altri abbonamenti attivi
        if (getAbbonamentiAttivi().size() <= 1) {
            this.TipoAbbonamento = tipo;
            this.CategoriaAbbonamento = categoria;
            this.DataInizioAbbonamento = nuovoAbb.getDataInizio();
            this.DataScadenzaAbbonamento = nuovoAbb.getDataScadenza();
        }
        
        // Imposta Abb_Attivo a "SI" se c'è almeno un abbonamento attivo
        if (hasAbbonamentiAttivi()) {
            this.Abb_Attivo = "SI";
        }
    }
    
    // Metodo per ottenere tutti gli abbonamenti attivi
    public List<Abbonamento> getAbbonamentiAttivi() {
        // Non chiamiamo sincronizzaAbbonamento() qui per evitare ricorsione
        
        List<Abbonamento> attivi = new ArrayList<>();
        for (Abbonamento abb : abbonamenti) {
            if (!abb.isScaduto()) {
                attivi.add(abb);
            }
        }
        
        // Ordina gli abbonamenti per data di scadenza (i più recenti prima)
        attivi.sort((a, b) -> b.getDataInizio().compareTo(a.getDataInizio()));
        
        return attivi;
    }
    
    // Metodo per ottenere tutti gli abbonamenti scaduti
    public List<Abbonamento> getAbbonamentiScaduti() {
        // Non chiamiamo sincronizzaAbbonamento() qui per evitare ricorsione
        
        List<Abbonamento> scaduti = new ArrayList<>();
        for (Abbonamento abb : abbonamenti) {
            if (abb.isScaduto()) {
                scaduti.add(abb);
            }
        }
        return scaduti;
    }
    
    // Metodo per verificare se ci sono abbonamenti attivi
    public boolean hasAbbonamentiAttivi() {
        // Verifica direttamente senza chiamare sincronizzaAbbonamento() per evitare ricorsione
        
        // Se non ci sono abbonamenti nella lista ma c'è un abbonamento principale valido
        if (abbonamenti.isEmpty() && DataScadenzaAbbonamento != null) {
            return !isAbbonamentoScaduto();
        }
        
        // Verifica direttamente se ci sono abbonamenti attivi
        for (Abbonamento abb : abbonamenti) {
            if (!abb.isScaduto()) {
                return true;
            }
        }
        
        return false;
    }
    
    // Metodo per ottenere una stringa formattata con tutti gli abbonamenti attivi
    public String getAbbonamentiAttiviFormattati() {
        List<Abbonamento> attivi = getAbbonamentiAttivi();
        if (attivi.isEmpty()) {
            return "Nessun abbonamento attivo";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < attivi.size(); i++) {
            sb.append(attivi.get(i).toString());
            if (i < attivi.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
    
    // Metodo per aggiornare lo storico con gli abbonamenti scaduti
    public void aggiornaStorico() {
        List<Abbonamento> scaduti = getAbbonamentiScaduti();
        if (!scaduti.isEmpty()) {
            StringBuilder nuovoStorico = new StringBuilder(Storico != null ? Storico : "");
            
            // Crea una copia della lista degli abbonamenti scaduti per evitare ConcurrentModificationException
            List<Abbonamento> scadutiCopy = new ArrayList<>(scaduti);
            
            for (Abbonamento abb : scadutiCopy) {
                if (nuovoStorico.length() > 0 && !nuovoStorico.toString().trim().isEmpty()) {
                    nuovoStorico.append("; ");
                }
                nuovoStorico.append(abb.toString());
                
                // Rimuovi l'abbonamento scaduto dalla lista
                abbonamenti.remove(abb);
            }
            
            Storico = nuovoStorico.toString();
        }
    }
}


