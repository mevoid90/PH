package info.androidhive.slidingmenu.adapter;

import info.androidhive.slidingmenu.ImageLoader;
import info.androidhive.slidingmenu.model.Pictures;
import info.androidhive.slidingmenu.R;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	Context mContext;
	LayoutInflater inflater;
	private List<Pictures> listUrl = null;
	private ArrayList<Pictures> arraylist;
	protected int count;
	ImageLoader imageLoader;

	public ListViewAdapter(Context context, List<Pictures> numberlist) {
		mContext = context;
		this.listUrl = numberlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<Pictures>();
		this.arraylist.addAll(numberlist);
		imageLoader = new ImageLoader(context);
	}

	public class ViewHolder {
		TextView num;
		ImageView imageView1;

	}

	@Override
	public int getCount() {
		return listUrl.size();
	}

	@Override
	public Pictures getItem(int position) {
		return listUrl.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_fun, null);
			holder.imageView1 = (ImageView) view.findViewById(R.id.imageView1);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		imageLoader.DisplayImage(listUrl.get(position).getNum(),
				holder.imageView1);

		return view;
	}
}
