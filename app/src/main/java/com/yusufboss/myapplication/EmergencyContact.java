package com.yusufboss.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class EmergencyContact extends ActionBarActivity {
    Drawer.Result result;
    AccountHeader.Result headerResult;
    ArrayList<String> phones= new ArrayList<String>();
    ArrayList<CharSequence> names= new ArrayList<>();
    ArrayList<CharSequence> contacts= new ArrayList<>();
    String contact;
//    private HashSet<String> phones;
    //AddFloatingActionButton addFloatingActionButton;
    //ListView listView;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        initDrawer(savedInstanceState);


    }
//    List<String> mItems = new ArrayList<String>();
    public void readContacts() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);


        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    Log.e("Friends" , name + " " + id);
                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phone = phone.replace("-", "");
                        phone = phone.replace(" ", "");
                        phone = phone.replace("+88", "");

                       phones.add(phone);
                         //mine
                       names.add(name);
                      // mItems.add(phone);
                       // contact = new Contact(name,phone);  //mine
                        //contacts.add(contact);  //mine
                        //Log.e("phone&Name", name + " " + phone);
//                        Toast.makeText(this,phone,Toast.LENGTH_LONG).show();
                    }
                    pCur.close();
//                    int m;
//                    m=phones.size();
//                    Log.e("ksj",m+"");
                }
            }
        }
    }

    private void initDrawer(Bundle savedInstanceState) {

        final Toolbar toolbar = (Toolbar) findViewById(R.id.emergencyToolbar);
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
                                startActivity(new Intent(EmergencyContact.this,MainActivity.class));
                                //Toast.makeText(getBaseContext(),"On Drawer Created",Toast.LENGTH_LONG).show();

                            } else if (drawerItem.getIdentifier() == 1) {
                                //fragment = new Calculator();
                                startActivity(new Intent(EmergencyContact.this,HealthCalculator.class));


                            } else if (drawerItem.getIdentifier() == 2) {

                                startActivity(new Intent(EmergencyContact.this, MotherHealth.class));
                            }
                            else if (drawerItem.getIdentifier() == 3) {

                                startActivity(new Intent(EmergencyContact.this, ChildHealth.class));
                            } else if (drawerItem.getIdentifier() == 4) {
                                startActivity(new Intent(EmergencyContact.this, Hospital.class));
                            } else if (drawerItem.getIdentifier() == 5) {
                                //startActivity(new Intent(MainActivity.this, EmergencyContact.class));
                            }
                            else if (drawerItem.getIdentifier() == 6) {
                                startActivity(new Intent(EmergencyContact.this, Vaccination.class));
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
                .withSelectedItem(5)
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
        getMenuInflater().inflate(R.menu.menu_emergency_contact, menu);
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
        else if (id==R.id.action_add){
            readContacts();


            showMultiChoiceListAlertDialog();
            //Toast.makeText(getBaseContext(),"Added", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog.Builder createAlertDialogBuilder() {
//        if (mTheme == NATIVE_THEME) {
        return new AlertDialog.Builder(EmergencyContact.this);
//        }

//        return new AlertDialogPro.Builder(this, mTheme);
    }
    final  ArrayList<CharSequence> mCheckedItems = new ArrayList();
    private void showMultiChoiceListAlertDialog() {
        final String[] list= new String[]{"a","b","c"};
        final CharSequence[] cs= names.toArray(new CharSequence[names.size()]);

//        List<String> mCheckedItems = new ArrayList<String>();

//        for(int i=0;i<)


        createAlertDialogBuilder()
                .setTitle("Contacts")
                .setMultiChoiceItems(cs,null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    mCheckedItems.add(cs[which]);
                                }
                                else if(mCheckedItems.contains(which)){
                                    //mCheckedItems.remove(phones[which]);
                                    mCheckedItems.remove(Integer.valueOf(which));
                                }

                                 /*else {
                                    mCheckedItems.remove(list[which]);
                                }*/
                                showToast(
                                        cs[which] + " is "
                                                + (isChecked ? "checked" : "unchecked" + ".")
                                );
                            }
                        })
                .setNeutralButton("More info", /*new ButtonClickedListener("More info")*/null)
                .setNegativeButton("Cancel",null)
                .setPositiveButton(
                        "Choose",

                        /*new ButtonClickedListener("Choose " + mCheckedItems.toString()*/null)

                .show();
                /*.setTitle("Contacts")
                .setMessage("Hello World")
                .setPositiveButton("Ok",null)
                .setNegativeButton("Cancel",null)
                .show();
*/
    }
    private Toast mToast = null;

    private void showToast(CharSequence toastText) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);
        mToast.show();
    }

}
