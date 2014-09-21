package yiou.yuzhe.yeseul.androiddiaryapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainMenuActivity extends Activity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add_diary) {
			Intent i = new Intent(MainMenuActivity.this, NewDiaryActivity.class);
			startActivity(i);
			
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends ListFragment {
		private ArrayList<Diary> mDiaries;
		private DiaryAdapter adapter;
		public PlaceholderFragment() {
		}
		
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
			getActivity().setTitle(R.string.main_menu_title);
			DiarySystem diarySystem=DiarySystem.get(getActivity());
			
			
			
			mDiaries=diarySystem.getDiaries();
			
			adapter=new DiaryAdapter(getActivity(),mDiaries);
			
			setListAdapter(adapter);
			
			setRetainInstance(true);
			
		}
		
		@Override
		public void onResume() {// TODO Auto-generated method stub
			super.onResume();
			adapter.notifyDataSetChanged();
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_menu,
					container, false);
			Button addNewDiaryButton=(Button)rootView.findViewById(R.id.new_diary_button);
			addNewDiaryButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getActivity(), NewDiaryActivity.class);
					startActivity(i);
					
				}
			});
			return rootView;
		}
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			Diary d = ((DiaryAdapter)getListAdapter()).getItem(position);
			Intent i = new Intent(getActivity(), ViewDiaryActivity.class);
			i.putExtra(ViewDiaryActivity.VIEW_DIARY_ACTIVITY_ID, d.getId());
			startActivity(i);
			
		}
	}

	public static class DiaryAdapter extends ArrayAdapter<Diary> {

		private Activity activity;

		public DiaryAdapter(Activity activity, ArrayList<Diary> diaries) {

			super(activity, R.layout.list_item_diary,R.id.diary_list_code, diaries);
		
			this.activity = activity;
			
			
			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = this.activity.getLayoutInflater().inflate(
						R.layout.list_item_diary, null);

			}
			Diary d=getItem(position);
			ImageView selfie=(ImageView)convertView.findViewById(R.id.diary_selfie);
			selfie.setImageBitmap(d.getSelfie(this.activity));
			TextView title=(TextView)convertView.findViewById(R.id.diary_title);
			title.setText(d.getTitle());
			Log.i("MainMenuActivity",d.getTitle());
			TextView content=(TextView)convertView.findViewById(R.id.diary_content);
			content.setText(d.getContent());
			TextView listItemCode=(TextView)convertView.findViewById(R.id.diary_list_code);
			listItemCode.setVisibility(View.INVISIBLE);
			
			return super.getView(position, convertView, parent);
		}
		
		

	}
}
