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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.geom.Shape;

import fourmiz.engine.Ability;
import fourmiz.engine.EntityListener;

/**
 * Class to manage collisions of {@link TouchHandle} with {@link TouchMarker}
 * @author Nicolas
 */
public class CollisionManager implements CollidableListener, EntityListener{
	private static Logger log=LogManager.getLogger(CollisionManager.class);
	private Map<Integer, Set<TouchHandle>> colliHandle=new HashMap<Integer, Set<TouchHandle>>();
	private Map<Integer, Set<TouchMarker>> colliMarker=new HashMap<Integer, Set<TouchMarker>>();
	
	/**
	 * Add entity and manage his {@link TouchHandle} and {@link TouchMarker}
	 * @param entity
	 */
	public void addEntity(Entity entity){
		for(Ability abillity : entity.getAllAbility()){
			abilityAdded(abillity);
		}
		
		entity.addEntityListener(this);
		//log.debug("After add entity "+entity.getID()+" we have: ");
		//showType();
	}
	
	@SuppressWarnings("unused")
	private void showType(){
		for(Map.Entry<Integer, Set<TouchHandle>> entry : colliHandle.entrySet()){
			log.debug("For type :"+entry.getKey()+" have "+entry.getValue().size()+" touchHandle");
		}
		for(Map.Entry<Integer, Set<TouchMarker>> entry : colliMarker.entrySet()){
			log.debug("For type :"+entry.getKey()+" have "+entry.getValue().size()+" touchMarker");
		}
	}
	
	/**
	 * See {@link #addEntity(Entity)}
	 * @param collection
	 */
	public void addEntity(Collection<Entity> collection){
		for(Entity entity : collection) addEntity(entity);
	}
	
	/**
	 * Remove entity from the manager and unload his {@link TouchHandle} and {@link TouchMarker}
	 * @param entity
	 */
	public void removeEntity(Entity entity){
		for(Ability abillity : entity.getAllAbility()){
			abilityRemoved(abillity);
		}
		
		entity.removeEntityListener(this);
		//log.info("After remove entity "+entity.getID()+" we have: ");
		//showType();
	}
	
	/**
	 * See {@link #removeEntity(Entity)}
	 * @param collection
	 */
	public void removeEntity(Collection<Entity> collection){
		for(Entity entity : collection) removeEntity(entity);
	}
	
	/**
	 * Take all {@link TouchMarker} and {@link TouchHandle} and try if they collide between them, sorted by type.
	 * If collide exist {@link TouchHandle#perform(TouchMarker)} is called
	 */
	public void performCollision(){
		List<DataCollide> toPerform=new ArrayList<DataCollide>();
		
		for(Iterator<Map.Entry<Integer, Set<TouchHandle>>> itH=this.colliHandle.entrySet().iterator(); itH.hasNext();){
			Map.Entry<Integer, Set<TouchHandle>> entry=itH.next();
			Set<TouchMarker> setM=colliMarker.get(entry.getKey());
			
			if(setM==null) continue;
			
			for(TouchHandle tH : entry.getValue()){
				int collide;
				Iterator<TouchMarker> it;
				
				for(it=setM.iterator(), collide=0; it.hasNext() 
						&& (tH.maxCollideByCycle()==TouchHandle.NO_COLLIDE_LIMIT || collide<tH.maxCollideByCycle());){
					TouchMarker tM=it.next();
					Shape handleArea=tH.getArea();
					Shape markerArea=tM.getArea();
					
					switch(tH.getCollideType()){
					case CONTAIN:
						if(handleArea.contains(markerArea)){
							toPerform.add(new DataCollide(tH, tM));
							collide++;
						}
						break;
					case CONTAIN_OR_INTERSECT:
						if(handleArea.contains(markerArea) || handleArea.intersects(markerArea)){
							toPerform.add(new DataCollide(tH, tM));
							collide++;
						}
						break;
					case INTERSECT:
						if(handleArea.intersects(markerArea)){
							toPerform.add(new DataCollide(tH, tM));
							collide++;
						}
						break;
					case OUT_OR_INTERSECT:
						if(!handleArea.contains(markerArea)){
							toPerform.add(new DataCollide(tH, tM));
							collide++;
						}
						break;
					case OUT:
						if(!handleArea.contains(markerArea) && !handleArea.intersects(markerArea)){
							toPerform.add(new DataCollide(tH, tM));
							collide++;
						}
						break;
					default:
						break;
					
					}
				}
			}
		}
		
		Collections.sort(toPerform);
		for(DataCollide data : toPerform) data.perform();		
	}

	@Override
	public void touchHandleAdded(TouchHandle handle) {
		Set<TouchHandle> set=colliHandle.get(handle.getType());
		
		if(set==null){
			set=new HashSet<TouchHandle>();
			colliHandle.put(handle.getType(), set);
		}
		
		set.add(handle);
	}
	
	@Override
	public void touchHandleRemoved(TouchHandle handle) {
		Set<TouchHandle> set=colliHandle.get(handle.getType());
		
		if(set==null || !set.remove(handle)){
			log.warn("Try to remove handle type "+handle.getType()+" but not found");
		}
	}
	
	@Override
	public void touchMarkerAdded(TouchMarker marker) {
		Set<TouchMarker> set=colliMarker.get(marker.getType());
		
		if(set==null){
			set=new HashSet<TouchMarker>();
			CollisionManager.this.colliMarker.put(marker.getType(), set);
		}
		
		set.add(marker);
	}
	
	@Override
	public void touchMarkerRemoved(TouchMarker marker) {
		Set<TouchMarker> set=colliMarker.get(marker.getType());
		
		if(set==null || !set.remove(marker)){
			log.warn("Try to remove handle type "+marker.getType()+" but not found");
		}
	}
	
	/**
	 * Class to facilitate store things to collide
	 * @author Nicolas
	 */
	public class DataCollide implements Comparable<DataCollide>{
		public TouchHandle handle;
		public TouchMarker marker;
		
		
		public DataCollide(TouchHandle handle, TouchMarker marker){
			this.handle=handle;
			this.marker=marker;
		}
		
		@Override
		public int compareTo(DataCollide o) {
			return handle.compareTo(o.handle);
		}
		
		public void perform(){
			this.handle.perform(this.marker);
		}
		
	}

	@Override
	public void abilityAdded(Ability abillity) {
		for(TouchHandle handle : abillity.getTouchHandle()) touchHandleAdded(handle);
		for(TouchMarker marker : abillity.getTouchMarker()) touchMarkerAdded(marker);
		
		abillity.addCollidableListener(this);
	}

	@Override
	public void abilityRemoved(Ability abillity) {
		for(TouchHandle handle : abillity.getTouchHandle()) touchHandleRemoved(handle);
		for(TouchMarker marker : abillity.getTouchMarker()) touchMarkerRemoved(marker);
		
		abillity.removeCollidableListener(this);
	}

	@Override
	public void positionUpdated() {
		
	}
}
