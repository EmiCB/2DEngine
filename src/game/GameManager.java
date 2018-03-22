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
		
		image2 = new Image("/woodboi.png");
		image2.setLightBlock(Light.FULL);
		
		light = new Light(100, 0xff00ffff);
	}
	
	public void reset() {
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawImage(image, 0, 0);
		r.drawImage(image2, 100, 100);
		
		r.drawLight(light, gc.getInput().getMouseX(), gc.getInput().getMouseY());
	}
	
	//MAIN LOOP
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
