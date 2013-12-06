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

package fourmiz.engine;

import java.util.ArrayList;
import java.util.List;

import fourmiz.collision.CollidableListener;
import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;

/**
 * It the main class of the engine, it define an ability like move, life or even suicide
 * @author Nicolas
 */
public abstract class Ability {
	private List<CollidableListener> collidableListeners=new ArrayList<CollidableListener>();
	private List<TouchHandle> touchHandle=new ArrayList<TouchHandle>(0);
	private List<TouchMarker> touchMarker=new ArrayList<TouchMarker>(0);
	protected int ID;
	private static int lastID;
	private Entity owner;
	
	{
		this.ID=Ability.lastID++;
	}
	
	/**
	 * Constructor
	 * @param owner the {@link Entity} attached to this Ability
	 */
	public Ability(Entity owner){
		setOwner(owner);
	}
	
	/**
	 * Return entity's ID
	 * @return ID
	 */
	public int getID(){
		return this.ID;
	}
	
	/**
	 * Set the owner/creator of this entity
	 * @param owner
	 */
	public void setOwner(Entity entity){
		this.owner=entity;
	}
	
	/**
	 * Return the owner/creator of this entity
	 * @return
	 */
	public Entity getOwner() {
		return owner;
	}

	/**
	 * Callback method called at each cycle by {@link Entity#update(int)}
	 * @param delta time elapsed from last call
	 */
	public abstract void update(int delta);
	
	/**
	 * Return current list {@link TouchMarker}
	 * @return
	 */
	public ArrayList<? extends TouchMarker> getTouchMarker(){
		return new ArrayList<TouchMarker>(touchMarker);
	}
	
	/**
	 * Return current list {@link TouchHandle}
	 * @return
	 */
	public ArrayList<? extends TouchHandle> getTouchHandle(){
		return new ArrayList<TouchHandle>(touchHandle);
	}
	
	/**
	 * Add {@link TouchMarker} to be matched
	 * @param marker 
	 * @return true if not already added
	 */
	protected boolean addTouchMarker(TouchMarker marker){
		boolean added=touchMarker.add(marker);
		
		if(added) notifyTouchMarkerAdded(marker);
		
		return added;
	}
	
	 /**
	  * Remove {@link TouchMarker} not be found
	  * @param marker
	  * @return false if not found
	  */
	protected boolean removeTouchMarked(TouchMarker marker){
		boolean removed=touchMarker.remove(marker);
		
		if(removed) notifyTouchMarkerRemoved(marker);
		
		return removed;
	}
	
	/**
	 * Add {@link TouchHandle} to match {@link TouchMarker}
	 * @param handle
	 * @return true if already added
	 */
	protected boolean addTouchHandle(TouchHandle handle){
		boolean added=touchHandle.add(handle);
		
		if(added) notifyTouchHandleAdded(handle);
		
		return added;
	}
	
	/**
	 * Remove {@link TouchHandle} to doesn't match {@link TouchMarker}
	 * @param handle
	 * @return false if not found
	 */
	protected boolean removeTouchHandle(TouchHandle handle){
		boolean removed=touchHandle.remove(handle);
		
		if(removed) notifyTouchHandleRemoved(handle);
		
		return removed;
	}
	
	/**
	 * Add the given {@link CollidableListener}
	 * @param listener
	 * @return true if doesn't already added
	 */
	public void addCollidableListener(CollidableListener listener){
		collidableListeners.add(listener);
	}
	
	/**
	 * Remove the given {@link CollidableListener}
	 * @param listener
	 * @return false if doesn't exist
	 */
	public boolean removeCollidableListener(CollidableListener listener){
		return collidableListeners.remove(listener);
	}
	
	private void notifyTouchHandleAdded(TouchHandle handle){
		for(CollidableListener listener : this.collidableListeners) listener.touchHandleAdded(handle);
	}
	
	private void notifyTouchHandleRemoved(TouchHandle handle){
		for(CollidableListener listener : this.collidableListeners) listener.touchHandleRemoved(handle);
	}

	private void notifyTouchMarkerAdded(TouchMarker marker){
		for(CollidableListener listener : this.collidableListeners) listener.touchMarkerAdded(marker);
	}
	
	private void notifyTouchMarkerRemoved(TouchMarker marker){
		for(CollidableListener listener : this.collidableListeners) listener.touchMarkerRemoved(marker);
	}
}
