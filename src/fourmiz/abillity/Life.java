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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Shape;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.touch.marker.LifeMarker;

public class Life extends Abillity{
	private static Logger log=LogManager.getLogger(Life.class);
	private static int INTERVAL_TIME=1000;
	private MyLifeMarker lifeMarker=new MyLifeMarker();
	//vie instantan�e
	private int life_current;
	//vie maximum
	private int life_max;
	//quantit� de vie perdue � chaque actualisation
	private int life_tic;
	private int time=0;
	
	
	public Life(Entity owner) {
		super(owner);
		
		addTouchMarker(lifeMarker);
	}

	@Override
	public void update(int delta) {
		time+=delta;
		
		if(time>=INTERVAL_TIME){
			life_current=life_current-life_tic;
			time-=INTERVAL_TIME;
		}
		//You are dead, life empty
		if(life_current<=0){
			log.info("entity ID: "+getOwner().getID()+" die cause to has no health");
			getOwner().getEngine().removeEntityToBuff(getOwner());	
		}
	}

	//retourne la vie actuelle
	public int getCurrentLife() {
		return life_current;
	}

	//met � jour la vie actuelle en v�rifiant qu'elle est conforme aux bornes
	public void setCurrentLife(int life_current) {
		if(life_current<0 || life_current>life_max) throw new IllegalArgumentException();
		
		this.life_current = life_current;
	}

	//retourne la vie maximum
	public int getMaxLife() {
		if(life_max<=0) throw new IllegalArgumentException();
		
		return life_max;
	}

	//met � jour la valeur maximale de vie possible
	public void setMaxLife(int life_max) {
		this.life_max = life_max;
	}

	//retourne la perte de vie p�riodique
	public int getUptake() {
		return life_tic;
	}

	//met � jour la perte de vie p�riodique
	public void setUptake(int uptake) {
		this.life_tic = uptake;
	}


	private class MyLifeMarker extends LifeMarker{

		@Override
		public int getLife() {
			return life_current;
		}

		@Override
		public int getMaxLife() {
			return life_max;
		}

		@Override
		public void setLife(int life) {
			
			setCurrentLife(life);
		}

		@Override
		//augmente la vie actuelle de la valeur de life
		public void addLife(int life) {
			life_current+=life;
			
			if(life_current>life_max) life_current=life_max;
		}

		@Override
		//diminue la vie actuelle de la valeur de life
		public void withdrawLife(int life) {
			life_current-=life;
			
			if(life_current<0) life_current=0;
		}

		@Override
		//retourne l'entit� � qui appartient cette vie
		public Entity getOwner() {
			return Life.this.getOwner();
		}

		//retourne la zone d'influence autour de l'entit� 
		@Override
		public Shape getArea() {
			return Life.this.getOwner().getCollisionShape();
		}
		
	}
}
