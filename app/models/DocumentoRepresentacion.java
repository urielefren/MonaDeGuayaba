package models;

import com.avaje.ebean.Model;
import java.util.Date;
import javax.persistence.*;

@Entity
public class DocumentoRepresentacion extends Documento {

    public Long idAlgo;

    @ManyToOne
    public Defensa defensa;

}