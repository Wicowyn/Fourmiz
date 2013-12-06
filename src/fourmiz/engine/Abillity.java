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

public abstract class Abillity {
	private List<CollidableListener> collidableListeners=new ArrayList<CollidableListener>();
	private List<TouchHandle> touchHandle=new ArrayList<TouchHandle>(0);
	private List<TouchMarker> touchMarker=new ArrayList<TouchMarker>(0);
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
	
	public ArrayList<? extends TouchMarker> getTouchMarker(){
		return new ArrayList<TouchMarker>(touchMarker);
	}
	
	public ArrayList<? extends TouchHandle> getTouchHandle(){
		return new ArrayList<TouchHandle>(touchHandle);
	}
	
	protected boolean addTouchMarker(TouchMarker marker){
		boolean added=touchMarker.add(marker);
		
		if(added) notifyTouchMarkerAdded(marker);
		
		return added;
	}
	
	protected boolean removeTouchMarked(TouchMarker marker){
		boolean removed=touchMarker.remove(marker);
		
		if(removed) notifyTouchMarkerRemoved(marker);
		
		return removed;
	}
	
	protected boolean addTouchHandle(TouchHandle handle){
		boolean added=touchHandle.add(handle);
		
		if(added) notifyTouchHandleAdded(handle);
		
		return added;
	}
	
	protected boolean removeTouchHandle(TouchHandle handle){
		boolean removed=touchHandle.remove(handle);
		
		if(removed) notifyTouchHandleRemoved(handle);
		
		return removed;
	}
	
	public void addCollidableListener(CollidableListener listener){
		collidableListeners.add(listener);
	}
	
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
