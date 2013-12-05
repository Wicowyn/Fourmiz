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
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import tools.ResourceManager;
import fourmiz.collision.CollisionManager;
import fourmiz.collision.Entity;

public class Engine {
	public static int SIZE_CASE=100;
	private int xCase, yCase;
	private float xScale, yScale;
    private List<EngineListener> listeners=new ArrayList<EngineListener>();
    private CollisionManager collisionManager=new CollisionManager();
    private List<Entity> entities=new ArrayList<Entity>();
    private List<Entity> entitiesAdd=new ArrayList<Entity>();
    private List<Entity> entitiesRemove=new ArrayList<Entity>();
    private Logger log=LogManager.getLogger(getClass());
    private boolean loaded=false;
	private static String renderSuffix="-render.xml";
	private static String mapSuffix="-map.xml";
	private static String resourcePath="ressources/";
	private String currentGame;
	private ResourceManager ressources=new ResourceManager();
    
    public Engine(){
            
    }
    
    public static Shape getDefaultShape(){
    	return new Rectangle(0, 0, SIZE_CASE, SIZE_CASE);
    }
    
    public ResourceManager getRessources() {
		return ressources;
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
	
	public static List<String> getPossibleGame(){
		List<String> listMap=new ArrayList<String>();
		List<String> listRender=new ArrayList<String>();
		
		File[] files=(new File(resourcePath)).listFiles();
		
		for(File file : files){
			String name=file.getName();
			if(name.endsWith(renderSuffix)){
				listRender.add(name.substring(0, name.indexOf(renderSuffix)));
			}
			if(name.endsWith(mapSuffix)){
				listMap.add(name.substring(0, name.indexOf(mapSuffix)));
			}
		}
		
		listMap.retainAll(listRender);
		
		return listMap;
	}
    
    public void loadLevel(String name) throws JDOMException, IOException, SlickException{
    	currentGame=name;
    	ressources.load(resourcePath+currentGame+renderSuffix);
        SAXBuilder sax=new SAXBuilder();
        Document doc=sax.build(new File(resourcePath+currentGame+mapSuffix));
        Element root=doc.getRootElement();
        List<Element> listElem=root.getChildren();
        
        for(Element elem : listElem){
            switch(elem.getName()){
            case "Config":
            	loadConfig(elem.getChildren());
            	break;
            case "Entity":
            	loadEntity(elem.getChildren());
            	break;
            default:
                log.warn("loadLevel: unknown type object -> "+elem.getName());
                continue;
            }                       
        }
        
        this.loaded=true;
    }
    
    private void loadConfig(Collection<Element> configs){
    	for(Element elem : configs){
            switch(elem.getName()){
            case "Chart":
            	setxCase(Integer.parseInt(elem.getAttributeValue("xCase")));
            	setyCase(Integer.parseInt(elem.getAttributeValue("yCase")));
            	setxScale(Float.parseFloat(elem.getAttributeValue("xScale")));
            	setyScale(Float.parseFloat(elem.getAttributeValue("yScale")));
            break;
            default:
                log.warn("loadLevel: unknown type config -> "+elem.getName());
                continue;
            }
            
        }
    }
    
    private void loadEntity(Collection<Element> elems) throws DataConversionException{
    	for(Element elem : elems){
            Entity entity=null;
            switch(elem.getName()){
            case "Egg":
            	entity=EntityFactory.createEntity(EntityName.Egg, this);
            	break;
            case "Larva":
            	entity=EntityFactory.createEntity(EntityName.Larva, this);
            	break;
            case "Nymph":
            	entity=EntityFactory.createEntity(EntityName.Nymph, this);
            	break;
            case "FourmizWorker":
            	entity=EntityFactory.createEntity(EntityName.FourmizWorker, this);
            	break;
            case "FourmizSoldier":
            	entity=EntityFactory.createEntity(EntityName.FourmizSoldier, this);
            	break;
            case "FourmizSex":
            	entity=EntityFactory.createEntity(EntityName.FourmizSex, this);
            	break;
            case "Queen":
            	entity=EntityFactory.createEntity(EntityName.Queen, this);
            	break;
            case "Prey":
            	entity=EntityFactory.createEntity(EntityName.Prey, this);
            	break;
            case "PopPrey":
            	entity=EntityFactory.createEntity(EntityName.PopPrey, this);
            	break;
            default:
                log.warn("loadLevel: unknown type entity -> "+elem.getName());
                continue;
            }

            Vector2f position=new Vector2f();
            position.x=elem.getAttribute("x").getIntValue()*SIZE_CASE;
            position.y=elem.getAttribute("y").getIntValue()*SIZE_CASE;
            entity.setPosition(position);
            
            entity.setDirection(elem.getAttribute("dir").getIntValue());
            
            addEntity(entity);                        
        }
    }

    public List<String> getListGame(){
        File directory=new File("ressources/map");
        GameFileFilter filter=new GameFileFilter();
        File[] arrayFile=directory.listFiles(filter);
        List<String> list=new ArrayList<String>();
        for(File file : arrayFile){
                list.add(file.getName().substring(0, file.getName().indexOf(".")));
        }
        
        return list;
    }
    
    public int getxCase() {
		return xCase;
	}

	public void setxCase(int xCase) {
		this.xCase = xCase;
	}

	public int getyCase() {
		return yCase;
	}

	public void setyCase(int yCase) {
		this.yCase = yCase;
	}

	public float getxScale() {
		return xScale;
	}

	public void setxScale(float xScale) {
		this.xScale = xScale;
	}

	public float getyScale() {
		return yScale;
	}

	public void setyScale(float yScale) {
		this.yScale = yScale;
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
