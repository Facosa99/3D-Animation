package carole3d;

import java.awt.Color;
import java.awt.Graphics;
import static java.awt.PageAttributes.ColorType.COLOR;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Carole3D extends JFrame 
{
    static Cohete Cuete = new Cohete();
    static Update Actualizar = new Update();
    static int Escena = 1;
    static  BufferedImage Buffer;  
            BufferedImage LimpiarBuffer; 
            BufferedImage Fondo; 
    
    int ResX = 1200, ResY = 700;
    int Humo = 0;
    
    static Carole3D Main = new Carole3D(); 
    public Carole3D()
    {
        setTitle("Parcial 3: Animación");
        this.setSize(ResX, ResY);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Buffer          = new BufferedImage(ResX,ResY,BufferedImage.TYPE_INT_RGB);
        LimpiarBuffer   = new BufferedImage(ResX,ResY,BufferedImage.TYPE_INT_RGB);        
        Cohete Cuete    = new Cohete();
        Cuete.Inicializar();
        
        setVisible(true);
    }
    public static void main(String[] args) 
    {
        Actualizar.start();
        Cuete.start();
    }     
    @Override
    public void paint(Graphics g)
    {     
        try 
        {    
            if ( Escena == 1)
            {
                Fondo            = ImageIO.read(getClass().getResource("img/Fondo.jpg")); 
                
            }
            else
            {
                Fondo            = ImageIO.read(getClass().getResource("img/Tierra.jpg")); 
            }
                         
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        Buffer.getGraphics().drawImage(Fondo, 1, 30, ResX, ResY, this);
        Cuete.Dibujar(Buffer);
        if (Escena == 1)
        {
            malla();
            humito(Humo-280);
            Humo += 6;
        }
        this.getGraphics().drawImage(Buffer, 0, 0, this);        
    } 
    public static void update()
    {
        Main.repaint();
    }    
    public void malla() {
        int M[][]=new int[11][11];
        double X=0, Y=0, X1=0, Y1=0, pi=3.1416, Ny=0, Nx=0;
        int A, B;
        for(A=0; A<=10; A++){
            for(B=0; B<=10; B++){
                M[A][B]=(B*20)+100;                
            }
        }
        for(A=0; A<=10; A++){
            for(B=0; B<=10; B++){
                if(B!=0){
                    DDA(Buffer, M[A][B]+400, M[A][A]+340, (int)Y+400, (int)X+340, Color.black);
                    DDA(Buffer, M[B][A]+400, M[B][B]+340, (int)X+400, (int)Y+340, Color.black);
                }
                X=M[A][A];
                Y=M[A][B];
            }            
        }
    }
    public void DDA(BufferedImage buffer, int x1, int y1, int x2, int y2, Color cc)
    {
        int dx=Math.abs(x2 - x1);
        int dy=Math.abs(y2 - y1);
        int sx=(x1<x2) ? 1 : -1;
        int sy=(y1<y2) ? 1 : -1;

        int p0=dx-dy;

        while(true){
            if ( x1 > 0 && x1 < ResX)
            {
                if ( y1 > 0 && y1 < ResY)
                {
                    buffer.setRGB(x1, y1, cc.getRGB());
                }
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
public void humito( int Y ) {
        System.out.println(Y);
        int x1 = 0, x2 = 0, y = 0;
        for (double i = 2*Math.PI; i>0; i=i-.01) 
        {
            x2 = x1;
            y =(int) ((int) (350-(6*i)));
            x1= (int)((int) 240+6*i*Math.cos(4*i));
            if(i!=2*Math.PI)
            DDA(Buffer, x1+360, (int) y-Y, x2+360, y-Y,Color.red);
        }
      }
}