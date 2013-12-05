package fourmiz.abillity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Shape;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.touch.marker.FoodMarker;

public class Food extends Abillity {
	private static Logger log=LogManager.getLogger(Food.class);
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
				
				log.info("has no longer food, go to die");
				Food.this.getOwner().getEngine().removeEntityToBuff(Food.this.getOwner());
			}
			else{
				foodTaked=amount;
				food-=amount;
			}
			
			return foodTaked;
		}

		@Override
		public Entity getOwner() {
			return Food.this.getOwner();
		}

		@Override
		public Shape getArea() {
			return Food.this.getOwner().getCollisionShape();
		}
		
	}
}
