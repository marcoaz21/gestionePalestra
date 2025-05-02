package model;
import java.time.LocalDate;

public class Abbonamento
{
    private String Pesi_Mensile;
    private String Pesi_Anno;
    private String Zumba_Mensile;
    private String Zumba_Anno;
    private String Pilates_Mensile;
    private String Pilates_Anno;

    private String dataInizioPesi;
    private String dataFinePesi;
    private String dataInizioZumba;
    private String dataFineZumba;
    private String dataInizioPilates;
    private String dataFinePilates;

    public Abbonamento(String pesiMensile, String pesiAnno, String zumbaMensile, String zumbaAnno, String pilatesMensile, String pilatesAnno, LocalDate parse, LocalDate parse1) {
    }

    public String getDataInizioPesi() {
        return dataInizioPesi;
    }

    public void setDataInizioPesi(String dataInizioPesi) {
        this.dataInizioPesi = dataInizioPesi;
    }

    public String getDataFinePesi() {
        return dataFinePesi;
    }

    public void setDataFinePesi(String dataFinePesi) {
        this.dataFinePesi = dataFinePesi;
    }

    public String getDataInizioZumba() {
        return dataInizioZumba;
    }

    public void setDataInizioZumba(String dataInizioZumba) {
        this.dataInizioZumba = dataInizioZumba;
    }

    public String getDataFineZumba() {
        return dataFineZumba;
    }

    public void setDataFineZumba(String dataFineZumba) {
        this.dataFineZumba = dataFineZumba;
    }

    public String getDataInizioPilates() {
        return dataInizioPilates;
    }

    public void setDataInizioPilates(String dataInizioPilates) {
        this.dataInizioPilates = dataInizioPilates;
    }

    public String getDataFinePilates() {
        return dataFinePilates;
    }

    public void setDataFinePilates(String dataFinePilates) {
        this.dataFinePilates = dataFinePilates;
    }

    public String getPesiMensile() {
        return Pesi_Mensile;
    }

    public String getPesiAnno() {
        return Pesi_Anno;
    }

    public String getZumbaMensile() {
        return Zumba_Mensile;
    }

    public String getZumbaAnno() {
        return Zumba_Anno;
    }

    public String getPilatesMensile() {
        return Pilates_Mensile;
    }

    public String getPilatesAnno() {
        return Pilates_Anno;
    }

    public Abbonamento(String Pesi_Mensile, String Pesi_Anno, String Zumba_Mensile, String Zumba_Anno, String Pilates_Mensile,
                       String Pilates_Anno) {
        this.Pesi_Anno = Pesi_Anno;
        this.Pesi_Mensile = Pesi_Mensile;
        this.Zumba_Mensile = Zumba_Mensile;
        this.Zumba_Anno = Zumba_Anno;
        this.Pilates_Mensile = Pilates_Mensile;
        this.Pilates_Anno = Pilates_Anno;


    }
}
