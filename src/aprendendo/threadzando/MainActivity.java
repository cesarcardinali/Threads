package aprendendo.threadzando;

import java.util.ArrayList;

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

	public int Stock;
	public TextView t1;
	public EditText e1;
	public Button b1,b2,b3,b4;
	public Context Contexto;
	public Producer Produtor;
	public Consumer Consumidor;
	public ArrayList<Producer> Produtores;
	public ArrayList<Consumer> Consumidores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Contexto = this.getApplicationContext();

		t1 = (TextView) findViewById(R.id.textView1);
		e1 = (EditText) findViewById(R.id.editText1);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button4);
		b4 = (Button) findViewById(R.id.button3);
		
		Produtores = new ArrayList<MainActivity.Producer>();
		Consumidores = new ArrayList<MainActivity.Consumer>();
		

		b1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Produtor = new Producer("Produtor " + (Produtores.size()+1));
				Produtor.execute();
				Produtores.add(Produtor);
			}
		});
		
		b2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!Produtores.isEmpty()){
					Produtor = Produtores.remove(Produtores.size()-1);
					Produtor.kill();
					Produtor.cancel(false);
				}
			}
		});
		
		b3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Consumidor = new Consumer("Consumidor " + (Consumidores.size()+1));
				Consumidor.execute();
				Consumidores.add(Consumidor);
			}
		});
		
		b4.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!Consumidores.isEmpty()){
					Consumidor = Consumidores.remove(Consumidores.size()-1);
					Consumidor.kill();
					Consumidor.cancel(false);
				}
			}
		});
	}
	
	@Override
    public void onBackPressed() {
		Consumidor.cancel(false);
		Produtor.cancel(false);
		finish();
		return;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	//------------------------------------------------------------------------------------------------------------
	

	private class Producer extends AsyncTask<String, Integer, Long> {
		String id;
		int run, pause;
		long aux;
		
		public Producer (String iid){
			id = iid;
			run = 1;
			pause = 0;
		}
		
		public void on_off(){
			if (pause == 1)
				pause = 0;
			else
				pause = 1;
		}
		
		public void kill(){
			run = 0;
		}

		@Override
		protected Long doInBackground(String... params) {
			while (run == 1) {
				if (pause == 0)
					publishProgress(1);
				aux = System.currentTimeMillis();
				while ((aux + 1500) > System.currentTimeMillis()) {}
			}
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			Stock++;
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(id + " adicionou 1" + "\n" + e1.getText());
			t1 = (TextView) findViewById(R.id.textView1);
			t1.setText("Stock: " + Stock);
			
		}

		protected void onPostExecute(Long result) {
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(e1.getText() + "\n" + id + "morreu");
		}
		
		@Override
	    protected void onCancelled() {
			kill();
			super.onCancelled();
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(id + "morreu" + "\n" + e1.getText());
			return;
	    }
	}
	
	//_______________________________________________________________________________________________
	
	private class Consumer extends AsyncTask<String, Integer, Long> {
		String id;
		int run, pause;
		long aux;
		
		public Consumer (String iid){
			id = iid;
			run = 1;
			pause = 0;
		}
		
		public void on_off(){
			if (pause == 1)
				pause = 0;
			else
				pause = 1;
		}
		
		public void kill(){
			run = 0;
		}
		
		@Override
		protected Long doInBackground(String... params) {
			while (run == 1) {
				if (pause == 0)
					publishProgress(1);
				aux = System.currentTimeMillis();
				while ((aux + 2500) > System.currentTimeMillis()) {}
			}
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			Stock--;
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(id + " consumiu 1" + "\n" + e1.getText());
			t1 = (TextView) findViewById(R.id.textView1);
			t1.setText("Stock: " + Stock);
			
		}

		protected void onPostExecute(Long result) {
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(e1.getText() + "\n" + id + "morreu");
		}
		
		@Override
	    protected void onCancelled() {
			kill();
			super.onCancelled();
			e1 = (EditText) findViewById(R.id.editText1);
			e1.setText(id + "morreu" + "\n" + e1.getText());
			return;
	    }
	}

}
