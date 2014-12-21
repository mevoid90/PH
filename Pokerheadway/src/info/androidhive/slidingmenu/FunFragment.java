package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.adapter.ListViewAdapter;
import info.androidhive.slidingmenu.model.Pictures;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class FunFragment extends Fragment {

	public FunFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.listview_main, container,
				false);
		new FunTask().execute();
		return rootView;
	}

	public class FunTask extends AsyncTask<Void, Void, Void> {
		ListView listview;
		List<ParseObject> ob;
		ProgressDialog mProgressDialog;
		ListViewAdapter adapter;
		List<Pictures> listUrl = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// Create a progressdialog
			mProgressDialog = new ProgressDialog(getActivity());
			// Set progressdialog title
			mProgressDialog.setTitle("Poker Funny Pictures");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			listUrl = new ArrayList<Pictures>();
			try {

				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"Fun");
				query.orderByDescending("createdAt");

				try {
					ob = query.find();
				} catch (com.parse.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try{
				for (ParseObject num : ob) {
					Pictures map = new Pictures();
					map.setNum((String) num.get("url"));
					listUrl.add(map);
				}}
				catch(Exception e){
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Error")
							.setMessage("Can't load the needed data")
							.setIcon(R.drawable.ic_launcher_)
							.setCancelable(false)
							.setPositiveButton("Œ ",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int id) {
											dialog.cancel();
						
										}
									});
					AlertDialog alert = builder.create();
					alert.show();
					
					
				}
			} catch (ParseException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			listview = (ListView) getView().findViewById(R.id.listview);
			adapter = new ListViewAdapter(getActivity(), listUrl);
			listview.setAdapter(adapter);
			mProgressDialog.dismiss();

		}
	}

}
