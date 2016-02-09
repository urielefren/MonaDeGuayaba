package models;

import com.avaje.ebean.Model;
import javax.persistence.*;
import play.data.format.Formats;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Ebean;

@Entity
public class Defensa extends Model {

    @Id
    public Long id;

    public Boolean seguimientoEspecial;

    public String tipoAutoridad;

    public static Finder<Long, Defensa> find = new Finder<>(Defensa.class);
}