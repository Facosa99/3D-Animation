package carole3d;

public class Update extends Thread
{
        @Override
    public void run()
    {
        try {
            do
            {                
                Carole3D.update();
                Thread.sleep(50);
            }
        while(true);
        } catch (InterruptedException ex) {            
        }
    } 
}