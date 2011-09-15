package org.adamtacy.client.ui.effects.impl;

import org.adamtacy.client.ui.effects.core.NMorphColor;
import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.css.StyleImplementation;
import org.adamtacy.client.ui.effects.transitionsphysics.EaseInOutTransitionPhysics;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class Highlight extends NMorphColor {

	HandlerRegistration h;

	public void setDuration(double dur) {
		super.setDuration(dur / 2);
	}

	public void setDelay(double del) {
		afterDelay = (int) del * 1000;
		GWT.log("new delay:" + afterDelay, null);
	}

	public Highlight(Element el) {
		super(el, "backgroundColor");
		create();
	}

	public Highlight() {
		super("backgroundColor");
		create();
	}

	boolean forward = true;

	private void create(){
		setTransitionType(new EaseInOutTransitionPhysics());
		setDuration(4.0);
		setDelay(2.5);

		h = addEffectCompletedHandler(new EffectCompletedHandler() {
			public void onEffectCompleted(EffectCompletedEvent event) {
				if(forward){
					goAgain();
					forward = false;
				} else 
					forward = true;
				//h.removeHandler();
				
			}
		});
	}

	int afterDelay = 0;

	public void goAgain() {
		if (afterDelay > 0)
			this.play(1.0, 0.0, afterDelay);
		else
			this.play(1.0, 0.0);
	}

	public void setStartColor(String styleSheetStartColor) {
		colourStyleImpl.setStartColor(styleSheetStartColor);
		if (!effectElements.isEmpty())
			setUpEffect();
	}

	public void setEndColor(String styleSheetEndColor) {
		colourStyleImpl.setEndColor(styleSheetEndColor);
		if (!effectElements.isEmpty())
			setUpEffect();
	}

	boolean resolveTransparent = false;

	public boolean isResolveTransparent() {
		return resolveTransparent;
	}

	public void setResolveTransparent(boolean resolveTransparent) {
		this.resolveTransparent = resolveTransparent;
	}

	public void setUpEffect() {
		super.setUpEffect();
		if (resolveTransparent){
			DeferredCommand.addCommand(new Command() {
				public void execute() {
					String startCol = StyleImplementation.getComputedStyle(
							effectElements.get(0), "backgroundColor");
					GWT.log("Color: " + startCol, null);
					colourStyleImpl.setStartColor(startCol);
				}
			});
		} else {
			// Remove on account of Issue #136
			//colourStyleImpl.setStartColor(trans(colourStyleImpl.getEndColour()));
		}
	}

	public String trans(String col){
		String newCol = "rgba(0,0,0,0)";
		if (col.startsWith("rgba(")){
			newCol = col.substring(0, col.lastIndexOf(","))+"0)";
		} else if (col.startsWith("rgb(")){
			newCol = "rgba("+col.substring(4, col.length()-1)+",0)";
		}
		return newCol;
	}
}
