package com.example.kelimeoyunu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Oyun extends Activity {
	
	

private Button[] butonlar;
	
	String kelime="";
	String il="";
	boolean blinkOn = false;
	int sorusayisi=0;
	int dogrucevapsayisi=0;
	int sure=15;
	boolean sureyidurdur=false;
	
	private static final String[] iller = new String[] {"ISPARTA","KONYA","ANKARA","�STANBUL","ADANA","�ZM�R","ED�RNE","KOCAEL�","BURSA","MAN�SA","K�TAHYA","AYDIN","MU�LA",
		"DEN�ZL�","BURDUR","ANTALYA","KARAMAN","HATAY","KAYSER�","S�VAS","BOLU","D�ZCE","KARAB�K","�ORUM","AMASYA","TOKAT","�ANKIRI","ORDU","G�RESUN","YOZGAT","ERZ�NCAN","S�VAS",
		"TRABZON","ERZURUM","KARS","A�RI","B�NG�L","MU�","B�TL�S"};
	char[] rasgeleHarf = new char[]{'A','B','C','�','D','E','F','G','H','I','�','J','K','L','M','N','O','�','P','R','S','�','T','U','�','V','Y','Z'};
	private ArrayList<Character > harfler = new ArrayList<Character >();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_oyun);
		final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
		final EditText textbox =(EditText)findViewById(R.id.editText1);
		textbox.setEnabled(false);
		final TextView durum =(TextView)findViewById(R.id.textView1);
		final TextView durum2 =(TextView)findViewById(R.id.textView2);
		final TextView txt_sure =(TextView)findViewById(R.id.textView3);
		RasgeleIl();
		
		butonlar=ButonAta();
		HarfAta();
		sorusayisi++;
		durum2.setText(sorusayisi + " soruda " + dogrucevapsayisi + " dogru bildiniz.");
		 
		for (int i = 0; i < 9; i++) {
			butonlar[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					HarfYaz((Button) v);
					KontrolEt();
					
				}
			});
		}
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sorusayisi++;
				if (sorusayisi == 11) {
					sureyidurdur=true;
					dlgAlert.setTitle("Puan�n�z : " + (dogrucevapsayisi*10) ); 
				    dlgAlert.setMessage("Yeniden Oyna!!"); 
				               		        
				    dlgAlert.setPositiveButton("EVET",new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	 sorusayisi=1;
				        	 dogrucevapsayisi=0;
				        	 sure=15;
				        	 HarfAta();
				        	 durum2.setText(sorusayisi + " soruda " + dogrucevapsayisi + " dogru bildiniz.");
				        }
				   });
				    
				    dlgAlert.setNegativeButton("HAYIR",new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	System.exit(0);
				        }
				   });
				    
				    dlgAlert.setCancelable(true);
				    dlgAlert.create().show();
				}
				else
				{
					harfler.removeAll(harfler);
					RasgeleIl();
					durum.setText("");
					textbox.setText("");
					kelime="";
					HarfAta();
					durum2.setText(sorusayisi + " soruda " + dogrucevapsayisi + " dogru bildiniz.");
					sureyidurdur=false;
				}
					for (int i = 0; i < 9; i++) {
						butonlar[i].setEnabled(true);
					}
					
					sure=15;
					
				
				
				
			}
		});
		
		
		//timer
		Timer tmrBlink = new Timer(500, new Runnable() { 
       	 
            public void run() { 
              if (blinkOn && sure>0 && !sureyidurdur) 
              { 
            	  sure--;
            	  txt_sure.setText("S�re : " + sure);
              } 
              if (sure==0) {
				durum.setText("S�reniz Doldu!!");
				for (int i = 0; i < 9; i++) {
					butonlar[i].setEnabled(false);
				}
				
				
				
			}
              
              
              blinkOn = !blinkOn; 
            } 
            
          });
          
          tmrBlink.start();
	}
	
	public void KontrolEt(){
		
		EditText textbox =(EditText)findViewById(R.id.editText1);
		TextView durum =(TextView)findViewById(R.id.textView1);
		final TextView durum2 =(TextView)findViewById(R.id.textView2);
		
		String a =textbox.getText().toString();
		if (il.length() == a.length()) {
			for (int i = 0; i < 9; i++) {
				butonlar[i].setEnabled(false);
			}
			if (kelime.equals(il)) {
				durum.setText("DO�RU");
				dogrucevapsayisi++;
				sureyidurdur=true;
			}
			else{
				durum.setText("YANLI�!! CEVAP : " + il);
				sureyidurdur=true;
			}
			
		}
		durum2.setText(sorusayisi + " soruda " + dogrucevapsayisi + " dogru bildiniz.");
		
	}
	
	public void RasgeleIl(){
		Random rnd = new Random();
		il =iller[rnd.nextInt(iller.length)];
	}
	
	public void HarfAta() {
		Random rnd = new Random();
		char[] harfler2 = il.toCharArray();
		
		
		for (int i = 0; i < il.length(); i++) {
			this.harfler.add(harfler2[i]);
		}
		
		while(harfler.size() < 9){
			this.harfler.add(rasgeleHarf[rnd.nextInt(rasgeleHarf.length)]);
		}
		
		Collections.shuffle(this.harfler);
		for (int i = 0; i < 9; i++) {
			String harf = Character.toString(harfler.get(i));
			butonlar[i].setText(harf);
		}
	}

	
	public void HarfYaz(final Button b) {
		final EditText textbox =(EditText)findViewById(R.id.editText1);
		
		String btn_text;
		btn_text = (String) b.getText();
		b.setEnabled(false);
		
		kelime += btn_text;
		
		textbox.setText(kelime);
	}

	
	public Button[] ButonAta() {
		Button[] b = new Button[9];
		
		b[0] = (Button) findViewById(R.id.Button00);
		b[1] = (Button) findViewById(R.id.Button01);
		b[2] = (Button) findViewById(R.id.Button02);
		b[3] = (Button) findViewById(R.id.Button03);
		b[4] = (Button) findViewById(R.id.Button04);
		b[5] = (Button) findViewById(R.id.Button05);
		b[6] = (Button) findViewById(R.id.Button06);
		b[7] = (Button) findViewById(R.id.Button07);
		b[8] = (Button) findViewById(R.id.Button08);
		return b;
	}
}
