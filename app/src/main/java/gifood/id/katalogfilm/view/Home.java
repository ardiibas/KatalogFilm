package gifood.id.katalogfilm.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import gifood.id.katalogfilm.R;
import gifood.id.katalogfilm.fragment.FragmentHome;
import gifood.id.katalogfilm.fragment.FragmentSearch;
import gifood.id.katalogfilm.fragment.FragmentSetting;

public class Home extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        FragmentHome.OnFragmentInteractionListener,
        FragmentSearch.OnFragmentInteractionListener,
        FragmentSetting.OnFragmentInteractionListener {

    private BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, FragmentHome.newInstance());
        fragmentTransaction.commit();

        bottomNavigationMenu = findViewById(R.id.home_bottom_nav);
        bottomNavigationMenu.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_home:
                fragment = FragmentHome.newInstance();
                break;

            case R.id.menu_search:
                fragment = FragmentSearch.newInstance();
                break;

            case R.id.menu_setting:
                fragment = FragmentSetting.newInstance();
                break;
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.home_frame, fragment);
            fragmentTransaction.commit();
        }

        return true;
    }
}
