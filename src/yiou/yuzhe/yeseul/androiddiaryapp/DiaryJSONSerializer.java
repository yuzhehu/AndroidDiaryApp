package yiou.yuzhe.yeseul.androiddiaryapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class DiaryJSONSerializer {
	private Context mContext;
	private String mFilename;

	public DiaryJSONSerializer(Context c, String f) {
		mContext = c;
		mFilename = f;
	}

	public ArrayList<Diary> loadDiary() throws IOException, JSONException {
		ArrayList<Diary> diaries = new ArrayList<Diary>();
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jSonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jSonString.append(line);
			}
			JSONArray array = (JSONArray) new JSONTokener(jSonString.toString())
					.nextValue();
			for (int i = 0; i < array.length(); i++) {
				diaries.add(new Diary(array.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (reader != null) {
				reader.close();
			}
			
		}
		return diaries;
	}

	public void saveDiaries(ArrayList<Diary> diaries) throws JSONException, IOException {
		JSONArray array=new JSONArray();
		for (Diary d:diaries){
			array.put(d.toJSON());
		}
		Writer writer=null;
		try{
			OutputStream out=mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer=new OutputStreamWriter(out);
			writer.write(array.toString());
		}finally{
			if (writer !=null)
				writer.close();
		}
	}
}
