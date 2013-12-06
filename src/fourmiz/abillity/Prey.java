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

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.touch.marker.PreyMarker;

public class Prey extends Abillity {
	private int packetFood=50;
	private int life;
	private int food;
	private MyPrey prey=new MyPrey();

	public Prey(Entity owner) {
		super(owner);
		
		addTouchMarker(prey);
	}

	@Override
	public void update(int delta) {

	}
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		if(life<0) throw new IllegalArgumentException();
		this.life = life;
	}
	
	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		if(food<0) throw new IllegalArgumentException();
		this.food = food;
	}

	public int getPacketFood() {
		return packetFood;
	}

	public void setPacketFood(int packetFood) {
		this.packetFood = packetFood;
	}

	private void popFood(){
		
		while(food>0){
			Vector2f position=getOwner().getPosition();
			Entity entity=new Entity(getOwner().getEngine(), Engine.getDefaultShape());
			
			Food food=new Food(entity);
			food.setFood(getFood(packetFood));
			entity.addAbillity(food);
			
			RealRender render=new RealRender(entity);
			render.setAnimation(getOwner().getEngine().getRessources().getAnimation("Food"));
			entity.addAbillity(render);
			
			int dir=(int) (Math.random()*360);
			int space=((int) (Math.random()*2*Engine.SIZE_CASE))+Engine.SIZE_CASE;
			
			position.x+=(float) (space*Math.cos(Math.toRadians(dir)));
			position.y+=(float) (space*Math.sin(Math.toRadians(dir)));
			
			entity.setPosition(position);
			entity.setDirection(dir);
			
			getOwner().getEngine().addEntityToBuff(entity);
		}
		
		getOwner().getEngine().removeEntityToBuff(getOwner());
	}
	
	private int getFood(int amount) {
		int foodTaked=0;
		
		if(amount>food){
			foodTaked=food;
			food=0;				
		}
		else{
			foodTaked=amount;
			food-=amount;
		}
		
		return foodTaked;
	}
	
	private class MyPrey extends PreyMarker{

		@Override
		public Entity getOwner() {
			return Prey.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return Prey.this.getOwner().getCollisionShape();
		}

		@Override
		public int getLife() {
			return life;
		}

		@Override
		public boolean hit(int attackLife) {
			life-=attackLife;
			
			if(life<=0){
				popFood();
				return true;
			}
			else return false;
		}

		@Override
		public int getFood() {
			return food;
		}
		
	}

}
