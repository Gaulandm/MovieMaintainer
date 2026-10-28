package gaulandm;

import java.util.List;

public interface ColumnProperty {

    String getName();

    Object getObject(List<Movie> movies, int rowIndex);
}
