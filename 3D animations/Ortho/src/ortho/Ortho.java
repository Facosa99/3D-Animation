package ortho;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class Ortho extends JFrame 
{
    public Image N64;
    public Image SF;
    Objeto3D FigT                   = new Objeto3D();
    Objeto3D Arg1                   = new Objeto3D();
    Objeto3D GreatFox               = new Objeto3D();
    Objeto3D Corneria               = new Objeto3D();
    FacosaGraphics FacoGraf         = new FacosaGraphics();
    
    int Contador1 = 0;
    double offset;
    Color CC  = Color.black;
    Color CC2 = Color.black;
    
    int N64_Y1 = -200, N64_Y2 = 0;
    int SFX1 = 0, SFY1 = 0, SFX2 = 0, SFY2 = 0;
    double X=0, Y=0, X1=0, Y1=0, pi=3.1416, Ny=0, Nx=0;

    
    Camara   Cam  = new Camara();
    BufferedImage Buffer;  
    BufferedImage LimpiarBuffer; 
    
    BufferedImage BufferEstrellas;  
    BufferedImage BufferGFLimpiar;  
    
    int ResX = 1200, ResY = 700;

    public Ortho()
    {
        setTitle("Parcial 3: Animación");
        this.setSize(ResX, ResY);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Buffer = new BufferedImage(ResX,ResY,BufferedImage.TYPE_INT_RGB);
        
        BufferEstrellas = new BufferedImage(ResX,ResY,BufferedImage.TRANSLUCENT);
        BufferGFLimpiar = new BufferedImage(ResX,ResY,BufferedImage.TRANSLUCENT);
        LimpiarBuffer = new BufferedImage(ResX,ResY,BufferedImage.TYPE_INT_RGB);
        
        Cam.SetParametros( 450, 350, -500,    450, 350, 500,      ResX, ResY);
        
        //Lista de figuras 3D
        FigT.setRes(ResX, ResY); //Cubo
        Arg1.setRes(ResX, ResY); //Cubo
        GreatFox.setRes(ResX, ResY); //Cubo
        Corneria.setRes(ResX, ResY); //Cubo
        
        GreatFox();
        Arwing1(400,-300,-200);
        Cubo();
        setVisible(true);
        
        Toolkit herramienta2 = Toolkit.getDefaultToolkit();
        SF = herramienta2.getImage(getClass().getResource("img/SF.png"));
    }
    
    public static void main(String[] args) 
    {
        Ortho Animacion = new Ortho();
        Animacion.CorrerArwing1();
    }    

    public void GreatFox()
    {
        //----------------------------------------------------CENTRO DE GRAVEDAD
        GreatFox.setCentroGravedad(150, 50, 150);        
        //----------------------------------------------------------------CABINA
        // NARIZ
        int CNA[]  =   { 100,    60,     250     };  //Nariz
        int CNB[]  =   { 90,     60,     210     };  //Nariz Superior Derecha
        int CNC[]  =   { 110,    60,     210     };  //Nariz Superior Izquierda
        int CND[]  =   { 90,     50,     210     };  //Nariz Inferior Derecha        
        int CNE[]  =   { 110,    50,     210     };  //Nariz Inferior Izquierda        
        GreatFox.addCara(CNA, CNB, CNC, Color.white);  //Nariz Panel Superior
        GreatFox.addCara(CNA, CND, CNE, Color.white);  //Nariz Panel Inferior
        GreatFox.addCara(CNA, CND, CNB, Color.white);  //Nariz Panel Derecho
        GreatFox.addCara(CNA, CNC, CNE, Color.white);  //Nariz Panel Izquierdo
        // CRISTAL
        int CCA[]  =   { 100,    70,     190     };  //Cupula        
        int CCB[]  =   {  90,    60,     210     };  //Fontral Derecha
        int CCC[]  =   { 110,    60,     210     };  //Frontal Izquierda               
        int CCD[]  =   {  90,    60,     160     };  //Trasera Derecha
        int CCE[]  =   { 110,    60,     160     };  //Trasera Izquierda
        GreatFox.addCara(CCA, CCB, CCC, Color.blue);  //Windshield
        GreatFox.addCara(CCA, CCD, CCE, Color.blue);  //Cristal Trasero
        GreatFox.addCara(CCA, CCB, CCD, Color.blue);  //Cristal Derecho
        GreatFox.addCara(CCA, CCC, CCE, Color.blue);  //Cristal Izquierdo
        // BASE
        int CBA[]  =   {  90,    50,     190     };  //Centro Derecha        
        int CBB[]  =   { 110,    50,     190     };  //Centro Izquierda  
        int CBC[]  =   {  90,    40,     160     };  //Trasero Derecha
        int CBD[]  =   { 110,    40,     160     };  //Trasero Izquierda
        GreatFox.addCara(CND, CNE, CBB, Color.white);  //Panel Frontal
        GreatFox.addCara(CBA, CBB, CND, Color.white);  //Panel Centro-Frontal
        GreatFox.addCara(CBA, CBB, CBC, Color.white);  //Panel Centro-Trasero
        GreatFox.addCara(CBC, CBD, CBB, Color.white);  //Panel Trasero
        GreatFox.addCara(CNB, CND, CBA, Color.white);  //Panel Derecho Frontal
        GreatFox.addCara(CCB, CCD, CBA, Color.white);  //Panel Derecho Medio
        GreatFox.addCara(CCD, CBC, CBA, Color.white);  //Panel Derecho Trasero
        
        GreatFox.addCara(CNC, CNE, CBB, Color.white);  //Panel Izquierdo Frontal
        GreatFox.addCara(CCC, CCE, CBB, Color.white);  //Panel Izquierdo Medio
        GreatFox.addCara(CCE, CBD, CBB, Color.white);  //Panel Izquierdo Trasero
        
        //--------------------------------------------------------------FUSELAJE
        //Dorso
        int FDA[]  =   {  90,    60,     160     };  //Frente Derecha
        int FDB[]  =   { 110,    60,     160     };  //Frente Izquierda  
        int FDC[]  =   {  90,    60,      70     };  //Trasero Derecha  
        int FDD[]  =   { 100,    60,      80     };  //Trasero Central  
        int FDE[]  =   { 110,    60,      70     };  //Trasero Izquierda
        GreatFox.addCara(FDA, FDB, FDD, Color.white);  //Panel Frontal
        GreatFox.addCara(FDA, FDC, FDD, Color.white);  //Panel Derecha
        GreatFox.addCara(FDB, FDD, FDE, Color.white);  //Panel Izquierdo
        //Timon
        int FTA[]  =   {  90,    60,     70     };  //Frontal Derecha
        int FTB[]  =   { 100,    60,     80     };  //Frontal Central
        int FTC[]  =   { 110,    60,     70     };  //Frontal Izquierda
        int FTD[]  =   { 100,    90,     30     };  //Superior Frontal 
        int FTE[]  =   { 100,    90,     10     };  //Superior Trasero
        int FTF[]  =   {  90,    60,     20     };  //Trasero Derecha
        int FTG[]  =   { 110,    60,     20     };  //Trasero Izquierda
        GreatFox.addCara(FTA, FTB, FTD, Color.white);  //Panel Frontal Derecho
        GreatFox.addCara(FTB, FTC, FTD, Color.white);  //Panel Frontal Izquierdo
        GreatFox.addCara(FTA, FTF, FTD, Color.white);  //Panel Central Derecho
        GreatFox.addCara(FTC, FTG, FTD, Color.white);  //Panel Central Izquierdo
        GreatFox.addCara(FTF, FTD, FTE, Color.white);  //Panel Trasero Derecho
        GreatFox.addCara(FTG, FTD, FTE, Color.white);  //Panel Trasero Izquierdo
        GreatFox.addCara(FTG, FTF, FTE, Color.white);  //Panel Trasero 
        //Rojo Derecho Superior
        int FRDSA[]  =   {  90,     60,     190     };  //Frontal Izquierda
        int FRDSB[]  =   {  80,     50,     180     };  //Frontal Derecha
        int FRDSC[]  =   {  90,     60,      70     };  //Central Izquierda
        int FRDSD[]  =   {  80,     50,      70     };  //Central Derecha
        int FRDSE[]  =   {  90,     60,      20     };  //Trasera Izquierda
        int FRDSF[]  =   {  80,     50,      20     };  //Trasera Derecha
        GreatFox.addCara(FRDSA, FRDSB, FRDSC, Color.red);  //Panel Frontal 
        GreatFox.addCara(FRDSB, FRDSF, FRDSC, Color.red);  //Panel Central 
        GreatFox.addCara(FRDSE, FRDSF, FRDSC, Color.red);  //Panel Trasero
        //Rojo Derecho Frontal
        int FRDFA[]  =   {  90,     40,     160     };  //Frontal 
        int FRDFB[]  =   {  80,     30,     140     };  //Trasero
        GreatFox.addCara(FRDSA, FRDSB, FRDFA, Color.red);  //Panel Superior
        GreatFox.addCara(FRDSB, FRDFB, FRDFA, Color.red);  //Panel Inferior
        //Rojo Derecho Lateral
        int FRDLA[]  =   {  80,     30,     140     };  //Frontal 
        int FRDLB[]  =   {  100,     30,      40     };  //Trasero
        GreatFox.addCara(FRDSB, FRDSD, FRDLA, Color.red);  //Panel Frontal
        GreatFox.addCara(FRDSD, FRDLA, FRDLB, Color.red);  //Panel Central
        GreatFox.addCara(FRDSF, FRDSD, FRDLB, Color.red);  //Panel Trasero
        //Rojo Izquierdo Superior
        int FRISA[]  =   {  110,     60,     190     };  //Frontal Izquierda
        int FRISB[]  =   {  120,     50,     180     };  //Frontal Derecha
        int FRISC[]  =   {  110,     60,      70     };  //Central Izquierda
        int FRISD[]  =   {  120,     50,      70     };  //Central Derecha
        int FRISE[]  =   {  110,     60,      20     };  //Trasera Izquierda
        int FRISF[]  =   {  120,     50,      20     };  //Trasera Derecha
        GreatFox.addCara(FRISA, FRISB, FRISC, Color.red);  //Panel Frontal 
        GreatFox.addCara(FRISB, FRISF, FRISC, Color.red);  //Panel Central 
        GreatFox.addCara(FRISE, FRISF, FRISC, Color.red);  //Panel Trasero
        //Rojo Izquierdo Frontal
        int FRIFA[]  =   {  110,     40,     160     };  //Frontal 
        int FRIFB[]  =   {  120,     30,     140     };  //Trasero
        GreatFox.addCara(FRISA, FRISB, FRIFA, Color.red);  //Panel Superior
        GreatFox.addCara(FRISB, FRIFB, FRIFA, Color.red);  //Panel Inferior
        //Rojo Izquierdo Lateral
        int FRILA[]  =   {  120,     50,     180     };  //Frontal  Superior
        int FRILB[]  =   {  120,     30,     140     };  //Frontal  Trasero
        int FRILD[]  =   {  120,     50,     70      };  //Central
        int FRILE[]  =   {  120,     50,     20      };  //Trasero Superior
        int FRILF[]  =   {  100,     30,     40      };  //Trasero Inferior
        GreatFox.addCara(FRILA, FRILB, FRILD, Color.red);  //Panel Frontal
        GreatFox.addCara(FRILF, FRILB, FRILD, Color.red);  //Panel Central
        GreatFox.addCara(FRILF, FRILE, FRILD, Color.red);  //Panel Trasero  
        //-----------------------------------------------------------BASE BLANCA
        int FBA[]  =   {   90,     40,     160     };  //Frontal  Derecha
        int FBB[]  =   {  110,     40,     160     };  //Frontal  Izquierda
        int FBD[]  =   {   80,     30,     140     };  //Central  Derecha
        int FBE[]  =   {  100,     30,     140     };  //Central  Central
        int FBF[]  =   {  120,     30,     140     };  //Central  Izquierda
        int FBG[]  =   {  100,     30,      40     };  //Trasero        
        GreatFox.addCara(FBA, FBB, FBE, Color.white);  //Panel Frontal Central
        GreatFox.addCara(FBA, FBD, FBE, Color.white);  //Panel Frontal Derecho
        GreatFox.addCara(FBB, FBF, FBE, Color.white);  //Panel Frontal Derecho
        GreatFox.addCara(FBG, FBD, FBF, Color.white);  //Panel Principal
        //Popa
        int FPG[]  =   {  100,     50,      20     };  //Trasero  central superior      
        GreatFox.addCara(FTF, FTG, FPG, Color.white);    //Panel Central Superior
        GreatFox.addCara(FTF, FRDSF, FPG, Color.white);  //Panel Derecha Superior
        GreatFox.addCara(FTG, FRISF, FPG, Color.white);  //Panel Izquierda Superior
        GreatFox.addCara(FRDSF, FRISF, FBG, Color.white);  //Panel Principal
        //------------------------------------------------------------------ALAS
        //Bahia de carga
        int ABA[]  =   {  80,     30,      140     };  //Superior Derecha
        int ABB[]  =   { 120,     30,      140     };  //Superior Izquierda
        int ABC[]  =   {  90,     10,      130     };  //Inferior Derecha
        int ABD[]  =   { 110,     10,      130     };  //Inferior Izquierda 
        
        int ABE[]  =   {  90,     30,       90     };  //Trasera Superior Derecha
        int ABF[]  =   { 110,     30,       90     };  //Trasera Superior Izquierda        
        int ABG[]  =   {  90,     20,       90     };  //Trasera Inferior Derecha
        int ABH[]  =   { 110,     20,       90     };  //Trasera Inferior Izquierda
        GreatFox.addCara(ABA, ABB, ABC, Color.gray);   //Panel Entrada 1
        GreatFox.addCara(ABD, ABC, ABB, Color.gray);   //Panel Entrada 2        
        GreatFox.addCara(ABA, ABB, ABF, Color.white);   //Panel Superior Frontal 1        
        GreatFox.addCara(ABA, ABC, ABG, Color.white);   //Panel Derecho Frontal 
        GreatFox.addCara(ABB, ABD, ABH, Color.white);   //Panel Izquierdo Frontal 
        GreatFox.addCara(ABA, ABE, ABG, Color.white);   //Panel Derecho Trasero 
        GreatFox.addCara(ABB, ABF, ABH, Color.white);   //Panel Izquierdo Trasero 
        GreatFox.addCara(ABC, ABD, ABH, Color.white);   //Panel Inferior Frontal
        GreatFox.addCara(ABH, ABG, ABC, Color.white);   //Panel Inferior Trasero
        
        //Ala Derecha
        int ADA[]  =   {  20,     20,       90     };  //Punta Frontal
        int ADB[]  =   {  20,     20,       60     };  //Punta Trasera
        int AC[]  =   { 100,     30,       40     };  //Cola
        int ADC[]  =   { 10,     30,       90     };  //Punta Flap Frontal
        int ADD[]  =   { 10,     30,       60     };  //Punta Flap Trasera
        GreatFox.addCara(ABE, ABG, ADA, Color.white);   //Panel Frontal
        GreatFox.addCara(ABE, ADA, ADB, Color.white);   //Panel Superior Frontal
        GreatFox.addCara(ABE, AC, ADB, Color.white);   //Panel Superior Trasero
        GreatFox.addCara(ADC, ADD, ADA, Color.white);   //Panel Panel Flap Frontal
        GreatFox.addCara(ADA, ADB, ADD, Color.white);   //Panel Panel Flap Trasero
        
        GreatFox.addCara(ABH, ABG, AC, Color.white);   //Panel De cola
        //Ala Izquierda
        int AIA[]  =   {  180,     20,       90     };  //Punta Frontal
        int AIB[]  =   {  180,     20,       60     };  //Punta Trasera
        int AIC[]  =   {  190,     30,       90     };  //Punta Flap Frontal
        int AID[]  =   {  190,     30,       60     };  //Punta Flap Trasera
        
        GreatFox.addCara(ABF, ABH, AIA, Color.white);   //Panel Frontal
        GreatFox.addCara(ABF, AIB, AIA, Color.white);   //Panel Superior Frontal
        GreatFox.addCara(ABF, AC, AIB, Color.white);   //Panel Superior Trasero
        GreatFox.addCara(AIC, AID, AIA, Color.white);   //Panel Panel Flap Frontal
        GreatFox.addCara(AIA, AIB, AID, Color.white);   //Panel Panel Flap Trasero
        
        GreatFox.addCara(ABH, ABG, AC, Color.white);   //Panel De cola

        GreatFox.Rotacion(-40, 180, 180);
        GreatFox.Traslacion(600, -900, 0);
        GreatFox.Escalacion(5, 5, 5);
    }    
    public void Arwing1( int x, int y, int z)
    {
        //----------------------------------------------------CENTRO DE GRAVEDAD
        Arg1.setCentroGravedad(150, 60, 25);
        
        //--------------------------------------------------------------FUSELAJE
        int pFA[]  =   { 150,  10,   20 };   //Nariz
        int pFB[]  =   { 140,  100,  30 };   //Esquina Cabina derecha
        int pFC[]  =   { 160,  100,  30 };   //Esquina Cabina izquierda
        int pFD[]  =   { 150,  100,  15 };   //Base de fuselaje
        int pFE[]  =   { 150,  150,  30 };   //Cola
        int pFF[]  =   { 150,  110,  35 };   //Cupula Fuselaje
        
        Arg1.addCara(pFA, pFB, pFC, Color.white); //Panel Superior
        Arg1.addCara(pFA, pFB, pFD, Color.white); //Panel Frontal Inferior derecha
        Arg1.addCara(pFA, pFC, pFD, Color.white); //Panel Frontal Inferior izquierda
        Arg1.addCara(pFB, pFC, pFF, Color.cyan);  //Windshield
        Arg1.addCara(pFB, pFE, pFF, Color.cyan);  //Cristal Derecho
        Arg1.addCara(pFC, pFE, pFF, Color.cyan);  //Cristal Izquierdo
        Arg1.addCara(pFB, pFD, pFE, Color.white);  //Panel Trasero Derecho
        Arg1.addCara(pFC, pFD, pFE, Color.white);  //Panel Trasero Izquierdo
        
        //---------------------------------------------------------MOTOR DERECHO
        int pMDA[]  =   { 170,  40,   30 };   //Nariz
        int pMDB[]  =   { 180,  100,  30 };   //Izquierda
        int pMDC[]  =   { 160,  100,  30 };   //Derecha
        int pMDD[]  =   { 170,  100,  35 };   //Centro Superior
        int pMDE[]  =   { 170,  100,  20 };   //Centro Inferior
        int pMDF[]  =   { 170,  120,  35 };   //Cola
        
        Arg1.addCara(pMDA, pMDD, pMDB, Color.blue); //Panel Frontal Superior Izquierdo
        Arg1.addCara(pMDA, pMDD, pMDC, Color.blue); //Panel Frontal Superior Derecho
        Arg1.addCara(pMDA, pMDE, pMDB, Color.blue); //Panel Frontal Inferior Izquierdo
        Arg1.addCara(pMDA, pMDE, pMDC, Color.blue); //Panel Frontal Inferior Derecho        
        Arg1.addCara(pMDB, pMDD, pMDF, Color.blue); //Panel Trasero Superior Izquierdo
        Arg1.addCara(pMDC, pMDD, pMDF, Color.blue); //Panel Trasero Superior Derecho
        Arg1.addCara(pMDB, pMDE, pMDF, Color.blue); //Panel Trasero Inferior Izquierdo
        Arg1.addCara(pMDC, pMDE, pMDF, Color.blue); //Panel Trasero Inferior Derecho
        
        //-------------------------------------------------------MOTOR IZQUIERDO
        int pMIA[]  =   { 130,  40,   30 };   //Nariz
        int pMIB[]  =   { 140,  100,  30 };   //Izquierda
        int pMIC[]  =   { 120,  100,  30 };   //Derecha
        int pMID[]  =   { 130,  100,  35 };   //Centro Superior
        int pMIE[]  =   { 130,  100,  20 };   //Centro Inferior
        int pMIF[]  =   { 130,  120,  35 };   //Cola
        
        Arg1.addCara(pMIA, pMID, pMIB, Color.blue); //Panel Frontal Superior Izquierdo
        Arg1.addCara(pMIA, pMID, pMIC, Color.blue); //Panel Frontal Superior Derecho
        Arg1.addCara(pMIA, pMIE, pMIB, Color.blue); //Panel Frontal Inferior Izquierdo
        Arg1.addCara(pMIA, pMIE, pMIC, Color.blue); //Panel Frontal Inferior Derecho        
        Arg1.addCara(pMIB, pMID, pMIF, Color.blue); //Panel Trasero Superior Izquierdo
        Arg1.addCara(pMIC, pMID, pMIF, Color.blue); //Panel Trasero Superior Derecho
        Arg1.addCara(pMIB, pMIE, pMIF, Color.blue); //Panel Trasero Inferior Izquierdo
        Arg1.addCara(pMIC, pMIE, pMIF, Color.blue); //Panel Trasero Inferior Derecho
        
        //---------------------------------------------------------ALA IZQUIERDA
        int pADA[]  =   { 290,  140,   20 };   //Punta
        int pADB[]  =   { 200,  70,    30 };   //Punta Frontal
        int pADC[]  =   { 200,  120,   30 };   //Trasero Superior
        int pADD[]  =   { 200,  120,   20 };   //Trasero Inferior
        int pADE[]  =   { 180,  100,   30 };   //Raiz
        
        Arg1.addCara(pADB, pADC, pADE, Color.white); //Panel Superior Raiz 
        Arg1.addCara(pADA, pADB, pADC, Color.white); //Panel Superior Externo  
        Arg1.addCara(pADB, pADE, pADD, Color.white); //Panel Inferior Raiz 
        Arg1.addCara(pADA, pADB, pADD, Color.white); //Panel Inferior Externo         
        Arg1.addCara(pADC, pADD, pADE, Color.white); //Panel Trasero Raiz  
        Arg1.addCara(pADA, pADC, pADD, Color.white); //Panel Trasero Externo
        //-----------------------------------------------------------ALA DERECHA
        int pAIA[]  =   { 10,  140,   20 };   //Punta
        int pAIB[]  =   { 100,  70,    30 };   //Punta Frontal
        int pAIC[]  =   { 100,  120,   30 };   //Trasero Superior
        int pAID[]  =   { 100,  120,   20 };   //Trasero Inferior
        int pAIE[]  =   { 120,  100,   30 };   //Raiz
        
        Arg1.addCara(pAIB, pAIC, pAIE, Color.white); //Panel Superior Raiz 
        Arg1.addCara(pAIA, pAIB, pAIC, Color.white); //Panel Superior Externo  
        Arg1.addCara(pAIB, pAIE, pAID, Color.white); //Panel Inferior Raiz 
        Arg1.addCara(pAIA, pAIB, pAID, Color.white); //Panel Inferior Externo         
        Arg1.addCara(pAIC, pAID, pAIE, Color.white); //Panel Trasero Raiz  
        Arg1.addCara(pAIA, pAIC, pAID, Color.white); //Panel Trasero Externo
        
        Arg1.Traslacion( x,y,z);
        Arg1.Rotacion(10,0,90); //Cubo
        Arg1.Escalacion(1.5,1.5,1.5); //Cubo
    }
    public void update()
    {
        Timer t1 = new Timer();
        t1.scheduleAtFixedRate(new TimerTask() 
        {    
            @Override            
            public void run()
            {
                Arg1.drawCuerpo(Buffer, Cam);
                repaint();             
            }
        },150,50);        
    }
    public void CorrerArwing1()
    {  
        //Actualizar pantalla con tasa de refresco de 20FPS
        Timer t1 = new Timer();
        t1.scheduleAtFixedRate(new TimerTask() 
        {    
            int i = 1;
            @Override            
            public void run()
            {                       
                repaint();
            }
        },10,50);
        //El Great Fox Avanza Lentamente hacia la derecha
        Timer t2 = new Timer();
        t2.scheduleAtFixedRate(new TimerTask() 
        {    
            int i = 1;
            @Override            
            public void run()
            {
                GreatFox.Traslacion( 0, 12, 0);
                //GreatFox.Escalacion( 1.01, 1.01, 1.01);
                GreatFox.Rotacion( .5, -0.2, 0); 
                
                Timer t2_1 = new Timer();
                t2_1.scheduleAtFixedRate(new TimerTask() 
                {    
                    int i = 1;
                    @Override            
                    public void run()
                    {
                        t2_1.cancel();
                        t2.cancel();
                    }
                },10000,100);                
        }
        },8000,100);
        Timer t3 = new Timer();
        t3.scheduleAtFixedRate(new TimerTask() 
        {    
            int i = 1;
            @Override            
            public void run()
            {
                GreatFox.Traslacion( 0, 0, 5);
                //GreatFox.Escalacion( 1.01, 1.01, 1.01);
                GreatFox.Rotacion( -0.1, -1.5, 0); 
                
                Timer t3_1 = new Timer();
                t3_1.scheduleAtFixedRate(new TimerTask() 
                {    
                    int i = 1;
                    @Override            
                    public void run()
                    {
                        t3_1.cancel();
                        t3.cancel();
                    }
                },10000,100);                
        }
        },18100,100);
        Timer t4 = new Timer();
        t4.scheduleAtFixedRate(new TimerTask() 
        {    
            int i = 1;
            @Override            
            public void run()
            {
                GreatFox.Traslacion( 0, 0, 100);
                GreatFox.Escalacion( 1.05, 0.9, 1);                
                Timer t4_1 = new Timer();
                t4_1.scheduleAtFixedRate(new TimerTask() 
                {    
                    int i = 1;
                    @Override            
                    public void run()
                    {
                        GreatFox.Escalacion( 0, 0, 0);
                        t4_1.cancel();
                        t4.cancel();
                    }
                },3000,100);                
        }
        },28000,100);
        Timer t5 = new Timer();
        t5.scheduleAtFixedRate(new TimerTask() 
        {    
            int i = 1;
            @Override            
            public void run()
            {
                CC = Color.red;
                offset += 1.001;
                Timer t5_1 = new Timer();
                t5_1.scheduleAtFixedRate(new TimerTask() 
                {    
                    public void run()
                    {
                        CC = Color.BLACK;
                        GreatFox.Escalacion( 0, 0, 0);
                        t5_1.cancel();
                        t5.cancel();
                    }
                },3000,100);                
        }
        },28000,100);
        Timer t6 = new Timer(); //Bajar el Arwing
        t6.scheduleAtFixedRate(new TimerTask() 
        {    
            int i = 1;
            @Override            
            public void run()
            {               
                Arg1.Traslacion( 0,5,0);

                Timer t6_1 = new Timer();
                t6_1.scheduleAtFixedRate(new TimerTask() 
                {    
                    public void run()
                    {
                        CC2 = Color.white;
                        SFX1 = 200; SFY1 = 50; SFX2 = 900; SFY2 = 250;
                        t6_1.cancel();
                        t6.cancel();
                    }
                },11500,100);                
        }
        },28000,100);
        
//        Timer t7 = new Timer(); //Mantener el arwing rotando
//        t7.scheduleAtFixedRate(new TimerTask() 
//        {    
//            int i = 1;
//            @Override            
//            public void run()
//            {
//                Arg1.Rotacion( 3,0,0);                
//            }
//        },39500,100);
        
        
        
        
        //Logo N64        
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() 
        {    
            @Override            
            public void run()
            {
                N64_Y1 += 1;
                N64_Y2 += 1; 
                
                
                Timer tt = new Timer();
                t.scheduleAtFixedRate(new TimerTask() 
                {    
                    @Override            
                    public void run()
                    {
                        tt.cancel();
                        t.cancel();             
                    }
                },15000,10);
            }
        },1000,15);

    }
    public void Malla()
    {
        int M[][]=new int[11][11];
        double X=0, Y=0, X1=0, Y1=0, pi=3.1416, Ny=0, Nx=0;
        int A, B;
        for(A=0; A<=10; A++){
            for(B=0; B<=10; B++){
                M[A][B]=(B*50)+100;                
            }
        }
        for(A=0; A<=10; A++){
            for(B=0; B<=10; B++){
                if(B!=0){
                    FacoGraf.FacosaLine(Buffer, M[A][B]+230, M[A][A], (int)Y+200, (int)X, CC2);
                    FacoGraf.FacosaLine(Buffer, M[B][A]+230, M[B][B], (int)X+200, (int)Y, CC2);
                }
                X=M[A][A];
                Y=M[A][B];
            }            
        }
    }
    @Override
    public void paint(Graphics g)
    {            
        Buffer.setData(LimpiarBuffer.getData());  
        Malla();
                        Buffer.setRGB( 100 , 100 , Color.white.getRGB());
                        Buffer.setRGB( 400 , 150 , Color.white.getRGB());
                        Buffer.setRGB( 210 , 240 , Color.white.getRGB());
                        Buffer.setRGB( 200 ,  14 , Color.white.getRGB());
                        Buffer.setRGB( 567 , 197 , Color.white.getRGB());
                        Buffer.setRGB( 808 , 213 , Color.white.getRGB());
                        Buffer.setRGB( 578 , 647 , Color.white.getRGB());
                        Buffer.setRGB( 937 , 412 , Color.white.getRGB());
                        Buffer.setRGB( 399 , 188 , Color.white.getRGB());
                        Buffer.setRGB( 779 , 690 , Color.white.getRGB());
                        Buffer.setRGB(1026 , 534 , Color.white.getRGB());
                        Buffer.setRGB(1143 , 241 , Color.white.getRGB());
                        Buffer.setRGB( 952 , 684 , Color.white.getRGB());
                        Buffer.setRGB( 100 , 533 , Color.white.getRGB());
                        Buffer.setRGB( 547 , 329 , Color.white.getRGB());
                        Buffer.setRGB( 300 , 500 , Color.white.getRGB());
                        Buffer.setRGB( 800 , 500 , Color.white.getRGB());
                        Buffer.setRGB( 800 , 100 , Color.white.getRGB());
                        
                        
        Portal();
        //RelojArena();
        //FigT.Traslacion( 0,0,20);
        
        //FigT.drawCuerpo(Buffer, Cam);   
        Arg1.Rotacion( 3,0,0);
        
       
        
        Arg1.drawCuerpo(Buffer, Cam);
        
        GreatFox.drawCuerpo(Buffer, Cam); 
        
        
        this.getGraphics().drawImage(Buffer, 0, 0, this);
        
         
        
        g.drawImage(SF , SFX1, SFY1 ,SFX2 , SFY2, 0 , 0 , 1000, 300, this);

        Toolkit herramienta = Toolkit.getDefaultToolkit();
        N64 = herramienta.getImage(getClass().getResource("img/N64.png"));
        g.drawImage(N64 , 400, N64_Y1 ,800 , N64_Y2, 0 , 0 , 200, 153, this);
        
    }
    public void Portal()
    {
                    
                    for(float i=0; i<=14; i+=0.05)
                    {
                    
                    X=((((17*Math.cos(i*pi))+(7*Math.cos(i*pi*offset*17/7)))*10)+200) * 1.5;
                    Y=(((17*Math.sin(i*pi))-(7*Math.sin(i*pi*offset*17/7)))*10)+300;
            
                    if(Ny != 0)
                    {
                        FacoGraf.FacosaLine(Buffer, (int)Nx+300, (int)Ny+50, (int)X+300, (int)Y+50, CC);
                    }
                    Nx=X;
                    Ny=Y;  
                    
                    Buffer.setRGB( (int)Math.round(X)+300 , (int)Math.round(Y)+50 , CC.getRGB());                    
                    } 
    }   
        public void Cubo()
    {
        FigT.setCentroGravedad(200, 200, 200);
        //Puntos         X      Y      Z        
        int pA[]  =   { 100,  100,  100 };
        int pB[]  =   { 300,  100,  100 };
        int pC[]  =   { 300,  300,  100 };
        int pD[]  =   { 100,  300,  100 }; 
        
        int pE[]  =   { 100,  100,  300 };
        int pF[]  =   { 300,  100,  300 };
        int pG[]  =   { 300,  300,  300 };
        int pH[]  =   { 100,  300,  300 }; 
        
        FigT.addCara(pA, pB, pC, Color.blue);
        FigT.addCara(pA, pD, pC, Color.blue);        
        FigT.addCara(pE, pF, pG, Color.red);
        FigT.addCara(pE, pH, pG, Color.red);        
        FigT.addCara(pB, pF, pC, Color.green);
        FigT.addCara(pF, pG, pC, Color.green);
    }
        public void RelojArena()
    {
        double G = 0;
        int X, Y, Z;
        for(double t=0; t<Math.PI; t+=0.0003)
        {                      
            X = (int) (100*( 2 +     ( Math.cos(t)*Math.cos(G) )      ));         
            Y = (int) (100*( 2 +     ( Math.cos(t)*Math.sin(G) )      ));         
            Z = (int) (100*t);          
            FigT.DibujarVertice(Buffer, Cam, X, Y, Z, Color.white);
            
            G+=1;   
            System.out.println("(" + X + "," + Y + "," + Z + ")");
        }      
    }
}