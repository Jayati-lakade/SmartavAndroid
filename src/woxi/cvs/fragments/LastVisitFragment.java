package woxi.cvs.fragments;

import java.net.URLDecoder;

import woxi.cvs.R;
import woxi.cvs.model.BulkTask;
import woxi.cvs.model.FreshTask;
import woxi.cvs.model.Visit;
import woxi.cvs.model.WLTask;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

public class LastVisitFragment extends Fragment {

	private Object obj;// TO get task from an intent
	private Visit visit;// To get rescent visit history
	private TextView personMet, personMetName, remarks, date;
	private ImageView signature, imgProofId, imgAddressId, imgDocId,
			imgHouseId;
	Bitmap image ;
	ProgressDialog pd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getActivity().getIntent().getExtras().get("task") != null) {
			obj = getActivity().getIntent().getExtras().get("task");
			if (obj instanceof FreshTask) {
				FreshTask freshTask = (FreshTask) obj;
				if (freshTask.getVisitList().size() > 0) {
					visit = freshTask.getVisitList().get(0);
				}
				if (freshTask.getVisitList() != null) {
					Log.i("Comment Fragment", "GOT FreshTask visitList size : ");
				}
			} else if (obj instanceof WLTask) {
				WLTask wlTask = (WLTask) obj;
				if (wlTask.getVisitList().size() > 0) {
					visit = wlTask.getVisitList().get(0);
				}
				if (wlTask.getVisitList() != null) {
					Log.i("Comment Fragment", "GOT WLTask visitList size : ");
				}
			}else if (obj instanceof BulkTask) {
				BulkTask bulkTask = (BulkTask) obj;
				if (bulkTask.getVisitList().size() > 0) {
					visit = bulkTask.getVisitList().get(0);
				}
				if (bulkTask.getVisitList() != null) {
					Log.i("Comment Fragment", "GOT BulkTask visitList size : ");
				} 
			}
		} else {
			Log.i("Comment Fragment", "Got null obj");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_lastvisit, container, false);

		personMet = (TextView) rootView.findViewById(R.id.personMet);
		personMetName = (TextView) rootView.findViewById(R.id.personMetName);
		remarks = (TextView) rootView.findViewById(R.id.remarks);
		date = (TextView) rootView.findViewById(R.id.date);
		signature = (ImageView) rootView.findViewById(R.id.signature);
		imgProofId = (ImageView) rootView.findViewById(R.id.imgProofId);
		imgAddressId = (ImageView) rootView.findViewById(R.id.imgAddressId);
		imgDocId = (ImageView) rootView.findViewById(R.id.imgDocId);
		imgHouseId = (ImageView) rootView.findViewById(R.id.imgHouseId);
		
		AQuery aqimgProofId = new AQuery(imgProofId);
		aqimgProofId.id(imgProofId).progress(R.id.progress1).image(visit.getProofId(), false, true, 0, R.drawable.cameraplaceholder);
		
		AQuery aqimgAddressId = new AQuery(imgAddressId);
		aqimgAddressId.id(imgAddressId).progress(R.id.progress2).image(visit.getAddressId(), false, true, 0, R.drawable.cameraplaceholder);
		
		AQuery aqimgHouseId = new AQuery(imgHouseId);
		aqimgHouseId.id(imgHouseId).progress(R.id.progress3).image(visit.getHouseId(), false, true, 0, R.drawable.cameraplaceholder);

		AQuery aqimgDocId = new AQuery(imgDocId);
		aqimgDocId.id(imgDocId).progress(R.id.progress4).image(visit.getDocumentId(), false, true, 0, R.drawable.cameraplaceholder);
		
		AQuery aqsignature = new AQuery(signature);
		aqsignature.id(signature).progress(R.id.progress0).image(visit.getCustomerSign(), false, true, 0, R.drawable.signature);
		
		personMet.setText(visit.getRelationship());
		String personName  = URLDecoder.decode(visit.getPersonName());
		personMetName.setText(personName);
		String ofrRemark = URLDecoder.decode(visit.getRemark());
		remarks.setText(ofrRemark);
		date.setText(visit.getAppointmentTimeDate());
		return rootView;
	}

	/*private Drawable LoadImageFromWebOperations(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			System.out.println("Exc=" + e);
			return null;
		}
	}

	private Bitmap downloadBitmap(String url) {
		// initilize the default HTTP client object
		final DefaultHttpClient client = new DefaultHttpClient();

		// forming a HttoGet request
		final HttpGet getRequest = new HttpGet(url);
		try {

			HttpResponse response = client.execute(getRequest);

			// check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;

			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					// getting contents from the stream
					inputStream = entity.getContent();

					// decoding stream data back into image Bitmap that android
					// understands
					image = BitmapFactory.decodeStream(inputStream);

				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// You Could provide a more explicit error message for IOException
			getRequest.abort();
			Log.e("ImageDownloader", "Something went wrong while"
					+ " retrieving bitmap from " + url + e.toString());
		}

		return image;
	}

	public Bitmap DownloadFullFromUrl(String imageFullURL) {
		Bitmap bm = null;
		try {
			URL url = new URL(imageFullURL);
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			bm = BitmapFactory.decodeByteArray(baf.toByteArray(), 0,
					baf.toByteArray().length);
		} catch (IOException e) {
			Log.d("ImageManager", "Error: " + e);
		}
		return bm;
	}*/
}
