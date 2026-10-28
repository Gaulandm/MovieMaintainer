package gaulandm;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeUserNameDialog extends JDialog implements ActionListener {

    private final User user;
    private final JFormattedTextField field;

    public ChangeUserNameDialog(JFrame frame, User user) {
        super(frame, true);

        this.user = user;

        setLayout(new BorderLayout());

        DefaultFormatter formatter = new DefaultFormatter();
        formatter.setOverwriteMode(false);


        field = new JFormattedTextField(formatter);
        field.setPreferredSize(new Dimension(100, 20));

        JLabel label = new JLabel("New username:");

        label.setLabelFor(field);
        field.setValue(user.getName());

        JButton submit = new JButton("Submit");
        submit.addActionListener(this);

        add(label,BorderLayout.LINE_START);
        add(field, BorderLayout.LINE_END);
        add(submit, BorderLayout.PAGE_END);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        user.setName((String)field.getValue());
    }
}
