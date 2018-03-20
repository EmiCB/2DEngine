package com.emicb.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.emicb.engine.gfx.Font;
import com.emicb.engine.gfx.Image;
import com.emicb.engine.gfx.ImageRequest;
import com.emicb.engine.gfx.ImageTile;

public class Renderer {
	private Font font = Font.STANDARD;		//make fonts easily changed
	
	private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();
	
	private int pW, pH;
	private int[] p;						//pixels
	private int[] zBuffer;
	private int zDepth = 0;
	private boolean processing = false;
	
	public Renderer(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zBuffer = new int[p.length];
	}
	
	public void clear() {
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
			zBuffer[i] = 0;
			
			//p[i] += i;				//cool pixel data stuff lol
			//p[i] = 0xff000000;		//sets screen to black
		}
	}
	
	public void process() {
		processing = true;
		
		//sorting
		Collections.sort(imageRequest, new Comparator<ImageRequest>() {
			@Override
			public int compare(ImageRequest i0, ImageRequest i1) {
				if(i0.zDepth < i1.zDepth) return -1;
				if(i0.zDepth > i1.zDepth) return 1;
				return 0;
			}
		});
		
		for(int i = 0; i < imageRequest.size(); i++ ) {
			ImageRequest ir = imageRequest.get(i);
			setzDepth(ir.zDepth);
			drawImage(ir.image, ir.offX, ir.offY);
		}
		imageRequest.clear();
		processing = false;
	}
	
	public void setPixel(int x, int y, int value) {
		int alpha = ((value >> 24) & 0xff);			//alpha values go up to 255
		
		//tells not to draw if out of bounds or hat one ugly pink color that makes thing transparent lol
		if ((x < 0 || x > pW || y < 0 || y >= pH) || alpha == 0 || value == 0xffff00ff) {
			return;
		}
		
		int index = x + y * pW;
		
		if( zBuffer[index] > zDepth) {
			return;
		}
		zBuffer[index] = zDepth;
		if(alpha == 255) {
			p[index] = value;
		}
		else {
			//Alpha blending
			int pixelColor = p[index];
			
			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha/255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha/255f));
			int newBlue = (pixelColor & 0xff0 - (int)(((pixelColor & 0xff) - (value & 0xff)) * (alpha/255f)));
			
			p[index] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
	}
	
	public void drawImage(Image image, int offX, int offY) {
		if(image.isAlpha() && !processing) {
			imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
			return;
		}
		
		//Stops rendering
		if (offX < -image.getW()) return;
		if (offY < -image.getH()) return;
		if (offX >= pW) return;
		if (offY >= pH) return;
		
		//Declare vars
		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();
		
		//Clips image
		if (offX < 0) newX -= offX;
		if (offY < 0) newY -= offY;
		if (newWidth + offX >= pW) newWidth -= newWidth + offX - pW;
		if (newHeight + offY >= pH) newHeight -= newHeight + offY - pH;
		
		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
			}
 		}
	}
	
	public void drawString(String text, int offX, int offY, int color) {
		//text = text.toUpperCase();						//uncomment if font only has upper case
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i);		// -32 will make space = 0
			
			for(int y = 0; y < font.getFontImage().getH(); y++) {
				for(int x = 0; x < font.getWidths()[unicode]; x++) {
					if(font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getW()] == 0xffffffff) {
						setPixel(x + offX + offset, y + offY, color);
					}
				}
			}
			offset += font.getWidths()[unicode];
		}
	}
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
		if(image.isAlpha() && !processing) {
			imageRequest.add(new ImageRequest(image.getTileImage(tileX, tileY), zDepth, offX, offY));
			return;
		}
		
		//Stops rendering
		if (offX < -image.getTileW()) return;
		if (offY < -image.getTileH()) return;
		if (offX >= pW) return;
		if (offY >= pH) return;
		
		//Declare vars
		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileW();
		int newHeight = image.getTileH();
		
		//Clips image
		if (offX < 0) newX -= offX;
		if (offY < 0) newY -= offY;
		if (newWidth + offX >= pW) newWidth -= newWidth + offX - pW;
		if (newHeight + offY >= pH) newHeight -= newHeight + offY - pH;
		
		for (int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
			}
 		}
	}
	
	public void drawRect(int offX, int offY, int width, int height, int color) {
		for(int y = 0; y <= height; y++) {
			setPixel(offX, y + offY, color);
			setPixel(offX + width, y + offY, color);
		}
		for(int x = 0; x <= width; x++) {
			setPixel(x + offX, offY, color);
			setPixel(x + offX, offY + height, color);
		}
	}
	
	public void drawRectFill(int offX, int offY, int width, int height, int color) {
		//Stops rendering
		if (offX < -width) return;
		if (offY < -height) return;
		if (offX >= pW) return;
		if (offY >= pH) return;
		
		//Declare vars
		int newX = 0;
		int newY = 0;
		int newWidth = width;
		int newHeight = height;
		
		//Clips image
		if (offX < 0) newX -= offX;
		if (offY < 0) newY -= offY;
		if (newWidth + offX >= pW) newWidth -= newWidth + offX - pW;
		if (newHeight + offY >= pH) newHeight -= newHeight + offY - pH;
		
		for(int y = newY; y < newHeight; y++) {
			for(int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, color);
			}
		}
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}
}
