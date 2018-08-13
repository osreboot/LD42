package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.HashMap;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.ld42.Game.EndState;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.menu.HvlButtonMenuLink;
import com.osreboot.ridhvl.menu.HvlComponent;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox.ArrangementStyle;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.HvlSpacer;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;
import com.osreboot.ridhvl.painter.HvlRenderFrame;

public class MenuManager {

	public static final float BUTTON_SPACING = 16f;

	public static HvlRenderFrame pauseFrame;

	private static HashMap<HvlLabeledButton, ButtonWrapper> buttonWrappers = new HashMap<>();

	public static HvlMenu main, levels, game, pause, options, credits, end, intro;

	public static void initialize(){

		try{
			pauseFrame = new HvlRenderFrame(Display.getWidth(), Display.getHeight());
		}catch(Exception e){}

		HvlArrangerBox defaultArrangerBox = new HvlArrangerBox(Display.getWidth(), Display.getHeight(), ArrangementStyle.VERTICAL);
		defaultArrangerBox.setxAlign(0.05f);
		HvlComponentDefault.setDefault(defaultArrangerBox);

		HvlLabeledButton defaultLabeledButton = new HvlLabeledButton(256, 64, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.getTexture(Main.INDEX_BUTTON), Main.COLOR_GREEN2);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.getTexture(Main.INDEX_BUTTON), Main.COLOR_GREEN3);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.getTexture(Main.INDEX_BUTTON), Main.COLOR_GREEN4);
			}
		}, Main.font, "", Main.COLOR_GREEN5);
		defaultLabeledButton.setUpdateOverride(new HvlAction2<HvlComponent, Float>(){
			@Override
			public void run(HvlComponent aArg, Float delta){
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				if(!buttonWrappers.containsKey(b)) buttonWrappers.put(b, new ButtonWrapper());
				b.update(delta);
			}
		});
		defaultLabeledButton.setTextScale(0.25f);
		defaultLabeledButton.setyAlign(0.7f);
		defaultLabeledButton.setxAlign(0.5f);
		HvlComponentDefault.setDefault(defaultLabeledButton);

		main = new HvlMenu();
		levels = new HvlMenu();
		game = new HvlMenu();
		pause = new HvlMenu();
		options = new HvlMenu();
		credits = new HvlMenu();
		end = new HvlMenu();
		intro = new HvlMenu();

		main.add(new HvlArrangerBox.Builder().build());
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("start").setClickedCommand(new HvlButtonMenuLink(levels)).build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setClickedCommand(new HvlButtonMenuLink(credits)).setText("credits").build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setClickedCommand(new HvlButtonMenuLink(options)).setText("options").build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("exit").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				System.exit(0);
			}
		}).build());

		levels.add(new HvlArrangerBox.Builder().build());
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("tutorial").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 0;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("station 1").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 1;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("station 2").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 2;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("station 3").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 3;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("station 4").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.selected_level = 4;
				HvlMenu.setCurrent(game);
				Game.restart();
			}
		}).build());
		levels.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		levels.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("back").setClickedCommand(new HvlButtonMenuLink(main)).build());

		pause.add(new HvlArrangerBox.Builder().build());
		pause.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("resume").setClickedCommand(new HvlButtonMenuLink(game)).build());
		pause.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		pause.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("exit").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				HvlMenu.setCurrent(main);
				FancyOverlay.resetMainBackground();
			}
		}).build());

		options.add(new HvlArrangerBox.Builder().build());
		options.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Sound: " + (Main.settings.soundEnabled ? "on" : "off")).setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg) {
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				Main.settings.soundEnabled = !Main.settings.soundEnabled;
				b.setText("Sound: " + (Main.settings.soundEnabled ? "on" : "off"));
			}
		}).build());
		options.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		options.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Music: " + (Main.settings.musicEnabled ? "on" : "off")).setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg) {
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				Main.settings.musicEnabled = !Main.settings.musicEnabled;
				b.setText("Music: " + (Main.settings.musicEnabled ? "on" : "off"));
			}
		}).build());
		options.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		options.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Tutorial: " + (Main.settings.tutorialsEnabled ? "on" : "off")).setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg) {
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				Main.settings.tutorialsEnabled = !Main.settings.tutorialsEnabled;
				b.setText("Tutorial: " + (Main.settings.tutorialsEnabled ? "on" : "off"));
			}
		}).build());
		options.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		options.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Cursor: " + (Main.settings.customCursor ? "on" : "off")).setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg) {
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				Main.settings.customCursor = !Main.settings.customCursor;
				b.setText("Cursor: " + (Main.settings.customCursor ? "on" : "off"));
			}
		}).build());
		options.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		options.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("back").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				HvlMenu.setCurrent(main);
				Main.saveConfig();
			}
		}).build());

		credits.add(new HvlArrangerBox.Builder().build());
		credits.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		credits.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("back").setClickedCommand(new HvlButtonMenuLink(main)).build());

		end.add(new HvlArrangerBox.Builder().build());
		end.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("levels").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				HvlMenu.setCurrent(levels);
				FancyOverlay.resetMainBackground();
			}
		}).build());
		end.getFirstArrangerBox().add(new HvlSpacer(0, BUTTON_SPACING));
		end.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("exit").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				HvlMenu.setCurrent(main);
				FancyOverlay.resetMainBackground();
			}
		}).build());

		HvlMenu.setCurrent(intro);
		FancyOverlay.resetMainBackground();
	}
	private static float introProgress = 0f;

	public static void update(float delta){
		if(HvlMenu.getCurrent() == pause || HvlMenu.getCurrent() == end){
			hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), pauseFrame);
			hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), new Color(0f, 0f, 0f, 0.4f));
		}

		if(HvlMenu.getCurrent() == main || HvlMenu.getCurrent() == levels || HvlMenu.getCurrent() == options || HvlMenu.getCurrent() == credits){
			FancyOverlay.drawMainBackground(delta);
		}

		HvlMenu.updateMenus(delta);

		if(HvlMenu.getCurrent() != game){
			for(HvlLabeledButton b : buttonWrappers.keySet()){
				buttonWrappers.get(b).update(delta, b.isHovering(), HvlMenu.getCurrent().getFirstArrangerBox().getChildren().contains(b));
				hvlDrawQuad(b.getX() - (buttonWrappers.get(b).fade * BUTTON_TARGET_SIZE/2f), b.getY() - (buttonWrappers.get(b).fade * BUTTON_TARGET_SIZE/2f), 
						b.getWidth() + (buttonWrappers.get(b).fade * BUTTON_TARGET_SIZE), b.getHeight() + (buttonWrappers.get(b).fade * BUTTON_TARGET_SIZE), 
						Main.getTexture(Main.INDEX_BUTTON_TARGET), new Color(1f, 1f, 1f, buttonWrappers.get(b).fade));
			
			}
		}else{
			if(TutorialManager.current == null){
				pauseFrame.doCapture(true, new HvlAction0(){
					@Override
					public void run(){
						Game.update(delta);
					}
				});
			}
			hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), pauseFrame);
			TutorialManager.update(delta);
		}

		if(HvlMenu.getCurrent() == main || HvlMenu.getCurrent() == levels){
			if(FancyOverlay.mainTimer > 2f){
				Main.font.drawWordc("Airlock \n Gridlock", Display.getWidth()/8*5 + 6f, Display.getHeight()/2 + 16f + 6f, Main.COLOR_GREEN5);
				Main.font.drawWordc("Airlock \n Gridlock", Display.getWidth()/8*5, Display.getHeight()/2 + 16f, Main.COLOR_GREEN1);
			}
		}else if (HvlMenu.getCurrent() == credits){
			Main.font.drawWordc("CREDITS", (Display.getWidth()/2) + 4, (Display.getHeight()/8) + 4, Main.COLOR_GREEN5, 0.5f);
			Main.font.drawWordc("CREDITS", Display.getWidth()/2, Display.getHeight()/8, Main.COLOR_GREEN1, 0.5f);
			Main.font.drawWordc("os_reboot", Display.getWidth()/2, Display.getHeight()*4/20 + 12, Main.COLOR_GREEN1, 0.325f);
			Main.font.drawWordc("Twitter: os_reboot", Display.getWidth()/2, Display.getHeight()*5/20 + 24, Main.COLOR_GREEN3, 0.25f);
			
			Main.font.drawWordc("HaveANiceDay", Display.getWidth()/2, Display.getHeight()*8/20,Main.COLOR_GREEN1, 0.325f);
			Main.font.drawWordc("github.com/haveaniceday33", Display.getWidth()/2, Display.getHeight()*9/20 + 12, Main.COLOR_GREEN3, 0.25f);
			
			Main.font.drawWordc("JKTransformers", Display.getWidth()/2, Display.getHeight()*12/20,Main.COLOR_GREEN1, 0.325f);	
			
			Main.font.drawWordc("Basset", Display.getWidth()/2, Display.getHeight()*15/20 - 12,Main.COLOR_GREEN1, 0.325f);
			Main.font.drawWordc("Twitter: xbassetx", Display.getWidth()/2, Display.getHeight()*16/20, Main.COLOR_GREEN3, 0.25f);
			
			Main.font.drawWordc("Made in 72 hours for Ludum Dare 42", Display.getWidth()/2, Display.getHeight()*18/20 + 16, Main.COLOR_GREEN3, 0.15f);
		}else if (HvlMenu.getCurrent() == intro){
			introProgress += delta/4f;
			if(introProgress >= 1f || (introProgress > 0.25f && Mouse.isButtonDown(0))) HvlMenu.setCurrent(main);
			float alpha = 1f - (Math.abs(introProgress - 0.5f)*2f);
			hvlDrawQuadc(Display.getWidth()/2, Display.getHeight()/2, 512, 512, Main.getTexture(Main.INDEX_HYPRGLOO), new Color(1f, 1f, 1f, alpha));
		}else if (HvlMenu.getCurrent() == end){
			if(Game.state == EndState.WIN_TUTORIAL){
				Main.font.drawWordc("TUTORIAL COMPLETE", (Display.getWidth()/2) + 4, (Display.getHeight()/8) + 4, Main.COLOR_GREEN5, 0.5f);
				Main.font.drawWordc("TUTORIAL COMPLETE", Display.getWidth()/2, Display.getHeight()/8, Main.COLOR_GREEN1, 0.5f);
				Main.font.drawWordc("Great job!", Display.getWidth()/8*5, Display.getHeight()/8*3, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("You're ready for the real thing!", Display.getWidth()/8*5, Display.getHeight()/8*3 + 48f, Main.COLOR_GREEN1, 0.25f);
			}else if(Game.state == EndState.LOSS_TUTORIAL){
				Main.font.drawWordc("TUTORIAL FAILED", (Display.getWidth()/2) + 4, (Display.getHeight()/8) + 4, Color.black, 0.5f);
				Main.font.drawWordc("TUTORIAL FAILED", Display.getWidth()/2, Display.getHeight()/8, Color.red, 0.5f);
				Main.font.drawWordc("try to...", Display.getWidth()/8*5, Display.getHeight()/8*3, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Keep both ships from crashing.", Display.getWidth()/8*5, Display.getHeight()/8*3 + 48f, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Don't stray from the objective.", Display.getWidth()/8*5, Display.getHeight()/8*3 + 96f, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Read descriptions carefully.", Display.getWidth()/8*5, Display.getHeight()/8*3 + 144f, Main.COLOR_GREEN1, 0.25f);
			}else if(Game.state == EndState.LOSS_ENERGY){
				Main.font.drawWordc("ENERGY DEPLETED", (Display.getWidth()/2) + 4, (Display.getHeight()/8) + 4, Color.black, 0.5f);
				Main.font.drawWordc("ENERGY DEPLETED", Display.getWidth()/2, Display.getHeight()/8, Color.red, 0.5f);
				Main.font.drawWordc("try to...", Display.getWidth()/8*5, Display.getHeight()/8*3, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Keep an eye on your energy bar.", Display.getWidth()/8*5, Display.getHeight()/8*3 + 48f, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Always have energy ships ready.", Display.getWidth()/8*5, Display.getHeight()/8*3 + 96f, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Don't let raiders steal energy.", Display.getWidth()/8*5, Display.getHeight()/8*3 + 144f, Main.COLOR_GREEN1, 0.25f);
			}else if(Game.state == EndState.LOSS_COLLISIONS){
				Main.font.drawWordc("MORALITY DEPLETED", (Display.getWidth()/2) + 4, (Display.getHeight()/8) + 4, Color.black, 0.5f);
				Main.font.drawWordc("MORALITY DEPLETED", Display.getWidth()/2, Display.getHeight()/8, Color.red, 0.5f);
				Main.font.drawWordc("try to...", Display.getWidth()/8*5, Display.getHeight()/8*3, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Don't be evil.", Display.getWidth()/8*5, Display.getHeight()/8*3 + 48f, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Don't let raiders crash into merchant", Display.getWidth()/8*5, Display.getHeight()/8*3 + 96f, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("ships by stopping in the traffic lanes.", Display.getWidth()/8*5, Display.getHeight()/8*3 + 144f, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Complain to the developers about buggy", Display.getWidth()/8*5, Display.getHeight()/8*3 + 192f, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("ship behavior.", Display.getWidth()/8*5, Display.getHeight()/8*3 + 240f, Main.COLOR_GREEN1, 0.25f);
			}if(Game.state == EndState.WIN){
				Main.font.drawWordc("JUMP SUCCESSFUL", (Display.getWidth()/2) + 4, (Display.getHeight()/8) + 4, Main.COLOR_GREEN5, 0.5f);
				Main.font.drawWordc("JUMP SUCCESSFUL", Display.getWidth()/2, Display.getHeight()/8, Main.COLOR_GREEN1, 0.5f);
				Main.font.drawWordc("Well done!", Display.getWidth()/8*5, Display.getHeight()/8*3, Main.COLOR_GREEN1, 0.25f);
				Main.font.drawWordc("Onward to the next adventure!", Display.getWidth()/8*5, Display.getHeight()/8*3 + 48f, Main.COLOR_GREEN1, 0.25f);
			}
		}
	}

	public static final float BUTTON_TARGET_SIZE = 16f;

	private static class ButtonWrapper{

		public float fade = 0f;
		public boolean rollover = false;

		private ButtonWrapper(){}

		private void update(float delta, boolean hovering, boolean menu){
			fade = menu ? HvlMath.stepTowards(fade, delta * 8f, hovering ? 1f : 0f) : 0f;
			if(menu && hovering && !rollover){
				rollover = true;
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_MENU_BLIP).playAsSoundEffect(1, 0.2f, false);
			}
			if(!hovering || !menu) rollover = false;
		}

	}

}
