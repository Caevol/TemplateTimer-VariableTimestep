
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

//Variable Timestep with experimental Rectangle!




public class main implements Runnable{


	long startTime, goalInterval, interval, currentTime, previousTime, lastfpsTime;
	int counter, fps;
	Boolean running;
	double gameSpeed;
	double rectSpeed = 3;
	float rectX = 5;
	JFrame userFrame = new JFrame();

	//run the program with a goal of 60 fps
	public static void main(String[] args) {
		new Thread(new main(60)).start();
	}

	public main(int frames){

		setupPanels();
		setupTimer(frames);

	}
	
	//this code sets up all the jframes and panelling before the code starts
	public void setupPanels(){
		userFrame.setSize(500, 500);
		userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userFrame.add(world);
		world.setSize(userFrame.getSize());
		userFrame.setVisible(true);
	}
	
	//the timer is prepared here.
	public void setupTimer(int frames){

		startTime = System.currentTimeMillis();
		previousTime = System.currentTimeMillis();
		goalInterval = 1000000000 / frames;
		counter = 0;
		lastfpsTime = 0;
		running = true;
	}
	
	//this label draws the background and the rectangle
	JLabel world = new JLabel(){
		protected void paintComponent(Graphics g){
			g.setColor(Color.black);
			g.fillRect(0, 0, world.getWidth(), world.getHeight());
			g.setColor(Color.blue);
			g.drawRect((int) rectX, 5, 10, 10);
		};
	};

	//Basic Timer code, once running
	public void run() {

		while(running){



			currentTime = System.nanoTime();
			interval = currentTime - previousTime;


			previousTime = currentTime;


			gameSpeed = interval / ((double)goalInterval);
			gameLogic();
			world.repaint();


			lastfpsTime += interval;
			fps ++;

			if(lastfpsTime > 1000000000){

				//debugging line if you want to see the FPS
				//System.out.println("FPS = " +fps + " Game speed = " + gameSpeed);
				lastfpsTime = 0;
				fps = 0;
			}
			try {
				Thread.sleep( (previousTime-System.nanoTime() + goalInterval)/1000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//all basic game code will go in here, in this case, move a rectangle along a straight line
	public void gameLogic(){

		if(rectX < 250){
			rectX += rectSpeed * gameSpeed;
		}
		else if(rectX >= 250){
			rectX = 0;
		}

	}
}
