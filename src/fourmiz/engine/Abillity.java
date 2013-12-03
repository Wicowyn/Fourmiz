/*//////////////////////////////////////////////////////////////////////
	This file is part of Bomberton, an Bomberman-like.
	Copyright (C) 2012-2013  Nicolas Barranger <wicowyn@gmail.com>

    Bomberton is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Bomberton is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Bomberton.  If not, see <http://www.gnu.org/licenses/>.
*///////////////////////////////////////////////////////////////////////

package fourmiz.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fourmiz.collision.CollidableListener;
import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;

public abstract class Abillity {
	private List<CollidableListener> collidableListeners=new ArrayList<CollidableListener>();
	protected int ID;
	private Entity owner;
	
	
	public Abillity(Entity owner){
		setOwner(owner);
	}
	
	public int getID(){
		return this.ID;
	}
	
	public void setOwner(Entity entity){
		this.owner=entity;
	}
	
	public Entity getOwner() {
		return owner;
	}

	public abstract void update(int delta);
	public abstract Collection<? extends TouchMarker> getTouchMarker();
	public abstract Collection<? extends TouchHandle> getTouchHandle();
	
	public void addCollidableListener(CollidableListener listener){
		collidableListeners.add(listener);
	}
	
	public boolean removeCollidableListener(CollidableListener listener){
		return collidableListeners.remove(listener);
	}
	
	protected void notifyTouchHandleAdded(TouchHandle handle){
		for(CollidableListener listener : this.collidableListeners) listener.touchHandleAdded(handle);
	}
	
	protected void notifyTouchHandleRemoved(TouchHandle handle){
		for(CollidableListener listener : this.collidableListeners) listener.touchHandleRemoved(handle);
	}

	protected void notifyTouchMarkerAdded(TouchMarker marker){
		for(CollidableListener listener : this.collidableListeners) listener.touchMarkerAdded(marker);
	}
	
	protected void notifyTouchMarkerRemoved(TouchMarker marker){
		for(CollidableListener listener : this.collidableListeners) listener.touchMarkerRemoved(marker);
	}
}
