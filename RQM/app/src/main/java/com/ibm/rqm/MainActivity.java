package com.ibm.rqm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ProjectListFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private SharedPreferences mPrefs;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();

    }


    //用来初始化。
    private void init() {

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        //获取当前Activity的标题。
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    //点击导航后，开始将fragment替换成对应的fragment.
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        //更新主界面的内容， 通过替换一个framgent, 其中R.id.container为默认的fragment，用来在主界面占位置
        //替换的为PlaceholderFragment产生的新实例
        //在这里，我们 可以通过switch语句来重写：

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, createCorrespondingFragement(position))
                .commit();
    }

    public Fragment createCorrespondingFragement(int sectionNum){

        Fragment fragment = null;
        switch (sectionNum){
            case 1:
                fragment = MainFragment.newInstance(sectionNum);
                break;

            case 3:
                fragment = ProjectListFragment.newInstance("par1", "par2");
                break;
            default:
                fragment = MainFragment.newInstance(sectionNum);
                break;
        }
        return  fragment;
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
            case 3:
                mTitle = getString(R.string.title_project_switch);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //判断点击的按钮
        if (id == R.id.action_settings) {
            //点击的是设置按钮，则转换到设置界面
            Intent intent = new Intent();
            intent.setClass(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_example){
            //点击的action。

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(String id) {

    }
}
