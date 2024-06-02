package server;

import java.io.File;
import java.io.FileNotFoundException;

public class DetectorDeArchivo {

    public static String detectarArchivo() throws FileNotFoundException {
    	String[] extensiones=ParametrosDeConexion.getExtensiones();
    	String[] archivos=ParametrosDeConexion.getArchivosLectura();
        Boolean encuentra = false;
        int i = 0;
        int j = 0;
        File archivo;
         while(i<extensiones.length && !encuentra) {
        	 archivo = new File(archivos[0] + extensiones[i]);
             if (archivo.exists()) {
                 encuentra=true;
             }
             else
            	 i++;
         }
         if(encuentra) {
        	 j++;
        	 while(j<archivos.length && encuentra) {
            	 archivo = new File(archivos[j] + extensiones[i]);
                 if (archivo.exists()) {
                     j++;
                 }
                 else
                	 encuentra=false;
             }
        	 if(j==archivos.length)
        		 j--;
         }
         else {
        	 throw new FileNotFoundException("configuracion");
         }
         
         if(!encuentra) {
        	 throw new FileNotFoundException(archivos[j]+extensiones[i]);
         }
        return extensiones[i];
    }
}
