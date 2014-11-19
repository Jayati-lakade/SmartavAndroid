package woxi.cvs.fragments;

import java.net.URLDecoder;
import java.util.ArrayList;

import woxi.cvs.R;
import woxi.cvs.model.BulkTask;
import woxi.cvs.model.FreshTask;
import woxi.cvs.model.Visit;
import woxi.cvs.model.WLTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommentFragment extends Fragment {

	ArrayList<Visit> visitList;
	ListView commentListView;
	ArrayList<String> commentsList = new ArrayList<String>();
	// ArrayList<String> commenterList = new ArrayList<String>();
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		commentsList = new ArrayList<String>();
		
		super.onCreate(savedInstanceState);
		if (getActivity().getIntent().getExtras().get("task") != null) {
			Object obj = getActivity().getIntent().getExtras().get("task");
			if (obj instanceof FreshTask) {
				FreshTask freshTask = (FreshTask) obj;
				commentsList = freshTask.getVisitList().get(0).getComments();
				if (commentsList != null && commentsList.size() > 0) {
					Log.i("Comment Fragment", "GOT WLTask visitList size : "
							+ commentsList.size());
				}else{
					commentsList = new ArrayList<String>(1);
					commentsList.add("No Comments available");
				}
			} else if (obj instanceof WLTask) {
				WLTask wlTask = (WLTask) obj;
				commentsList = wlTask.getVisitList().get(0).getComments();
				if (commentsList != null && commentsList.size() > 0) {
					Log.i("Comment Fragment", "GOT WLTask visitList size : "
							+ commentsList.size());
				}else{
					commentsList = new ArrayList<String>(1);
					commentsList.add("No Comments available");
				}
				if (wlTask.getVisitList() != null) {

				}
			}else if (obj instanceof BulkTask) {
				BulkTask bulkTask = (BulkTask) obj;
				commentsList = bulkTask.getVisitList().get(0).getComments();
				if (commentsList != null && commentsList.size() > 0) {
					Log.i("Comment Fragment", "GOT BulkTask visitList size : "
							+ commentsList.size());
				}else{
					commentsList = new ArrayList<String>(1);
					commentsList.add("No Comments available");
				}
				if (bulkTask.getVisitList() != null) {

				}
			}
		} else {
			Log.i("Comment Fragment", "Got null obj");
		}

		// commenterList.add("Commenter 1");
		// commentsList.add("Commenter 1 : Comment");

		// commenterList.add("Commenter 2");
		// commentsList.add("Commenter 2 : Comment");

		// commenterList.add("Commenter 3");
		// commentsList.add("Commenter 3 : Comment");

		// commenterList.add("Commenter 4");
		// commentsList.add("Commenter 4 : Comment");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_comment_list,
				container, false);
		commentListView = (ListView) rootView.findViewById(R.id.commentList);
		commentListView.setAdapter(new CommentListAdapter());

		return rootView;
	}

	public class CommentListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return commentsList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public class ViewHolder {

			// public TextView commenter;
			public TextView comment;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			ViewHolder holder;
			LayoutInflater inflater = getActivity().getLayoutInflater();
			if (convertView == null) {

				/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
				vi = inflater.inflate(R.layout.comment_list_item, null);

				/****** View Holder Object to contain tabitem.xml file elements ******/

				holder = new ViewHolder();
				// holder.commenter = (TextView)
				// vi.findViewById(R.id.commentBy);
				holder.comment = (TextView) vi.findViewById(R.id.comment);

				/************ Set holder with LayoutInflater ************/
				vi.setTag(holder);

				// holder.commenter.setText(commenterList.get(position).toString());
				String comments  = URLDecoder.decode(commentsList.get(position).toString());
				holder.comment.setText(comments);
				System.out.println("Smart AV : - Commments" + position + " - " + commentsList.get(position).toString());
			} else {
				holder = (ViewHolder) vi.getTag();
			}

			return vi;
		}

	}
}
