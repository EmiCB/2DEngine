package game;

import com.emicb.engine.AbstractGame;
import com.emicb.engine.GameContainer;
import com.emicb.engine.Renderer;
import com.emicb.engine.audio.SoundClip;
import com.emicb.engine.gfx.Image;
import com.emicb.engine.gfx.ImageTile;

public class GameManager extends AbstractGame {
	
	private Image image;
	
	public GameManager() {
		image = new Image("/bgtest.png");
	}
	
	public void reset() {
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawImage(image, 0, 0);
	}
	
	//MAIN LOOP
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
