package com.ibm.rqm;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

















/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private static final String ARG_SECTION_NUM = "section_num";


    private int sectionNum;

    private OnFragmentInteractionListener mListener;

    private MainActivity myActivity;

    private Button pieBtn = null;
    private Button barBtn = null;



    public void draw(String[] titles,ArrayList<double[]> values)
    {

    }


























    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNum Parameter 1.
     *
     * @return A new instance of fragment MainFragment.
     */



    public static MainFragment newInstance(int sectionNum) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUM, sectionNum);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionNum = getArguments().getInt(ARG_SECTION_NUM);
        }


        String[] a={"amazing","good"};
        double[] b={123,345,456,123};
        double[] data={20, 30, 40 };


        BarChartBuilder BarChart=new BarChartBuilder();
        PieChartBuilder PieChart=new PieChartBuilder();

        BarChart.put2bar(a, b);
        PieChart.put2pie(data);



    }

    private final class PieBtn implements OnClickListener {
        @Override
        public void onClick(View arg0) {

            Intent intent = new Intent();
            intent.setClass(myActivity, PieChartBuilder.class);
            startActivity(intent);
//            MainActivity.this.finish();
        }

    }

    private final class BarBtn implements OnClickListener {
        @Override
        public void onClick(View arg0) {

            Intent intent = new Intent();
            intent.setClass(myActivity, BarChartBuilder.class);
            startActivity(intent);
//            MainActivity.this.finish();
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        pieBtn = (Button) rootView.findViewById(R.id.pieBtn);
        pieBtn.setOnClickListener(new PieBtn());
        barBtn = (Button) rootView.findViewById(R.id.barBtn);
        barBtn.setOnClickListener(new BarBtn());
        return rootView;
    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //还没用到listener, 且MainActivity还没实现这个接口。
            //  mListener = (OnFragmentInteractionListener) activity;

            //在attach主界面的时候，告诉主界面设置相应的标题。

            myActivity = ((MainActivity) activity);
            myActivity.onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUM));

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
