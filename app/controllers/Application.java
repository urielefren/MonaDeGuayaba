package controllers;

import play.*;
import play.mvc.*;
import models.*;
import com.avaje.ebean.Model;

import views.html.*;
import java.util.*;

import java.lang.reflect.Field;
import controllers.Alfresco;

public class Application extends Controller {

    public Result index() {
    	// DocumentoDefensa doc = new DocumentoDefensa();
    	// doc.nameEcm = "prueba2";
    	Defensa defensa = Defensa.find.byId(15L);
    	// doc.defensa = defensa;
    	// doc.idSeccion = 5L;
    	// doc.save();
    	Map<String, Object> atr = new HashMap<String, Object>();
    	atr.put("nameEcm", "pruebaReflect");
    	atr.put("defensa", defensa);
    	atr.put("idSeccion", new Long(3L));
    	Map<String, Object> atr2 = new HashMap<String, Object>();
    	atr2.put("nameEcm", "pruebaReflect2");
    	atr2.put("defensa", defensa);
    	atr2.put("idSeccion", new Long(8L));
    	Map<String, Object> atr3 = new HashMap<String, Object>();
    	atr3.put("nameEcm", "pruebaReflect3");
    	atr3.put("defensa", defensa);
    	atr3.put("idAlgo", new Long(9L));
    	atr3.put("uuidEcm", "2434fdfsf434rdf454ffdv45454tf");
    	try{
    		Alfresco.insertDocumento(DocumentoDefensa.class, atr);
    		Alfresco.insertDocumento(DocumentoDefensa.class, atr2);
    		Alfresco.insertDocumento(DocumentoRepresentacion.class, atr3);
    	}catch(Exception exc){
    		exc.printStackTrace();
    	}

    	// DocumentoDefensa docu= DocumentoDefensa.find.byId(1L);

    	// Documento doc = (Documento) docu;

    	// List<Object> lista= doc.retrieveExtraObjects();
    	// System.out.println("size:"+lista.size());
    	// for(Object obj: lista){
    	// 	System.out.println("class:"+Documento.retrieveNameClass(obj));
    	// }
        return ok(index.render("Your new application is ready."));
    }

}
