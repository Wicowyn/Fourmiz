package fourmiz.abillity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Shape;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.touch.marker.FoodMarker;

//la nourriture est une entit� � part enti�re
public class Food extends Abillity {
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
