package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 

 
public class SingleItemView extends Fragment {
	// Declare Variables
	
	public SingleItemView() {
		
		}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.singleitemview, container,
				false);
		TextView  txtMain = (TextView) rootView.findViewById(R.id.name);
		
		
		TextView txtName = (TextView) rootView. findViewById(R.id.namelabel);
		txtName.setText(getArguments().getString("name"));
		txtMain.setText(getArguments().getString("text"));
		
		return rootView;
	
	}

}
