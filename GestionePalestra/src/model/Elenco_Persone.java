package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Elenco_Persone {
   private final ArrayList<iscritto> _elenco;

   public Elenco_Persone(){
       this._elenco= new ArrayList<>();
   }
    public void add( iscritto p){
        if(p!=null) this._elenco.add(p);
    }
    public int find (String uni ){
        for(int i=0; i < getlen();i++)
        {
            if(this._elenco.get(i).getCodice_Uni().equals(uni)) return i;
        }
        return -1;
    }
    public int getlen()
    {
        return _elenco.size();
    }
    public iscritto getElemento(int posizione)
    {
        return _elenco.get(posizione);
    }
}
