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

package fourmiz.abillity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Yellow;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import fourmiz.collision.Entity;
import fourmiz.collision.TouchHandle;
import fourmiz.collision.TouchMarker;

public class RealRender extends Render {
	private Map<IntervalAngl, Renderable> mapMoveRender=new HashMap<IntervalAngl, Renderable>();
	private Map<IntervalAngl, Renderable> mapStaticRender=new HashMap<IntervalAngl, Renderable>();
	private Renderable currentRender;
	private Vector2f lastPos;
	private int diffX, diffY;
	private static Image images;
	
	
	public RealRender(Entity owner) {
		super(owner);
		this.lastPos=this.getOwner().getPosition().scale(0.1f);
	}
	
	public void setMoveRender(float anglFirst, float anglSecond, Renderable renderable){
		this.mapMoveRender.put(new IntervalAngl(anglFirst, anglSecond), renderable);
	}
	
	public void setStaticRender(float anglFirst, float anglSecond, Renderable renderable){
		this.mapStaticRender.put(new IntervalAngl(anglFirst, anglSecond), renderable);
	}
	
	public void addDiffX(int diffX){
		this.diffX+=diffX;
	}
	public void addDiffY(int diffY){
		this.diffY+=diffY;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)throws SlickException {
			
		    g.pushTransform();
		    g.translate(getImage(getOwner().getPosition()).x,getImageResource(getOwner().getPosition()).y);
		    g.rotate(15, 15, (float) Math.toDegrees(getOwner().getScale()));
		    part.draw();
		    g.popTransform();

		    g.drawString("Count: " + cont, 5, 40);
	}
	
	public static Image getImagePosition(int X, int Y) {
		    return images;
	}
	

	@Override
	public void update(int delta) {
	
	}

	@Override
	public Collection<? extends TouchMarker> getTouchMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends TouchHandle> getTouchHandle() {
		// TODO Auto-generated method stub
		return null;
	}

}