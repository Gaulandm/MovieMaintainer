package gaulandm.table_model;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class CreateDialog extends JDialog implements ActionListener {


    private final List<JFormattedTextField> fields;
    private final MovieTableModel model;


    public CreateDialog(JFrame frame, MovieTableModel model) {
        super(frame, true);
        this.model = model;


        fields = new ArrayList<>();

        setLayout(new BorderLayout());

        JPanel labelPane = new JPanel(new GridLayout(0,1));
        JPanel fieldPane = new JPanel(new GridLayout(0, 1));

        for (ColumnProperty property : model.getProperties()) {
            JFormattedTextField field = property.isUpdatable();
            if (field != null) {

                field.setSize(50, 20);
                field.setPreferredSize(new Dimension(50, 20));
                JLabel label = new JLabel(property.getName());
                label.setLabelFor(field);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);

        int selectedMovie = model.createEmptyMovie();

        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).getValue() != null) {
                model.setValueAt(fields.get(i).getValue(), selectedMovie, i);
            }
        }
    }
}
