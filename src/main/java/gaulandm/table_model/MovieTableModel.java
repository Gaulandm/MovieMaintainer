package gaulandm.table_model;


import gaulandm.Movie;
import gaulandm.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.NumberFormatter;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MovieTableModel extends AbstractTableModel {

    private List<ColumnProperty> properties;
    private List<Movie> movies;
    private List<JButton> buttons;
    private List<String> filterProperties;
    private final User currentUser;

    private int registeredUsersColumn = -1;

    public MovieTableModel(List<Movie> movies, List<String> filterProperties, User currentUser) {
        this.movies = movies != null ? movies : new ArrayList<>();
        this.properties = new ArrayList<>();
        this.buttons = new ArrayList<>();
        this.filterProperties = filterProperties;
        this.currentUser = currentUser;

        init();



    }

    private void init() {
        properties.add(new ColumnProperty() {
            @Override
            public String getName() {
                return "Title";
            }

            @Override
            public JFormattedTextField isUpdatable() {
                DefaultFormatter format = new DefaultFormatter();
                format.setOverwriteMode(false);

                return new JFormattedTextField(format);
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).getTitle();
            }

            @Override
            public void setObject(Object value, int rowIndex) {
                Objects.requireNonNull(movies).get(rowIndex).setTitle((String)value);
            }
        });

        properties.add(new ColumnProperty() {
            @Override
            public String getName() {
                return "Room";
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).getRoom();
            }

            @Override
            public JFormattedTextField isUpdatable() {
                DefaultFormatter format = new DefaultFormatter();
                format.setOverwriteMode(false);

                return new JFormattedTextField(format);
            }

            @Override
            public void setObject(Object value, int rowIndex) {
                Objects.requireNonNull(movies).get(rowIndex).setRoom((String)value);
            }
        });

        properties.add(new ColumnProperty() {

            @Override
            public String getName() {
                return "Date";
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).getDate();
            }

            @Override
            public JFormattedTextField isUpdatable() {
                DateFormat format = DateFormat.getDateInstance();

                return new JFormattedTextField(format);
            }

            @Override
            public void setObject(Object value, int rowIndex) {
                Objects.requireNonNull(movies).get(rowIndex).setDate((Date)value);
            }
        });

        properties.add(new ColumnProperty() {
            @Override
            public String getName() {
                return "RegisteredUsers";
            }

            @Override
            public JFormattedTextField isUpdatable() {
                NumberFormatter formatter = new NumberFormatter(NumberFormat.getNumberInstance());
                formatter.setValueClass(Integer.class);
                formatter.setOverwriteMode(false);

                return new JFormattedTextField(formatter);
            }

            @Override
            public Object getDefaultFieldValue(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).getMaxUsers();
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).getUsers().size() +
                        "/" + movies.get(rowIndex).getMaxUsers();
            }

            @Override
            public void setObject(Object value, int rowIndex) {
                Objects.requireNonNull(movies).get(rowIndex).setMax((int)value);
            }
        });

        properties.add(new ColumnProperty() {

            @Override
            public boolean isEditable() {
                return true;
            }

            @Override
            public String getName() {
                return "Register";
            }

            @Override
            public Object getObject(int rowIndex) {
                return Objects.requireNonNull(movies).get(rowIndex).isRegistered(currentUser.getName());
            }

            @Override
            public void setObject(Object value, int rowIndex) {
                if (!(boolean)value) {
                    Objects.requireNonNull(movies).get(rowIndex).removeUser(currentUser.getName());
                }
                else {
                    Objects.requireNonNull(movies).get(rowIndex).addUser(currentUser.getName());
                }
                if (registeredUsersColumn != -1) {
                    fireTableCellUpdated(rowIndex, registeredUsersColumn);
                }
            }
        });

        properties.add(new ColumnProperty() {

            @Override
            public boolean isEditable() {
                return true;
            }

            @Override
            public String getName() {
                return "Update";
            }

            @Override
            public Object getObject(int rowIndex) {
                return getButton(getName() + " " + rowIndex);
            }
        });

        initButton(properties.getLast().getName());

        properties.add(new ColumnProperty() {
            @Override
            public boolean isEditable() {
                return true;
            }

            @Override
            public String getName() {
                return "SeeUsers";
            }

            @Override
            public Object getObject(int rowIndex) {
                return getButton(getName()  + " " + rowIndex);
            }
        });

        initButton(properties.getLast().getName());

        properties.add(new ColumnProperty() {
            @Override
            public boolean isEditable() {
                return true;
            }

            @Override
            public String getName() {
                return "Remove";
            }

            @Override
            public Object getObject(int rowIndex) {
                return getButton(getName()  + " " + rowIndex);
            }
        });

        initButton(properties.getLast().getName());


        if (filterProperties != null) {
            for (int i = 0; i < properties.size(); i++) {
                if (filterProperties.contains(properties.get(i).getName())) {
                    properties.remove(i);
                    i--;
                }
            }
        }


        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getName().equals("RegisteredUsers")) {
                registeredUsersColumn = i;
            }
        }
    }

    public int createEmptyMovie() {
        movies.add(new Movie());
        addButton();

        fireTableDataChanged();

        return movies.size()-1;
    }

    private List<String> getButtonNames() {
        if (isButtonsNotValid()) {
            return null;
        }

        List<String> list = new ArrayList<>();

        int i = 0;
        while (i < buttons.size()) {
            list.add((buttons.get(i).getText()));
            i += movies.size()-1;
        }


        return list;
    }

    public void setFilter(List<String> filterProperties) {

        this.properties = new ArrayList<>();
        this.buttons = new ArrayList<>();

        this.filterProperties = filterProperties;

        init();

        fireTableStructureChanged();
    }

    private void addButton() {

        List<String> names = getButtonNames();
        if (names == null) {
            return;
        }
        buttons = new ArrayList<>();

        for (String name : names) {
            initButton(name);
        }
    }

    public void removeMovie(int index) {
        movies.remove(index);
        fireTableDataChanged();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }

    private void initButton(String name) {
        if (filterProperties != null && filterProperties.contains(name))
            return;

        for (int i = 0; i < movies.size(); i++) {
            JButton button = new JButton(name);
            button.setActionCommand(name + " " + i);
            buttons.add(button);
        }
    }

    private boolean isButtonsNotValid() {
        return buttons == null || buttons.isEmpty();
    }

    private JButton getButton(String fullName) {
        if (isButtonsNotValid()) {
            return null;
        }

        int i = 0;
        while (i < buttons.size() && buttons.get(i).getName() != null && !fullName.contains(buttons.get(i).getName())) {
            i += movies.size();
        }

        for (int j = i; j < buttons.size(); j++) {
            if (buttons.get(j).getActionCommand().equals(fullName)) {
                return buttons.get(j);
            }
        }
        return null;
    }

    public List<User> getUsers(int index) {
        return movies.get(index).getUsers();
    }

    public void removeUser(int movieIndex, int userIndex) {
        movies.get(movieIndex).removeUser(userIndex);
        fireTableDataChanged();
    }

    public List<ColumnProperty> getProperties() {
        return properties;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return properties.get(col).isEditable();
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return getValueAt(0, col).getClass();
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

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        properties.get(columnIndex).setObject(value, rowIndex);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
