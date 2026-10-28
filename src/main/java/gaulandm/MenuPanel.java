package gaulandm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class MenuPanel extends JPanel implements ActionListener {

    private final MovieTable table;

    private static JFrame frame;

    private static final String title = "Movie Maintainer";

    private static final HashMap<String, Command> options = new HashMap<>();

    private static List<String> registrationsFilter;

    private final User currentUser;

    public MenuPanel() {
        options.put("Movies", this::actionMovies);
        options.put("My registrations", this::myRegistrations);
        options.put("Change user", this::changeUser);
        options.put("Exit", () -> System.exit(0) );

        registrationsFilter = new ArrayList<>();
        registrationsFilter.add("Remove");
        registrationsFilter.add("Update");
        registrationsFilter.add("SeeUsers");


        currentUser = new User("test");

        table = new MovieTable(frame, this, currentUser);

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

    private void changeUser() {
        ChangeUserNameDialog dialog = new ChangeUserNameDialog(frame, currentUser);

        dialog.setLocationRelativeTo(frame);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private void actionMovies() {

        table.getAllMovies();

        if (currentUser.isOperator()) {
            table.setFilter(null);
        }
        else {
            table.setFilter(registrationsFilter);
        }

        frame.setContentPane(table);
        frame.pack();
        frame.repaint();
    }

    private void myRegistrations() {

        table.getRegisteredMovies(currentUser);

        table.setFilter(registrationsFilter);

        frame.setContentPane(table);
        frame.pack();
        frame.repaint();
    }

    public static void createAndShowGUI() {
        frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MenuPanel());

        frame.setVisible(true);

        frame.pack();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Command command = options.get(e.getActionCommand());

        if (command != null) {
            command.execute();
        }
    }
}
