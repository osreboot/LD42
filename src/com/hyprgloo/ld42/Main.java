package com.hyprgloo.ld42;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.input.HvlInput;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String[] args){
		new Main();
	}

	public static final int 
	INDEX_FONT = 0,
	INDEX_CANISTER_FUEL = 1,
	INDEX_CANISTER_ENERGY = 2,
	INDEX_CANISTER_AMMO = 3;

	public static final Color 
	COLOR_BLUE0 = new Color(0.2f, 0.2f, 1.0f),
	COLOR_BLUE1 = new Color(0f, 0f, 1.0f),
	COLOR_BLUE2 = new Color(0f, 0f, 0.8f),
	COLOR_BLUE3 = new Color(0f, 0f, 0.6f),
	COLOR_BLUE4 = new Color(0f, 0f, 0.4f);

	public static HvlFontPainter2D font;

	public static HvlInput inputPause;

	public Main(){
		super(144, 1280, 720, "Airlock Gridlock by HYPRGLOO", new HvlDisplayModeDefault());
	}

	@Override
	public void initialize(){
		getTextureLoader().loadResource("Font");
		getTextureLoader().loadResource("CanisterFuel");
		getTextureLoader().loadResource("CanisterEnergy");
		getTextureLoader().loadResource("CanisterAmmo");

		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_AGOFFICIAL);
		font.setCharSpacing(16f);

		inputPause = new HvlInput(new HvlInput.InputFilter(){
			@Override
			public float getCurrentOutput(){
				return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_P) ? 1 : 0;
			}
		});

		ShipSelector.initialize();
		MenuManager.initialize();
		
	}

	@Override
	public void update(float delta){
		MenuManager.update(delta);
	}

}
