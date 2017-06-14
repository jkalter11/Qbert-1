package qBert;

public class EnemyMetaur extends AbstractEnemy {

	private boolean back = false;
	private int counter = 0;
	
	public EnemyMetaur(Render render, int floor, int numCube, int sign, int dir){
		super(render);		

		this.floor = floor;
		this.numCube = numCube;
		this.sign = sign;
		this.dir = dir;
				
		render.metaurShape.Load(Main.DIR + "metaur.obj");
		
		this.oldPlace[0] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[0];
		this.oldPlace[1] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[1] + 0.97f*sign;
		this.oldPlace[2] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[2];	
	}
	
	public void init(){
		floor = 0;
		sign = 1;
		back = false;
		updatePlace();
	}
	
	@Override
	public void updateDeltas(int k){
		if(k != 0){		
			if(k == 3)
				if(back){
					dy = -0.6f;
					dx = (float)(this.place[0] - this.oldPlace[0])/4;
					dz = (float)(this.place[2] - this.oldPlace[2])/4;
				}else{
					dy = -1.2f;
					dx = (float)(this.place[0] - this.oldPlace[0])/2;
					dz = (float)(this.place[2] - this.oldPlace[2])/2;
				}
			else{
				dy = (float)0.5/3;
				dx = (this.place[0] - this.oldPlace[0])/2;
				dz = (this.place[2] - this.oldPlace[2])/2;
			}
		}else{
			dx = 0;
			dy = 0;
			if((back && sign > 0) || (!back && sign < 0))
				dy = 0.7f*sign;
			else
				dy = 1.5f*sign;
		}
	}
	
	public void jump(){	
		if(sign > 0)
			lookSide = 0;
		else
			lookSide = 1 + dir*2;
		updatePlace();	 
	}
	
	public void updatePlace(){
		if(sign == 1 && !back && floor == 6){
			back = true;
			counter = 0;
		}
		if(sign == 1 && floor == 0 && back)
			sign = -1;
		if(sign == -1 && floor == 6 && back)
			back = false;
		if(sign == -1 && floor == 0 && !back){
			sign = 1;
			counter = 0;
		}
		
		if(sign == 1){
			if(!back){
				if(!(floor == 0 && counter == 0)){
					floor++;
					if(floor == 6)
						numCube = 0;
					else
						this.numCube = dir*render.pyramid.side[floor]*3; 							
				}else
					counter++;
						
			}else{	
				floor--;
				this.numCube = render.pyramid.side[floor]*(2-dir); 		
			}
		}else{//sign = -1
			if(back){
				if(!(floor == 0 && counter == 0)){			
					floor++;
					if(floor == 6)
						numCube = 0;
					else
						this.numCube = render.pyramid.side[floor]*(2-dir); 
				}else
					counter++;
			}else{	
					floor--;
					this.numCube = dir*render.pyramid.side[floor]*3;
			}
		}
		
		if(floor == 0){
			this.place[0] = render.pyramid.floors[0][floor].cubes[numCube].place[0];
			this.place[1] = render.pyramid.floors[0][floor].cubes[numCube].place[1] + 0.97f*sign;
			this.place[2] = render.pyramid.floors[0][floor].cubes[numCube].place[2];
		}else{
			this.place[0] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[0];
			this.place[1] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[1] + 0.97f*sign;
			this.place[2] = render.pyramid.floors[-(sign-1)/2][floor].cubes[numCube].place[2];
		}
	}

}
