/*//////////////////////////////////////////////////////////////////////
	This file is part of Bomberton, an Bomberman-like.
	Copyright (C) 2012-2013  Nicolas Barranger <wicowyn@gmail.com>

    Bomberton is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Bomberton is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Bomberton.  If not, see <http://www.gnu.org/licenses/>.
*///////////////////////////////////////////////////////////////////////

package display.state;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import display.component.LayoutMenu;
import display.component.Text;

public class MenuState extends BasicGameState implements LayoutMenuActionListener{
	private StateBasedGame baseGame;
	private Map<AbstractComponent, Integer> mapMenuPage=new HashMap<AbstractComponent, Integer>();
	private Input input;
	private Image menu;
	private LayoutMenu menu2;
	private Animation courir;
	private SpriteSheet perso;

	
	
	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		
		this.baseGame=arg1;
		
		input = container.getInput();
			
		menu = new Image("image/wallpaper.png");
		
		menu2=new LayoutMenu(container, new Image("ressources/fleche.png"));
		menu2.setLocation(115, 630);
			
		AngelCodeFont font=new AngelCodeFont("ressources/Latin.fnt", new Image("ressources/Latin.tga"));
		
		Text text1=new Text(container, font);
		text1.setText("Play");
		text1.setColor(Color.white);
		menu2.addElement(text1);
		this.mapMenuPage.put(text1, PageName.SelectGame);
		
		Text text2=new Text(container, font);
		text2.setText("Quit");
		text2.setColor(Color.red);
		menu2.addElement(text2);
		this.mapMenuPage.put(text2, PageName.Quit);
		
		perso = new SpriteSheet("image/KarabounChicken.gif", 80,80); //80 = taille de l'image
		
		courir = new Animation(perso, 0,0,0,3,true, 100, false);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		this.menu2.addListener(this);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		this.menu2.removeListener(this);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
	
		menu.draw();
		g.drawString("Menu principale",500,280);
		menu2.render(container, g);
		courir.draw(200+200,200);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		if(this.input.isKeyDown(Input.KEY_Z)) arg1.enterState(PageName.Gaming);
	
		if(this.input.isKeyDown(Input.KEY_ESCAPE)){
			container.exit();
		}
		if(this.input.isKeyDown(Input.KEY_D)){
			courir.setCurrentFrame(3);
		}
		if(this.input.isKeyDown(Input.KEY_Q)){
			courir.setCurrentFrame(2);
		}
		
	}

	@Override
	public int getID() {
		return PageName.Menu;
	}
	
	@Override
	public void fieldSelected(int index) {
		switch (mapMenuPage.get(menu2.getComponent(index))) {
		case PageName.SelectGame:
			baseGame.enterState(PageName.SelectGame);
			break;
		case PageName.Quit:
			baseGame.getContainer().exit();
			break;
		default:
			break;
		}
	}

	@Override
	public void fieldOverfly(int index) {
		
	}

}

