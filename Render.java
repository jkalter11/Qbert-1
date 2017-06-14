package qBert;


import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import com.sun.opengl.util.GLUT;

import org.eclipse.swt.awt.SWT_AWT;


public  class Render implements GLEventListener{
	
	float  deltax=0 ,deltay=0, deltaz=0, zoom= 0;
	float  deltax2=0 ,deltay2=0, deltaz2=0;
	int oldx, oldy;
	
	Background background;
	
	Pyramid pyramid = new Pyramid();
	ObjObject qBertShape = new ObjObject();
	QBert qBert = new QBert(this);	
	AbstractEnemy enemyBall1 = new EnemyBall(this,3,0,1,-1);
	AbstractEnemy enemyBall2 = new EnemyBall(this,3,0,-1,-1);
	AbstractEnemy enemyBall3 = new EnemyBall(this,1,0,1,1);
	AbstractEnemy enemyBall4 = new EnemyBall(this,1,0,-1,1);
	AbstractEnemy enemyBall5 = new EnemyBall(this,5,0,1,1);
	AbstractEnemy enemyBall6 = new EnemyBall(this,5,0,-1,1);
	ObjObject metaurShape = new ObjObject();
	AbstractEnemy enemyMetaur1 = new EnemyMetaur(this,0,0,1,0);
	AbstractEnemy enemyMetaur2 = new EnemyMetaur(this,0,18,1,1);
	ObjObject smurfShape = new ObjObject();
	AbstractEnemy enemySmurf = new EnemySmurf(this,0,2,1);
	
	private int myCubeList;
	
	final GLCanvas canvas;
	Composite composite;
		
	Render(Display display, Shell shell){
		
		// this allows us to set particular properties for the GLCanvas
		GLCapabilities glCapabilities = new GLCapabilities();
		
		glCapabilities.setDoubleBuffered(true);
		glCapabilities.setHardwareAccelerated(true);

		// instantiate the canvas
		canvas = new GLCanvas(glCapabilities);

		// we can't use the default Composite because using the AWT bridge
		// requires that it have the property of SWT.EMBEDDED
		composite = new Composite(shell, SWT.EMBEDDED);
		GridData ld = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(ld);
		
		// the grid layout allows you to add more widgets in the main window.
			
		// set the internal layout so our canvas fills the whole control
		FillLayout clayout = new FillLayout();
		composite.setLayout(clayout);
		
		// create the special frame bridge to AWT
		java.awt.Frame glFrame = SWT_AWT.new_Frame(composite);
		// we need the listener so we get the GL events
		canvas.addGLEventListener(this);

		// finally, add our canvas as a child of the frame
		glFrame.add(canvas);		
	}

	
	public void init(GLAutoDrawable drawable){
		GL gl = drawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		    
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();	  
		
		myCubeList = gl.glGenLists(1);
		gl.glNewList(myCubeList, GL.GL_COMPILE);
		
		background= new Background(Main.DIR + "background.jpg");
		    
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);
		gl.glLoadIdentity();
	}
		
		
	public void display(GLAutoDrawable drawable){		      
		GLUT glut = new GLUT();
		GL gl = drawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glColor3f(1,1,1);
		background.DrawBackgeound(gl);
		gl.glClearColor((float)139/255,(float)69/255,(float)19/255,1);
		
		if(!Main.win(this)){
			if(Main.lives > 0){
				drawPyramid(gl);   
				drawQbert(gl);
				
				drawEnemyBall(gl,enemyBall1);
				drawEnemyBall(gl,enemyBall2);
				drawMetaur(gl,enemyMetaur1);				
				drawSmurf(gl,enemySmurf);
				if(Main.level == 2 || Main.level == 3){
					drawEnemyBall(gl,enemyBall3);
					drawEnemyBall(gl,enemyBall4);
					drawMetaur(gl,enemyMetaur2);
				}
				if(Main.level == 3){
					drawEnemyBall(gl,enemyBall5);
					drawEnemyBall(gl,enemyBall6);
				}
				
				gl.glEnable(GL.GL_COLOR_MATERIAL);
				gl.glColor3f(1,1,1);
				gl.glNormal3d(0.0f,0.0f, 1.0f);	
				
				gl.glRasterPos3f(-0.3f, -1.000f, -3.0f);	  
				glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Level " + Main.level);
				
				gl.glRasterPos3f(-1.5f, 0.750f, -3.0f);
				glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Lives " + Main.lives);
				
				gl.glRasterPos3f(1.0f, 1.000f, -3.0f);
				glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Scores " + Main.scores);
				
				gl.glRasterPos3f(0.63f, 0.750f, -3.0f);
				glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "(100 Scores = 1 Life)");
			}else{					
				gl.glRasterPos3f(-0.3f, 0f, -3.0f);	
				glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "GAME OVER");
				Main.display.wake();
			}
		}else{
			Main.mover.stopRunning();
			gl.glRasterPos3f(-0.3f, 0f, -3.0f);	
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "YOU WIN");
		}
	}
			
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){
		GL gl = drawable.getGL();
		final GLU glu = new GLU();

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, (double) width / (double) height, 0.1f, 1000.0f);
		gl.glMatrixMode(GL.GL_MODELVIEW);
	}		
		

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// should remain empty
	}
	
	public void drawPyramid(GL gl){
		gl.glNewList(myCubeList, GL.GL_COMPILE);
		drawCube(gl);
		gl.glEndList();
		for(int j=0; j<2; j++){
			for(int i=0; i < 7; i++){
				if(j == 1 && i == 0)
					continue;
				int len = pyramid.floors[j][i].cubes.length;
				for(int k=0; k<len; k++){
					Cube cube = pyramid.floors[j][i].cubes[k];
					double[] color = cube.getColor();
					gl.glColor3d(color[0],color[1],color[2]);
					
					gl.glLoadIdentity();
					gl.glTranslatef(0.0f, 0.0f, -20 + this.zoom);
					gl.glRotatef(45, 1.0f, 0.0f, 0.0f);
					gl.glRotatef(deltaz + deltaz2, 0.0f, 0.0f, 1.0f);
					gl.glRotatef(deltay + deltay2, 0.0f, 1.0f, 0.0f);
					gl.glRotatef(deltax + deltax2, 1.0f, 0.0f, 0.0f);
					gl.glTranslatef(cube.place[0], cube.place[1], cube.place[2]);
					gl.glCallList(myCubeList);
				}
					
			}
		}		
	}
	
	public void drawCube(GL gl){
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);
			
		
		gl.glRotatef(45, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(-0.5f, -0.5f, 0.5f);
			
		gl.glBegin(GL.GL_QUADS);
			
	    gl.glNormal3d(0, 0, 1);
	    //forward face
	    gl.glVertex3d(0, 0, 0);
	    gl.glVertex3d(1, 0, 0);
	    gl.glVertex3d(1, 1, 0);
	    gl.glVertex3d(0, 1, 0);
	    
	    
	    gl.glNormal3d(0, 0, -1);
	    //backward face
	    gl.glVertex3d(0, 0, -1);
	    gl.glVertex3d(0, 1, -1);
	    gl.glVertex3d(1, 1, -1);
	    gl.glVertex3d(1, 0, -1);
	    
	    
	    gl.glNormal3d(0, 1, 0);
	    //up face
	    gl.glVertex3d(0, 1, 0);
	    gl.glVertex3d(1, 1, 0);
	    gl.glVertex3d(1, 1, -1);
	    gl.glVertex3d(0, 1, -1);
	    
	    
	    gl.glNormal3d(0, -1, 0);
	    //down face
	    gl.glVertex3d(0, 0, 0);
	    gl.glVertex3d(0, 0, -1);
	    gl.glVertex3d(1, 0, -1);
	    gl.glVertex3d(1, 0, 0);
	        	       
	    
	    gl.glNormal3d(-1, 0, 0);
	    //left face
	    gl.glVertex3d(0, 0, 0);
	    gl.glVertex3d(0, 1, 0);
	    gl.glVertex3d(0, 1, -1);
	    gl.glVertex3d(0, 0, -1);
	    
	    
	    gl.glNormal3d(1, 0, 0);
	    //right face
	    gl.glVertex3d(1, 0, 0);
	    gl.glVertex3d(1, 0, -1);
	    gl.glVertex3d(1, 1, -1);
	    gl.glVertex3d(1, 1, 0);
		      
	    gl.glEnd();
	    
	    gl.glLoadIdentity();
	    gl.glDisable(GL.GL_COLOR_MATERIAL);
	}
	
	public void drawQbert(GL gl){	
		int sign = qBert.sign;
		int lookSide = pyramid.lookSide;
		qBertShape.rotateFirst(-90, 90+90*lookSide*sign, 90*(1-sign));
		
		qBertShape.scale(1.5f, 1.5f, 1.5f);
		qBertShape.translateAfterRotate(0, 0, -20+zoom);	
		qBertShape.translateBeforeRotate(qBert.oldPlace[0], qBert.oldPlace[1], qBert.oldPlace[2]);	
		qBertShape.rotate(deltax+deltax2,deltay+deltay2,deltaz+deltaz2);							
		qBertShape.Draw(gl,this,qBert.look);
		
		gl.glLoadIdentity();
	}
	
	public void drawSmurf(GL gl, AbstractEnemy enemy){	
		int sign = enemy.sign;
		int lookSide = enemy.lookSide;
			
		smurfShape.rotateFirst(-90, 0,(-1+sign)*90);	
		smurfShape.translateAfterRotate(0, 0, -20+zoom);
		smurfShape.translateBeforeRotate(enemy.oldPlace[0], enemy.oldPlace[1], enemy.oldPlace[2]);	
		smurfShape.rotate(deltax+deltax2,deltay+deltay2,deltaz+deltaz2);
		smurfShape.scale(7f, 7f, 9f);
		smurfShape.Draw(gl,this,lookSide);
		
		gl.glLoadIdentity();
	}
	
	public void drawMetaur(GL gl, AbstractEnemy enemy){	
		int sign = enemy.sign;
		int lookSide = enemy.lookSide;
			
		metaurShape.rotateFirst(-90, 135 - 90*enemy.dir, (-1+sign)*90);	
		metaurShape.translateAfterRotate(0, 0, -20+zoom);
		metaurShape.translateBeforeRotate(enemy.oldPlace[0], enemy.oldPlace[1], enemy.oldPlace[2]);	
		metaurShape.rotate(deltax+deltax2,deltay+deltay2,deltaz+deltaz2);
		metaurShape.scale(1.25f, 1.25f, 1.25f);
		metaurShape.Draw(gl,this,lookSide);
		
		gl.glLoadIdentity();
	}
		
	public void drawEnemyBall(GL gl,AbstractEnemy enemyBall){
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);
		
		final GLU glu = new GLU();
		GLUquadric q = glu.gluNewQuadric();
			
		gl.glColor3d(enemyBall.color[0],enemyBall.color[1],enemyBall.color[2]);
		glu.gluQuadricNormals(q, GLU.GLU_SMOOTH);
		glu.gluQuadricTexture(q, false);
		glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);
		
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -20f + zoom);
		gl.glRotatef(45, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(deltaz + deltaz2, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(deltay + deltay2, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(deltax + deltax2, 1.0f, 0.0f, 0.0f);
		gl.glTranslatef(enemyBall.oldPlace[0], enemyBall.oldPlace[1], enemyBall.oldPlace[2]);
		gl.glRotatef(45, 0.0f, 1.0f, 0.0f);
		
		glu.gluSphere(q, 0.3, 50, 50);
		gl.glLoadIdentity();
		
		gl.glDisable(GL.GL_COLOR_MATERIAL);
	}


}
