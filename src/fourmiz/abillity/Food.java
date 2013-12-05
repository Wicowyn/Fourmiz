package fourmiz.abillity;

import org.newdawn.slick.geom.Shape;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.touch.marker.FoodMarker;

public class Food extends Abillity {
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
		public int getFood(int amount) {
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

		@Override
		public Entity getOwner() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Shape getArea() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
