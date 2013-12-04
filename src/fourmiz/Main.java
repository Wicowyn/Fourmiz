package fourmiz;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import display.TheGame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AppGameContainer app;
		try {
			app = new AppGameContainer(new TheGame("Fourmizz"));
			app.setDisplayMode(1024, 768, false);
			app.setTargetFrameRate(30);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
