package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;

public class Ship {

	public static ArrayList<Ship> ships = new ArrayList<>();

	public static void updateShips(float delta){
		for(Ship s : ships) s.update(delta);
		for(Ship s : ships) s.draw(delta);
	}
	
	public float x, y, xs, ys, xGoal, yGoal, rotation, maxSpeed;
	
	public Ship(float xArg, float yArg, float xsArg, float ysArg, float xGoalArg, float yGoalArg, float rotationArg, float maxSpeedArg){
		x = xArg;
		y = yArg;
		xs = xsArg;
		ys = ysArg;
		xGoal = xGoalArg;
		yGoal = yGoalArg;
		rotation = rotationArg;
		maxSpeed = maxSpeedArg;
		ships.add(this);
	}
	
	public void update(float delta){
		if(x < xGoal) xs = HvlMath.stepTowards(xs, delta * maxSpeed, Math.min(maxSpeed, Math.abs(x - xGoal)));
		if(x > xGoal) xs = HvlMath.stepTowards(xs, delta * maxSpeed, -Math.min(maxSpeed, Math.abs(x - xGoal)));
		if(y < yGoal) ys = HvlMath.stepTowards(ys, delta * maxSpeed, Math.min(maxSpeed, Math.abs(y - yGoal)));
		if(y > yGoal) ys = HvlMath.stepTowards(ys, delta * maxSpeed, -Math.min(maxSpeed, Math.abs(y - yGoal)));
		
		x += xs * delta;
		y += ys * delta;
	}
	
	public void draw(float delta) {;
		hvlDrawQuadc(xGoal, yGoal, 10, 10, Color.white);
	}
	
	public void setGoal(float xArg, float yArg){
		xGoal = xArg;
		yGoal = yArg;
	}
	
}
