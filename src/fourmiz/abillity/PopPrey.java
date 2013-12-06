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

package fourmiz.abillity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.engine.EngineListener;

/**
 * Classe PopPrey
 * Permet la g�n�ration des proies dans un certain p�rim�tre
 */
public class PopPrey extends Abillity implements EngineListener{
	private List<Entity> list=new ArrayList<Entity>();
	private Shape area;
	private int maxPrey=5;
	private int popDelay=5;
	private int time=0;
	

	public PopPrey(Entity owner) {
		super(owner);
	}
	
	@Override
	public void setOwner(Entity owner){
		if(getOwner()!=null) getOwner().getEngine().removeListener(this);
		
		super.setOwner(owner);
		
		getOwner().getEngine().addListener(this);
	}

	
	
	/**
	 * G�n�ration des pops des proies toutes les N secondes
	 */
	@Override
	public void update(int delta) {
		time+=delta;
		
		if(time>=popDelay){
			popPrey();
			time-=popDelay;
		}
	}

	/**
	 * Pop & initialisation de la proie dans la zone de proie
	 */
	public Shape getArea() {
		return area;
	}

	public void setArea(Shape area) {
		this.area = area;
	}

	private void popPrey(){
		if(list.size()>=maxPrey) return;
		
		Entity prey=new Entity(getOwner().getEngine(), Engine.getDefaultShape());
		Prey preyA=new Prey(prey);
		preyA.setLife(160);
		preyA.setFood(100);
		prey.addAbillity(preyA);
		
		ShapeMove move=new ShapeMove(prey);
		move.setSpeed(2);
		move.setArea(getArea());
		prey.addAbillity(move);
		
		RealRender render=new RealRender(prey);
		render.setAnimation(getOwner().getEngine().getRessources().getAnimation("Prey"));
		prey.addAbillity(render);
		
		Vector2f position=new Vector2f(
				getArea().getCenterX(),
				getArea().getCenterY());
		
		prey.setPosition(position);
		prey.setDirection(270);
		
		getOwner().getEngine().addEntityToBuff(prey);
		list.add(prey);
	}

	public int getMaxPrey() {
		return maxPrey;
	}

	public void setMaxPrey(int maxPrey) {
		this.maxPrey = maxPrey;
	}

	public int getPopDelay() {
		return popDelay;
	}

	public void setPopDelay(int popDelay) {
		this.popDelay = popDelay;
	}

	@Override
	public void entityAdded(Entity entity) {
		//nothing to do		
	}

	@Override
	public void entityRemoved(Entity entity) {
		list.remove(entity); //can be our prey		
	}

}
