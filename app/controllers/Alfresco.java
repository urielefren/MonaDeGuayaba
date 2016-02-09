package controllers;

import models.*;
import java.util.*;
import java.lang.reflect.Field;

public class Alfresco{

	public static void insertDocumento(Class docClass, Map<String, Object> atributos) throws Exception{
		Iterator it = atributos.entrySet().iterator();
		boolean b= false;
		Object objetoDoc = docClass.newInstance();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        List<Field> fields = new LinkedList<Field>();
    		fields= Documento.retrieveAllFields(fields, docClass);
    		for(Field field: fields){
    			System.out.println("|"+field.getName().toLowerCase()+" ----- "+pair.getKey()+"|");
    			if(field.getName().toLowerCase().equals(pair.getKey().toString().toLowerCase())){
    				System.out.println("*********seteado**********");
	    			field.setAccessible(true);
	    			field.set(docClass.cast(objetoDoc), pair.getValue());
	    			field.setAccessible(false);
	    			b=true;
	    			break;
	    		}
    		}
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    System.out.println("b:"+b);
	    System.out.println("obj:"+docClass.cast(objetoDoc).getClass().getName());
	    Documento docu= (Documento)objetoDoc;
	    if(b && docu!=null){
	    	System.out.println("*********insertado**********");
    		docu.save();
    	}
	}
}