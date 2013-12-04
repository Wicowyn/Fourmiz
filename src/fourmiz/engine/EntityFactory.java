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

import fourmiz.abillity.Healer;
import fourmiz.abillity.Level;
import fourmiz.abillity.Queen;
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
				x*EntityFactory.width/Engine.SIZE_CASE,
				y*EntityFactory.heigt/Engine.SIZE_CASE,
				width*EntityFactory.width/Engine.SIZE_CASE,
				height*EntityFactory.heigt/Engine.SIZE_CASE);
	}
	
	public static Entity createEntity(EntityName name, Engine engine){
		Entity entity=new Entity(engine, get(0, 0, Engine.SIZE_CASE, Engine.SIZE_CASE));
		Level level=null;
		Life life=null;
		Healer healer=null;
		
		switch(name){
		case Egg:
			level=new Level(entity);
			level.setState(LifeState.EGGS);
			entity.addAbillity(level);
			break;
		case Larva:
			life=new Life(entity);
			life.setMaxLife(300);
			life.setUptake(5);
			entity.addAbillity(life);
			
			level=new Level(entity);
			level.setState(LifeState.LARVA);
			entity.addAbillity(level);
			break;
		case Nymph:
			level=new Level(entity);
			level.setState(LifeState.NYMPH);
			entity.addAbillity(level);
			break;
		case FourmizWorker:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbillity(level);
			
			life=new Life(entity);
			life.setMaxLife(150);
			life.setUptake(1);
			entity.addAbillity(life);
			
			healer = new Healer(entity);
			entity.addAbillity(healer);
			break;
		case FourmizSoldier:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbillity(level);
			
			life=new Life(entity);
			life.setMaxLife(150);
			life.setUptake(1);
			entity.addAbillity(life);
			break;
		case FourmizSex:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbillity(level);
			
			life=new Life(entity);
			life.setMaxLife(150);
			life.setUptake(1);
			entity.addAbillity(life);
			break;
		case Queen:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbillity(level);
			
			Queen queen=new Queen(entity);
			entity.addAbillity(queen);
			break;
		default:
			EntityFactory.log.fatal("Value : "+name+" don't handle");
			System.exit(1);
			break;
		}
		
		return entity;
	}
}
