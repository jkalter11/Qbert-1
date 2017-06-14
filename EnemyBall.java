package qBert;


public class EnemyBall extends AbstractEnemy{

	int dir;
	public EnemyBall(Render render, int floor, int numCube, int sign, int dir){
		super(render);		

		this.floor = floor;
		this.numCube = numCube;
		this.sign = sign;
		this.dir = dir;
		
		color[0] = 0; color[1] = 0; color[2] = 1;
		
		this.oldPlace[0] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[0];
		this.oldPlace[1] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[1] + 0.8f*sign;
		this.oldPlace[2] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[2];	
	}
	
	public void updateDeltas(int k){
		dx = (this.place[0] - this.oldPlace[0])/4;
		dy = 0.5f*sign;
		dz = (this.place[2] - this.oldPlace[2])/4;
		
		if(k == 2){
			dy = -dy;
			dx *= 2;
			dz *= 2;
		}
	}
	
	public void updatePlace(){
		if(dir == -1 && numCube == 0){
			numCube = render.pyramid.side[floor]*4-1;
		}else
		this.numCube = (this.numCube + 1*dir) % (render.pyramid.side[this.floor]*4); 			
		
		this.place[0] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[0];
		this.place[1] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[1] + 0.8f*sign;
		this.place[2] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[2];
	}
}
