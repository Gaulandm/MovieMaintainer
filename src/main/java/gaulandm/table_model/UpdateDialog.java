package gaulandm.table_model;



import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class UpdateDialog extends JDialog implements ActionListener {


    private final List<JFormattedTextField> fields;
    private final List<ColumnProperty> updatableProperties;
    private int selectedMovie = -1;
    private final TableModel model;


    public UpdateDialog(JFrame frame, MovieTableModel model) {
        super(frame, true);
        this.model = model;

        updatableProperties = new ArrayList<>();
        fields = new ArrayList<>();

        setLayout(new BorderLayout());

        JPanel labelPane = new JPanel(new GridLayout(0,1));
        JPanel fieldPane = new JPanel(new GridLayout(0, 1));

        for (ColumnProperty property : model.getProperties()) {
            JFormattedTextField field = property.isUpdatable();
            if (field != null) {
                JLabel label = new JLabel(property.getName());
                label.setLabelFor(field);
                updatableProperties.add(property);
                fields.add(field);

                labelPane.add(label);
                fieldPane.add(field);
            }
        }

        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);

        JButton submit = new JButton("Submit");
        submit.addActionListener(this);

        add(submit, BorderLayout.PAGE_END);

        setResizable(false);
        pack();
    }

    public void setSelectedMovie(int selectedMovie) {
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setValue(updatableProperties.get(i).getDefaultFieldValue(selectedMovie));
        }
        this.selectedMovie = selectedMovie;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);

        for (int i = 0; i < fields.size(); i++) {
            model.setValueAt(fields.get(i).getValue(), selectedMovie, i);
        }
    }
}
