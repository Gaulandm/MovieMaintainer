package gaulandm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class MenuPanel extends JPanel implements ActionListener {

    private static JFrame frame;

    private static final String title = "Movie Maintainer";

    private static final HashMap<String, Command> options = new HashMap<>() {{
        put("Movies", MenuPanel::actionMovies);
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

    private static void actionMovies() {
        List<Movie> movies = new ArrayList<>();



        movies.add(new Movie("Spiderman", "RBK34", new GregorianCalendar(2026, Calendar.NOVEMBER, 30, 14, 0, 0).getTime(), 20));
        movies.getFirst().addUser(new User("anyad"));
        movies.getFirst().addUser(new User("anyad2"));
        movies.getFirst().addUser(new User("anyad3"));

        movies.add(new Movie("Superman", "RB45K3",  new GregorianCalendar(2026, Calendar.NOVEMBER, 20, 16, 0, 0).getTime(), 20));

        JTable table = new JTable();
        table.setModel(new MovieTableModel(movies, null));

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        frame.setContentPane(scrollPane);
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
