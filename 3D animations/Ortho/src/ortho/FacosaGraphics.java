package ortho;

import java.awt.Color;
import static java.awt.Color.red;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Stack;



public class FacosaGraphics 
{
    
    public  void FacosaRec   ( BufferedImage buffer, int X, int Y, int B, int H, Color c)
    {
        FacosaLine (buffer, X,   Y,   X,   Y+H, c );
        FacosaLine (buffer, X,   Y+H, X+B, Y+H, c );
        FacosaLine (buffer, X+B, Y,   X+B, Y+H, c );
        FacosaLine (buffer, X,   Y,   X+B, Y,   c );
    }
    
    public void FacosaLine(BufferedImage buffer, int x1, int y1, int x2, int y2, Color cc)
    {
        int dx=Math.abs(x2 - x1);
        int dy=Math.abs(y2 - y1);

        int sx=(x1<x2) ? 1 : -1;
        int sy=(y1<y2) ? 1 : -1;

        int p0=dx-dy;

        while(true){
            buffer.setRGB(x1, y1, cc.getRGB());
            
            if (x1==x2 && y1==y2){
                break;
            }

            int e2 = 2*p0;

            if (e2>-dy){
                p0 =p0-dy;
                x1 =x1+sx;
            }

            if (e2<dx) {
                p0 = p0+dx;
                y1 = y1+sy;
            }
        }
    }
    
    public void FacosaRecEsc ( BufferedImage buffer, int X, int Y, int B, int H, Color c)
    {
        FacosaLine (buffer, X,   Y,   X,   Y+H, c );
        FacosaLine (buffer, X,   Y+H, X+B, Y+H, c );
        FacosaLine (buffer, X+B, Y,   X+B, Y+H, c );
        FacosaLine (buffer, X,   Y,   X+B, Y,   c );
    }
    
    public void FacosaTri ( BufferedImage buffer, int X1, int Y1, int X2, int Y2,int X3, int Y3, Color c)
    {
        FacosaLine (buffer, X1, Y1, X2, Y2, c );
        FacosaLine (buffer, X2, Y2, X3, Y3, c );
        FacosaLine (buffer, X3, Y3, X1, Y1, c );
    }
    
    public void FloodFill ( BufferedImage buffer, int X, int Y, Color c)
    {  
        //------------------------------------------------------Rellenar Circulo        
        //Lista de coordenadas a revisar
        Stack   CoorX = new Stack();    //Coordenadas en X
        Stack   CoorY = new Stack();    //Coordenadas en Y
        //Coordenadas del pixel actual
        int     xActual;
        int     yActual;
        
        //Agregamos el un pixel de la pantalla a la lista
        CoorX.push(X);
        CoorY.push(Y);
        do
        {            
            
            //Removemos el pixel de la lista y lo agregamos a nuestra variable
            xActual = (int) CoorX.pop();
            yActual = (int) CoorY.pop();
            int c1, c2;//Colores
            c1 = c.getRGB();  
            
            buffer.setRGB(xActual,yActual,c.getRGB());
            
            //Norte
            c2 = buffer.getRGB(xActual, yActual-1);            
            if ( c1 != c2 )
            {
                CoorY.push(yActual-1);
                CoorX.push(xActual);
            }
            //Sur
            c2 = buffer.getRGB(xActual, yActual+1);
            if ( c1 != c2 )
            {
                CoorX.push(xActual);
                CoorY.push(yActual+1);
            }
            //Este
            c2 = buffer.getRGB(xActual-1, yActual);
           
            if ( c1 != c2 )
            {
                CoorX.push(xActual-1);
                CoorY.push(yActual);
            }
            //Oeste
            c2 = buffer.getRGB(xActual+1, yActual);
            if ( c1 != c2 )
            {
                CoorX.push(xActual+1);
                CoorY.push(yActual);
            }            
        }
        while(CoorX.empty() == false); //Cuando la lista esta vacia, paramos el while        
    }
    
    public void CirculoFloodFill(BufferedImage buffer, int rX, int rY, int Xc, int Yc, Color c)
    {        
        //-------------------------------------------------------Dibujar Circulo  
        int X = 0;
        int Y = 0;
        double t = 0;
        for( t=0 ; t<= 360 ; t = t+.03)
        {
            X = (int) ( Xc + ( rX*cos(t) ) );
            Y = (int) ( Yc + ( rY*sin(t) ) );
            buffer.setRGB(X,Y, c.getRGB() );    
        }  
        //------------------------------------------------------Rellenar Circulo        
        //Lista de coordenadas a revisar
        Stack   CoorX = new Stack();    //Coordenadas en X
        Stack   CoorY = new Stack();    //Coordenadas en Y
        //Coordenadas del pixel actual
        int     xActual;
        int     yActual;
        
        //Agregamos el un pixel de la pantalla a la lista
        CoorX.push(Xc);
        CoorY.push(Yc);
        do
        {            
            
            //Removemos el pixel de la lista y lo agregamos a nuestra variable
            xActual = (int) CoorX.pop();
            yActual = (int) CoorY.pop();
            int c1, c2;//Colores
            c1 = c.getRGB();  
            
            buffer.setRGB(xActual,yActual,c.getRGB());
            
            //Norte
            c2 = buffer.getRGB(xActual, yActual-1);            
            if ( c1 != c2 )
            {
                CoorY.push(yActual-1);
                CoorX.push(xActual);
            }
            //Sur
            c2 = buffer.getRGB(xActual, yActual+1);
            if ( c1 != c2 )
            {
                CoorX.push(xActual);
                CoorY.push(yActual+1);
            }
            //Este
            c2 = buffer.getRGB(xActual-1, yActual);
           
            if ( c1 != c2 )
            {
                CoorX.push(xActual-1);
                CoorY.push(yActual);
            }
            //Oeste
            c2 = buffer.getRGB(xActual+1, yActual);
            if ( c1 != c2 )
            {
                CoorX.push(xActual+1);
                CoorY.push(yActual);
            }            
        }
        while(CoorX.empty() == false); //Cuando la lista esta vacia, paramos el while        
    }
    
    
}
