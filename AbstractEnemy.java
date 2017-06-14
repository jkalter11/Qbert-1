package qBert;

public abstract class AbstractEnemy {
	public float[] place = new float[3];
	public float[] oldPlace = new float[3];
	public float[] color = new float[3];
	public int floor, numCube, sign, lookSide = 0,dir=0;
	public float dx,dy,dz;
	public Render render;
	
	public AbstractEnemy(Render render){
		this.render = render;
	}
	
	public void jump(){
		Pyramid pyramid = render.pyramid;

		lookSide = numCube/pyramid.side[floor];
		
		updatePlace();	 
	}
	
	abstract public void updatePlace();
	
	abstract public void updateDeltas(int k);
	
	public void init(){};
}
