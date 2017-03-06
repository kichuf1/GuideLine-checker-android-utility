package comguess.example.android.vlabs;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SearchRecentSuggestionsProvider;
import android.widget.Toast;


import java.util.ArrayList;

import static android.R.attr.country;
import static comguess.example.android.vlabs.R.id.footer;

public class SearchWithScrollable extends AppCompatActivity {
    ArrayAdapter<String > adapter;
    ListView list;
    Button footer;
    Database_Helper db;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_with_scrollable);
        list = (ListView)findViewById(R.id.listView);
        footer = (Button)findViewById(R.id.footer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new Database_Helper(this);
        ArrayList<String> country = new ArrayList<>();
        ArrayList<String> listWithHistory = new ArrayList<>();
        listWithHistory.add("---------------History-------------");
        country.add("India");
        country.add("Argentina");
        country.add("Australia");
        country.add("Brazil");
        country.add("West Indies");
        country.add("South Africa");
        country.add("Germany");
        country.add("usa");
        country.add("China");
        country.add("Green Land");
        country.add("South America");
        country.add("New Zealand");
        country.add("FinLand");
        country.add("Mysore");
        country.add("Bangladesh");
        country.add("Austria");
        country.add("Armenia");
        country.add("Andorra");
        country.add("Bahamas");
        country.add("Cambodia");
        country.add("Canada");
        country.add("Egypt");
        country.add("Ethiopia");
        country.add("Latvin");
        country.add("Macau");
        country.add("Romania");



        res = db.getSearches();
        while(res.moveToNext()){
            Toast.makeText(this,res.getString(0),Toast.LENGTH_LONG).show();
            Toast.makeText(this,res.getString(1),Toast.LENGTH_LONG).show();
            Toast.makeText(this,res.getString(2),Toast.LENGTH_LONG).show();
            listWithHistory.add(res.getString(0));
        }
        listWithHistory.add("---------------End of History-------------");
        for(int i=0;i<country.size();i++){
            listWithHistory.add(country.get(i));
        }

        if(listWithHistory.size()>2){
            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, country);
            list.setAdapter(adapter);
        }else{
            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listWithHistory);
            list.setAdapter(adapter);
        }

        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (list.getLastVisiblePosition() - list.getHeaderViewsCount() -
                        list.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                    // Now your listview has hit the bottom
                    footer.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // Perform action on click
                            list.setSelection(0);
                        }
                    });



                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                Object item = arg0.getItemAtPosition(arg2);
                String str = item.toString();
                db.insertSearch(str);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

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
                db.insertSearch(newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
