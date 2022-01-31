package carole3d;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cohete extends Thread
{
    static Objeto3D Cuete;
    static int Fase = 1;
    
    public void Inicializar()
    {
        Cuete = new Objeto3D();
        Cuete.setRes(1200, 700);
        Cuete.setCentroGravedad(24, 23, 74); //Nuestras tranformaciones serán a partir de esta coordenada     
        //Coordenadas
        int P00[]  =   { 34,  33,   104 };   
        int P01[]  =   { 34,  13,   104 };
        int P02[]  =   { 14,  33,   104 };
        int P03[]  =   { 14,  13,   104 };                 
        int P05[]  =   { 34,  13,    14 };
        int P06[]  =   { 34,  33,    34 };
        int P07[]  =   { 24,  23,   124 };
        int P08[]  =   { 14,  13,   104 };
        int P09[]  =   { 14,  13,    14 };
        int P10[]  =   { 44,   3,     4 };
        int P11[]  =   { 34,  13,    34 };
        int P12[]  =   {  4,   3,     4 };
        int P13[]  =   { 14,  13,    34 };
        int P14[]  =   { 34,  33,    14 };
        int P15[]  =   { 14,  33,    14 };
        int P16[]  =   { 44,  43,     4 };
        int P17[]  =   { 14,  33,    34 };
        int P18[]  =   {  4,  43,     4 };        
        //Punta
        Cuete.addCara(P01, P07, P08, Color.red);
        Cuete.addCara(P00, P02, P07, Color.red);
        Cuete.addCara(P00, P01, P07, Color.red);        
        Cuete.addCara(P02, P07, P08, Color.red);
        //Cuerpo
        Cuete.addCara(P01, P03, P05, Color.white);
        Cuete.addCara(P00, P02, P15, Color.white);
        Cuete.addCara(P00, P01, P05, Color.white);
        Cuete.addCara(P02, P08, P15, Color.white);        
        Cuete.addCara(P00, P14, P15, Color.white);
        Cuete.addCara(P03, P09, P05, Color.white);
        Cuete.addCara(P00, P05, P14, Color.white);
        Cuete.addCara(P08, P09, P15, Color.white);        
        //Aletas
        Cuete.addCara(P05, P10, P11, Color.red);
        Cuete.addCara(P06, P14, P16, Color.red);
        Cuete.addCara(P09, P12, P13, Color.red);
        Cuete.addCara(P15, P17, P18, Color.red);
        
        Cuete.Traslacion( 900,700,1000);
        Cuete.Rotacion(90,0,0);
        Cuete.Escalacion(8,8,8); 
    }

    
    
    public void Dibujar (BufferedImage Buffer)
    {
        Cuete.drawCuerpo(Buffer);
    }    
       
    @Override
    public void run()
    {          
        do
        {
            if ( Fase <= 115)
            {
                Cuete.Escalacion( 0.98,0.98,0.98);
                Cuete.Traslacion( 0, -19, 0);
                Cuete.Rotacion(   0,  20, 0);
                
            }
            else if ( Fase == 116)
            {
                Carole3D.Escena = 2;
                Cuete.Traslacion(1000, 2000, 0);
                Cuete.Escalacion(2,2,2);
                Cuete.Rotacion(0, 30, 0);
                Cuete.setCentroGravedad(1000, 500, 74);
            }
            else
            {
                Cuete.Rotacion(0, 0, -5);
            }
            System.out.println(Fase);
            Fase++;
            
            

            try {
                Thread.sleep(70);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cohete.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        while (true);
    }    
}