/*//////////////////////////////////////////////////////////////////////
	This file is part of Fourmiz, an simulation of ant live.
	Copyright (C) 2013  Nicolas Barranger <wicowyn@gmail.com>
						Jean-Baptiste Le Henaff <jb.le.henaff@gmail.com>
						Antoine Fouque <antoine.fqe@gmail.com>
						Julien Camenen <jcamenen@gmail.Com>
    Fourmiz is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Fourmiz is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Fourmiz.  If not, see <http://www.gnu.org/licenses/>.
*///////////////////////////////////////////////////////////////////////

package display.state;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fourmiz.abillity.Render;
import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.engine.EngineListener;
import fourmiz.engine.EntityListener;



public class GamingState extends BasicGameState implements SelectGame, EngineListener, EntityListener {
	private String currentGame;
	private List<Render> renders=new ArrayList<Render>();
	private Engine engine=new Engine();
	private Image wallpaper;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
			wallpaper = new Image("image/ground.png");
			engine.addListener(this);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game){
		try {
			this.engine.unLoad();
			this.engine.loadLevel(currentGame);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Accesseur au jeu en cour
	 * @param game
	 */
	public void setGame(String game){
		currentGame=game;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		if(this.currentGame.equals("Quit")) System.exit(0);
		arg2.setBackground(Color.darkGray);
		wallpaper.draw();
		for(Render render : renders) render.render(arg0, arg1, arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		this.engine.update(arg2);
		
	}

	@Override
	public int getID() {
		return PageName.Gaming;
	}

	@Override
	public void selectGame(String game) {
		this.currentGame=game;
		
	}

	@Override
	public void entityAdded(Entity entity) {
		for(Abillity abillity : entity.getAllAbillity()){
			if(abillity instanceof Render) renders.add((Render) abillity); 
		}
	}

	@Override
	public void entityRemoved(Entity entity) {
		for(Abillity abillity : entity.getAllAbillity())
			if(abillity instanceof Render) renders.remove((Render) abillity);
	}

	@Override
	public void abillityAdded(Abillity abillity) {
		if(abillity instanceof Render) renders.add((Render) abillity);
	}

	@Override
	public void abillityRemoved(Abillity abillity) {
		if(abillity instanceof Render) renders.remove((Render) abillity);
	}

	@Override
	public void positionUpdated() {
		
	}

}
