package qBert;

import qBert.Cube.check;

public class QBert {
	
	public float[] place = new float[3];
	public float[] oldPlace = new float[3];
	public int floor = 6;
	public int numCube = 0;
	public int oldFloor = 6;
    public int oldNumCube = 0;
	public int sign = 1;
	public int look = 0;
	public Render render;
	
	public QBert(Render render){
		this.render = render;
		render.qBertShape.Load(Main.DIR + "toad.obj");
		this.oldPlace[0] = render.pyramid.floors[0][floor].cubes[numCube].place[0];
		this.oldPlace[1] = render.pyramid.floors[0][floor].cubes[numCube].place[1] + 1f;
		this.oldPlace[2] = render.pyramid.floors[0][floor].cubes[numCube].place[2];	
	}
	
	public void init(){
		floor = 6;
		numCube = 0;
		oldFloor = 6;
	    oldNumCube = 0;
		look = 0;
		
		this.oldPlace[0] = render.pyramid.floors[(sign-1)/-2][floor].cubes[numCube].place[0];
		this.oldPlace[1] = render.pyramid.floors[(sign-1)/-2][floor].cubes[numCube].place[1] + 1f*sign;
		this.oldPlace[2] = render.pyramid.floors[(sign-1)/-2][floor].cubes[numCube].place[2];
	}
	
	public void jump(Pyramid pyramid, int key, Render render){
		this.oldFloor = this.floor;
		this.oldNumCube = this.numCube;
		
		if (this.floor < 0)
        	this.sign = -1;
        
        this.floor = Math.abs(this.floor);
        
        if (key == Main.UP){
        	look = 2;
        	if(this.floor != 6){
	        	if(this.floor != 5){
	        		this.numCube = (this.numCube%pyramid.side[this.floor] + (this.numCube/pyramid.side[this.floor])
	        				*pyramid.side[this.floor + 1]) % (pyramid.side[this.floor + 1]*4);
	        		this.floor++;
	        	}else{
	        		this.numCube = 0;
	        		this.floor = 6;
	        	}
        	}
        }
                       
        if (key == Main.DOWN){
        	look = 0;
        	if(this.floor != 0){
        		this.numCube = (this.numCube%pyramid.side[this.floor] + (this.numCube/pyramid.side[this.floor])
        				*pyramid.side[this.floor - 1]) % (pyramid.side[this.floor - 1]*4);
        		this.floor--;
        	}else{
        		this.sign = -this.sign;
        	}
        } 
        
        if ((key == Main.LEFT && this.sign > 0) || (key == Main.RIGHT && this.sign < 0)){
        	look = 3;
        	if(this.floor != 6)
        		if(this.numCube != 0)
        			this.numCube = (this.numCube - 1) % (pyramid.side[this.floor]*4);
	   	    	else
	        		this.numCube = (pyramid.side[this.floor]*4) - 1;
        }
        
        if ((key == Main.RIGHT && this.sign > 0) || (key == Main.LEFT && this.sign < 0)){ 
        	look = 1;
        	if(this.floor != 6)
        		this.numCube = (this.numCube + 1) % (pyramid.side[this.floor]*4); 
        }
        
        this.floor = this.floor * this.sign;
        
        
		int i=floor,j=0;
		
		if(floor < 0){
			i = -floor;
			j = 1;
		}
			
		this.place[0] = pyramid.floors[j][i].cubes[numCube].place[0];
		this.place[1] = pyramid.floors[j][i].cubes[numCube].place[1] + 1f*sign;
		this.place[2] = pyramid.floors[j][i].cubes[numCube].place[2];
		
		if(key != Main.DOWN || oldFloor != 0){
			float dx = (place[0] - oldPlace[0])/4;
			float dy = ((place[1] - oldPlace[1])*2 + 2*sign)/4;
			float dz = (place[2] - oldPlace[2])/4;
			
			if(Math.round(dy) == 0){
				dy = 0.5f*sign;
			}
			
			if(dy > 0.99 && dy < 1.001){
				dy = 2f*sign;
			}
			
			for(int k=0; k<3; k++){
				if(k == 2){
					dy = -dy;
					oldPlace[0] += dx;
					oldPlace[2] += dz;
				}
				oldPlace[0] += dx;
				if(!(dy == 2f*sign && k==1))
					oldPlace[1] += dy;
				else
					dy = 0.5f;
				oldPlace[2] += dz;
	    		render.canvas.display();
		        try {
		        	Thread.sleep(40);
		        }catch(InterruptedException e){
		        	e.printStackTrace();
		        }
	    	}
		}
		
		oldPlace[0] = place[0];
		oldPlace[1] = place[1];
		oldPlace[2] = place[2];
		
		if(pyramid.floors[j][i].cubes[numCube].cubeCheck == check.UNMARKED){
			pyramid.floors[j][i].cubes[numCube].cubeCheck = check.MARKED;
			pyramid.NumOfUnMarked--;
			Main.scores += 10;
			if((Main.scores % 100 == 0) && (Main.scores > 0)){
				Main.player.play("three");
				Main.lives++;
			}
			
			if(Main.level != 3 && Main.win(render)){
				Main.level++;
				Main.player.play("four");
				pyramid.unMark();
				render.qBert.init();
				render.enemySmurf.floor = 0;
				render.enemyMetaur1.floor = 0;
				render.enemyMetaur2.floor = 0;
				render.enemySmurf.updatePlace();
				render.enemyMetaur1.updatePlace();
				render.enemyMetaur2.updatePlace();
			}
		}
	}

}
