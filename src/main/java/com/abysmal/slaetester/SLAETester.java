package com.abysmal.slaetester;

import java.awt.Color;
import java.nio.FloatBuffer;

import com.abysmal.slae.SLAE;
import com.abysmal.slae.framework.Input;
import com.abysmal.slae.framework.Window;
import com.abysmal.slae.message.Message;
import com.abysmal.slae.object.GUIObject;
import com.abysmal.slae.object.HUDObject;
import com.abysmal.slae.system.System;

import org.joml.Rectanglef;
import org.joml.Vector2i;

public class SLAETester implements System {

	public static void main(String[] args) {
		SLAE.initialise(new SLAETester());
	}

	public void init() {
		String shaderPath = SLAETester.class.getResource("/shaders/Basic.shader").getPath();
		Object[] data = { 1,
				new GUIObject(new Rectanglef(0, 0, Window.getWidth(), Window.getHeight()), Color.white, shaderPath) };
		SLAE.execute("Add GUIObject", data.clone());
		data[1] = new GUIObject(new Rectanglef(0, 0, Window.getWidth(), Window.getHeight() * 0.25f), Color.gray,
				shaderPath);
		SLAE.execute("Add GUIObject", data.clone());

		Object[] data2 = { 1, new HUDObject(new Vector2i(0, 0), new Vector2i(100, 100), (b, a, m) -> {
			java.lang.System.out.println("test");
		}) };
		SLAE.execute("Add HUDObject", data2);
		SLAE.execute("Switch Scene", 1);

		new Thread(() -> {
			while (SLAE.isRunning()) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				FloatBuffer JoyInput = Input.getJoystickInput(1);
				for (int i = 0; i < 20; i++) {
					java.lang.System.out
							.print(i < 4 ? (int) (JoyInput.get(i) * 100) + " | " : (int) JoyInput.get(i) + " | ");
				}
				java.lang.System.out.println();
			}
		}).start();
	}

	@Override
	public void handleMessage(Message message) {
		if (message.getMessage().equalsIgnoreCase("slae init")) {
			init();
		}
	}
}