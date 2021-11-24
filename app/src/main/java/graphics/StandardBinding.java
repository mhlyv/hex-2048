package graphics;

import logic.GameLogic.Direction;

public class StandardBinding extends Binding {
    StandardBinding() {
        file = "standard.dat";
        read();
    }

	@Override
	public void setDefault() {
        binding.clear();
        binding.put("UP",    Direction.Up);
        binding.put("DOWN",  Direction.Down);
        binding.put("LEFT",  Direction.Left);
        binding.put("RIGHT", Direction.Right);
	}
}
