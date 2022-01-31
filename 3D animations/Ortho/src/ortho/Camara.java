package ortho;

import static java.lang.Math.tan;

public class Camara 
{
    int CenX,   CenY,   CenZ;
    int PlaX,   PlaY,   PlaZ;
    int ResX,   ResY;
    
    public void SetParametros( int CentroX, int CentroY, int CentroZ, int PlanoX, int PlanoY, int PlanoZ, int ResolucionX, int ResolucionY)
    {
        CenX = CentroX;     CenY = CentroY;     CenZ = CentroZ;
        PlaX = PlanoX;      PlaY = PlanoY;      PlaZ = PlanoZ;
        ResX = ResolucionX; ResY = ResolucionY;
    }
    
    public int GetCentroX()
    {
        return CenX;
    }
    public int GetCentroY()
    {
        return CenY;
    }
    public int GetCentroZ()
    {
        return CenZ;
    }
    public int GetPlanoX()
    {
        return PlaX;
    }
    public int GetPlanoY()
    {
        return PlaY;
    }
    public int GetPlanoZ()
    {
        return PlaZ;
    }
    
}
