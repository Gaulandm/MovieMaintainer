package gaulandm;

import gaulandm.table_model.ButtonEditor;
import gaulandm.table_model.ButtonRenderer;
import gaulandm.table_model.MovieTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MovieTable extends JPanel implements ActionListener {

    private final JTable table;
    private final JPanel menuPane;
    private final JFrame frame;

    private final List<Movie> movies;

    public MovieTable(JFrame frame, JPanel menuPane, User current_user) {
        super(new BorderLayout());
        this.frame = frame;
        this.menuPane = menuPane;

        movies = new ArrayList<>();

        movies.add(new Movie("Spiderman", "RBK34", new GregorianCalendar(2026, Calendar.NOVEMBER, 30, 14, 0, 0).getTime(), 3));

        movies.getFirst().addUser("anyad");
        movies.getFirst().addUser("anyad2");
        movies.getFirst().addUser("anyad3");

        movies.add(new Movie("Superman", "RB45K3",  new GregorianCalendar(2026, Calendar.NOVEMBER, 20, 16, 0, 0).getTime(), 20));

        table = new JTable();
        MovieTableModel model = new MovieTableModel(movies, null, current_user);
        table.setModel(model);
        table.setDefaultRenderer(JButton.class, new ButtonRenderer());
        ButtonEditor buttonEditor = new ButtonEditor(model, frame);

        table.setDefaultEditor(JButton.class, buttonEditor);

        JScrollPane pane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel bottomPane = new JPanel(new GridLayout(1, 0));

        JButton back = new JButton("Back");
        back.addActionListener(this);

        if (current_user.isOperator()) {
            JButton create = new JButton("Create");
            create.addActionListener(buttonEditor);
            create.setActionCommand("Create -1");
            bottomPane.add(create);
        }



        bottomPane.add(back);

        add(pane, BorderLayout.CENTER);
        add(bottomPane, BorderLayout.PAGE_END);
    }

    public void setFilter(List<String> filter) {
        ((MovieTableModel)table.getModel()).setFilter(filter);
    }

    public void getRegisteredMovies(User user) {
        List<Movie> filteredMovies = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.isRegistered(user.getName())) {
                filteredMovies.add(movie);
            }
        }

        ((MovieTableModel)table.getModel()).setMovies(filteredMovies);
    }

    public void getAllMovies() {
        ((MovieTableModel)table.getModel()).setMovies(movies);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setContentPane(menuPane);
        frame.pack();
        frame.repaint();
    }
}
