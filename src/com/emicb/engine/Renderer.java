package com.emicb.engine;

import java.awt.image.DataBufferInt;

public class Renderer {
	
	private int pW, pH;
	private int[] p;
	
	public Renderer(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}
	
	public void clear() {
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
			//p[i] += i;				//cool pixel data stuff lol
			//p[i] = 0xff000000;		//sets screen to black
		}
	}
}
