package graphics;

import java.util.*;
import javax.swing.table.*;

public class DirectionBindings extends AbstractTableModel {
    private Map<Class<?>, Binding> bindings;

    DirectionBindings() {
        bindings = new HashMap<>();
    }

    @Override
    public int getRowCount() {
        int size = 0;
        for (Binding b : bindings.values()) {
            size += b.size();
        }
        return size;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
