package com.emicb.engine;

public class GameContainer implements Runnable {
	
	private Thread thread;
	private Window window;
	
	private boolean running = false;
	private final double UPDATE_CAP = 1.0/60.0;
	
	private int width = 1600, height = 900; //16:9 aspect ratio
	private float scale = 1f;
	private String title = "2DEngine v1.0";
	
	// CONSTRUCTOR
	public GameContainer() {
		
	}
	// START
	public void start() {
		window = new Window(this);
		
		thread = new Thread(this);
		thread.run();
	}
	// STOP
	public void stop() {
		
	}
	// RUN
	public void run() {
		running = true;
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while (running) {
			render = false;
			
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			// Handles game updates 
			while (unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;
				//TODO: update game
				
				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
					System.out.println("FPS: " + fps);
				}
			}
			
			if (render) {
				//TODO: render game
				window.update();
				frames++;
			}
			// Uses less CPU% 
			else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		dispose();
	}
	
	private void dispose() {
		
	}
	
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer();
		gc.start();
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
