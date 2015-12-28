package system;

import java.util.ArrayList;

import tests.MainGameLoop;

public class InputSystem extends BaseSystem {

	public ArrayList<Click> clicks = new ArrayList<Click>();

	public InputSystem(MainGameLoop game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	public class Click {
		float posX, posY; int action, button; 
		public Click(float x, float y, int a, int b) {posX = x; posY = y; action = a; button = b;}
	}
	public class Press {int key, action; public Press(int k, int a) {key = k; action = a;}}

}
