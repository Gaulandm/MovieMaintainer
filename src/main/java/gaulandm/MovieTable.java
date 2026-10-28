package gaulandm;

import gaulandm.table_model.ButtonEditor;
import gaulandm.table_model.ButtonRenderer;
import gaulandm.table_model.MovieTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieTable extends JPanel implements ActionListener {

    private final JTable table;
    private final JPanel menuPane;
    private final JFrame frame;
    private final MoviesLoader loader;
    private final User currentUser;
    private final JPanel bottomPane;
    private final JButton button;

    private final List<Movie> movies;

    public MovieTable(JFrame frame, JPanel menuPane, User currentUser) throws IOException, ClassNotFoundException {
        super(new BorderLayout());
        this.loader = new MoviesLoader();
        this.frame = frame;
        this.currentUser = currentUser;
        this.menuPane = menuPane;
        this.bottomPane = new JPanel(new GridLayout(1, 0));
        this.button= new JButton("Create");


        movies = loader.load();

        table = new JTable();
        MovieTableModel model = new MovieTableModel(movies, null, currentUser);
        table.setModel(model);
        table.setDefaultRenderer(JButton.class, new ButtonRenderer());
        ButtonEditor buttonEditor = new ButtonEditor(model, frame);

        table.setDefaultEditor(JButton.class, buttonEditor);

        JScrollPane pane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JButton back = new JButton("Back");
        back.addActionListener(this);

        button.addActionListener(buttonEditor);
        button.setActionCommand("Create -1");

        bottomPane.add(back);

        add(pane, BorderLayout.CENTER);
        add(bottomPane, BorderLayout.PAGE_END);
    }
    public void saveMovies() throws IOException {
        loader.save(movies);
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

    public void refreshCreateButton() {
        if (currentUser.isOperator()) {

            bottomPane.add(button);
        }
        else {
            bottomPane.remove(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setContentPane(menuPane);
        frame.pack();
        frame.repaint();
    }
}
