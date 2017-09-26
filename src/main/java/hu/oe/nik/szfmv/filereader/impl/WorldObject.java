package hu.oe.nik.szfmv.filereader.impl;

public class WorldObject {
    public int PosX;
    public int PosY;
    public int RoadPainting1;
    public int RoadPainting2;
    public int RoadPainting3;

    public int[] Transform = new int[4];

    public int getPosX(){ return PosX; }
    public void setPosX(int x){ this.PosX = x; }

    public int getPosY(){ return PosY; }
    public void setPosY(int y){ this.PosY = y; }

    public int getRoadPainting1(){ return RoadPainting1; }
    public void setRoadPainting1(int n){ this.RoadPainting1 = n; }

    public int getRoadPainting2(){ return RoadPainting2; }
    public void setRoadPainting2(int n){ this.RoadPainting2 = n; }

    public int getRoadPainting3(){ return RoadPainting3; }
    public void setRoadPainting3(int n){ this.RoadPainting3 = n; }


    public int[] getTransformationMatrix()
    {
        return Transform;
    }

    public void setTransformationMatrix(int[] matrix)
    {
        this.Transform = matrix;
    }
}
