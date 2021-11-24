package graphics;

import java.io.*;
import java.util.*;

import logic.GameLogic.Direction;

public abstract class Binding implements Serializable {
    protected Map<String, Direction> binding;
    protected String file;

    Binding() {
        binding = new HashMap<>();
    }

    public int size() {
        return binding.size();
    }

    @SuppressWarnings("unchecked")
    protected void read() {
        File f = new File(file);
        if (f.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                binding = (Map<String, Direction>)ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(String a, String b) {
        Direction d = binding.remove(a);
        binding.put(b, d);
    }

    protected void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(binding);
            oos.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public abstract void setDefault();
}
