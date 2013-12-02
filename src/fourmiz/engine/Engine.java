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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.JDOMException;

import fourmiz.collision.CollisionManager;
import fourmiz.collision.Entity;

public class Engine {
    private List<EngineListener> listeners=new ArrayList<EngineListener>();
    private CollisionManager collisionManager=new CollisionManager();
    private List<Entity> entities=new ArrayList<Entity>();
    private List<Entity> entitiesAdd=new ArrayList<Entity>();
    private List<Entity> entitiesRemove=new ArrayList<Entity>();
    private Logger log=LogManager.getLogger(getClass());
    private boolean loaded=false;
    
    public Engine(){
            
    }
    
    public void update(int delta){
        for(Entity entity : this.entities) entity.update(delta);
        checkEntityBuff();
        this.collisionManager.performCollision();
        checkEntityBuff();
    }
    
    public void checkEntityBuff(){
    	entities.addAll(entitiesAdd);
    	collisionManager.addEntity(entitiesAdd);
    	
    	entities.removeAll(entitiesRemove);
    	collisionManager.removeEntity(entitiesRemove);
    	
        for(Entity entity : this.entitiesAdd) notifyEntityAdded(entity);
        for(Entity entity : this.entitiesRemove) notifyEntityRemoved(entity);
            
        entitiesAdd.clear();
        entitiesRemove.clear();
    }
    
    public List<Entity> getEntities(){
        return this.entities;
    }
    
    public void addEntityToBuff(Entity entity){
        this.entitiesAdd.add(entity);
        this.log.debug("add Entity: "+entity.getClass().getSimpleName()+", ID: "+entity.getID()+", position: "+entity.getPosition()+", direction: "+entity.getDirection());
    }
    
    public void removeEntityToBuff(Entity entity){
        this.entitiesRemove.add(entity);
        this.log.debug("remove Entity: "+entity.getClass().getSimpleName()+", ID: "+entity.getID()+", position: "+entity.getPosition()+", direction: "+entity.getDirection());
    }
    
    public void addEntity(Entity entity){
        this.entities.add(entity);
        
        this.collisionManager.addEntity(entity);
        notifyEntityAdded(entity);
        this.log.debug("add Entity: "+entity.getClass().getSimpleName()+", ID: "+entity.getID()+", position: "+entity.getPosition()+", direction: "+entity.getDirection());
    }
    
    public void removeEntity(Entity entity){
        this.entities.remove(entity);
        
        this.collisionManager.removeEntity(entity);
        
        notifyEntityRemoved(entity);
        this.log.debug("remove Entity: "+entity.getClass().getSimpleName()+", ID: "+entity.getID()+", position: "+entity.getPosition()+", direction: "+entity.getDirection());
    }
    
    public List<? extends Entity> getListOf(Class<?> classType){
        List<Entity> list=new ArrayList<Entity>();
        
        for(Entity entity : this.entities) if(entity.getClass().equals(classType)) list.add(entity);
        
        return list;
    }
    
    public void loadLevel(String filePath) throws JDOMException, IOException{
        /*SAXBuilder sax=new SAXBuilder();
        Document doc=sax.build(new File(filePath));
        Element root=doc.getRootElement();
        List<Element> listElem=root.getChildren();
        
        for(Element elem : listElem){
            Entity entity=null;
            switch(elem.getName()){
            default:
                this.log.warn("loadLevel: unknown type object -> "+elem.getName());
                continue;
            }

            Vector2f position=new Vector2f();
            position.x=elem.getAttribute("x").getIntValue()*1000;
            position.y=elem.getAttribute("y").getIntValue()*1000;
            entity.setPosition(position);
            
            entity.setDirection(elem.getAttribute("dir").getIntValue());
            
            addEntity(entity);                        
        }
        */
        this.loaded=true;
    }

    public List<String> getListGame(){ //TODO à améliorer
        File directory=new File("ressources/map");
        GameFileFilter filter=new GameFileFilter();
        File[] arrayFile=directory.listFiles(filter);
        List<String> list=new ArrayList<String>();
        for(File file : arrayFile){
                list.add(file.getName().substring(0, file.getName().indexOf(".")));
        }
        
        return list;
    }
    
    public void unLoad(){
        for(Entity entity : this.entities) entity.clear();
        
        this.entities.clear();
        this.entitiesAdd.clear();
        this.entitiesRemove.clear();
        this.collisionManager=new CollisionManager();
        
        this.loaded=false;
    }
    
    public boolean isLoad(){
        return this.loaded;
    }
    
    public boolean addListener(EngineListener listener){
        return this.listeners.add(listener);
    }
    
    public boolean removeListener(EngineListener listener){
        return this.listeners.remove(listener);
    }
    
    protected void notifyEntityAdded(Entity entity){
        for(EngineListener listener : this.listeners) listener.entityAdded(entity);
    }
    
    protected void notifyEntityRemoved(Entity entity){
        for(EngineListener listener : this.listeners) listener.entityRemoved(entity);
    }
    
    private static class GameFileFilter implements FilenameFilter{

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".xml");
        }
            
    }
}