package gaulandm.table_model;

import gaulandm.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class SeeUsersDialog extends JDialog implements ActionListener {

    private final MovieTableModel model;
    private final int selectedMovie;

    public SeeUsersDialog(MovieTableModel model, int index, JFrame frame) {
        super(frame, true);

        this.model = model;
        this.selectedMovie = index;

        JButton back = new JButton("Back");
        back.setActionCommand("Back");
        back.addActionListener(this);

        add(back, BorderLayout.PAGE_END);

        init(index);
    }

    private void init(int index) {

        JPanel namePane = new JPanel(new GridLayout(0, 1));
        JPanel removePane = new JPanel(new GridLayout(0, 1));

        List<User> users = model.getUsers(index);
        int userSize = users.size();
        for (int i = 0; i < userSize; i++) {
            User user = users.get(i);
            JLabel label = new JLabel(user.getName());

            JButton button = new JButton("Remove");
            button.setActionCommand(Integer.toString(i));
            button.addActionListener(this);



            namePane.add(label);
            removePane.add(button);

            removePane.add(button);
        }


        add(namePane, BorderLayout.CENTER);
        add(removePane, BorderLayout.LINE_END);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Back")) {
            setVisible(false);
            return;
        }

        int userIndex = Integer.parseInt(e.getActionCommand());
        model.removeUser(selectedMovie, userIndex);
        init(selectedMovie);
        repaint();

        if (model.getUsers(selectedMovie).isEmpty()) {
            setVisible(false);
        }

    }
}
