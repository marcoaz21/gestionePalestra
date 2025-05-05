package model;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CsvManager {
    private static final String CSV_FILE_PATH = "iscritti.csv";
    private static final String CSV_SEPARATOR = ",";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    
    public static boolean salvaIscritto(iscritto nuovoIscritto) {
        try {
            File file = new File(CSV_FILE_PATH);
            boolean fileEsistente = file.exists();
            
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            if (!fileEsistente) {
                bufferedWriter.write("Nome,Cognome,Codice_Uni,Abb_Attivo,Storico,DataInizio,DataScadenza,TipoAbbonamento,CategoriaAbbonamento");
                bufferedWriter.newLine();
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append(nuovoIscritto.getNome()).append(CSV_SEPARATOR)
              .append(nuovoIscritto.getCognome()).append(CSV_SEPARATOR)
              .append(nuovoIscritto.getCodice_Uni()).append(CSV_SEPARATOR)
              .append(nuovoIscritto.getAbb_Attivo()).append(CSV_SEPARATOR)
              .append(nuovoIscritto.getStorico()).append(CSV_SEPARATOR);
            
            if (nuovoIscritto.getDataInizioAbbonamento() != null) {
                sb.append(nuovoIscritto.getDataInizioAbbonamento().format(DATE_FORMATTER));
            } else {
                sb.append("");
            }
            sb.append(CSV_SEPARATOR);
            
            if (nuovoIscritto.getDataScadenzaAbbonamento() != null) {
                sb.append(nuovoIscritto.getDataScadenzaAbbonamento().format(DATE_FORMATTER));
            } else {
                sb.append("");
            }
            sb.append(CSV_SEPARATOR);
            
            sb.append(nuovoIscritto.getTipoAbbonamento()).append(CSV_SEPARATOR)
              .append(nuovoIscritto.getCategoriaAbbonamento());
            
            bufferedWriter.write(sb.toString());
            bufferedWriter.newLine();
            bufferedWriter.close();
            
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public static List<iscritto> leggiIscritti() {
        List<iscritto> iscritti = new ArrayList<>();
        
        try {
            File file = new File(CSV_FILE_PATH);
            if (!file.exists()) {
                return iscritti;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                String[] data = line.split(CSV_SEPARATOR);
                if (data.length >= 5) {
                    iscritto i = new iscritto(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                    );
                    
                    if (data.length >= 9) {
                        try {
                            if (!data[5].isEmpty()) {
                                i.setDataInizioAbbonamento(LocalDate.parse(data[5], DATE_FORMATTER));
                            }
                            
                            if (!data[6].isEmpty()) {
                                i.setDataScadenzaAbbonamento(LocalDate.parse(data[6], DATE_FORMATTER));
                            }
                            
                            i.setTipoAbbonamento(data[7]);
                            i.setCategoriaAbbonamento(data[8]);
                            
                            i.sincronizzaAbbonamento();
                        } catch (DateTimeParseException e) {
                            System.err.println("Errore nel parsing della data: " + e.getMessage());
                        }
                    }
                    
                    iscritti.add(i);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Errore di I/O durante la lettura del file: " + e.getMessage());
        }
        
        return iscritti;
    }
    
    public static iscritto cercaIscritto(String codiceUni) {
        List<iscritto> iscritti = leggiIscritti();
        
        for (iscritto i : iscritti) {
            if (i.getCodice_Uni().equals(codiceUni)) {
                return i;
            }
        }
        
        return null;
    }
    
    public static boolean eliminaIscritto(String codiceUni) {
        if (codiceUni == null || codiceUni.isEmpty()) {
            return false;
        }
        
        List<iscritto> iscritti = leggiIscritti();
        boolean trovato = false;
        
        for (int i = 0; i < iscritti.size(); i++) {
            if (iscritti.get(i).getCodice_Uni().equals(codiceUni)) {
                iscritti.remove(i);
                trovato = true;
                break;
            }
        }
        
        if (trovato) {
            try (FileWriter fileWriter = new FileWriter(CSV_FILE_PATH);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                
                bufferedWriter.write("Nome,Cognome,Codice_Uni,Abb_Attivo,Storico,DataInizio,DataScadenza,TipoAbbonamento,CategoriaAbbonamento");
                bufferedWriter.newLine();
                
                for (iscritto i : iscritti) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(i.getNome()).append(CSV_SEPARATOR)
                      .append(i.getCognome()).append(CSV_SEPARATOR)
                      .append(i.getCodice_Uni()).append(CSV_SEPARATOR)
                      .append(i.getAbb_Attivo()).append(CSV_SEPARATOR)
                      .append(i.getStorico() != null ? i.getStorico() : "").append(CSV_SEPARATOR);
                    
                    if (i.getDataInizioAbbonamento() != null) {
                        sb.append(i.getDataInizioAbbonamento().format(DATE_FORMATTER));
                    } else {
                        sb.append("");
                    }
                    sb.append(CSV_SEPARATOR);
                    
                    if (i.getDataScadenzaAbbonamento() != null) {
                        sb.append(i.getDataScadenzaAbbonamento().format(DATE_FORMATTER));
                    } else {
                        sb.append("");
                    }
                    sb.append(CSV_SEPARATOR);
                    
                    sb.append(i.getTipoAbbonamento() != null ? i.getTipoAbbonamento() : "").append(CSV_SEPARATOR)
                      .append(i.getCategoriaAbbonamento() != null ? i.getCategoriaAbbonamento() : "");
                    
                    bufferedWriter.write(sb.toString());
                    bufferedWriter.newLine();
                }
                
                return true;
            } catch (IOException e) {
                System.err.println("Errore di I/O durante l'eliminazione dell'iscritto: " + e.getMessage());
                return false;
            }
        }
        
        return false;
    }
    
    public static boolean modificaIscritto(iscritto iscrittoModificato) {
        iscrittoModificato.sincronizzaAbbonamento();
        
        if (iscrittoModificato.hasAbbonamentiAttivi()) {
            iscrittoModificato.setAbb_Attivo("SI");
        } else {
            iscrittoModificato.setAbb_Attivo("NO");
        }
        
        boolean eliminato = eliminaIscritto(iscrittoModificato.getCodice_Uni());
        
        if (eliminato) {
            return salvaIscritto(iscrittoModificato);
        }
        
        return false;
    }
    
    public static List<String> getTipiAbbonamento() {
        List<String> tipi = new ArrayList<>();
        tipi.add("Mensile");
        tipi.add("Annuale");
        return tipi;
    }
    
    public static List<String> getCategorieAbbonamento() {
        List<String> categorie = new ArrayList<>();
        categorie.add("Pesi");
        categorie.add("Zumba");
        categorie.add("Pilates");
        return categorie;
    }
    
    public static String generaCodiceUnivoco() {
        List<iscritto> iscritti = leggiIscritti();
        int maxCodice = 0;
        
        for (iscritto i : iscritti) {
            try {
                int codice = Integer.parseInt(i.getCodice_Uni());
                if (codice > maxCodice) {
                    maxCodice = codice;
                }
            } catch (NumberFormatException e) {
                System.err.println("Errore nel parsing del codice univoco: " + e.getMessage());
            }
        }
        
        return String.valueOf(maxCodice + 1);
    }
}

