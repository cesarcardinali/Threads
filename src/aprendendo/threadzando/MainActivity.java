package aprendendo.threadzando;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	public int Products, Consumers, runP, runC, f;
	public TextView t1;
	public EditText e1;
	public Button b1,b2,b3;
	public Context Contexto;
	public Producer P1;
	public Consumer C1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Contexto = this.getApplicationContext();

		t1 = (TextView) findViewById(R.id.textView1);
		e1 = (EditText) findViewById(R.id.editText1);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		f=1;
		runC = 1;
		runP = 1;
		P1 = new Producer();
		P1.execute("P1");
		C1 = new Consumer();
		C1.execute("C1");

		b1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (runP == 1) {
					runP = 0;
				} else {
					runP = 1;
				}
			}
		});
		
		b2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (runC == 1) {
					runC = 0;
				} else {
					runC = 1;
				}
			}
		});
		
		b3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (f == 1) {
					f = 0;
				} else {
					f = 1;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	

	private class Producer extends AsyncTask<String, Integer, Long> {
		String nome;
		
		@Override
		protected Long doInBackground(String... params) {
			nome = params[0];
			while (f == 1){
				if(runP == 1)
				try {
					publishProgress(1);
					Thread.currentThread().sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			Products++;
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(e1.getText() + "\n" + nome + " adicionou 1");
			t1 = (TextView) findViewById(R.id.textView1);
			t1.setText("Producsts: " + Products);
			
		}

		protected void onPostExecute(Long result) {
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(e1.getText() + "\n" + nome + "morreu");
		}
	}
	
	//_______________________________________________________________________________________________
	
	private class Consumer extends AsyncTask<String, Integer, Long> {
		String nome;
		
		@Override
		protected Long doInBackground(String... params) {
			nome = params[0];
			while (f == 1){
				if(runC == 1)
				try {
					publishProgress(1);
					Thread.currentThread().sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			Products--;
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(e1.getText() + "\n" + nome + " consumiu 1");
			t1 = (TextView) findViewById(R.id.textView1);
			t1.setText("Producsts: " + Products);
			
		}

		protected void onPostExecute(Long result) {
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(e1.getText() + "\n" + nome + "morreu");
		}
	}

}
