package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Ship {

	public static ArrayList<Ship> ships = new ArrayList<>();

	public static void updateShips(float delta){
		for(Ship s : ships) s.update(delta);
		for(Ship s : ships) s.draw(delta);
	}

	public float x, y, xs, ys, speed, xGoal, yGoal, rotation, maxSpeed;
	public Cargo cargo;
	public boolean docking = false;

	public Ship(float xArg, float yArg, float xGoalArg, float yGoalArg, float rotationArg, float maxSpeedArg, Cargo cargoArg){
		x = xArg;
		y = yArg;
		xs = 0;
		ys = 0;
		speed = 0;
		xGoal = xGoalArg;
		yGoal = yGoalArg;
		rotation = rotationArg;
		maxSpeed = maxSpeedArg;
		cargo = cargoArg;
		ships.add(this);
	}

	public void update(float delta){
		if(docking){
//			if(x < xGoal) xs = HvlMath.stepTowards(xs, delta * maxSpeed, Math.min(maxSpeed, Math.abs(x - xGoal)));
//			if(x > xGoal) xs = HvlMath.stepTowards(xs, delta * maxSpeed, -Math.min(maxSpeed, Math.abs(x - xGoal)));
//			if(y < yGoal) ys = HvlMath.stepTowards(ys, delta * maxSpeed, Math.min(maxSpeed, Math.abs(y - yGoal)));
//			if(y > yGoal) ys = HvlMath.stepTowards(ys, delta * maxSpeed, -Math.min(maxSpeed, Math.abs(y - yGoal)));
		}else{
			speed = HvlMath.stepTowards(speed, delta * maxSpeed, Math.min(maxSpeed, HvlMath.distance(x, y, xGoal, yGoal)));
			HvlCoord2D speedCoord = new HvlCoord2D(xGoal - x, yGoal - y);
			speedCoord.normalize();
			if(Float.isNaN(speedCoord.x)) speedCoord.x = 0;
			if(Float.isNaN(speedCoord.y)) speedCoord.y = 0;
			speedCoord.mult(speed);
			xs = HvlMath.stepTowards(xs, delta * maxSpeed, speedCoord.x);
			ys = HvlMath.stepTowards(ys, delta * maxSpeed, speedCoord.y);
			x += xs * delta;
			y += ys * delta;
		}
	}

	public void draw(float delta){
		hvlDrawQuadc(xGoal, yGoal, 10, 10, new Color(1f, 1f, 1f, 0.1f));
	}

	public void setGoal(float xArg, float yArg){
		xGoal = xArg;
		yGoal = yArg;
	}

}
