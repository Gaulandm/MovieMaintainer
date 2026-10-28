package gaulandm.table_model;

import gaulandm.Movie;
import gaulandm.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieTableModel extends AbstractTableModel {

    private final List<ColumnProperty> properties;


    private final List<Movie> movies;

    public MovieTableModel(List<Movie> movies, List<String> filterProperties, User currentUser) {
        this.movies = movies != null ? movies : new ArrayList<>();
        this.properties = new ArrayList<>();

        properties.add(new ColumnProperty() {
            @Override
            public String getName() {
                return "Title";
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).getTitle();
            }
        });

        properties.add(new ColumnProperty(movies) {
            @Override
            public String getName() {
                return "Room";
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).getRoom();
            }
        });

        properties.add(new ColumnProperty(movies) {
            @Override
            public String getName() {
                return "Date";
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).getDate();
            }
        });

        properties.add(new ColumnProperty(movies) {
            @Override
            public String getName() {
                return "Registered Users";
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).getUsers().size() + "/" + movies.get(rowIndex).getMaxUsers();
            }
        });

        properties.add(new ColumnProperty() {
            @Override
            public String getName() {
                return "Register";
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).isRegistered(currentUser.getName());
            }
        });

        if (filterProperties == null) {
            return;
        }

        for (int i =0; i < properties.size(); i++) {
            if (filterProperties.contains(properties.get(i).getName())) {
                properties.remove(i);
                i--;
            }
        }

    }
    @Override
    public String getColumnName(int col) {
        return properties.get(col).getName();
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return properties.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return properties.get(columnIndex).getObject(rowIndex);
    }
}
