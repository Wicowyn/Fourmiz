package fourmiz.abillity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Shape;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.touch.marker.FoodMarker;

//la nourriture est une entité à part entière
public class Food extends Abillity {
	private static Logger log=LogManager.getLogger(Food.class);
	//quantité de nourriture exploitable pour l'entité
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
		//prélève une quantité de nourriture du total de l'entité (amount)
		public int getFood(int amount) {
			//quantité de nourriture prélevée
			int foodTaked=0;
			
			//si la quantité voulue est supérieure au total de l'entité
			if(amount>food){
				//on prélève tout
				foodTaked=food;
				food=0;
				
				log.info("has no longer food, go to die");
				//cette entité nourriture est supprimée du système
				Food.this.getOwner().getEngine().removeEntityToBuff(Food.this.getOwner());
			}
			//sinon on prélève la quantité voulue en déduisant celle ci du total
			else{
				foodTaked=amount;
				food-=amount;
			}
			
			//on retourne la quantité de nourriture prélevée
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
