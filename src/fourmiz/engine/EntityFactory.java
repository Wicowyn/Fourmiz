/*//////////////////////////////////////////////////////////////////////
	This file is part of Bomberton, an Bomberman-like.
	Copyright (C) 2013  Nicolas Barranger <wicowyn@gmail.com>

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

package fourmiz.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Rectangle;

import fourmiz.abillity.Level;
import fourmiz.abillity.Level.LifeState;
import fourmiz.abillity.Life;
import fourmiz.collision.Entity;

public class EntityFactory {
	private static Logger log=LogManager.getLogger(EntityFactory.class);
	private static float heigt;
	private static float width;
	
	public static void setGlobalHeight(float height){
		EntityFactory.heigt=height;
	}
	
	public static void setGobalWidth(float width){
		EntityFactory.width=width;
	}
	
	private static Rectangle get(float x, float y, float width, float height){
		return new Rectangle(
				x*EntityFactory.width/1000,
				y*EntityFactory.heigt/1000,
				width*EntityFactory.width/1000,
				height*EntityFactory.heigt/1000);
	}
	
	public static Entity createEntity(EntityName name, Engine engine){
		Entity entity=new Entity(engine, get(0, 0, 1000, 1000));
		
		switch(name){
		case Egg:
			break;
		case Larva:
			Life life=new Life(entity);
			//TODO to parameter setLife...
			entity.addAbillity(life);
			
			Level level=new Level(entity);
			level.setState(LifeState.LARVA);
			break;
		case Nymph:
			break;
		case FourmizWorker:
			break;
		case FourmizSoldier:
			break;
		case FourmizSex:
			break;
		default:
			EntityFactory.log.fatal("Value : "+name+" don't handle");
			System.exit(1);
			break;
		}
		
		return entity;
	}
}
