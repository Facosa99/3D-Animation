/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortho;

import java.awt.Color;
import java.awt.image.BufferedImage;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Stack;

/**
 *
 * @author facos
 */
public class Objeto2D 
{
    int ResX;       int ResY;
    int EscalaX = 1;    int EscalaY = 1;
    int CentroGravedadX, CentroGravedadY;
    
    Vector  vectores[]  = new Vector[1000];     int NumVectores = 0;
    Cara    caras[]     = new Cara[1000];       int NumCaras = 0;
 
    public void Traslación ( int X, int Y)
    {
        CentroGravedadX += X;
        CentroGravedadY += Y;
        
        double MatrizT[][] = {  {1,0,X},         
                                {0,1,Y},      
                                {0,0,1},      
                                }; 
        
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++)
            {
                vectores[i].CoordenadaX1 += MatrizT[i][j];
                vectores[i].CoordenadaY1 += MatrizT[i][j];
            }
        }        
    }
    public void Rotación ( double X, double Y)
    {        
        CentroGravedadX += X;
        CentroGravedadY += Y;
        
        double MatrizR[][] = {  {cos(X),    -sin(X),    0},         
                                {sin(X),     cos(X),    0},      
                                {  0,          0,       0}  };     
       for ( int k = 0 ; k<NumCaras ; k++)
        {
            double Puntos[] = { caras[k].CoordenadaX1, caras[k].CoordenadaY1, caras[k].CoordenadaZ1, 0};
            for(int i=0;i<4;i++)
            {
            for(int j=0;j<4;j++){
                Puntos[i] += Puntos[j]*MatrizR[i][j];
                }
            }            
        }
    }
    public void Escalación( double x, double y)
    {
        double MatrizS[][] = {  {x, 0, 0},         
                                {0, y, 0},      
                                {0, 0, 1}   }; 
        double  CoorX1, CoorY1;
        double  CoorX2, CoorY2;
        //Para ahorrar tiempo, vamos a guardar la posición actual del objeto
        int xActual = CentroGravedadX;
        int yActual = CentroGravedadY;
        //Ahora, mandamos el objeto a la coordenada 0,0,0
        Traslación( -CentroGravedadX, -CentroGravedadY);
        //Ejecutamos nuestras formulas de rotación
        for ( int k = 0 ; k<NumVectores ; k++)
        {
            double Puntos[] = { vectores[k].CoordenadaX1, vectores[k].CoordenadaY1, 0};
            for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                Puntos[i] += Puntos[j]*MatrizS[i][j];
                }
        }            
        }
        //Y con el cuerpo ya rotado, regresamos a su ubicación original
        Traslación( xActual, yActual);    
    }
    public void setCentroGravedad( int x, int y)
    {
        CentroGravedadX = x;
        CentroGravedadY = y;
    } 
    public void drawFigura(BufferedImage buffer)
    {                
        System.out.println("Hola");
  
        for ( int i = 0 ; i<NumCaras ; i++)
        {
            drawVector( buffer, vectores[i]);
            drawTri(   buffer, caras[i]);
        }        
    }  
    public void drawTri( BufferedImage buffer,Cara cara)
    {
        int CoorX1, CoorY1, CoorX2, CoorY2, CoorX3, CoorY3;
        CoorX1 = (int) cara.CoordenadaX1;   CoorY1 = (int) cara.CoordenadaY1;
        CoorX2 = (int) cara.CoordenadaX2;   CoorY2 = (int) cara.CoordenadaY2;
        CoorX3 = (int) cara.CoordenadaX3;   CoorY3 = (int) cara.CoordenadaY3;

        ScanLine(buffer, CoorX1, CoorY1, CoorX2, CoorY2, CoorX3, CoorY3, cara.Color );
        FacosaLine(buffer, CoorX1, CoorY1, CoorX2, CoorY2, cara.Color );  
        FacosaLine(buffer, CoorX3, CoorY3, CoorX2, CoorY2, cara.Color );  
        FacosaLine(buffer, CoorX1, CoorY1, CoorX3, CoorY3, cara.Color );  
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
    public void addVector ( int Coordenadas1[], int Coordenadas2[], Color c)
    {
        vectores[NumVectores] = new Vector();
        
        vectores[NumVectores].CoordenadaX1 = Coordenadas1[0];   vectores[NumVectores].CoordenadaY1 = Coordenadas1[1];
        vectores[NumVectores].CoordenadaX2 = Coordenadas2[0];   vectores[NumVectores].CoordenadaY2 = Coordenadas2[1];
        
        vectores[NumVectores].CentroX = (vectores[NumVectores].CoordenadaX1 + vectores[NumVectores].CoordenadaX2)/2;
        vectores[NumVectores].CentroY = (vectores[NumVectores].CoordenadaY1 + vectores[NumVectores].CoordenadaY2)/2;        
        vectores[NumVectores].Color = c;
        NumVectores++;
    }
    public void addTri ( int Coordenadas1[], int Coordenadas2[], int Coordenadas3[], Color c)
    {
        caras[NumCaras] = new Cara();
        caras[NumCaras].CoordenadaX1 = Coordenadas1[0]; caras[NumCaras].CoordenadaY1 = Coordenadas1[1];        
        caras[NumCaras].CoordenadaX2 = Coordenadas2[0]; caras[NumCaras].CoordenadaY2 = Coordenadas2[1];        
        caras[NumCaras].CoordenadaX3 = Coordenadas3[0]; caras[NumCaras].CoordenadaY3 = Coordenadas3[1];
        
        //Calcular posicion central
        caras[NumVectores].CentroX = (caras[NumVectores].CoordenadaX1 + caras[NumVectores].CoordenadaX2 + caras[NumVectores].CoordenadaX3)/3;
        caras[NumVectores].CentroY = (caras[NumVectores].CoordenadaY1 + caras[NumVectores].CoordenadaY2 + caras[NumVectores].CoordenadaY3)/3;        
        
        caras[NumVectores].Color = c;             
        NumCaras++;
    }
    public void setRes(int X, int Y)
    {        
        ResX = X;
        ResY = Y;
    }
    public void drawVector( BufferedImage buffer, Vector vector)
    {        
        int CoorX1, CoorY1, CoorX2, CoorY2;
        CoorX1 = vector.CoordenadaX1;   CoorY1 = vector.CoordenadaY1;
        CoorX2 = vector.CoordenadaX2;   CoorY2 = vector.CoordenadaY2;
        Color c = vector.Color;
        FacosaLine(buffer, CoorX1, CoorY1, CoorX2, CoorY2, c);           
    }
    public void FacosaLine(BufferedImage buffer, int x1, int y1, int x2, int y2, Color cc)
    {
        int dx=Math.abs(x2 - x1);
        int dy=Math.abs(y2 - y1);

        int sx=(x1<x2) ? 1 : -1;
        int sy=(y1<y2) ? 1 : -1;

        int p0=dx-dy;

        while(true){            
            if ( x1 > 0 && y1 > 0 && x1 < ResX && y1 < ResY) //IF para evitar pintar fuera de coordenadas
            {
                buffer.setRGB(x1, y1, cc.getRGB());
            }            
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
    public void ScanLine(BufferedImage buffer, int x1, int y1, int x2, int y2, int x3, int y3, Color cc)
    {
        int XA = x1,    YA = y1,        XB = x2,    YB = y2;        
        int XD = x3,    YD = y3;        
        int dx1 =    Math.abs(XB - XA);
        int dy1 =    Math.abs(YB - YA);
        int sx1=(XA<XB) ? 1 : -1;
        int sy1=(YA<YB) ? 1 : -1;
        int p1=dx1-dy1;

        while(true){ 
            
            FacosaLine(buffer,  XA,  YA,  XD,  YD,cc);            
            if (XA==XB && YA==YB )
            {          
               break;
            }
            int e1 = 2*p1;
            if (e1>-dy1){
                p1 =p1-dy1;
                XA =XA+sx1;
            }
            if (e1<dx1) {
                p1 = p1+dx1;
                YA = YA+sy1;
            } 
        }
    }
    public void Traslacion ( int X, int Y)
    {
        CentroGravedadX += X;
        CentroGravedadY += Y;
        
        for ( int i = 0 ; i<NumCaras ; i++)
        {
            caras[i].CoordenadaX1 += X;     caras[i].CoordenadaY1 += Y;            
            caras[i].CoordenadaX2 += X;     caras[i].CoordenadaY2 += Y;
            caras[i].CoordenadaX3 += X;     caras[i].CoordenadaY3 += Y;
        }
        for ( int i = 0 ; i<NumVectores ; i++)
        {          
            vectores[i].CoordenadaX1 += X;  vectores[i].CoordenadaY1 += X;
            vectores[i].CoordenadaX2 += X;  vectores[i].CoordenadaY2 += X;
        }
    }
    public void Escalacion ( double x, double y)
    {
        //Para ahorrar tiempo, vamos a guardar la posición actual del objeto
        ActualizarCentroR();
        int xActual = CentroGravedadX,  yActual = CentroGravedadY;
        //Ahora, mandamos el objeto a la coordenada 0,0,0
        Traslacion( -CentroGravedadX, -CentroGravedadY);
        //Ejecutamos nuestras formulas de rotación
        for ( int i = 0 ; i<NumCaras ; i++)
        {
            caras[i].CoordenadaX1 =  caras[i].CoordenadaX1 * x;    caras[i].CoordenadaY1 =  caras[i].CoordenadaY1 * y;
            caras[i].CoordenadaX2 =  caras[i].CoordenadaX2 * x;    caras[i].CoordenadaY2 =  caras[i].CoordenadaY2 * y;
            caras[i].CoordenadaX3 =  caras[i].CoordenadaX3 * x;    caras[i].CoordenadaY3 =  caras[i].CoordenadaY3 * y;
        }
        for ( int i = 0 ; i<NumVectores ; i++)
        {
            vectores[i].CoordenadaX1 =  (int) (vectores[i].CoordenadaX1 * x);    vectores[i].CoordenadaY1 =  (int) (vectores[i].CoordenadaY1 * y);
            vectores[i].CoordenadaX2 =  (int) (vectores[i].CoordenadaX2 * x);    vectores[i].CoordenadaY2 =  (int) (vectores[i].CoordenadaY2 * y);
        }
        //Y con el cuerpo ya rotado, regresamos a su ubicación original
        Traslacion( xActual, yActual);
    }
    public void Rotacion ( double Z)
    {        
        double  AnguloZ = Math.toRadians(Z);
        double  CoorX1, CoorY1;
        double  CoorX2, CoorY2;
        double  CoorX3, CoorY3;
        //Para ahorrar tiempo, vamos a guardar la posición actual del objeto
        ActualizarCentroR();
        int xActual = CentroGravedadX;
        int yActual = CentroGravedadY;
        //Ahora, mandamos el objeto a la coordenada 0,0,0
        Traslacion( -CentroGravedadX, -CentroGravedadY);
        //Ejecutamos nuestras formulas de rotación
        for ( int i = 0 ; i<NumCaras ; i++)
        {
            //Primer Punto
            CoorX1 = (( caras[i].CoordenadaX1  * cos(AnguloZ) )      -       ( caras[i].CoordenadaY1 * sin(AnguloZ)));
            CoorY1 = (( caras[i].CoordenadaX1  * sin(AnguloZ) )      +       ( caras[i].CoordenadaY1 * cos(AnguloZ)));
            //Segundo Punto
            CoorX2 = (( caras[i].CoordenadaX2  * cos(AnguloZ) )      -       ( caras[i].CoordenadaY2 * sin(AnguloZ)));
            CoorY2 = (( caras[i].CoordenadaX2  * sin(AnguloZ) )      +       ( caras[i].CoordenadaY2 * cos(AnguloZ)));
            //Tercer Punto            
            CoorX3 = (( caras[i].CoordenadaX3  * cos(AnguloZ) )      -       ( caras[i].CoordenadaY3 * sin(AnguloZ)));
            CoorY3 = (( caras[i].CoordenadaX3  * sin(AnguloZ) )      +       ( caras[i].CoordenadaY3 * cos(AnguloZ)));
            
            caras[i].CoordenadaX1 =  CoorX1;
            caras[i].CoordenadaY1 =  CoorY1;
            caras[i].CoordenadaX2 =  CoorX2;
            caras[i].CoordenadaY2 =  CoorY2;
            caras[i].CoordenadaX3 =  CoorX3;
            caras[i].CoordenadaY3 =  CoorY3;
        }
        //Y con el cuerpo ya rotado, regresamos a su ubicación original
        Traslacion( xActual, yActual);
    }
    void ActualizarCentroR() //Centro de rotacion
    {
        int n = NumCaras;        
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (vectores[j].CentroX < vectores[j+1].CentroX)
                {
                    Vector temp = vectores[j];
                    vectores[j] = vectores[j+1];
                    vectores[j+1] = temp;
                }
        
        CentroGravedadX = (int) (caras[0].CentroX + caras[n-1].CentroX)/2;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (caras[j].CentroY < caras[j+1].CentroY)
                {
                    Cara temp = caras[j];
                    caras[j] = caras[j+1];
                    caras[j+1] = temp;
                }
        CentroGravedadY = (int) (caras[0].CentroY + caras[n-1].CentroY)/2;
    }
}