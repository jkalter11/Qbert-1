package qBert;

public class EnemySmurf extends AbstractEnemy {

	int counter = 0;
	
	public EnemySmurf(Render render, int floor, int numCube, int sign){
		super(render);
		
		this.floor = 0;
		this.numCube = numCube;
		this.sign = sign;
		this.dir = 1;
		
		render.smurfShape.Load(Main.DIR + "smurf_2.obj");
		
		this.oldPlace[0] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[0];
		this.oldPlace[1] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[1] + 1.8f*sign;
		this.oldPlace[2] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[2];	
	}
	
	public void init(){
		floor = 0;
		dir = 1;
		updatePlace();
	}
	
	public void updateDeltas(int k){
		if(counter != 0){
			dx = (this.place[0] - this.oldPlace[0])/4;
			dy = (this.place[1] - this.oldPlace[1])/4;
			dz = (this.place[2] - this.oldPlace[2])/4;
		}else{
			dx=0;dy=0;dz=0;
		}
	}
	
	public void updatePlace(){
		QBert qBert = render.qBert;
		
		if(counter >= 4 && (floor != qBert.floor*qBert.sign || sign != qBert.sign)){
			counter = 0;
			if(qBert.sign != this.sign){
				if(this.floor != 0){
					floor--;
				}else{
					sign = -sign;
				}
			}else{
				if(qBert.floor*qBert.sign < this.floor && floor != 0)
					floor--;
				if(qBert.floor*qBert.sign > this.floor){
					floor++;
					numCube = numCube % (render.pyramid.side[this.floor]*4);
				}
			}
		}else{
			counter++;
				this.numCube = (this.numCube + 1*dir) % (render.pyramid.side[this.floor]*4);
		}
		
		if(floor == 6)
			numCube = 0;
		
		if(floor == 0){
			this.place[0] = render.pyramid.floors[0][floor].cubes[numCube].place[0];
			this.place[1] = render.pyramid.floors[0][floor].cubes[numCube].place[1] + 1.8f*sign;
			this.place[2] = render.pyramid.floors[0][floor].cubes[numCube].place[2];
		}else{
			this.place[0] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[0];
			this.place[1] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[1] + 1.8f*sign;
			this.place[2] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[2];
		}
	}
}
