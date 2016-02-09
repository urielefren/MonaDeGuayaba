package models;

import com.avaje.ebean.Model;
import java.util.Date;
import javax.persistence.*;
import java.util.*;

@Entity
public class DocumentoDefensa extends Documento {

    public Long idSeccion;

    @ManyToOne
    public Defensa defensa;

    public static Finder<Long, DocumentoDefensa> find = new Finder<>(DocumentoDefensa.class);

    public List<Object> retrieveExtraObjects(){
        System.out.println("______________________");
        List<Object> listaObjectos = new LinkedList<Object>();
        listaObjectos.add(idSeccion);
        listaObjectos.add(defensa);
        return listaObjectos;
    }

    public String saveExtraObjects(List<Object> objs){
        String res="";
        if(objs!=null && objs.size()>0){
            try{
                for(Object obj: objs){
                    if(obj.getClass().isInstance(Defensa.class)){
                        defensa = (Defensa) obj;
                    }else if(obj.getClass().isInstance(Defensa.class)){
                        
                    }
                }
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }else{
            res= "Lista Vacía";
        }
        return res; 
    }

}