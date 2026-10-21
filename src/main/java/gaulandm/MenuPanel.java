package gaulandm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MenuPanel extends JPanel implements ActionListener {

    private static final String title = "Movie Maintainer";

    private static final HashMap<String, Command> options = new HashMap<>() {{
        put("Movies", () -> { });
        put("My registrations", () -> { });
        put("Change user", () -> { });
        put("Exit", () -> System.exit(0) );
    }};

    public MenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 15)));

        options.forEach((option, _) -> addButton(option));
    }

    private void addButton(String title) {
        JButton button = new JButton(title);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 10)));
        button.setActionCommand(title);
        button.addActionListener(this);
        add(button);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Command command = options.get(e.getActionCommand());

        if (command != null) {
            command.execute();
        }
    }
}
