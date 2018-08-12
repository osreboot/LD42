package com.hyprgloo.ld42;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

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

	public static HvlMenu main, levels, game, pause, options, credits, end;
	
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
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.COLOR_GREEN2);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.COLOR_GREEN3);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, Main.COLOR_GREEN4);
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
		
		HvlMenu.setCurrent(main);
		FancyOverlay.resetMainBackground();
	}
	
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
			pauseFrame.doCapture(true, new HvlAction0(){
				@Override
				public void run(){
					Game.update(delta);
				}
			});
			hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), pauseFrame);
		}
		
		if(HvlMenu.getCurrent() == main){
			if(FancyOverlay.mainTimer > 2f){
				Main.font.drawWordc("Airlock \n Gridlock", Display.getWidth()/8*5 + 6f, Display.getHeight()/2 + 16f + 6f, Main.COLOR_GREEN5);
				Main.font.drawWordc("Airlock \n Gridlock", Display.getWidth()/8*5, Display.getHeight()/2 + 16f, Main.COLOR_GREEN1);
			}
		}
	}
	
	public static final float BUTTON_TARGET_SIZE = 16f;
	
	private static class ButtonWrapper{
		
		public float fade = 0f;
		
		private ButtonWrapper(){}
		
		private void update(float delta, boolean hovering, boolean menu){
			fade = menu ? HvlMath.stepTowards(fade, delta * 8f, hovering ? 1f : 0f) : 0f;
		}
		
	}
	
}
