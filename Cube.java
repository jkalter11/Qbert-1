package qBert;


public class Cube {
	
	public enum check {MARKED, UNMARKED};
	
	public float[] place = new float[3];// the place of the center of the cube
	public check cubeCheck = check.UNMARKED;
	public float size = 1;
	
	public double[] getColor(){
		if(Main.level == 1){
			if(cubeCheck == check.UNMARKED){
				double[] color = {1,1,1};
				return color;
			}else{
				double[] color = {1,1,0.3};
				return color;
			}
		}
		
		if(Main.level == 2){
			if(cubeCheck == check.UNMARKED){
				double[] color = {0.726,0.827,0.933};
				return color;
			}else{
				double[] color = {(float)85/255,(float)107/255,(float)47/255};
				return color;
			}
		}
		
		if(Main.level == 3){
			if(cubeCheck == check.UNMARKED){
				double[] color = {(float)216/255,(float)191/255,(float)216/255};
				return color;
			}else{
				double[] color = {(float)139/255,(float)101/255,(float)8/255};
				return color;
			}
		}
		
		double[] color = {1,1,1};
		return color;
	}
}
