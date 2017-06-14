package qBert;

import java.awt.event.MouseEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;


public class Main{
	final public static int UP = 16777217;
    final public static int DOWN = 16777218;
    final public static int LEFT = 16777219;
    final public static int RIGHT = 16777220;
    static final WavePlayer player = new WavePlayer();
    final public static String DIR = "QBert\\";
    static Display display;
    
    static boolean bump = false;
    static EnemyMover mover;
    static int lives = 3;
    static int level = 1;
    static int scores = 0;   
    static int counter = 0;  
    static boolean button = false;
    
	public static void main(String[] args){	
		display = new Display();	
			
		Shell shell2 = new Shell (display);
		Rectangle size = display.getBounds();
		shell2.setLocation(0, 0);
        shell2.setSize(size.width, size.height);
		shell2.setBackground(new Color(display,139,69,19));
		int h = shell2.getBounds().height;
		int w = shell2.getBounds().width;
		
		Font times32BI = new Font(null, "Times New Roman",32, SWT.BOLD | SWT.ITALIC);
		Font times16BI = new Font(null, "Times New Roman",16, SWT.BOLD | SWT.ITALIC);
		
		Label lbl = new Label(shell2, SWT.CENTER);
		lbl.setFont(times32BI);
		lbl.setBackground(new Color(display,139,69,19));
		lbl.setForeground(display.getSystemColor(SWT.COLOR_RED));
		lbl.setText("Q*Bert");
		lbl.setBounds(6*w/13, h/45, 6*w/13, h/15);
		lbl.pack();
		
		Label lbl2 = new Label(shell2, SWT.CENTER);
		lbl2.setFont(times16BI);
		lbl2.setBackground(new Color(display,139,69,19));
		lbl2.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		lbl2.setText("Instructions:\n");
		lbl2.setBounds(w/10,2*h/12,(8*w)/10,(4*h)/6);
		lbl2.pack();
		
		Label lbl3 = new Label(shell2, SWT.CENTER);
		lbl3.setFont(times16BI);
		lbl3.setBackground(new Color(display,139,69,19));
		lbl3.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
		lbl3.setText("Rotations:\n" +"\tw,s - X axis\n" +
				"\ta,d - Y axis\n" + "\tq,e - Z axis\n" + "\tz,x - Zoom\n"
				+ "\t+\n \tMouse");
		lbl3.setBounds(4*w/10,2*h/5,(8*w)/10,(4*h)/6);
		lbl3.pack();
		
		Label lbl4 = new Label(shell2, SWT.CENTER);
		lbl4.setFont(times16BI);
		lbl4.setBackground(new Color(display,139,69,19));
		lbl4.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
		lbl4.setText("Move Q*Bert around using the arrows. " +
				"\nThe purpose is to hop around the cubes," +
				" changing the color of every cube.");
		lbl4.setBounds(2*w/10,h/4,(8*w)/10,(4*h)/6);
		lbl4.pack();
		
		Button startButton = new Button (shell2, SWT.PUSH);
		startButton.setBounds(6*w/14,10*h/13,w/7,h/13);
		startButton.setText ("START");
		startButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				button = true;
			}
		});
		
		shell2.setDefaultButton (startButton);
		shell2.open ();
		
		while (!shell2.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
			
			if(button){
				if(button){
					Main main = new Main();	
					main.runMain(display);
					bump = false;
				    lives = 3;
				    level = 1;
				    scores = 0;   
				    counter = 0;  
				    button = false;
				}
			}
		}
		
		display.dispose();
	}
	
	void init (Display display){
	}
	
	void runMain(Display display){
		final Shell shell = new Shell(display);
		shell.setText("Q*bert 3D - OpenGL Exercise");
		
		player.add("one", DIR + "jump.wav");
		player.add("two", DIR + "crash.wav");
		player.add("three", DIR + "newLife.wav");
		player.add("four", DIR + "newLevel.wav");
		player.add("five", DIR + "win.wav");
		player.add("six", DIR + "gameOver.wav");
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
	
		shell.setLayout(gridLayout);
		
		Rectangle size = display.getBounds();
		shell.setLocation(0, 0);
        shell.setSize(size.width, size.height);

		
		final Render render = new Render(display, shell);
		
		mover = new EnemyMover(render);
		
		Thread t = new Thread(mover);
		t.start();
		
		//key listener
		render.composite.setFocus();
        render.composite.addKeyListener(new KeyAdapter() {  	
        	public void keyPressed(KeyEvent k) {       		
        		int key = k.keyCode;
        		if(!Main.win(render) && Main.lives > 0 && !Main.bump)
        			updateAndDisplay(key,render);
            }
        });
        
        //mouse listener
        render.canvas.addMouseListener(
        		new java.awt.event.MouseAdapter(){
	            	@Override
	                public void mousePressed(java.awt.event.MouseEvent e) {
	                	render.oldx = e.getX();
	                    render.oldy=  e.getY();
	                }
	            });
        
        render.canvas.addMouseMotionListener(
        		new java.awt.event.MouseMotionAdapter(){
        			@Override
        			public void mouseDragged(MouseEvent e) {
                        float dy = (float)(render.oldy - e.getY())/5.0f;
                        float dx = (float)(render.oldx - e.getX())/5.0f; 
                        render.oldy = e.getY();
                        render.oldx = e.getX();
                        if (dy <50)
                        	render.deltax2 -= dy;
                        if (dx < 50) 
                        	render.deltay2 -= dx;
                        render.canvas.display();
                    }
                }
        );
        
		// show it all
		shell.open();
		
		// the event loop.	
		while(!shell.isDisposed ()) {
			if(!display.readAndDispatch()) display.sleep();
			
			if(Main.bump || lives == 0){
				if(lives > 0){
					Main.bump = false;
					lives--;
					render.qBert.init();
				}else{	
					render.canvas.display();
					mover.stopRunning();	
					if(counter == 0){
						counter = 1;
						player.play("six");
					}
					try {
						Thread.sleep(2500);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					shell.dispose();
				}
			}
			
			if(Main.win(render)){
				mover.stopRunning();
				if(counter == 0){
					counter = 1;
					player.play("five");
				}	
				try {
					Thread.sleep(2500);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				shell.dispose();
			}
		}
		
		mover.stopRunning();
		shell.dispose();
		
	}	
	
	static public boolean win(Render render){
		//There are 146 cubes to mark
		if(render.pyramid.NumOfUnMarked <= 0)
			return true;
		else
			return false;
	}
	
	public void updateAndDisplay(int key, Render render){

		if (key == 'w')
        	render.deltax2 -= 2;
        if (key == 's')
        	render.deltax2 += 2;   
        if (key == 'a')
        	render.deltay2 -= 2;
        if (key == 'd')
        	render.deltay2 += 2;
        if (key == 'q')
        	render.deltaz2 += 2;
        if (key == 'e')
        	render.deltaz2 -= 2;
        if (key == 'z')
        	render.zoom += 2;
        if (key == 'x')
        	render.zoom -= 2;
    
       
        if(key == Main.UP || key == Main.DOWN || key == Main.RIGHT || key == Main.LEFT){  
        	player.play("one");
        	render.deltax2=0;
        	render.deltay2=0;
        	render.deltaz2=0;
        	render.qBert.jump(render.pyramid,key,render);      
        	render.pyramid.rotatePyramid(render.qBert, key, render);
        }
        render.canvas.display();
	}
}
