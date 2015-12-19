package top.itmp.swpes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static String tmp = "";
    private static String lists = "";
    private String[] songs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                return new Fragment() {


                    @Override
                    public void onCreate(@Nullable Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                    }

                    @Nullable

                    @Override
                    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

                        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                        //textView.setVisibility(View.GONE);
                        final ListView listView = (ListView)rootView.findViewById(R.id.listView);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Snackbar.make(listView, songs[position], Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                Toast.makeText(getApplicationContext(), songs[position], Toast.LENGTH_SHORT).show();
                                Intent it = new Intent(Intent.ACTION_VIEW);
                                Uri uri = Uri.parse("file://" + songs[position]);
                                it.setDataAndType(uri, "audio/mp3");
                                startActivity(it);
                            }
                        });

                        new ScanTask(listView).execute();


                        Button button = (Button)rootView.findViewById(R.id.button);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayAdapter newadapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, getArray());
                                listView.setAdapter(newadapter);
                            }
                        });


                       // FileIndexer.index_file(textView, new File(Environment.getExternalStorageDirectory().toString()+"/BaiduNetdisk"), "mp3",0);
                        return rootView;
                    }
                };
                case 1:
                case 2:
                    return PlaceholderFragment.newInstance(position + 1);
                default:
                    return null;
            }

        }


        private List<String> getData(){
                List<String> data = new ArrayList<String>();
                for(int i = 0;i <20;i++) {
                    data.add(i+"");
                }
            return data;
            }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
    public class ScanTask extends AsyncTask<String, Integer, String> {
        private ListView v;
        int fls = 0;
        int dirs = 0;
        ProgressDialog progressDialog;

        public ScanTask() {}

        public ScanTask(ListView view){
            v = view;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("扫描音乐文件中");
            progressDialog.setMessage("正在扫描中, 请等待....");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            // Log.d("pre", length + "");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置进度条风格
            progressDialog.setIndeterminate(true); // set the indeterminate for true  cause it will be downloaded so soon
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            songs = s.split("\n");
            /*for(int i = 0; i < array.length; i++)
            Log.v("scanarray", array[i]);
            if(s != null) {
                v.append(s);
               // v.append("\n\n\n\n");
                Log.d("scan", s);
            }
            else
                ;
                */
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1,getArray());
            v.setAdapter(arrayAdapter);
        }

        @Override
        protected String doInBackground(String... params) {
            File file = new File(Environment.getExternalStorageDirectory().toString());
            int i = index_file( new File(Environment.getExternalStorageDirectory().toString()), "mp3", 0);
            Log.d("scantmp", tmp + " " + i);
            return lists + "";

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //progressDialog.setProgress(values[0]);
            progressDialog.setMessage("已扫描了" + values[0] + "音乐");
        }
        public int index_file(File file, String extension, int level){
            if(file.isHidden()){;}
            // Log.d("scaned", file.getName().substring(file.getName().lastIndexOf(".")+1));
            if(file.isFile() && file.getName().substring(file.getName().lastIndexOf(".")+1).equals("mp3")){
                fls++;
                //System.out.println("└──" + file.getName());
                // Log.d("scaned", file.getName().substring(file.getName().lastIndexOf(".") + 1));
                Log.d("scaned", file.getAbsolutePath());
                //view.append(file.getAbsolutePath() + "\n");
                //scaned = scaned + file.getAbsolutePath() + "\n";
                tmp += file.getName() + "\n";
                lists += file.getAbsolutePath() + "\n";
                Log.v("scanprogs", fls + "");
                publishProgress(fls);
                return fls;
            }
            if(file.isDirectory() && file.exists()){
                dirs++;
                File[] files = file.listFiles();
                if(level != 0 && files != null){
                    //System.out.println("├──" + file.getName());
                }
            /*
            if(files.length > 0 && level >= levels ){
                levels = level + 1;
            }*/


                for (int i = 0; i < files.length; i++){
                    index_file(files[i], extension, level + 1);
                }
            }
            return fls;
        }


        }
    public List<String> getArray(){
        String[] array = tmp.split("\n");
        List<String> list = Arrays.asList(array);
        return list;
    }

}
