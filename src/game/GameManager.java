package game;

import com.emicb.engine.AbstractGame;
import com.emicb.engine.GameContainer;
import com.emicb.engine.Renderer;
import com.emicb.engine.audio.SoundClip;
import com.emicb.engine.gfx.Image;
import com.emicb.engine.gfx.ImageTile;

public class GameManager extends AbstractGame {
	
	private Image image;
	private Image image2;
	
	public GameManager() {
		image = new Image("/test.png");
		image2 = new Image("/test2.png");
	}
	
	public void reset() {
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
		for(int x = 0; x < image.getW(); x++) {
			for(int y = 0; y < image.getH(); y++) {
				r.setLightMap(x, y, image.getP()[x + y * image.getW()]);
			}
		}
		
		r.drawImage(image2, gc.getInput().getMouseX() - 32, gc.getInput().getMouseY() - 32);
	}
	
	//MAIN LOOP
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
