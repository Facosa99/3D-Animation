package carole3d;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite 
{
    BufferedImage Sprite;
    //Sp        = Abreviación para "Sprite"
    //SpX, SpY  = Coordenadas en pantalla de donde imprimir el Sprite
    //SpTam     = Tamaño del Sprite en pantalla
    //Spsec     = Coordenadas de la sección de la imagen original del sprite a mostrar
    public static int LinkX = -200,  LinkY = -200, TmLinkX = 100, TmLinkY = 100, LSecX1 = 29, LSecY1 = 29, LSecX2 = LSecX1+30, LSecY2 = LSecY1 - 30;
    int SpX1 = 100, SpY1 = 100, SpX2 = 500, SpY2 = 500, SpSecX1 = 1, SpSecY1= 1 , SpSecX2 = 400, SpSecY2 = 400;
    ImageObserver Monitor;
    BufferedImage Buffer;
    
    public void SetTamaño ( int X, int Y)
    {
        SpX2 = SpX1 + X;
        SpY2 = SpY1 + Y;
    }
    public void SetCoordenadas( int X, int Y)
    {
        int TamX, TamY; //Tam = Tamaño
        TamX = SpX2 - SpX1;        TamY = SpY2 - SpY1;
        
        SpX1 = X;   SpY1 = Y;
        SpX2 = SpX1 + TamX;        SpY2 = SpY1 + TamY;        
    }
    public void Traslacion( int X, int Y)
    {
        SpX1 = SpX1 + X;
        SpY1 = SpY1 + Y;
        SpX2 = SpX2 + X;
        SpY2 = SpY2 + Y;
    }
    public void Escalacion( double X, double Y)
    {
        if ( SpX1 == 0 || SpY1 == 0 || SpX2 == 0 || SpY2 == 0 )
        {
            Traslacion(1,1);
        }        
        SpX1 *= X;  SpY1 *= Y;  SpX2 *= X;  SpY2 *= Y;   
    }
    public void Escalacion( double S)
    {     
        if ( SpX1 == 0 || SpY1 == 0 || SpX2 == 0 || SpY2 == 0 )
        {
            Traslacion(1,1);
        }        
        SpX1 *= S;  SpY1 *= S;  SpX2 *= S;  SpY2 *= S;   
    }
    public void SelectFrame ( int Frame)
    {
        switch (Frame) {
            case 1:
                SpSecX1 = 1;
                SpSecY1 = 1;
                SpSecX2 = 400;
                SpSecY2 = 400;
                break;
            case 2:
                SpSecX1 = 401;
                SpSecY1 = 1;
                SpSecX2 = 800;
                SpSecY2 = 400;
                break;
            case 3:
                SpSecX1 = 801;
                SpSecY1 = 1;
                SpSecX2 = 1200;
                SpSecY2 = 400;
                break;
            default:
                break;
        }
    }

    public void CargarImagen( String Ruta)
    {
        try 
        {        
            Sprite            = ImageIO.read(getClass().getResource(Ruta));              
        } catch (IOException e) 
        {            
            e.printStackTrace();
        }
    }
    
    public void DibujarImagen( BufferedImage buffer)
    {        
       buffer.getGraphics().drawImage(Sprite, SpX1, SpY1, SpX2, SpY2, SpSecX1, SpSecY1, SpSecX2, SpSecY2, Monitor);   
    }

}