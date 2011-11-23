/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.physics;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;



/**
 * @author Liao
 *
 */
public class CoordinateConverter {
	private static final float METER_PER_PIXEL=0.0375f;
	
	private static int width=0;
	private static int height=0;
	public static void init(int w,int h){
		width=w;
		height=h;
	}
	public static Vec2 coordPixelToWorld(Point p){
		return new Vec2(scalerPixelsToWorld(p.getX()-width/2.0),scalerPixelsToWorld(height/2.0-p.getY()));
	}
	public static Vec2 vectorPixelToWorld(Vector v){
		return new Vec2(scalerPixelsToWorld(v.getVectorX()),scalerPixelsToWorld(-v.getVectorY()));
	}
	public static Vector vectorWorldToPixel(Vec2 v){
		return new Vector(scalerWorldToPixels(v.x),scalerWorldToPixels(-v.y));
	}
	public static Point coordWorldToPixels(Vec2 v){
		return new Point(scalerWorldToPixels(v.x)+(width/2f),height/2f-scalerWorldToPixels(v.y));
	}
	public static float scalerPixelsToWorld(double p){
		return ((float)p)*METER_PER_PIXEL;
	}
	public static double scalerWorldToPixels(float p){
		return p/METER_PER_PIXEL;
	}
	public static PixelAABB worldAABBToPixelAABB(AABB aabb){
		return new PixelAABB(coordWorldToPixels(aabb.lowerBound),coordWorldToPixels(aabb.upperBound));
	}
	public static AABB pixelAABBToWorldAABB(PixelAABB aabb){
		return new AABB(coordPixelToWorld(aabb.getLowerVertex()),coordPixelToWorld(aabb.getUpperVertex()));
	}
}
