package info.androidhive.slidingmenu;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

	public RegisterFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.register, container, false);
		final EditText email = (EditText) rootView.findViewById(R.id.email);
		final EditText name = (EditText) rootView.findViewById(R.id.name);

		// Locate Buttons in main.xml
		Button button = (Button) rootView.findViewById(R.id.login);

		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				String emailtxt = email.getText().toString();
				String nametxt = name.getText().toString();

				ParseUser user = new ParseUser();
				user.setUsername(nametxt);
				user.setEmail(emailtxt);
				user.setPassword("");
				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						
						if (e == null) {
							// Show a simple Toast message upon successful
							// registration
							Toast.makeText(getActivity(), "Success",
									Toast.LENGTH_LONG).show();
							Fragment fragment = new HomeFragment();
							if (fragment != null) {
								FragmentManager fragmentManager = getFragmentManager();
								fragmentManager.beginTransaction()
										.replace(R.id.frame_container, fragment).commit();}
						} else {
							Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG)
									.show();
						}
					}
				});
			}
		});
		return rootView;
	}
}
