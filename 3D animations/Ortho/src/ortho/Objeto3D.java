package ortho;

import java.awt.Color;
import java.awt.image.BufferedImage;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Stack;

public class Objeto3D 
{   
    int ResX;       int ResY;
    int EscalaX = 1;    int EscalaY = 1;
    int CentroGravedadX, CentroGravedadY, CentroGravedadZ;
    
    Vertice vertices[] = new Vertice[1000];  int NumVertices = 0;
    Vector  vectores[] = new Vector[1000];   int NumVectores = 0;
    Cara    caras[]    = new Cara[1000] ;    int NumCaras    = 0;
 
    public void Traslación ( int X, int Y, int Z)
    {
        CentroGravedadX += X;
        CentroGravedadY += Y;
        CentroGravedadZ += Z;
        
        double MatrizT[][] = {  {1,0,0,X},         
                                {0,1,0,Y},      
                                {0,0,1,Z},      
                                {0,0,0,1}     }; 
        
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++)
            {
                caras[i].CoordenadaX1 += MatrizT[i][j];
                caras[i].CoordenadaY1 += MatrizT[i][j];
                caras[i].CoordenadaZ1 += MatrizT[i][j];
            }
        }        
    }
    public void Rotación ( double X, double Y, double Z)
    {        
        CentroGravedadX += X;
        CentroGravedadY += Y;
        CentroGravedadZ += Z;
        
        double MatrizR[][] = {  {cos(X),  -sin(X), 0},         
                                {sin(X),   cos(X), 0},      
                                {   0,       0,    1}   }; 
        
       for ( int k = 0 ; k<NumCaras ; k++)
        {
            double Puntos[] = { caras[k].CoordenadaX1, caras[k].CoordenadaY1, caras[k].CoordenadaZ1, 0};
            for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                Puntos[i] += Puntos[j]*MatrizR[i][j];
                }
        }            
        }
    }
    public void Escalación( double x, double y, double z)
    {
        double MatrizS[][] = {  {x, 0, 0, 0},         
                                {0, y, 0, 0},      
                                {0, 0, z, 0},      
                                {0, 0, 0, 1}     }; 
        double  CoorX1, CoorY1, CoorZ1;
        double  CoorX2, CoorY2, CoorZ2;
        double  CoorX3, CoorY3, CoorZ3;
        //Para ahorrar tiempo, vamos a guardar la posición actual del objeto
        int xActual = CentroGravedadX;
        int yActual = CentroGravedadY;
        int zActual = CentroGravedadZ;
        //Ahora, mandamos el objeto a la coordenada 0,0,0
        Traslacion( -CentroGravedadX, -CentroGravedadY, -CentroGravedadZ);
        //Ejecutamos nuestras formulas de rotación
        for ( int k = 0 ; k<NumCaras ; k++)
        {
            double Puntos[] = { caras[k].CoordenadaX1, caras[k].CoordenadaY1, caras[k].CoordenadaZ1, 0};
            for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                Puntos[i] += Puntos[j]*MatrizS[i][j];
                }
        }            
        }
        //Y con el cuerpo ya rotado, regresamos a su ubicación original
        Traslacion( xActual, yActual, zActual);    
    }
    public void setCentroGravedad( int x, int y, int z)
    {
        CentroGravedadX = x;
        CentroGravedadY = y;
        CentroGravedadZ = z;
    } 
    public void drawCuerpo(BufferedImage buffer, Camara cam)
    {
        bubbleSort();        
        for ( int i = 0 ; i<NumCaras ; i++)
        {
            drawCara ( buffer, caras[i], cam);
        }
    }  
    public void drawCara( BufferedImage buffer,Cara cara, Camara Cam)
    {
        int CoorX1, CoorY1, CoorX2, CoorY2, CoorX3, CoorY3;
                
        CoorX1 = CalcularX( Cam, cara.CoordenadaX1, cara.CoordenadaY1, cara.CoordenadaZ1);
        CoorY1 = CalcularY( Cam, cara.CoordenadaX1, cara.CoordenadaY1, cara.CoordenadaZ1);
        
        CoorX2 = CalcularX( Cam, cara.CoordenadaX2, cara.CoordenadaY2, cara.CoordenadaZ2);
        CoorY2 = CalcularY( Cam, cara.CoordenadaX2, cara.CoordenadaY2, cara.CoordenadaZ2);
        
        CoorX3 = CalcularX( Cam, cara.CoordenadaX3, cara.CoordenadaY3, cara.CoordenadaZ3);
        CoorY3 = CalcularY( Cam, cara.CoordenadaX3, cara.CoordenadaY3, cara.CoordenadaZ3);

        ScanLine(buffer, CoorX1, CoorY1, CoorX2, CoorY2, CoorX3, CoorY3, cara.Color );
        
        
        System.out.println("( " + CoorX1 + " , " + CoorY1 + " )" );
        System.out.println("( " + CoorX2 + " , " + CoorY2 + " )" );
        System.out.println("( " + CoorX3 + " , " + CoorY3 + " )" );
        FacosaLine(buffer, CoorX1, CoorY1, CoorX2, CoorY2, Color.black );  
        FacosaLine(buffer, CoorX3, CoorY3, CoorX2, CoorY2, Color.black );  
        FacosaLine(buffer, CoorX1, CoorY1, CoorX3, CoorY3, Color.black );  
         
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
    public void addVertice ( int X, int Y, int Z, Color c)
    {
        vertices[NumVertices] = new Vertice();
        vertices[NumVertices].CoordenadaX = X;
        vertices[NumVertices].CoordenadaY = Y;
        vertices[NumVertices].CoordenadaZ = Z;
        
        vertices[NumVertices].Color = c;
        NumVertices++;
    }
    public void addVector ( int Coordenadas1[], int Coordenadas2[], Color c)
    {
        vectores[NumVectores] = new Vector();
        vectores[NumVectores].CoordenadaX1 = Coordenadas1[0];
        vectores[NumVectores].CoordenadaY1 = Coordenadas1[1];
        vectores[NumVectores].CoordenadaZ1 = Coordenadas1[2];
        
        vectores[NumVectores].CoordenadaX2 = Coordenadas2[0];
        vectores[NumVectores].CoordenadaY2 = Coordenadas2[1];
        vectores[NumVectores].CoordenadaZ2 = Coordenadas2[2];
        
        vectores[NumVertices].Color = c;
        NumVectores++;
    }
    public void addCara ( int Coordenadas1[], int Coordenadas2[], int Coordenadas3[], Color c)
    {
        caras[NumCaras] = new Cara();
        caras[NumCaras].CoordenadaX1 = Coordenadas1[0];
        caras[NumCaras].CoordenadaY1 = Coordenadas1[1];
        caras[NumCaras].CoordenadaZ1 = Coordenadas1[2];
        
        caras[NumCaras].CoordenadaX2 = Coordenadas2[0];
        caras[NumCaras].CoordenadaY2 = Coordenadas2[1];
        caras[NumCaras].CoordenadaZ2 = Coordenadas2[2];
        
        caras[NumCaras].CoordenadaX3 = Coordenadas3[0];
        caras[NumCaras].CoordenadaY3 = Coordenadas3[1];
        caras[NumCaras].CoordenadaZ3 = Coordenadas3[2];
        
        //Calcular posicion Z promedio
        
        caras[NumCaras].Color   = c;
        caras[NumCaras].NumID   = NumCaras;
        ActualizarCentroZ( caras[NumCaras]);                
        NumCaras++;
    }
    public void ActualizarCentroZ ( Cara cara)
    {
        cara.CentroZ = (     ( cara.CoordenadaZ1 + cara.CoordenadaZ2 + cara.CoordenadaZ3 )     / 3  );
    }
    public void setRes(int X, int Y)
    {        
        ResX = X;
        ResY = Y;
    }
    public int CalcularX( Camara Cam, double X, double Y, double Z)
    {
        double Dx = X - Cam.GetCentroX();
        double Dz = Z - Cam.GetCentroZ();

        double Bx = ( (Cam.GetPlanoZ() / Dz ) * Dx ) + Cam.GetPlanoX();

        return (int) Bx;    
    }
    public int CalcularY( Camara Cam, double X, double Y, double Z)
    {     
        double Dy = Y - Cam.GetCentroY();
        double Dz = Z - Cam.GetCentroZ();
        
        double By = ( (Cam.GetPlanoZ() / Dz ) * Dy ) + Cam.GetPlanoY();

        return (int) By;
    }
    public void DibujarVertice ( BufferedImage buffer, Camara Cam, int X, int Y, int Z, Color c)
    {
        int CoorX, CoorY;
        CoorX = CalcularX( Cam, X, Y, Z);
        CoorY = CalcularY( Cam, X, Y, Z);
        buffer.setRGB(CoorX, CoorY, c.getRGB()); 
        
        //System.out.println("(" + CoorX + "," + CoorY + ")");
    }
    public void DibujarVector( BufferedImage buffer, Camara Cam, int P1[], int P2[], Color c)
    {        
        int CoorX1, CoorY1, CoorX2, CoorY2;
        CoorX1 = CalcularX( Cam, P1[0], P1[1], P1[2]);
        CoorY1 = CalcularY( Cam, P1[0], P1[1], P1[2]);
        
        CoorX2 = CalcularX( Cam, P2[0], P2[1], P2[2]);
        CoorY2 = CalcularY( Cam, P2[0], P2[1], P2[2]);
        
        System.out.println("(" + CoorX1 + "," + CoorY1 + ") - (" + CoorX2 + "," + CoorY2 + ")");
        
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
    public void Traslacion ( int X, int Y, int Z)
    {
        CentroGravedadX += X;
        CentroGravedadY += Y;
        CentroGravedadZ += Z;
        
        for ( int i = 0 ; i<NumCaras ; i++)
        {
            caras[i].CoordenadaX1 += X;
            caras[i].CoordenadaY1 += Y;
            caras[i].CoordenadaZ1 += Z;
            
            caras[i].CoordenadaX2 += X;
            caras[i].CoordenadaY2 += Y;
            caras[i].CoordenadaZ2 += Z;
            
            caras[i].CoordenadaX3 += X;
            caras[i].CoordenadaY3 += Y;
            caras[i].CoordenadaZ3 += Z;
        }
    }
    public void Escalacion ( double x, double y, double z)
    {
        double MatrizS[][] = {  {x, 0, 0, 0},         
                                {0, y, 0, 0},      
                                {0, 0, z, 0},      
                                {0, 0, 0, 1}     };
        
        double Puntos[][]  = {  {x, 0, 0, 0},         
                                {0, y, 0, 0},      
                                {0, 0, z, 0},      
                                {0, 0, 0, 1}     };

        double  CoorX1, CoorY1, CoorZ1;
        double  CoorX2, CoorY2, CoorZ2;
        double  CoorX3, CoorY3, CoorZ3;
        //Para ahorrar tiempo, vamos a guardar la posición actual del objeto
        int xActual = CentroGravedadX;
        int yActual = CentroGravedadY;
        int zActual = CentroGravedadZ;
        //Ahora, mandamos el objeto a la coordenada 0,0,0
        Traslacion( -CentroGravedadX, -CentroGravedadY, -CentroGravedadZ);
        //Ejecutamos nuestras formulas de rotación
        for ( int i = 0 ; i<NumCaras ; i++)
        {
            //Primer Punto
            CoorX1 =  caras[i].CoordenadaX1 * x;
            CoorY1 =  caras[i].CoordenadaY1 * y;
            CoorZ1 =  caras[i].CoordenadaZ1 * z;
            //Segundo Punto
            CoorX2 =  caras[i].CoordenadaX2 * x;
            CoorY2 =  caras[i].CoordenadaY2 * y;
            CoorZ2 =  caras[i].CoordenadaZ2 * z;
            //Tercer Punto
            CoorX3 =  caras[i].CoordenadaX3 * x;
            CoorY3 =  caras[i].CoordenadaY3 * y;
            CoorZ3 =  caras[i].CoordenadaZ3 * z;
            
            caras[i].CoordenadaX1 =  CoorX1;
            caras[i].CoordenadaY1 =  CoorY1;
            caras[i].CoordenadaZ1 =  CoorZ1;            
            caras[i].CoordenadaX2 =  CoorX2;
            caras[i].CoordenadaY2 =  CoorY2;
            caras[i].CoordenadaZ2 =  CoorZ2;            
            caras[i].CoordenadaX3 =  CoorX3;
            caras[i].CoordenadaY3 =  CoorY3;
            caras[i].CoordenadaZ3 =  CoorZ3; 
            ActualizarCentroZ( caras[i]);
        }
        //Y con el cuerpo ya rotado, regresamos a su ubicación original
        Traslacion( xActual, yActual, zActual);    
    }
    public void Rotacion ( double X, double Y, double Z)
    {        
        double  AnguloX = Math.toRadians(X);
        double  AnguloY = Math.toRadians(Y);
        double  AnguloZ = Math.toRadians(Z);
        double  CoorX1, CoorY1, CoorZ1;
        double  CoorX2, CoorY2, CoorZ2;
        double  CoorX3, CoorY3, CoorZ3;
        //Para ahorrar tiempo, vamos a guardar la posición actual del objeto
        int xActual = CentroGravedadX;
        int yActual = CentroGravedadY;
        int zActual = CentroGravedadZ;
        //Ahora, mandamos el objeto a la coordenada 0,0,0
        Traslacion( -CentroGravedadX, -CentroGravedadY, -CentroGravedadZ);
        //Ejecutamos nuestras formulas de rotación
        for ( int i = 0 ; i<NumCaras ; i++)
        {
            //-----------------------------------------------------ROTACION EN X
            //Primer Punto
            CoorY1 =  (( caras[i].CoordenadaY1 * cos(AnguloX) )      -       ( caras[i].CoordenadaZ1 * sin(AnguloX)));
            CoorZ1 =  (( caras[i].CoordenadaY1 * sin(AnguloX) )      +       ( caras[i].CoordenadaZ1 * cos(AnguloX)));
            caras[i].CoordenadaY1 =  CoorY1;
            caras[i].CoordenadaZ1 =  CoorZ1;
            //Segundo Punto
            CoorY2 =  (( caras[i].CoordenadaY2 * cos(AnguloX) )      -       ( caras[i].CoordenadaZ2 * sin(AnguloX)));
            CoorZ2 =  (( caras[i].CoordenadaY2 * sin(AnguloX) )      +       ( caras[i].CoordenadaZ2 * cos(AnguloX)));
            caras[i].CoordenadaY2 =  CoorY2;
            caras[i].CoordenadaZ2 =  CoorZ2;
            //Tercer Punto
            CoorY3 =  (( caras[i].CoordenadaY3 * cos(AnguloX) )      -       ( caras[i].CoordenadaZ3 * sin(AnguloX)));
            CoorZ3 =  (( caras[i].CoordenadaY3 * sin(AnguloX) )      +       ( caras[i].CoordenadaZ3 * cos(AnguloX)));
            caras[i].CoordenadaY3 =  CoorY3;
            caras[i].CoordenadaZ3 =  CoorZ3;
            
            //-----------------------------------------------------ROTACION EN Y
            //Primer Punto
            CoorX1 = ((  caras[i].CoordenadaX1 * cos(AnguloY) )      +       ( caras[i].CoordenadaZ1 * sin(AnguloY)));
            CoorZ1 = (( -caras[i].CoordenadaX1 * sin(AnguloY) )      +       ( caras[i].CoordenadaZ1 * cos(AnguloY)));
            //Segundo Punto
            CoorX2 = ((  caras[i].CoordenadaX2 * cos(AnguloY) )      +       ( caras[i].CoordenadaZ2 * sin(AnguloY)));
            CoorZ2 = (( -caras[i].CoordenadaX2 * sin(AnguloY) )      +       ( caras[i].CoordenadaZ2 * cos(AnguloY)));
            //Tercer Punto
            CoorX3 = ((  caras[i].CoordenadaX3 * cos(AnguloY) )      +       ( caras[i].CoordenadaZ3 * sin(AnguloY)));
            CoorZ3 = (( -caras[i].CoordenadaX3 * sin(AnguloY) )      +       ( caras[i].CoordenadaZ3 * cos(AnguloY)));
            caras[i].CoordenadaX1 =  CoorX1;
            caras[i].CoordenadaZ1 =  CoorZ1;
            caras[i].CoordenadaX2 =  CoorX2;
            caras[i].CoordenadaZ2 =  CoorZ2;
            caras[i].CoordenadaX3 =  CoorX3;
            caras[i].CoordenadaZ3 =  CoorZ3;
            //-----------------------------------------------------ROTACION EN Z
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
            
            ActualizarCentroZ( caras[i]);
        }
        //Y con el cuerpo ya rotado, regresamos a su ubicación original
        Traslacion( xActual, yActual, zActual);
    }
    void bubbleSort()
    {
        int n = NumCaras;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (caras[j].CentroZ < caras[j+1].CentroZ)
                {
                    Cara temp = caras[j];
                    caras[j] = caras[j+1];
                    caras[j+1] = temp;
                }
    }   
}