package fourmiz.abillity;

import org.newdawn.slick.geom.Vector2f;

import fourmiz.collision.Entity;
import fourmiz.engine.Abillity;
import fourmiz.engine.Engine;
import fourmiz.engine.EntityFactory;
import fourmiz.engine.EntityName;

public class Queen extends Abillity{
	private int time;
	private int timeToLay;
	
	public Queen(Entity owner){
		super(owner);
		
		time=0;
		generateTimeToLay();
	}
	
	@Override
	public void update(int delta){
		time+=delta;
		
		//si l'age de la reine à dépassé ou est égal à la date de ponte suivante 
		if(time>=timeToLay){
			time=0;
			generateTimeToLay();
			
			lay();
		}
	}
	
	//ponte d'un oeuf
	private void lay(){
		//création d'une nouvelle entité de type oeuf
		Entity egg=EntityFactory.createEntity(EntityName.Egg, getOwner().getEngine());
		
		//on lui donne la position de la reine au moment de la ponte
		Vector2f position=getOwner().getPosition();
		position.x+=(float) (Engine.SIZE_CASE*Math.cos(Math.toRadians(getOwner().getDirection()-180)));
		position.y+=(float) (Engine.SIZE_CASE*Math.sin(Math.toRadians(getOwner().getDirection()-180)));
		
		egg.setPosition(position);
		egg.setDirection(getOwner().getDirection());
		
		//on ajoute l'oeuf à la liste des entités
		getOwner().getEngine().addEntityToBuff(egg);
	}
	
	//génération du prochain temps de ponte
	private void generateTimeToLay(){
		timeToLay=3000+((int) Math.random()%3000)-1000;
	}
}
