
package comguess.example.android.vlabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
ArrayAdapter<String > adapter;
    ListView list;
    TextView footer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        list = (ListView)findViewById(R.id.listview);
        footer = (TextView) findViewById(R.id.footer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<String> country = new ArrayList<>();
        country.add("India");
        country.add("India");
        country.add("India");
        country.add("India");
        country.add("India");
        country.add("India");
        country.add("India");
        country.add("usa");
        country.add("India");
        country.add("India");
        country.add("India");
        country.add("malesiya");
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, country);
        list.setAdapter(adapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_search,menu);

        MenuItem item = menu.findItem(R.id.menuSearch);

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override

            public boolean onQueryTextSubmit(String query) {


                return false;

            }

            @Override

            public boolean onQueryTextChange(String newText) {


                adapter.getFilter().filter(newText);


                return false;

            }

        });

        return super.onCreateOptionsMenu(menu);

    }


}