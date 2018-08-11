package com.hyprgloo.ld42;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.ld42.ships.HeavyMerchant;
import com.hyprgloo.ld42.ships.LightMerchant;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.painter.HvlCursor;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String[] args){
		new Main();
	}

	public static final int INDEX_FONT = 0;
	
	public static HvlFontPainter2D font;
	
	public Main(){
		super(144, 1280, 720, "Airlock Gridlock by HYPRGLOO", new HvlDisplayModeDefault());
	}

	@Override
	public void initialize(){
		getTextureLoader().loadResource("Font");
		
		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_AGOFFICIAL);
		font.setCharSpacing(16f);
		
		new LightMerchant(0f, 0f, 0f, 0f, 0f, 0f, 0f);
		new HeavyMerchant(0f, 0f, 0f, 0f, 0f, 0f, 0f);
	}

	@Override
	public void update(float delta){
		Ship.ships.get(0).setGoal(HvlCursor.getCursorX(), HvlCursor.getCursorY());
		Ship.ships.get(1).setGoal(HvlCursor.getCursorX(), HvlCursor.getCursorY());
		
		Ship.updateShips(delta);
		
		font.drawWordc("THE QUICK BROWN FOX\n JUMPS OVER THE LAZY DOG", Display.getWidth()/2, Display.getHeight()/2, Color.white, 0.5f);
		font.drawWordc("the quick brown fox\n jumps over the lazy dog", Display.getWidth()/2, Display.getHeight()/2 + 200, Color.white, 0.5f);
	}

}
