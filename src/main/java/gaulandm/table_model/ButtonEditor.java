package gaulandm.table_model;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class ButtonEditor extends AbstractCellEditor implements ActionListener, TableCellEditor {
    private final JButton button;
    private final UpdateDialog dialog;
    private final CreateDialog createDialog;
    private final HashMap<String, ButtonCommand> buttonActions;
    private final MovieTableModel model;
    private final JFrame frame;



    public ButtonEditor(MovieTableModel model, JFrame frame) {
        this.model = model;
        this.buttonActions = new HashMap<>();
        this.button = new JButton();
        this.frame = frame;
        button.addActionListener(this);
        dialog = new UpdateDialog(frame, model);
        createDialog = new CreateDialog(frame, model);

        buttonActions.put("Update", this::updateMovie);
        buttonActions.put("SeeUsers", this::getUsers);
        buttonActions.put("Remove", this::remove);
        buttonActions.put("Create", this::create);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String[] args = e.getActionCommand().split(" ");
        buttonActions.get(args[0]).execute(Integer.parseInt(args[1]));

        fireEditingStopped();
    }

    private void create(int index) {
        createDialog.setLocationRelativeTo(frame);
        createDialog.setVisible(true);
    }

    private void getUsers(int index) {
        if (model.getUsers(index).isEmpty()) {
            return;
        }

        SeeUsersDialog usersDialog = new SeeUsersDialog(model, index, frame);
        usersDialog.setLocationRelativeTo(frame);
        usersDialog.setVisible(true);
    }

    private void remove(int index) {
        model.removeMovie(index);
    }

    private void updateMovie(int index) {
        dialog.setSelectedMovie(index);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JButton buttValue = (JButton) value;

        button.setText(buttValue.getText());
        button.setActionCommand(buttValue.getActionCommand());

        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return button;
    }

}
