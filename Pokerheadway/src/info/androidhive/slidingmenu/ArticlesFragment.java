package info.androidhive.slidingmenu;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ArticlesFragment extends Fragment {

	public ArticlesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.listview_main, container,
				false);
		new RemoteDataTask().execute();

		return rootView;
	}

	public class RemoteDataTask extends AsyncTask<Void, Void, Void> {

		List<ParseObject> object;
		ProgressDialog mProgressDialog;
		ArrayAdapter<String> adapter;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(getActivity());
			// Set progressdialog title
			mProgressDialog.setTitle("PokerHeadway");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Locate the class table named "Country" in Parse.com
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"Country");
			query.orderByDescending("_created_at");
			try {
				object = query.find();
			} catch (ParseException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			ListView listview = (ListView) getView()
					.findViewById(R.id.listview);

			adapter = new ArrayAdapter<String>(getActivity(),
					R.layout.listview_item);
			// Retrieve object "name" from Parse.com database
			try {
				for (ParseObject country : object) {
					adapter.add((String) country.get("name"));
				}
			} catch (Exception e) {
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
										if (ArticlesFragment.this.isOnline() == true) {
											FragmentManager fragmentManager = getFragmentManager();
											fragmentManager
													.beginTransaction()
													.replace(
															R.id.frame_container,
															new ArticlesFragment())
													.commit();
										} else {
												FragmentManager fragmentManager = getFragmentManager();
												fragmentManager
														.beginTransaction()
														.replace(
																R.id.frame_container,
																new HomeFragment())
														.commit();
						
										}
									}
								});
				AlertDialog alert = builder.create();
				alert.show();

			}
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
			// Capture button clicks on ListView items
			listview.setOnItemClickListener(new OnItemClickListener() {
				Fragment fragment = new SingleItemView();

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Bundle args = new Bundle();
					args.putString("name",
							object.get(position).getString("name").toString());
					args.putString("text",
							object.get(position).getString("text").toString());
					fragment = new SingleItemView();
					fragment.setArguments(args);
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();

				}
			});
		}
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(getActivity().CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cm.getActiveNetworkInfo();
		if (nInfo != null && nInfo.isConnected()) {
		
			return true;
		} else {
		
			return false;
		}
	}
}
