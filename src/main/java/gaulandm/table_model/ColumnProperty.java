package gaulandm.table_model;

import javax.swing.*;

public interface ColumnProperty {

    default boolean isEditable() {
        return false;
    }

    default JFormattedTextField isUpdatable() {
        return null;
    }

    String getName();

    Object getObject(int rowIndex);

    default void setObject(Object value, int rowIndex) {

    }

    default Object getDefaultFieldValue(int rowIndex) {
        return getObject(rowIndex);
    }
}
