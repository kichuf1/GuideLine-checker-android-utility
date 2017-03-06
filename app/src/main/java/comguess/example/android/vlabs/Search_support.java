package comguess.example.android.vlabs;

/**
 * Created by vleadvlabs on 16/2/17.
 */
import android.content.SearchRecentSuggestionsProvider;
public class Search_support  extends SearchRecentSuggestionsProvider{
    public static final String AUTHORITY =
            Search_support.class.getName();

    public static final int MODE = DATABASE_MODE_QUERIES;

    public Search_support() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
