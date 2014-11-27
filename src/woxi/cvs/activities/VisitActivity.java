package woxi.cvs.activities;

import java.util.ArrayList;

import woxi.cvs.R;
import woxi.cvs.adapter.VisitListAdapter;
import woxi.cvs.model.Visit;
import woxi.cvs.model.VisitForm;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.widget.ExpandableListView;

public class VisitActivity extends Activity {

	private DisplayMetrics metrics;
	private int width;
	private ExpandableListView visitLists;
	private ArrayList<VisitForm> parents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		parents = buildDummyData();

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setTitle("SmartAV - Visit Form");
		getActionBar().setLogo(null);
		setContentView(R.layout.activity_visitform);

		visitLists = (ExpandableListView) this.findViewById(R.id.visitLists);
		visitLists.setAdapter(new VisitListAdapter(VisitActivity.this, parents));
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		// this code for adjusting the group indicator into right side of the
		// view
		visitLists.setIndicatorBounds(width - GetDipsFromPixel(50), width
				- GetDipsFromPixel(10));

		/*visitLists.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						parents.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});

		visitLists.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						parents.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});*/
	}

	public int GetDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	private ArrayList<VisitForm> buildDummyData() {
		// Creating ArrayList of type parent class to store parent class objects
		final ArrayList<VisitForm> list = new ArrayList<VisitForm>();
		for (int i = 1; i < 4; i++) {
			// Create parent class object
			final VisitForm parent = new VisitForm();

			// Set values in parent class object
			if (i == 1) {
				parent.setVisitNumber("First Visit");
				parent.setVisitsList(new ArrayList<Visit>());

				// Create Child class object
				final Visit child = new Visit();
				child.setPersonMet("Person 1");
				child.setRelationship("Name 1");
				parent.getVisitsList().add(child);
			} else if (i == 2) {
				parent.setVisitNumber("Second Visit");
				parent.setVisitsList(new ArrayList<Visit>());

				final Visit child = new Visit();
				child.setPersonMet("Person 1");
				child.setRelationship("Name 1");
				parent.getVisitsList().add(child);

			} else if (i == 3) {
				parent.setVisitNumber("Third Visit");
				parent.setVisitsList(new ArrayList<Visit>());

				final Visit child = new Visit();
				child.setPersonMet("Person 1");
				child.setRelationship("Name 1");
				parent.getVisitsList().add(child);

			}

			// Adding Parent class object to ArrayList
			list.add(parent);
		}

		return list;
	}

}
