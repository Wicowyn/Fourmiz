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
import fourmiz.engine.Ability;
import fourmiz.touch.marker.FoodMarker;

//la nourriture est une entit� � part enti�re
public class Food extends Ability {
	private static Logger log=LogManager.getLogger(Food.class);
	//quantit� de nourriture exploitable pour l'entit�
	private int food;
	private MyFood foodMarker=new MyFood();

	public Food(Entity owner) {
		super(owner);
		addTouchMarker(foodMarker);
	}
	
	@Override
	public void update(int delta) {

	}
	public int getFood() {
		return food;
	}
	
	public void setFood(int food) {
		this.food = food;
	}

	private class MyFood extends FoodMarker{

		@Override
		public int getTotalFood() {
			return food;
		}

		@Override
		//pr�l�ve une quantit� de nourriture du total de l'entit� (amount)
		public int getFood(int amount) {
			//quantit� de nourriture pr�lev�e
			int foodTaked=0;
			
			//si la quantit� voulue est sup�rieure au total de l'entit�
			if(amount>food){
				//on pr�l�ve tout
				foodTaked=food;
				food=0;
				
				log.info("has no longer food, go to die");
				//cette entit� nourriture est supprim�e du syst�me
				Food.this.getOwner().getEngine().removeEntityToBuff(Food.this.getOwner());
			}
			//sinon on pr�l�ve la quantit� voulue en d�duisant celle ci du total
			else{
				foodTaked=amount;
				food-=amount;
			}
			
			//on retourne la quantit� de nourriture pr�lev�e
			return foodTaked;
		}

		@Override
		public Entity getOwner() {
			return Food.this.getOwner();
		}

		@Override
		//retourne la zone d'influence de la nourriture
		public Shape getArea() {
			return Food.this.getOwner().getCollisionShape();
		}
		
	}
}
