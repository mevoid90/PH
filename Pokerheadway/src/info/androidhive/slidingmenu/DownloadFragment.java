package info.androidhive.slidingmenu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DownloadFragment extends Fragment {

	public DownloadFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_community,
				container, false);
		// declare the dialog as a member field of your activity
		final DownloadDataTask downloadTask = new DownloadDataTask(
				getActivity());

		// instantiate it within the onCreate method
		Button myBtn = (Button) rootView.findViewById(R.id.button1);
		Button myBtn2 = (Button) rootView.findViewById(R.id.button2);
		Button myBtn3 = (Button) rootView.findViewById(R.id.button3);
		Button myBtn4 = (Button) rootView.findViewById(R.id.button4);
		Button myBtn5 = (Button) rootView.findViewById(R.id.button5);
		Button myBtn6 = (Button) rootView.findViewById(R.id.button6);
		Button myBtn7 = (Button) rootView.findViewById(R.id.button7);
		Button myBtn8 = (Button) rootView.findViewById(R.id.button8);
		Button myBtn9 = (Button) rootView.findViewById(R.id.button9);
		Button myBtn10 = (Button) rootView.findViewById(R.id.button10);
		
		
		Check(myBtn, myBtn2, "PokerHeadway_Books", "/PokerMentalgame.pdf");
		Check(myBtn3, myBtn4, "PokerHeadway_Books",
				"/otnesites-k-pokeru-kak-k-biznesu.pdf");
		Check(myBtn5, myBtn6, "PokerHeadway_Books",
				"/Sklansky_The_Theory_of_Poker.pdf");
		Check(myBtn7, myBtn8, "PokerHeadway_Books",
				"/Applications_of_NoLimit_Holdem.pdf");
		Check(myBtn9, myBtn10, "PokerHeadway_Books", "/tournament-poker.pdf");

		OnClickListener Btn = new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.button1:
					downloadTask.execute(
							"http://bum.biz.ua/books/PokerMentalgame.pdf",
							"PokerHeadway_Books", "/PokerMentalgame.pdf");
					break;
				case R.id.button2:
					Open("PokerHeadway_Books", "/PokerMentalgame.pdf");
					break;
				case R.id.button3:
					downloadTask
							.execute(
									"http://bum.biz.ua/books/otnesites-k-pokeru-kak-k-biznesu.pdf",
									"PokerHeadway_Books",
									"/otnesites-k-pokeru-kak-k-biznesu.pdf");
					break;
				case R.id.button4:
					Open("PokerHeadway_Books",
							"/otnesites-k-pokeru-kak-k-biznesu.pdf");
					break;
				case R.id.button5:
					downloadTask
							.execute(
									"http://bum.biz.ua/books/Sklansky_The_Theory_of_Poker.pdf",
									"PokerHeadway_Books",
									"/Sklansky_The_Theory_of_Poker.pdf");
					break;
				case R.id.button6:
					Open("PokerHeadway_Books",
							"/Sklansky_The_Theory_of_Poker.pdf");
					break;
				case R.id.button7:
					downloadTask
							.execute(
									"http://bum.biz.ua/books/Applications_of_NoLimit_Holdem.pdf",
									"PokerHeadway_Books",
									"/Applications_of_NoLimit_Holdem.pdf");
					break;
				case R.id.button8:
					Open("PokerHeadway_Books",
							"/Applications_of_NoLimit_Holdem.pdf");
					break;
				case R.id.button9:
					downloadTask.execute(
							"http://bum.biz.ua/books/tournament-poker.pdf",
							"PokerHeadway_Books", "/tournament-poker.pdf");
					break;
				case R.id.button10:
					Open("PokerHeadway_Books", "/tournament-poker.pdf");
					break;
				}

			}
		};
		myBtn.setOnClickListener(Btn);
		myBtn2.setOnClickListener(Btn);
		myBtn3.setOnClickListener(Btn);
		myBtn4.setOnClickListener(Btn);
		myBtn5.setOnClickListener(Btn);
		myBtn6.setOnClickListener(Btn);
		myBtn7.setOnClickListener(Btn);
		myBtn8.setOnClickListener(Btn);
		myBtn9.setOnClickListener(Btn);
		myBtn10.setOnClickListener(Btn);

		return rootView;
	}

	public void Check(Button mp, Button mp2, String s, String v) {
		File externalStorageDir = Environment.getExternalStorageDirectory();
		File playNumbersDir = new File(externalStorageDir, s);
		File myFile = new File(playNumbersDir, v);

		if (!myFile.exists()) {

			mp.setEnabled(true);
			mp2.setEnabled(false);
		} else {
			mp.setEnabled(false);
			mp2.setEnabled(true);

		}

	}

	public void Open(String s, String v) {
		File externalStorageDir = Environment.getExternalStorageDirectory();
		File playNumbersDir = new File(externalStorageDir, s);
		File file = new File(playNumbersDir, v);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	public class Wrapper {
		public String folder;
		public String name;
		public String error;
	}

	public class DownloadDataTask extends AsyncTask<String, Integer, Wrapper> {

		private Context context;
		private PowerManager.WakeLock mWakeLock;
		ProgressDialog mProgressDialog;

		public DownloadDataTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setMessage("A message");
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(true);
			// take CPU lock to prevent CPU from going off if the user
			// presses the power button during download
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
					getClass().getName());
			mWakeLock.acquire();
			mProgressDialog.show();
		}

		@Override
		protected Wrapper doInBackground(String... sUrl) {
			InputStream input = null;
			OutputStream output = null;
			HttpURLConnection connection = null;

			Wrapper w = new Wrapper();

			String name = w.folder = sUrl[1];
			String name1 = w.name = sUrl[2];

			try {
				URL url = new URL(sUrl[0]);
				File externalStorageDir = Environment
						.getExternalStorageDirectory();
				File playNumbersDir = new File(externalStorageDir, name);
				File myFile = new File(playNumbersDir, name1);

				if (!playNumbersDir.exists()) {
					playNumbersDir.mkdirs();
				}
				if (!myFile.exists()) {
					myFile.createNewFile();
				}

				connection = (HttpURLConnection) url.openConnection();
				connection.connect();

				// expect HTTP 200 OK, so we don't mistakenly save error report
				// instead of the file
				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					w.error = "Server returned HTTP "
							+ connection.getResponseCode() + " "
							+ connection.getResponseMessage();
				}

				// this will be useful to display download percentage
				// might be -1: server did not report the length
				int fileLength = connection.getContentLength();

				// download the file
				input = connection.getInputStream();
				// output = new FileOutputStream("/sdcard/file_name.extension");
				output = new FileOutputStream(myFile);
				byte data[] = new byte[4096];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					// allow canceling with back button
					if (isCancelled()) {
						input.close();
						return null;
					}
					total += count;
					// publishing the progress....
					if (fileLength > 0) // only if total length is known
						publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}
			} catch (Exception e) {
				w.error = e.toString();
			} finally {
				try {
					if (output != null)
						output.close();
					if (input != null)
						input.close();
				} catch (IOException ignored) {
				}

				if (connection != null)
					connection.disconnect();
			}

			return w;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
			// if we get here, length is known, now set indeterminate to false
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMax(100);
			mProgressDialog.setProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(Wrapper result) {
			mWakeLock.release();
			mProgressDialog.dismiss();
			if (result.error != null) {
				Toast.makeText(context, "Download error: " + result.error,
						Toast.LENGTH_LONG).show();
				File externalStorageDir = Environment
						.getExternalStorageDirectory();
				File playNumbersDir = new File(externalStorageDir, result.folder);
				File myFile = new File(playNumbersDir, result.name);

				
				if (myFile.exists()) {
					myFile.delete();
				}
				Fragment fragment = new DownloadFragment();
				if (fragment != null) {
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();
				}
			} else {
				Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT)
						.show();

				Fragment fragment = new DownloadFragment();
				if (fragment != null) {
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();
				}
			}
		}

	}
}
