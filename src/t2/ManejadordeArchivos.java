/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author 
 */
public class ManejadordeArchivos {
   
    /**
     * Esete metodo se encarga de leer un archivo y lo regresa en un String
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @return contenido del archivo
     */
    public String getContenidoArchivo(String ruta) throws FileNotFoundException, IOException{
        StringBuilder s = new StringBuilder();
        String texto = null;
        
        
        FileReader lector = new FileReader(ruta);
        BufferedReader br = new BufferedReader(lector);
        boolean b = false;
        
        while((texto = br.readLine()) != null){
            s.append((b?"\n":"") + texto);
            b = true;
        }
            
        lector.close();
        return s.toString();
    }
    
    /**
     * Esete metodo se encarga de leer un archivo y lo regresa en un ArrayList<String>
     * cada elemento de la lista hace referencia a una linea del archivo
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @return contenido del archivo
     */
    public ArrayList<String> getContenidoArchivoToArray(String ruta) throws FileNotFoundException, IOException{
        ArrayList<String> al = new ArrayList<>();
        String texto;

        
        FileReader lector = new FileReader(ruta);
        BufferedReader br = new BufferedReader(lector);
        
        while((texto = br.readLine()) != null)
            al.add(texto);
            
        lector.close();
        return al;
    }
    
    /**
     * Esete metodo se encarga de leer un archivo codificado en Huffman
     * regresa un ArrayList<String> donde el primer string son las codificaciones 
     * y el segundo todos los datos.
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @return contenido del archivo
     */
    public ArrayList getContenidoArchivoCodificado(String ruta) throws FileNotFoundException, IOException{
        StringBuilder s = new StringBuilder();
        ArrayList<String> al = new ArrayList<>();
        String texto;

        FileReader lector = new FileReader(ruta);
        BufferedReader br = new BufferedReader(lector);
        
        //Leemos la primera linea del archivo -- linea de codificaciones
        texto = br.readLine();
        al.add(texto);
        
        //Leemos el resto del archivo
        while((texto = br.readLine()) != null)
            s.append(texto);
        al.add(s.toString());
        
        lector.close();
        return al;
    }
    
    /**
     * Este metodo crea o sobreescribe el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido caracteres que se excribiran en el archivo
     * @throws IOException 
     */
    public void setContenidoArchivo(String ruta, String contenido) throws IOException{
        //validamos el directorio de trabajo
        verificaDiretorioTrabajo(ruta.substring(0, ruta.lastIndexOf("/")));
        
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, false);
        fw.write(contenido);
        fw.close();
    }
    
    /**
     * Este metodo crea o sobreescribe el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido caracteres que se excribiran en el archivo
     * @throws IOException 
     */
    public void setContenidoArchivoInArray(String ruta, ArrayList<String> contenido) throws IOException{
        
        //validamos el directorio de trabajo
        verificaDiretorioTrabajo(ruta.substring(0, ruta.lastIndexOf("/")));
        
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, false);
        for(String s : contenido)
            fw.write(s + "\n");
        
        fw.close();
    }
    
    /**
     * Este metodo crea o agrega el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido caracteres que se excribiran en el archivo
     * @throws IOException 
     */
    public void agregaContenidoArchivo(String ruta, String contenido) throws IOException{
        
        //validamos el directorio de trabajo
        verificaDiretorioTrabajo(ruta.substring(0, ruta.lastIndexOf("/")));
        
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, true);
        fw.write("\n" + contenido);
        fw.close();
    }
    
    /**
     * Este metodo crea o agrega el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido caracteres que se excribiran en el archivo
     * @throws IOException 
     */
    public void agregaContenidoArchivoInArray(String ruta, ArrayList<String> contenido) throws IOException{
        
        //validamos el directorio de trabajo
        verificaDiretorioTrabajo(ruta.substring(0, ruta.lastIndexOf("/")));
        
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, true);
        fw.write("\n");
        for(String s : contenido)
            fw.write(s + "\n");
        
        fw.close();
    }
    
    /**
     * Este metodo crea o agrega el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido array de bytes que se excribiran en el archivo
     * @param codif array de bytes que representa las codificaciones    
     * @throws IOException 
     */
    public void agregaContenidoArchivoByte(String ruta, byte [] contenido, byte [] codif) throws IOException{
        
        //validamos el directorio de trabajo
        verificaDiretorioTrabajo(ruta.substring(0, ruta.lastIndexOf("/")));
        
        FileOutputStream fos = new FileOutputStream(ruta, true);
        DataOutputStream salida = new DataOutputStream(fos);
        
        if(codif != null)
            salida.write(codif);
        salida.write(contenido);
        salida.close();
    }
    
    /**
     * Este metodo crea o sobreescribe el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido array de bytes que se excribiran en el archivo
     * @param codif array de bytes que representa las codificaciones    
     * @throws IOException 
     */
    public void setContenidoArchivoByte(String ruta, byte [] contenido, byte [] codif) throws IOException{
        
        //validamos el directorio de trabajo
        verificaDiretorioTrabajo(ruta.substring(0, ruta.lastIndexOf("/")));
        
        FileOutputStream fos = new FileOutputStream(ruta, false);
        DataOutputStream salida = new DataOutputStream(fos);
        
        if(codif != null)
            salida.write(codif);
        salida.write(contenido);
        salida.close();
    }
    
    /**
     * Este archivo comprueba la existencia del
     * archivo que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @return True si el archivo existe
     */
    public boolean existeAchivo(String ruta){
        
        File f = new File(ruta);
        return f.exists();
        
    }
    
    /**
    * Este metodo verifica que el directorio de trabajo exista
    * y si no existe lo crea
    */
    private void verificaDiretorioTrabajo(String directorioTrabajo){
        File f = new File(directorioTrabajo);
        if(!f.exists())
            f.mkdirs();
    }

    public byte[] getContenidoArchivoBytes(String ruta) throws IOException{
        Path path = Paths.get(ruta);
        return Files.readAllBytes(path);
    }
}

