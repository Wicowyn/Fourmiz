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
import org.newdawn.slick.geom.Shape;

import fourmiz.abillity.Attack;
import fourmiz.abillity.Healer;
import fourmiz.abillity.Level;
import fourmiz.abillity.Level.LifeState;
import fourmiz.abillity.Life;
import fourmiz.abillity.PopPrey;
import fourmiz.abillity.Prey;
import fourmiz.abillity.Queen;
import fourmiz.abillity.RealRender;
import fourmiz.abillity.ShapeMove;
import fourmiz.collision.Entity;

public class EntityFactory {
	private static Logger log=LogManager.getLogger(EntityFactory.class);
	
	public static Entity createEntity(EntityName name, Engine engine){
		Entity entity=new Entity(engine, Engine.getDefaultShape());
		RealRender render=new RealRender(entity);
		Level level=null;
		Life life=null;
		Healer healer=null;
		ShapeMove move=null;
		
		switch(name){
		case Anthill:
			life = new Life(entity);
			life.setMaxLife(1000);
			life.setCurrentLife(1000);
			life.setUptake(10);
			entity.addAbillity(life);
			
			level = new Level(entity);
			level.setState(LifeState.ANTHILL);
			entity.addAbillity(level);
			break;
		case Egg:
			level=new Level(entity);
			level.setState(LifeState.EGGS);
			entity.addAbillity(level);
			
			life=new Life(entity);
			life.setMaxLife(2);
			life.setCurrentLife(1);
			life.setUptake(5);
			entity.addAbillity(life);
			
			render.setAnimation(engine.getRessources().getAnimation("Egg"));
			entity.addAbillity(render);
			break;
		case Larva:
			life=new Life(entity);
			life.setMaxLife(300);
			life.setCurrentLife(300);
			life.setUptake(5);
			entity.addAbillity(life);
			
			level=new Level(entity);
			level.setState(LifeState.LARVA);
			entity.addAbillity(level);
			
			render.setAnimation(engine.getRessources().getAnimation("Larva"));
			entity.addAbillity(render);
			break;
		case Nymph:
			level=new Level(entity);
			level.setState(LifeState.NYMPH);
			entity.addAbillity(level);

			render.setAnimation(engine.getRessources().getAnimation("Nymph"));
			entity.addAbillity(render);
			break;
		case FourmizWorker:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbillity(level);
			
			life=new Life(entity);
			life.setMaxLife(150);
			life.setCurrentLife(150);
			life.setUptake(1);
			entity.addAbillity(life);
			
			healer = new Healer(entity);
			healer.setMaxFoodStock(100000);
			healer.setSpeed(5);
			entity.addAbillity(healer);

			render.setAnimation(engine.getRessources().getAnimation("FourmizWorker"));
			entity.addAbillity(render);
			break;
		case FourmizSoldier:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbillity(level);
			
			life=new Life(entity);
			life.setMaxLife(150);
			life.setCurrentLife(150);
			life.setUptake(1);
			entity.addAbillity(life);
			
			Attack fs = new Attack(entity);
			fs.setAttack(50);
			fs.setSpeed(1.5f);
			entity.addAbillity(fs);
			
			render.setAnimation(engine.getRessources().getAnimation("FourmizSoldier"));
			entity.addAbillity(render);
			break;
		case FourmizSex:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbillity(level);
			
			life=new Life(entity);
			life.setMaxLife(150);
			life.setCurrentLife(150);
			life.setUptake(1);
			entity.addAbillity(life);
			
			render.setAnimation(engine.getRessources().getAnimation("FourmizSex"));
			entity.addAbillity(render);
			break;
		case Queen:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbillity(level);
			
			Queen queen=new Queen(entity);
			entity.addAbillity(queen);
			
			move=new ShapeMove(entity);
			move.setSpeed(2);
			Shape area=new Rectangle(6*Engine.SIZE_CASE, 6*Engine.SIZE_CASE, 8*Engine.SIZE_CASE, 8*Engine.SIZE_CASE);
			move.setArea(area);
			entity.addAbillity(move);

			render.setAnimation(engine.getRessources().getAnimation("Queen"));
			entity.addAbillity(render);
			break;
		case Dead:
			level=new Level(entity);
			level.setState(LifeState.DEAD);
			entity.addAbillity(level);
			break;
		case Prey:
			Prey prey=new Prey(entity);
			prey.setLife(160);
			prey.setFood(356);
			entity.addAbillity(prey);
			
			move=new ShapeMove(entity);
			move.setSpeed(2);
			Shape area2=new Rectangle(6*Engine.SIZE_CASE, 6*Engine.SIZE_CASE, 10*Engine.SIZE_CASE, 10*Engine.SIZE_CASE);
			move.setArea(area2);
			entity.addAbillity(move);

			render.setAnimation(engine.getRessources().getAnimation("Prey"));
			entity.addAbillity(render);
			break;
		case PopPrey:
			entity=new Entity(engine, new Rectangle(0, 0, 15*Engine.SIZE_CASE, 15*Engine.SIZE_CASE));
			PopPrey popPrey=new PopPrey(entity);
			popPrey.setMaxPrey(10);
			popPrey.setPopDelay(5000);
			entity.addAbillity(popPrey);
			break;
		default:
			EntityFactory.log.fatal("Value : "+name+" don't handle");
			System.exit(1);
			break;
		}
		
		return entity;
	}
}
