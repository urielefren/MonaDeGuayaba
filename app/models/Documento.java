package models;
import java.io.File;

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.libs.Json;
import play.Logger;

import com.avaje.ebean.Model;
import com.avaje.ebean.ExpressionList;
import static com.avaje.ebean.Expr.eq;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.ManyToOne;
import java.lang.reflect.Field;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

//**********Modificada Por: UrielEfren

//Clase padre de todos los modelos de Documentos en la App
@MappedSuperclass
public abstract class Documento extends Model {

    /***************************************************************/
    /***************************************************************/
    /***************************************************************/
    //Reflections
    @Id
    public int id;

    public String uuidEcm;

    public String pathEcm;

    public String nameEcm;

    public String contentType;

    //metodo que devuelve los atributos declarados en la clase dada
    public static List<Field> retrieveAllFields(List<Field> fields, Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            if(!field.getName().toUpperCase().contains("EBEAN") && !field.getType().toString().contains("com.avaje.ebean.Model$Finder")){
                fields.add(field);
            }
        }

        if (type.getSuperclass() != null) {
            fields = retrieveAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public static  void listFields(List<Field> fields) {
        try{
            for (Field field : fields) {
                //field.setAccessible(true); // if you want to modify private fields
                System.out.println(field.getName()
                        + " - " + field.getType());
                        //+ " - " + field.get(this));
            }
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public static String retrieveNameClass(Object obj){
        Class<?> enclosingClass = obj.getClass().getEnclosingClass();
        if (enclosingClass != null) {
          return enclosingClass.getName();
        } else {
          return obj.getClass().getName();
        }
    }

    //******************metodo para listar documentos en base de datos.*****************************
    public static ObjectNode listDocumentsByDocumentType(Class<? extends Model> docClass, Map<String, Object>whereClause){
        Finder<Long, ?> find = new Finder<>(docClass);
        ExpressionList<?> myQuery = find.where();
        Iterator it = whereClause.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (pair.getKey().toString() != null && pair.getValue() != null) {
                myQuery.add(eq(pair.getKey().toString(), pair.getValue()));
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        List<?> resQuery = myQuery.findList();
        ArrayList<HashMap<String, String>> listaDocumentos= new ArrayList<HashMap<String, String>>();
        for (Object item : resQuery) {
            Documento docActual = (Documento)item;
            //System.out.println("Class::"+docActual.getClass().getName());
            HashMap<String, String> obj = new HashMap<String, String>();
            obj.put("name", docActual.nameEcm);
            obj.put("id", String.valueOf(docActual.id));
            obj.put("url", docActual.infoUrlDownloadFile(docActual.nameEcm, docActual.uuidEcm, docActual.contentType));
            obj.put("contentType", docActual.contentType);
            listaDocumentos.add(obj);
        }
        ObjectNode json = Json.newObject();
        json.set("files", Json.toJson(listaDocumentos));
        return json;
    }

    //******************metodo para insertar documentos en base de datos.*****************************
    public static void insertarDocumento(Class classDocumento, Map<String, Object> atributos, MultipartFormData body, String pathEcm) throws Exception{
        ArrayList<Map<String, Object>> documents = Documento.attachDocs(body, pathEcm);
        if(documents!=null && !documents.isEmpty()){
            for (Map<String, Object> document : documents) {
                if(!atributos.isEmpty()){
                    Iterator it = atributos.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        if(pair.getKey()!=null && pair.getValue()!=null){
                            document.put(pair.getKey().toString(), pair.getValue());
                        }
                    }
                }
                insertDocumento(classDocumento, document);
            }
        }
    }

    //******************metodo para insertar un documento en base de datos.*****************************
    public static void insertarUnDocumento(String formAtributo, Class classDocumento, Map<String, Object> atributos, MultipartFormData body, String pathEcm) throws Exception{
        Map<String, Object> document = Documento.attachDoc(body, formAtributo, pathEcm);
        if(document!=null && !document.isEmpty()){
            if(!atributos.isEmpty()){
                Iterator it = atributos.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if(pair.getKey()!=null && pair.getValue()!=null){
                        document.put(pair.getKey().toString(), pair.getValue());
                    }
                }
            }
            insertDocumento(classDocumento, document);
        }
    }

    public static void insertDocumento(Class docClass, Map<String, Object> atributos) throws Exception{
        if(atributos!=null && atributos.size()>0){
            Iterator it = atributos.entrySet().iterator();
            boolean b= false;
            Object objetoDoc = docClass.newInstance();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                //System.out.println(pair.getKey() + " = " + pair.getValue());
                List<Field> fields = new LinkedList<Field>();
                fields= retrieveAllFields(fields, docClass);
                for(Field field: fields){
                    //System.out.println("|"+field.getName().toLowerCase()+" ----- "+pair.getKey()+"|");
                    if(field.getName().toLowerCase().equals(pair.getKey().toString().toLowerCase())){
                        //System.out.println("*********seteado**********");
                        field.setAccessible(true);
                        field.set(docClass.cast(objetoDoc), pair.getValue());
                        field.setAccessible(false);
                        b=true;
                        break;
                    }
                }
                it.remove(); // avoids a ConcurrentModificationException
            }
            //System.out.println("b:"+b);
            //System.out.println("obj:"+docClass.cast(objetoDoc).getClass().getName());
            Documento docu= (Documento)objetoDoc;
            if(b && docu!=null){
                //System.out.println("*********insertado**********");
                docu.save();
            }
        }
    }
    /***************************************************************/
    /***************************************************************/
    /***************************************************************/
    /***************************************************************/
}