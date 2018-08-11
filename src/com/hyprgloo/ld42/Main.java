package com.hyprgloo.ld42;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String[] args){
		new Main();
	}

	public static final int 
	INDEX_FONT = 0;

	public static final Color 
	COLOR_BLUE0 = new Color(0.2f, 0.2f, 1.0f),
	COLOR_BLUE1 = new Color(0f, 0f, 1.0f),
	COLOR_BLUE2 = new Color(0f, 0f, 0.8f),
	COLOR_BLUE3 = new Color(0f, 0f, 0.6f),
	COLOR_BLUE4 = new Color(0f, 0f, 0.4f);

	public static HvlFontPainter2D font;

	public Main(){
		super(144, 1280, 720, "Airlock Gridlock by HYPRGLOO", new HvlDisplayModeDefault());
	}

	@Override
	public void initialize(){
		getTextureLoader().loadResource("Font");

		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_AGOFFICIAL);
		font.setCharSpacing(16f);

		MenuManager.initialize();
	}

	@Override
	public void update(float delta){
		MenuManager.update(delta);
	}

}
