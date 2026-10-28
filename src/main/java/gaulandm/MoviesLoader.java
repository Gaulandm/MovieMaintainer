package gaulandm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesLoader {

    private static final String path = "C:\\Users\\gaula\\IdeaProjects\\" +
            "MovieMaintainer\\src\\main\\resources\\movies.txt";

    public List<Movie> load() throws IOException, ClassNotFoundException {
        List<Movie> movies = new ArrayList<>();

        if (!new File(path).exists()) {
            init();
        }

        ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));

        int size = is.readInt();

        for (int i = 0; i < size; i++) {
            movies.add((Movie)is.readObject());
        }

        is.close();

        return movies;
    }

    private void init() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));

        os.writeInt(0);

        os.close();
    }

    public void save(List<Movie> movies) throws IOException {

        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));

        os.writeInt(movies.size());

        for (Movie movie : movies) {
            os.writeObject(movie);
        }

        os.close();
    }


}
