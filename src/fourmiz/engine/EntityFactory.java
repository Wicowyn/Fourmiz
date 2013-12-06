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
	
	/**
	 * Fonction permettant la cr�ation d'une entit�
	 * @param name
	 * @param engine
	 * @return
	 */
	public static Entity createEntity(EntityName name, Engine engine){
		Entity entity=new Entity(engine, Engine.getDefaultShape());
		Shape anthil=new Rectangle(3*Engine.SIZE_CASE, 3*Engine.SIZE_CASE, 5*Engine.SIZE_CASE, 5*Engine.SIZE_CASE);
		Shape preyPop=new Rectangle(20*Engine.SIZE_CASE, 12*Engine.SIZE_CASE, 12*Engine.SIZE_CASE, 12*Engine.SIZE_CASE);
	    RealRender render=new RealRender(entity);
		Level level=null;
		Life life=null;
		Healer healer=null;
		ShapeMove move=null;
		
		switch(name){
		case Anthill:
			entity=new Entity(engine,  anthil);
			life = new Life(entity);
			life.setMaxLife(1000);
			life.setCurrentLife(1000);
			life.setUptake(10);
			entity.addAbility(life);
			
			level = new Level(entity);
			level.setState(LifeState.ANTHILL);
			entity.addAbility(level);
			break;
		case Egg:
			level=new Level(entity);
			level.setState(LifeState.EGGS);
			entity.addAbility(level);
			
			render.setAnimation(engine.getRessources().getAnimation("Egg"));
			entity.addAbility(render);
			break;
		case Larva:
			life=new Life(entity);
			life.setMaxLife(300);
			life.setCurrentLife(300);
			life.setUptake(5);
			entity.addAbility(life);
			
			level=new Level(entity);
			level.setState(LifeState.LARVA);
			entity.addAbility(level);
			
			render.setAnimation(engine.getRessources().getAnimation("Larva"));
			entity.addAbility(render);
			break;
		case Nymph:
			level=new Level(entity);
			level.setState(LifeState.NYMPH);
			entity.addAbility(level);

			render.setAnimation(engine.getRessources().getAnimation("Nymph"));
			entity.addAbility(render);
			break;
		case FourmizWorker:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbility(level);
			
			life=new Life(entity);
			life.setMaxLife(150);
			life.setCurrentLife(150);
			life.setUptake(1);
			entity.addAbility(life);
			
			healer = new Healer(entity);
			healer.setMaxFoodStock(500);
			healer.setFoodRadius(10*Engine.SIZE_CASE);
			healer.setHealRadius(10*Engine.SIZE_CASE);
			healer.addStaticHealArea(anthil);
			healer.addStaticHealArea(preyPop);
			healer.addStaticSearchArea(preyPop);
			healer.setSpeed(5);
			entity.addAbility(healer);

			render.setAnimation(engine.getRessources().getAnimation("FourmizWorker"));
			entity.addAbility(render);
			
			
			Attack fsWorker = new Attack(entity);
			fsWorker.setAttack(50);
			fsWorker.setSpeed(1.5f);
			fsWorker.addStaticArea(preyPop);
			entity.addAbility(fsWorker);
			
			break;
		case FourmizSoldier:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbility(level);
			
			life=new Life(entity);
			life.setMaxLife(150);
			life.setCurrentLife(150);
			life.setUptake(1);
			entity.addAbility(life);
			
			move=new ShapeMove(entity);
			move.setSpeed(2);
			move.setArea(new Rectangle(3*Engine.SIZE_CASE, 3*Engine.SIZE_CASE, 6*Engine.SIZE_CASE, 6*Engine.SIZE_CASE));
			entity.addAbility(move);
			
			Attack fsSoldier = new Attack(entity);
			fsSoldier.setAttack(50);
			fsSoldier.setSpeed(1.5f);
			//fsSoldier.addStaticArea(anthil);
			entity.addAbility(fsSoldier);
			
			render.setAnimation(engine.getRessources().getAnimation("FourmizSoldier"));
			entity.addAbility(render);
			break;
		case FourmizSex:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbility(level);
			
			life=new Life(entity);
			life.setMaxLife(150);
			life.setCurrentLife(150);
			life.setUptake(1);
			entity.addAbility(life);
			
			move=new ShapeMove(entity);
			move.setSpeed(2);
			move.setArea(anthil);
			entity.addAbility(move);
			
			render.setAnimation(engine.getRessources().getAnimation("FourmizSex"));
			entity.addAbility(render);
			break;
		case Queen:
			level=new Level(entity);
			level.setState(LifeState.FOURMIZ);
			entity.addAbility(level);
			
			Queen queen=new Queen(entity);
			queen.setInterval(1000);
			queen.setIntervalOffset(500);
			queen.setNbMaxEgg(100);
			entity.addAbility(queen);
			
			move=new ShapeMove(entity);
			move.setSpeed(2);
			move.setArea(anthil);
			entity.addAbility(move);

			render.setAnimation(engine.getRessources().getAnimation("Queen"));
			entity.addAbility(render);
			break;
		case Dead:
			level=new Level(entity);
			level.setState(LifeState.DEAD);
			entity.addAbility(level);
			break;
		case Prey:
			Prey prey=new Prey(entity);
			prey.setLife(160);
			prey.setFood(356);
			entity.addAbility(prey);
			
			move=new ShapeMove(entity);
			move.setSpeed(2);
			Shape area2=new Rectangle(6*Engine.SIZE_CASE, 6*Engine.SIZE_CASE, 10*Engine.SIZE_CASE, 10*Engine.SIZE_CASE);
			move.setArea(area2);
			entity.addAbility(move);

			render.setAnimation(engine.getRessources().getAnimation("Prey"));
			entity.addAbility(render);
			break;
		case PopPrey:

			PopPrey popPrey=new PopPrey(entity);

			popPrey.setArea(preyPop);
			popPrey.setMaxPrey(50);
			popPrey.setPopDelay(1000);
			entity.addAbility(popPrey);
			break;
		default:
			EntityFactory.log.fatal("Value : "+name+" don't handle");
			System.exit(1);
			break;
		}
		
		return entity;
	}
}
