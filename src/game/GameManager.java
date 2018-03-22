package game;

import com.emicb.engine.AbstractGame;
import com.emicb.engine.GameContainer;
import com.emicb.engine.Renderer;

public class GameManager extends AbstractGame {
	public GameManager() {
		
	}
	
	public void reset() {
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		
	}
	
	//MAIN LOOP
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
