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


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.JDOMException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tools.ResourceManager;
import fourmiz.abillity.BasicRender;
import fourmiz.abillity.RealRender;
import fourmiz.abillity.Render;
import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.engine.EngineListener;
import fourmiz.engine.EntityListener;



public class GamingState extends BasicGameState implements SelectGame, EngineListener, EntityListener {
    private static Logger log=LogManager.getLogger(GamingState.class);
	private static String renderSuffix="-render.xml";
	private static String mapSuffix="-map.xml";
	private static String resourcePath="ressources/";
	private String currentGame;
	private List<Render> renders=new ArrayList<Render>();
	private Engine engine=new Engine();
	private ResourceManager ressources=new ResourceManager();

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
			
			engine.addListener(this);
	}
	
	public static List<String> getPossibleGame(){
		List<String> listMap=new ArrayList<String>();
		List<String> listRender=new ArrayList<String>();
		
		File[] files=(new File(GamingState.resourcePath)).listFiles();
		
		for(File file : files){
			String name=file.getName();
			if(name.endsWith(GamingState.renderSuffix)){
				listRender.add(name.substring(0, name.indexOf(GamingState.renderSuffix)));
				log.debug("render conf found :"+name);
			}
			if(name.endsWith(GamingState.mapSuffix)){
				listMap.add(name.substring(0, name.indexOf(GamingState.mapSuffix)));
				log.debug("map conf found :"+name);
			}
		}
		
		listMap.retainAll(listRender);
		
		return listMap;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game){
		try {
			this.ressources.load(GamingState.resourcePath+this.currentGame+GamingState.renderSuffix);
			
			this.engine.unLoad();
			this.engine.loadLevel(GamingState.resourcePath+this.currentGame+GamingState.mapSuffix);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		

	}
	
	public void setGame(String game){
		currentGame=game;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.setBackground(Color.darkGray);
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
		RealRender render=new RealRender(entity);
		render.setAnimation(ressources.getAnimation("FourmizWorker"));
		
		entity.addAbillity(render);
		renders.add(render);
	}

	@Override
	public void entityRemoved(Entity entity) {
		for(Abillity abillity : entity.getAllAbillity())
			if(abillity instanceof BasicRender) renders.remove((BasicRender) abillity);
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
