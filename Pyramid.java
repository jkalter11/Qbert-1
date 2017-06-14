package qBert;


import qBert.Cube.check;


public class Pyramid {
		
	public Floor[][] floors = new Floor[2][7];
	//number of cubes on one side in qBert.floor i
    int side[] = {6, 5, 4, 3, 2, 1, 1};
	int lookSide = 0;
	int NumOfUnMarked = 146;	
	
	public Pyramid(){
		float dx = (float)Math.sqrt(2), dy = 1 ,dz = (float)Math.sqrt(2);
		
		floors[0][6] = new Floor(1);
		floors[1][6] = new Floor(1);
		for(int i=0; i<6; i++){
			floors[0][i] = new Floor(4*(6-i));
			if(i != 0)
				floors[1][i] = new Floor(4*(6-i));
		}
	    
	    //0 floor
		float move[] = {-3*dx,0*dy,3*dz};
		for(int i=0; i<7; i++){	
			floors[0][0].cubes[i] = new Cube();
			floors[0][0].cubes[i].place[0] = move[0] + dx*i;
			floors[0][0].cubes[i].place[1] = move[1];
			floors[0][0].cubes[i].place[2] = move[2];
		}
		for(int i=7; i<12; i++){		
			floors[0][0].cubes[i] = new Cube();
			floors[0][0].cubes[i].place[0] = move[0] + dx*6;
			floors[0][0].cubes[i].place[1] = move[1];
			floors[0][0].cubes[i].place[2] = move[2] - dz*(i-6);
		}
		for(int i=12; i<19; i++){	
			floors[0][0].cubes[i] = new Cube();
			floors[0][0].cubes[i].place[0] = move[0] + dx*6 - dx*(i-12);
			floors[0][0].cubes[i].place[1] = move[1];
			floors[0][0].cubes[i].place[2] = move[2] - dz*6;
		}
		for(int i=19; i<24; i++){	
			floors[0][0].cubes[i] = new Cube();
			floors[0][0].cubes[i].place[0] = move[0];
			floors[0][0].cubes[i].place[1] = move[1];
			floors[0][0].cubes[i].place[2] = move[2] - dz*5 + dz*(i-19);
		}
		
		//1 floor
		move[0] += 0.5f*dx;
		move[1] += dy;
		move[2] -= 0.5f*dz;
		for(int j=0; j<2; j++){
			for(int i=0; i<6; i++){		
				floors[j][1].cubes[i] = new Cube();
				floors[j][1].cubes[i].place[0] = move[0] + dx*i;
				floors[j][1].cubes[i].place[1] = 1+ move[1]*(-2)*j;
				floors[j][1].cubes[i].place[2] = move[2];
			}
			for(int i=6; i<10; i++){		
				floors[j][1].cubes[i] = new Cube();
				floors[j][1].cubes[i].place[0] = move[0] + dx*5;
				floors[j][1].cubes[i].place[1] = 1+ move[1]*(-2)*j;
				floors[j][1].cubes[i].place[2] = move[2] - dz*(i-5);
			}
			for(int i=10; i<16; i++){	
				floors[j][1].cubes[i] = new Cube();
				floors[j][1].cubes[i].place[0] = move[0] + dx*5 - dx*(i-10);
				floors[j][1].cubes[i].place[1] = 1+ move[1]*(-2)*j;
				floors[j][1].cubes[i].place[2] = move[2] - dz*5;
			}
			for(int i=16; i<20; i++){	
				floors[j][1].cubes[i] = new Cube();
				floors[j][1].cubes[i].place[0] = move[0];
				floors[j][1].cubes[i].place[1] = 1+ move[1]*(-2)*j;
				floors[j][1].cubes[i].place[2] = move[2] - dz*4 + dz*(i-16);
			}
		}
		
		//2 floor
		move[0] = -2*dx;
		move[1] = 0*dy;
		move[2] = 2*dz;
		for(int j=0; j<2; j++){
			for(int i=0; i<5; i++){	
				floors[j][2].cubes[i] = new Cube();
				floors[j][2].cubes[i].place[0] = move[0] + dx*i;
				floors[j][2].cubes[i].place[1] = 2 + (-4)*j;
				floors[j][2].cubes[i].place[2] = move[2];
			}
			for(int i=5; i<8; i++){		
				floors[j][2].cubes[i] = new Cube();
				floors[j][2].cubes[i].place[0] = move[0] + dx*4;
				floors[j][2].cubes[i].place[1] = 2 + (-4)*j;
				floors[j][2].cubes[i].place[2] = move[2] - dz*(i-4);
			}
			for(int i=8; i<13; i++){	
				floors[j][2].cubes[i] = new Cube();
				floors[j][2].cubes[i].place[0] = move[0] + dx*4 - dx*(i-8);
				floors[j][2].cubes[i].place[1] = 2 + (-4)*j;
				floors[j][2].cubes[i].place[2] = move[2] - dz*4;
			}
			for(int i=13; i<16; i++){	
				floors[j][2].cubes[i] = new Cube();
				floors[j][2].cubes[i].place[0] = move[0];
				floors[j][2].cubes[i].place[1] = 2 + (-4)*j;
				floors[j][2].cubes[i].place[2] = move[2] - dz*3 + dz*(i-13);
			}
		}
		
		//3 floor
		move[0] += 0.5f*dx;
		move[2] -= 0.5f*dz;
		for(int j=0; j<2; j++){
			for(int i=0; i<4; i++){		
				floors[j][3].cubes[i] = new Cube();
				floors[j][3].cubes[i].place[0] = move[0] + dx*i;
				floors[j][3].cubes[i].place[1] = 3 + (-6)*j;
				floors[j][3].cubes[i].place[2] = move[2];
			}
			for(int i=4; i<6; i++){		
				floors[j][3].cubes[i] = new Cube();
				floors[j][3].cubes[i].place[0] = move[0] + dx*3;
				floors[j][3].cubes[i].place[1] = 3 + (-6)*j;
				floors[j][3].cubes[i].place[2] = move[2] - dz*(i-3);
			}
			for(int i=6; i<10; i++){	
				floors[j][3].cubes[i] = new Cube();
				floors[j][3].cubes[i].place[0] = move[0] + dx*3 - dx*(i-6);
				floors[j][3].cubes[i].place[1] = 3 + (-6)*j;
				floors[j][3].cubes[i].place[2] = move[2] - dz*3;
			}
			for(int i=10; i<12; i++){	
				floors[j][3].cubes[i] = new Cube();
				floors[j][3].cubes[i].place[0] = move[0];
				floors[j][3].cubes[i].place[1] = 3 + (-6)*j;
				floors[j][3].cubes[i].place[2] = move[2] - dz*2 + dz*(i-10);
			}
		}
		
		//4 floor
		move[0] += 0.5f*dx;
		move[2] -= 0.5f*dz;
		for(int j=0; j<2; j++){
			for(int i=0; i<3; i++){		
				floors[j][4].cubes[i] = new Cube();
				floors[j][4].cubes[i].place[0] = move[0] + dx*i;
				floors[j][4].cubes[i].place[1] = 4 + (-8)*j;
				floors[j][4].cubes[i].place[2] = move[2];
			}
			for(int i=3; i<4; i++){		
				floors[j][4].cubes[i] = new Cube();
				floors[j][4].cubes[i].place[0] = move[0] + dx*2;
				floors[j][4].cubes[i].place[1] = 4 + (-8)*j;
				floors[j][4].cubes[i].place[2] = move[2] - dz*(i-2);
			}
			for(int i=4; i<7; i++){	
				floors[j][4].cubes[i] = new Cube();
				floors[j][4].cubes[i].place[0] = move[0] + dx*2 - dx*(i-4);
				floors[j][4].cubes[i].place[1] = 4 + (-8)*j;
				floors[j][4].cubes[i].place[2] = move[2] - dz*2;
			}
			for(int i=7; i<8; i++){	
				floors[j][4].cubes[i] = new Cube();
				floors[j][4].cubes[i].place[0] = move[0];
				floors[j][4].cubes[i].place[1] = 4 + (-8)*j;
				floors[j][4].cubes[i].place[2] = move[2] - dz + dz*(i-7);
			}
		}
		
		//5 floor
		move[0] += 0.5f*dx;
		move[2] -= 0.5f*dz;
		for(int j=0; j<2; j++){	
			floors[j][5].cubes[0] = new Cube();
			floors[j][5].cubes[0].place[0] = move[0];
			floors[j][5].cubes[0].place[1] = 5 + (-10)*j;
			floors[j][5].cubes[0].place[2] = move[2];
			
			floors[j][5].cubes[1] = new Cube();
			floors[j][5].cubes[1].place[0] = move[0] + dx;
			floors[j][5].cubes[1].place[1] = 5 + (-10)*j;
			floors[j][5].cubes[1].place[2] = move[2];
			
			floors[j][5].cubes[2] = new Cube();
			floors[j][5].cubes[2].place[0] = move[0] + dx;
			floors[j][5].cubes[2].place[1] = 5 + (-10)*j;
			floors[j][5].cubes[2].place[2] = move[2] - dz;
			
			
			floors[j][5].cubes[3] = new Cube();
			floors[j][5].cubes[3].place[0] = move[0];
			floors[j][5].cubes[3].place[1] = 5 + (-10)*j;
			floors[j][5].cubes[3].place[2] = move[2] - dz;		
		}
		
		//6 floor
		move[0] = 0;
		move[2] = 0;
		for(int j=0; j<2; j++){	
			floors[j][6].cubes[0] = new Cube();
			floors[j][6].cubes[0].place[0] = move[0];
			floors[j][6].cubes[0].place[1] = 6 + (-12)*j;;
			floors[j][6].cubes[0].place[2] = move[2];
		}			
	}
	
	public void rotatePyramid(QBert qBert,int key, Render render){
		int add = 0;
        //pyramid Y axis rotations
        if(qBert.oldNumCube%side[Math.abs(qBert.oldFloor)] == 0 && (key == Main.RIGHT || key == Main.LEFT)){
        	if(qBert.numCube/side[Math.abs(qBert.floor)] != lookSide){
        		
        		if(Math.abs(qBert.floor) != 6 && Math.abs(qBert.floor) != 5){
        			lookSide = qBert.numCube/side[Math.abs(qBert.floor)];
        			add = -30;
		        	if(key == Main.LEFT)
		  	     		add = -add;       
        		}
        	}
        	
        	if(Math.abs(qBert.floor) == 5){
        		if(lookSide == 0){
        			if(qBert.numCube == 2)
        				add = -30;
        			if(qBert.numCube == 3)
        				add = 30;
        		}
        		if(lookSide == 1){
        			if(qBert.numCube == 3)
        				add = -30;
        			if(qBert.numCube == 0)
        				add = 30;
        		}
        		if(lookSide == 2){
        			if(qBert.numCube == 0)
        				add = -30;
        			if(qBert.numCube == 1)
        				add = 30;
        		}
        		if(lookSide == 3){
        			if(qBert.numCube == 1)
        				add = -30;
        			if(qBert.numCube == 2)
        				add = 30;
        		}
        		
        		if(add == -30)
        			if(lookSide == 3)
        				lookSide = 0;
        			else
        				lookSide++;
        		if(add == 30)
        			if(lookSide == 0)
        				lookSide = 3;
        			else
        				lookSide--;
        	}	
        }
      
    	if(Math.abs(qBert.oldFloor) == 6  && (key == Main.DOWN)){
    		if(lookSide == 1)
    			add = 30;
    		if(lookSide == 3)
    			add = -30;
    		if(lookSide == 2)
    			add = -60;
    		lookSide = 0;
    	}
        
    	if(qBert.sign < 0 && Math.abs(qBert.floor) != 5)
    		add = -1*add;
    	
    	if(add != 0){
    		add = add/2;
        	for(int i=0; i<5; i++){
        		render.deltay += add;
    	        render.canvas.display();
    	        try {
    	        	Thread.sleep(100); // do nothing for 300 miliseconds
    	        }catch(InterruptedException e){
    	        	e.printStackTrace();
    	        }
        	}        
        	render.deltay += add;
    	}//end of pyramid rotation in Y axis
        
    	//Z axis rotation
        if(qBert.oldFloor == 0 && (key == Main.DOWN)){
        	add = 18;
        	for(int i=0; i<9; i++){
        		render.deltaz += add;
        		render.canvas.display();
    	        try {
    	        	Thread.sleep(100); // do nothing for 300 miliseconds
    	        }catch(InterruptedException e){
    	        	e.printStackTrace();
    	        }
        	}        
        	render.deltaz += add;
        }//end of pyramid rotation in Z axis
	}
	
	public void unMark(){
		NumOfUnMarked = 146;
				
		for(int j=0; j<2; j++){
			for(int i=0; i < 7; i++){
				if(j == 1 && i == 0)
					continue;
				int len = floors[j][i].cubes.length;
				for(int k=0; k<len; k++)
					floors[j][i].cubes[k].cubeCheck = check.UNMARKED;
			}
		}		
		
	}
}
