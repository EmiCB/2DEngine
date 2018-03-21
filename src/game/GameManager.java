package game;

import com.emicb.engine.AbstractGame;
import com.emicb.engine.GameContainer;
import com.emicb.engine.Renderer;
import com.emicb.engine.audio.SoundClip;
import com.emicb.engine.gfx.Image;
import com.emicb.engine.gfx.ImageTile;
import com.emicb.engine.gfx.Light;

public class GameManager extends AbstractGame {
	
	private Image image;
	private Image image2;
	
	private Light light;
	
	public GameManager() {
		image = new Image("/bgtest.png");
		image2 = new Image("/test2.png");
		
		light = new Light(50, 0xff00ffff);
	}
	
	public void reset() {
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
		for(int x = 0; x < light.getDiameter(); x++) {
			for(int y = 0; y < light.getDiameter(); y++) {
				r.setLightMap(x, y, light.getLightMap()[x + y * light.getDiameter()]);
			}
		}
		
		r.drawImage(image, 0, 0);
		r.drawImage(image2, gc.getInput().getMouseX() - 32, gc.getInput().getMouseY() - 32);
	}
	
	//MAIN LOOP
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
