package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.painter.HvlAnimatedTextureUV;

public class Explosion {
	public float x, y;
	public HvlAnimatedTextureUV explosionAnimation;
	public Explosion(float xArg, float yArg) {
		x = xArg;
		y = yArg;
		explosionAnimation = new HvlAnimatedTextureUV(Main.getTexture(Main.INDEX_EXP), 128, 26, 0.06f);
		explosionAnimation.setAutoStop(true);
		explosionAnimation.setRunning(true);

	}
	public void draw(float delta) {
		if(explosionAnimation.isRunning()) {
			hvlDrawQuadc(x, y, 128, 128, explosionAnimation, Color.lightGray);
		}
	}
}
