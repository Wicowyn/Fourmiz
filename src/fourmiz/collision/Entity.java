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

package fourmiz.collision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import fourmiz.engine.Ability;
import fourmiz.engine.Engine;
import fourmiz.engine.EntityListener;

/**
 * Class to define each entity present in the engine. To be make something entity need {@link Ability} 
 * @author Nicolas
 */
public final class Entity{
	private static Logger log=LogManager.getLogger(Entity.class);
	private static int lastID;
	private int ID;
	private float scale;
	private float direction;
	private Vector2f position;
	private boolean inUpdate=false;
	private boolean modifNCS=true; //NCS NormalCollisionShape, usefull for optimisation
	private List<EntityListener> entityListeners=new ArrayList<EntityListener>();
	private List<Ability> abillities=new ArrayList<Ability>();
	private Set<Ability> abillitiesAdd=new HashSet<Ability>();
	private Set<Ability> abillitiesRemove=new HashSet<Ability>();
	private Entity owner=null;
	private Shape normalCollisionShape=null;
	private Shape collisionShape=null;
	private Engine engine=null;
	
	{
		this.ID=Entity.lastID++;
	}
	
	/**
	 * Constructor
	 * @param engine {@link Engine} attached
	 * @param collisionShape base {@link Shape} of the entity
	 */
	public Entity(Engine engine, Shape collisionShape){
		this.normalCollisionShape=collisionShape;
		this.engine=engine;
	}
	
	/**
	 * Give attached engine
	 * @return {@link Engine}
	 */
	public Engine getEngine(){
		return this.engine;
	}
	
	/**
	 * Clear the entity of all {@link Ability}
	 */
	public void clear(){
		for(Ability ability : this.abillities) notifyAbilityRemoved(ability);
		this.abillities.clear();
	}
	
	/**
	 * Callback method called at each cycle, it call each {@link Ability#update(int)} of ability contained
	 * @param delta time elapsed from last call
	 */
	public void update(int delta){
		inUpdate=true;
		for(Ability ability : this.abillities) ability.update(delta);
		inUpdate=false;
		checkAbilityBuff();
	}
	
	/**
	 * Like we can't add or remove {@link Ability} during update phase we must add temporary this change to different
	 * list. This method check is temporary {@link Ability} exist and if is the case, it add them to right mode.
	 */
	private void checkAbilityBuff(){
		for(Ability ability : abillitiesAdd) addAbility(ability);
		for(Ability ability : abillitiesRemove) removeAbility(ability);
		
		abillitiesAdd.clear();
		abillitiesRemove.clear();
	}
	
	/**
	 * Add {@link Ability} to this entity
	 * @param ability ability to add
	 */
	public void addAbility(Ability ability){
		if(inUpdate){
			abillitiesAdd.add(ability);
			return;
		}
		
		ability.setOwner(this);		
		this.abillities.add(ability);
		
		notifyAbilityAdded(ability);
		
		log.debug("ability: "+ability.getClass().getSimpleName()+" add to "+getID());
	}
	
	/**
	 * Remove {@link Ability} from this entity
	 * @param ability ability removed
	 * @return false if not found
	 */
	public boolean removeAbility(Ability ability){
		if(this.inUpdate){
			if(!this.abillities.contains(ability)) return false;
			return this.abillitiesRemove.add(ability);
		}
		
		if(this.abillities.remove(ability)){
			notifyAbilityRemoved(ability);
			log.debug("ability: "+ability.getClass().getSimpleName()+" remove from "+getID());
			return true;
		}
		
		return false;
	}
	
	/**
	 * Return copy of {@link ArrayList} of all {@link Ability} contained
	 * @return the list
	 */
	public ArrayList<Ability> getAllAbility(){
		return new ArrayList<Ability>(this.abillities);
	}
	
	/**
	 * Get {@link Ability} by his ID
	 * @param ID
	 * @return the ability or null if not found
	 */
	public Ability getAbility(int ID){
		for(Ability ability : this.abillities){
			if(ability.getID()==ID) return ability;
		}
		
		return null;
	}
	
	/**
	 * Return the last entity owner which do not have owner. See {@link #getOwner()}
	 * @param entity entity source
	 * @return last entity
	 */
	public static Entity getRootOwner(Entity entity){
		while(entity.getOwner()!=null){
			entity=entity.getOwner();
		}
			
		return entity;
	}

	/**
	 * Return entity's ID
	 * @return ID
	 */
	public int getID(){
		return ID;
	}

	/**
	 * Return scale
	 * @return his scale
	 */
	public float getScale(){
		return scale;
	}

	/**
	 * Set scale
	 * @param scale the new scale
	 */
	public void setScale(float scale){
		this.scale=scale;
	}

	/**
	 * Return the current direction in degree, 0 to 360 exclude
	 * @return the angle
	 */
	public float getDirection(){
		return direction;
	}

	/**
	 * Set the current direction
	 * @param direction if it <0 or >=360 it will be reduced
	 */
	public void setDirection(float direction){
		this.direction=direction;
		this.modifNCS=true;
		
		while(this.direction<0) this.direction+=360;
		while(this.direction>=360) this.direction-=360;
		
		notifyPositionUpdated();
	}

	/**
	 * Return a copy of the position
	 * @return the current position
	 */
	public Vector2f getPosition(){
		return this.position.copy();
	}

	/**
	 * Set the current position
	 * @param position
	 */
	public void setPosition(Vector2f position){
		this.position=position;
		this.modifNCS=true;
		
		notifyPositionUpdated();
	}
	
	/**
	 * Return the owner/creator of this entity
	 * @return his owner
	 */
	public Entity getOwner(){
		return this.owner;
	}
	
	/**
	 * Set the owner/creator of this entity
	 * @param owner
	 */
	public void setOwner(Entity owner){
		this.owner=owner;
	}
	
	/**
	 * Add the given {@link EntityListener}
	 * @param listener
	 * @return true if doesn't already added
	 */
	public boolean addEntityListener(EntityListener listener){
		return this.entityListeners.add(listener);
	}
	
	/**
	 * Remove the given {@link EntityListener}
	 * @param listener
	 * @return false if doesn't exist
	 */
	public boolean removeEntityListener(EntityListener listener){
		return this.entityListeners.remove(listener);
	}
	
	private void notifyAbilityAdded(Ability ability){
		for(EntityListener listener : this.entityListeners) listener.abilityAdded(ability);
	}
	
	private void notifyAbilityRemoved(Ability ability){
		for(EntityListener listener : this.entityListeners) listener.abilityRemoved(ability);
	}

	private void notifyPositionUpdated(){
		for(EntityListener listener : this.entityListeners) listener.positionUpdated();
	}
	
	/**
	 * Return the base shape of the entity. See {@link #Entity(Engine, Shape)}
	 * @return the base shpae
	 */
	public Shape getNormalCollisionShape(){
		return this.normalCollisionShape;
	}
	
	/**
	 * Return the shape placed in function of position and direction
	 * @return the shape
	 */
	public Shape getCollisionShape(){
		if(modifNCS){
			collisionShape=normalCollisionShape.transform(Transform.createRotateTransform(
					(float) Math.toRadians(getDirection()), Engine.SIZE_CASE/2, Engine.SIZE_CASE/2))
					.transform(Transform.createTranslateTransform(position.x, position.y));
			
			modifNCS=false;
		}
		return collisionShape;
	}	
}
