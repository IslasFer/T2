package t2; 

import java.io.IOException;

/**
 * Main/launch program for Huff assignment. A better
 * comment than this is warranted.
 * @author YOU THE STUDENT
 *
 */
public class Huff {

    public static void main(String[] args) throws IOException{ 
        /****************************************************************************
        //********************** ORIGINAL ********************************************
        //****************************************************************************
        **/
        HuffViewer sv = new HuffViewer("Duke Compsci Huffing");
        IHuffProcessor proc = new SimpleHuffProcessor();
        sv.setModel(proc);
        
        /****************************************************************************
        ********************** MODIFICADO ********************************************
        ****************************************************************************
        * HuffMark HM = new HuffMark();
        HM.doMark();
        **/
        
        
        
    }
}
