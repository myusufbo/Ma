package com.yusufboss.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import fragments.MotherPageFragment;


public class ChildHealth extends ActionBarActivity {
    TextView tv;
    Drawer.Result result;
    AccountHeader.Result headerResult;
    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private static final int Num_Pages=5;

    @Override
    protected void onPause() {

        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_health);
       // tv=(TextView)findViewById(R.id.textview1);
        //tv.setText("sdada");
        initDrawer(savedInstanceState);
        mPager=(ViewPager)findViewById(R.id.childContainer);

        mAdapter=new PageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class PageAdapter extends FragmentStatePagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MotherPageFragment();
        }

        @Override
        public int getCount() {
            return Num_Pages;
        }
    }



    private void initDrawer(Bundle savedInstanceState) {

        final Toolbar toolbar = (Toolbar) findViewById(R.id.childrenHealthToolbar);
        setSupportActionBar(toolbar);

        headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.babyheader)
                /*.addProfiles(
                        new ProfileDrawerItem().withName("Yusuf").withEmail("yusufboss420@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile)),
                        new ProfileDrawerItem().withName("Srabon").withEmail("kazisrabon@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })*/
                .build();

        // Handle Toolbar
        result = new Drawer()
                .withActivity(this)
                .withHeader(R.layout.header)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                        //.withTranslucentStatusBar(false)
                        //.withActionBarDrawerToggle(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(0),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_health_calculator).withIcon(FontAwesome.Icon.faw_calculator).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_mother_health_info).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_child_info).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_hospital).withIcon(FontAwesome.Icon.faw_hospital_o).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_Emergency_contact).withIcon(FontAwesome.Icon.faw_ambulance).withIdentifier(5),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_vaccenation).withIcon(FontAwesome.Icon.faw_venus).withIdentifier(6)


                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
                            //toolbar.setTitle("Home");
                            Fragment fragment = null;
                            if (drawerItem.getIdentifier() == 0) {
                                //fragment = new Calculator();
                                startActivity(new Intent(ChildHealth.this, MainActivity.class));
                                //Toast.makeText(getBaseContext(),"On Drawer Created",Toast.LENGTH_LONG).show();

                            } else if (drawerItem.getIdentifier() == 1) {
                                //fragment = new Calculator();
                                startActivity(new Intent(ChildHealth.this,HealthCalculator.class));


                            } else if (drawerItem.getIdentifier() == 2) {

                                startActivity(new Intent(ChildHealth.this, MotherHealth.class));
                            }
                            else if (drawerItem.getIdentifier() == 3) {


                            } else if (drawerItem.getIdentifier() == 4) {
                                startActivity(new Intent(ChildHealth.this, Hospital.class));
                            } else if (drawerItem.getIdentifier() == 5) {
                                startActivity(new Intent(ChildHealth.this, EmergencyContact.class));
                            }
                            else if (drawerItem.getIdentifier() == 6) {
                                startActivity(new Intent(ChildHealth.this, Vaccination.class));
                            }
                            if (fragment != null) {
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, fragment)
                                        .commit();
                            }

                        }
                    }
                })
                .withFireOnInitialOnClick(true)
                .withSelectedItem(3)
                .build();
        //result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_child_health, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
