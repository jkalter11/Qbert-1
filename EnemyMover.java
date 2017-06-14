package qBert;

public class EnemyMover implements Runnable {
	public boolean ok = true;
	public int numOfEnemies = 9;
	public AbstractEnemy[] enemiesArr = new AbstractEnemy[numOfEnemies];
	public Render render;
	
	public EnemyMover(Render render){
		this.render = render;
		enemiesArr[0] = render.enemySmurf;
		enemiesArr[1] = render.enemyBall1;
		enemiesArr[2] = render.enemyBall2;
		enemiesArr[3] = render.enemyMetaur1;	
		enemiesArr[4] = render.enemyMetaur2;
		enemiesArr[5] = render.enemyBall3;
		enemiesArr[6] = render.enemyBall4;
		enemiesArr[7] = render.enemyBall5;
		enemiesArr[8] = render.enemyBall6;		
	}
		
	public void run() {	
		while (ok) {		
			if(Main.level == 1)
				numOfEnemies = 4;
			if(Main.level == 2)
				numOfEnemies = 7;
			if(Main.level == 3)
				numOfEnemies = 9;
			
			render.canvas.display();
			
			for(int i=0; i<numOfEnemies; i++){
				enemiesArr[i].jump();			
				enemiesArr[i].updateDeltas(0);
			}	
			
			for(int k=0; k<3; k++){
				for(int i=0; i<numOfEnemies; i++){
					enemiesArr[i].updateDeltas(k);
				
					enemiesArr[i].oldPlace[0] += enemiesArr[i].dx;
					enemiesArr[i].oldPlace[1] += enemiesArr[i].dy;
					enemiesArr[i].oldPlace[2] += enemiesArr[i].dz;
				
					float deltaX = (float)Math.pow(Math.abs(render.qBert.oldPlace[0]-enemiesArr[i].oldPlace[0]),2);
					float deltaY = (float)Math.pow(Math.abs(render.qBert.oldPlace[1]-enemiesArr[i].oldPlace[1]),2);
					float deltaZ = (float)Math.pow(Math.abs(render.qBert.oldPlace[2]-enemiesArr[i].oldPlace[2]),2);
					if((Math.sqrt(deltaX + deltaY + deltaZ) < 0.4 || 
							(Math.abs(render.qBert.floor) == enemiesArr[i].floor 
									&& render.qBert.numCube == enemiesArr[i].numCube
									&& render.qBert.sign == enemiesArr[i].sign) )){
						Main.bump = true;
						render.enemySmurf.init();
						render.enemyMetaur1.init();
						render.enemyMetaur2.init();
						Main.player.play("two");
						Main.display.wake();
						render.canvas.display();
						
						try {
							Thread.sleep(1000);
						}catch(InterruptedException e){
							e.printStackTrace();
						}
					}
				}
					
				try {
					Thread.sleep(250 - (Main.level - 1)*100);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
					
				render.canvas.display();
			}
			
			for(int i=0; i<numOfEnemies; i++){
				enemiesArr[i].oldPlace[0] = enemiesArr[i].place[0];
				enemiesArr[i].oldPlace[1] = enemiesArr[i].place[1];
				enemiesArr[i].oldPlace[2] = enemiesArr[i].place[2];
				
				float deltaX = (float)Math.pow(Math.abs(render.qBert.oldPlace[0]-enemiesArr[i].oldPlace[0]),2);
				float deltaY = (float)Math.pow(Math.abs(render.qBert.oldPlace[1]-enemiesArr[i].oldPlace[1]),2);
				float deltaZ = (float)Math.pow(Math.abs(render.qBert.oldPlace[2]-enemiesArr[i].oldPlace[2]),2);
				if((Math.sqrt(deltaX + deltaY + deltaZ) < 0.4 || 
						(Math.abs(render.qBert.floor) == enemiesArr[i].floor 
								&& render.qBert.numCube == enemiesArr[i].numCube 
								&& render.qBert.sign == enemiesArr[i].sign) )){
					Main.bump = true;		
					render.enemySmurf.init();
					render.enemyMetaur1.init();
					render.enemyMetaur2.init();
					Main.player.play("two");
					Main.display.wake();
					render.canvas.display();
					
					try {
						Thread.sleep(1000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}	
	}
	
	public void stopRunning() {
		ok = false;
	}

}
