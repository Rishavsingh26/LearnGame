package com.mycompany.learn;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.content.*;
import android.graphics.*;

public class MainActivity extends Activity 
{
	Button b1,b2,b3,b4,b5,b6,b7,b8,b9;
	TextView t1,t2,t3,t4;
	int c=0,u=0,m=0;
	String MENU ,p1;
	boolean tip;
	boolean play = false;
	SharedPreferences sp;
	ArrayList<HashMap<String,String>> list;
	ArrayList<String> data;
//	String[] item = {"KING","QUEEN","NOVEL","ARMY","ENEMY","PUBLIC","CHIEF","PRIEST","RIVAL"};
	String[] item = {"राजा","रानी","मंतरी","सेना","डाकू","जनता","सेनापति","पंडित","शतरू"};
	String[] tips;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		b1 = (Button) findViewById(R.id.mainButton1);
		b2 = (Button) findViewById(R.id.mainButton2);
		b3 = (Button) findViewById(R.id.mainButton3);
		b4 = (Button) findViewById(R.id.mainButton4);
		b5 = (Button) findViewById(R.id.mainButton5);
		b6 = (Button) findViewById(R.id.mainButton6);
		b7 = (Button) findViewById(R.id.mainButton7);
		b8 = (Button) findViewById(R.id.mainButton8);
		b9 = (Button) findViewById(R.id.mainButton9);
		t1 = (TextView) findViewById(R.id.mainTextView1);
		t2 = (TextView) findViewById(R.id.mainTextView2);
		t3 = (TextView) findViewById(R.id.mainTextView3);
		t4 = (TextView) findViewById(R.id.mainTextView4);
		
		b1.setText(item[0]);
		b2.setText(item[1]);
		b3.setText(item[2]);
		b4.setText(item[3]);
		b5.setText(item[4]);
		b6.setText(item[5]);
		b7.setText(item[6]);
		b8.setText(item[7]);
		b9.setText(item[8]);
		
		
		list = new ArrayList<HashMap<String,String>>();
		data = new ArrayList<String>();
		
		sp = getSharedPreferences("setting",0);
		MENU = sp.getString("menu","USER");
		switch(MENU)
		{
			case "COMP":t3.setText("PLAYER FIRST");break;
			case "USER":t3.setText("COMPUTER FIRST");break;
			case "TWO" :t3.setText("PLAYER VS PLAYER");break;
		}
		
		tip = sp.getBoolean("tips",true);
		if(tip==true)
		tip();
    }
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.mainButton1 : menu(item[0]);
				break;
			case R.id.mainButton2 : menu(item[1]);
				break;
			case R.id.mainButton3 : menu(item[2]);
				break;
			case R.id.mainButton4 : menu(item[3]);
				break;
			case R.id.mainButton5 : menu(item[4]);
				break;
			case R.id.mainButton6 : menu(item[5]);
				break;
			case R.id.mainButton7 : menu(item[6]);
				break;
			case R.id.mainButton8 : menu(item[7]);
				break;
			case R.id.mainButton9 : menu(item[8]);
				break;
		}
	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) { 
		//inflate the menu resource file in your activity 
		getMenuInflater().inflate(R.menu.menu, menu); 
		return true; 
	} 

	@Override 
	public boolean onOptionsItemSelected(MenuItem item) { 
		//code for item select event handling 
		switch(item.getItemId()){ 
			case R.id.item1 : game();
			//MENU = "COMP"; t3.setText("PLAYER FIRST");  u=0;c=0;m=0;
			//SharedPreferences.Editor edit1 = sp.edit();
			//edit1.putString("menu","COMP");
			//edit1.apply();
			//edit1.commit();
				break; 
			case R.id.item2 : level();
				//MENU = "USER"; t3.setText("COMPUTER FIRST"); u=0;c=0;m=0;
				//SharedPreferences.Editor edit2 = sp.edit();
				//edit2.putString("menu","USER");
				//edit2.apply();
				//edit2.commit();
				break; 
			case R.id.item3 : gt();
				//MENU = "TWO"; t3.setText("PLAYER VS PLAYER");  u=0;c=0;m=0;
				//SharedPreferences.Editor edit3 = sp.edit();
				//edit3.putString("menu","TWO");
				//edit3.apply();
				//edit3.commit();
				break; 
			case R.id.item4 : hs();
				break;
			case R.id.item5 : clear();
				break;
			
		} 

		return super.onOptionsItemSelected(item); 
	} 
	
	private void res(String res)
	{
		String th = think(res);
		String w = win(res,th);

		if(w.equalsIgnoreCase("Comp"))
		{
			s(w);
			learn(res,th);
			//s(map.toString());
		}
		else if(w.equalsIgnoreCase("User"))
		{
			int l = sp.getInt("level",0);
			if(l==1)
			{
				learn(th,res);
			}
			s(w);
		}
		else
		{
			s(w);
		}

		t1.setText("User : ["+res+"]  vs  "+"Comp : ["+th+"]\n\n"+w+" Wins !!!");
		t2.setText("User Score  : "+u+"\n"+"Comp Score : "+c+"\n"+"Total Match : "+m);
		if(sp.getInt("level",0)==1)
		{
			if(u>sp.getInt("l1_p_f1",0))
			{
				SharedPreferences.Editor edit = sp.edit();
				edit.putInt("l1_p_f1",u);
				edit.putInt("l1_m_f1",m);
				edit.apply();
				edit.commit();
				s("New High Score !");
			}
		}
		else
		{
			if(u>sp.getInt("l0_p_f1",0))
			{
				SharedPreferences.Editor edit = sp.edit();
				edit.putInt("l0_p_f1",u);
				edit.putInt("l0_m_f1",m);
				edit.apply();
				edit.commit();
				s("New High Score !");
			}
		}
	}
	
	private String think(String t)
	{
		
		int i = 0;
		Random r = new Random();
		String c = item[r.nextInt(9)];
		
		while(!list.isEmpty() && list.size()>i)
		{
			if(list.get(i).get("key").equalsIgnoreCase(t))
			{
				c = list.get(i).get("value");
				return c;
				//t4.append(i+" "+list.get(i).get("key")+" : "+list.get(i).get("value")+"\n");
				
			}
			
			i++;
		}
		
		return c;
	}
	
	private void comp(String user)
	{
		
		String[] POW = {item[0], item[6],item[7],item[8]};
		Random r = new Random();
		String d = item[r.nextInt(9)];
		if(sp.getInt("level",0)==1)
		{
			d = POW[r.nextInt(4)];
		}
		
		String w = win(user,d);
		
		/*
		if(w.equals("Comp"))
		{
			//data.add("Win : "+user+" : "+d);
		}
		else if(w.equals("User"))
		{
			data.add(user);
		}
		*/
		s(w);
		t1.setText("User : ["+user+"]  vs  "+"Comp : ["+d+"]\n\n"+w+" Wins !!!");
		t2.setText("User Score  : "+u+"\n"+"Comp Score : "+c+"\n"+"Total Match : "+m);
		if(sp.getInt("level",0)==1)
		{
			if(u>sp.getInt("l1_p_f2",0))
			{
				SharedPreferences.Editor edit = sp.edit();
				edit.putInt("l1_p_f2",u);
				edit.putInt("l1_m_f2",m);
				edit.apply();
				edit.commit();
				s("New High Score !");
			}
		}
		else
		{
			if(u>sp.getInt("l0_p_f2",0))
			{
				SharedPreferences.Editor edit = sp.edit();
				edit.putInt("l0_p_f2",u);
				edit.putInt("l0_m_f2",m);
				edit.apply();
				edit.commit();
				s("New High Score !");
			}
		}
		
		
		//t4.setText(data.toString());
	}
	
	private void clear()
	{
		SharedPreferences.Editor edit1 = sp.edit();
		edit1.remove("l0_p_f1");
		edit1.remove("l0_p_f2");
		edit1.remove("l0_m_f1");
		edit1.remove("l0_m_f2");
		edit1.remove("l1_p_f1");
		edit1.remove("l1_p_f1");
		edit1.remove("l1_m_f1");
		edit1.remove("l1_m_f2");
		edit1.apply();
		edit1.commit();
		s("All Previous High Scores Are Cleared");
		/*
		for(int i=0;i<list.size()-1;i++)
		{
			for(int j=0;j<list.size()-1;j++)
			{
				data[i]
			}
		}*/
		
	}
	
	private void menu(String a)
	{
		
		switch(MENU)
		{
			case "COMP" : res(a); 
				break;
			case "USER" : comp(a);
				break;
			case "TWO" : two(a);
				break;

		}

	}
	
	private void two(String b)
	{
		String p2 = "test 2";
		
		if(play==true)
		{
			play=false;
			p2 = b;
			String w = win(p1,p2);
			if(w.equals("User"))
			{
				w = "Player1";
				
			}
			else if(w.equals("Comp"))
			{
				w = "Player2";
			}
			
			t1.setText("Player1 : ["+p1+"]  vs  "+"Player2 : ["+p2+"]\n\n"+w+" Wins !!!");
			t2.setText("Player1 : "+u+"\n"+"Player2  : "+c+"\n"+"Total Match : "+m);
			p1 = "";
			p2 = "";
			
		}
		
		else
		{
			play=true;
			p1 = b;
			s("Player2's Turn !!!");
		}
	}
	
	private void learn(String k,String v)
	{
		int i = 0;
		if(list.isEmpty()==true)
		{
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("key",k);
			map.put("value",v);
			list.add(map);
			//s(map.toString());
		}
		else
		{
			while(list.size()>i)
			{
				String t = list.get(i).get("key");
				if(!t.equals(k))
				{
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("key",k);
					map.put("value",v);
					list.add(map);
					//t4.setText(list.toString()+"\n\n");
					//s(map.toString());
					break;
				}
				
				i++;
			}
		}
		
		
	}
	
	private void game()
	{
		final CharSequence[] game = { "PLAYER FIRST","COMPUTER FIRST","PLAYER VS PLAYER" };
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("Choose Game Mode");
		ab.setItems(game,new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					switch(p2)
					{
						case 0 : 	
							MENU = "COMP"; t3.setText("PLAYER FIRST");  u=0;c=0;m=0;
							SharedPreferences.Editor edit1 = sp.edit();
							edit1.putString("menu","COMP");
							edit1.apply();edit1.commit(); break; 
						case 1 :
							MENU = "USER"; t3.setText("COMPUTER FIRST"); u=0;c=0;m=0;
							SharedPreferences.Editor edit2 = sp.edit();
							edit2.putString("menu","USER");
							edit2.apply();edit2.commit();break; 
						case 2 : 
							MENU = "TWO"; t3.setText("PLAYER VS PLAYER");  u=0;c=0;m=0;
							SharedPreferences.Editor edit3 = sp.edit();
							edit3.putString("menu","TWO");
							edit3.apply();edit3.commit();break; 
					}
					s(String.valueOf(game[p2])+" MODE");
				}});
		ab.show();
	}
	
	private void hs()
	{
		
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("Highest Scores");
		LinearLayout al1 = new LinearLayout(this);
		al1.setOrientation(LinearLayout.VERTICAL);
		TextView at1 = new TextView(this);
		TextView at2 = new TextView(this);
		at1.setTextSize(18);
		at2.setTextSize(18);
		
		at1.setText(
		"\n Level Easy :--\n\n Player_First_Score : "+sp.getInt("l0_p_f1",0)+
		"\n Matches_Played : "+sp.getInt("l0_m_f1",0)+
		"\n\n"+"Comp_First_Score : "+sp.getInt("l0_p_f2",0)+
		"\n Matches_Played : "+sp.getInt("l0_m_f2",0));
		
		at2.setText(
		"\nLevel Hard :--\n\n Player_First_Score : "+sp.getInt("l1_p_f1",0)+
		"\n Matches_Played : "+sp.getInt("l1_m_f1",0)+
		"\n\n"+"Comp_First_Score : "+sp.getInt("l1_p_f2",0)+
		"\n Matches_Played : "+sp.getInt("l1_m_f2",0)+"\n");
		al1.addView(at1);
		al1.addView(at2);
		ab.setView(al1);
		//ab.setMessage("PLAYER FIRST Score : "+sp.getInt("COMP",0)+"\n\n"+
		//"COMPUTER FIRST Score : "+sp.getInt("USER",0));
		ab.show();
	}
	
	private void level()
	{
		final CharSequence[] level = {"EASY","HARD"};
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("Game Leevel :  "+level[sp.getInt("level",0)]);
		ab.setItems(level,new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					switch(p2)
					{
						case 0 : 
							u=0;c=0;m=0;
							SharedPreferences.Editor edit1 = sp.edit();
							edit1.putInt("level",0);
							edit1.apply();
							edit1.commit();break;
						case 1 : 
							u=0;c=0;m=0;
							SharedPreferences.Editor edit2 = sp.edit();
							edit2.putInt("level",1);
							edit2.apply();
							edit2.commit();break;
					}
					s(String.valueOf(level[p2])+" Level");
					}});
		ab.show();
	}
	
	private void gt()
	{
		//s("Game Tips Always On in This Version");
		final CharSequence[] game = { "Tips ON","Tips OFF" };
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("Tips ON/OFF");
		ab.setItems(game,new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					switch(p2)
					{
						case 0 : 	
							tip = true;
							SharedPreferences.Editor edit1 = sp.edit();
							edit1.putBoolean("tips",true);
							edit1.apply();edit1.commit();tip(); break; 
						case 1 :
							tip = false;
							SharedPreferences.Editor edit2 = sp.edit();
							edit2.putBoolean("tips",false);
							edit2.apply();edit2.commit();break; 
						}
					s(String.valueOf(game[p2]));
				}});
		ab.show();
	}
	
	private String win(String p1,String p2)
	{
		String win = "User";
		m++;
		if(p1.equalsIgnoreCase(item[0]) && p2.equalsIgnoreCase(item[1]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[0]) && p2.equalsIgnoreCase(item[5]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[0]) && p2.equalsIgnoreCase(item[7]))
		{
			win = "Comp";
			c++;
		}
		
		
		
		
		if(p1.equalsIgnoreCase(item[1]) && p2.equalsIgnoreCase(item[4]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[1]) && p2.equalsIgnoreCase(item[5]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[1]) && p2.equalsIgnoreCase(item[7]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[1]) && p2.equalsIgnoreCase(item[8]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[2]) && p2.equalsIgnoreCase(item[0]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[2]) && p2.equalsIgnoreCase(item[1]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[2]) && p2.equalsIgnoreCase(item[7]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[3]) && p2.equalsIgnoreCase(item[0]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[3]) && p2.equalsIgnoreCase(item[1]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[3]) && p2.equalsIgnoreCase(item[2]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[3]) && p2.equalsIgnoreCase(item[6]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[3]) && p2.equalsIgnoreCase(item[8]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[4]) && p2.equalsIgnoreCase(item[0]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[4]) && p2.equalsIgnoreCase(item[2]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[4]) && p2.equalsIgnoreCase(item[3]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[4]) && p2.equalsIgnoreCase(item[6]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[4]) && p2.equalsIgnoreCase(item[8]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[5]) && p2.equalsIgnoreCase(item[3]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[5]) && p2.equalsIgnoreCase(item[4]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[5]) && p2.equalsIgnoreCase(item[6]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[5]) && p2.equalsIgnoreCase(item[7]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[5]) && p2.equalsIgnoreCase(item[8]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[6]) && p2.equalsIgnoreCase(item[0]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[6]) && p2.equalsIgnoreCase(item[1]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[6]) && p2.equalsIgnoreCase(item[2]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[7]) && p2.equalsIgnoreCase(item[3]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[7]) && p2.equalsIgnoreCase(item[4]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[7]) && p2.equalsIgnoreCase(item[6]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[7]) && p2.equalsIgnoreCase(item[8]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[8]) && p2.equalsIgnoreCase(item[0]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[8]) && p2.equalsIgnoreCase(item[2]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[8]) && p2.equalsIgnoreCase(item[6]))
		{
			win = "Comp";
			c++;
		}
		
		if(p1.equalsIgnoreCase(item[0]) && p2.equalsIgnoreCase(item[0]))
		{
			win = "Draw";
		}
		if(p1.equalsIgnoreCase(item[1]) && p2.equalsIgnoreCase(item[1]))
		{
			win = "Draw";
		}
		if(p1.equalsIgnoreCase(item[2]) && p2.equalsIgnoreCase(item[2]))
		{
			win = "Draw";
		}
		if(p1.equalsIgnoreCase(item[3]) && p2.equalsIgnoreCase(item[3]))
		{
			win = "Draw";
		}
		if(p1.equalsIgnoreCase(item[4]) && p2.equalsIgnoreCase(item[4]))
		{
			win = "Draw";
		}
		if(p1.equalsIgnoreCase(item[5]) && p2.equalsIgnoreCase(item[5]))
		{
			win = "Draw";
		}
		if(p1.equalsIgnoreCase(item[6]) && p2.equalsIgnoreCase(item[6]))
		{
			win = "Draw";
		}
		if(p1.equalsIgnoreCase(item[7]) && p2.equalsIgnoreCase(item[7]))
		{
			win = "Draw";
		}
		if(p1.equalsIgnoreCase(item[8]) && p2.equalsIgnoreCase(item[8]))
		{
			win = "Draw";
		}
		if(win.equals("User"))
		{
			u++;
		}
		
		return win;
	}
	
	private void s(String s)
	{
		Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
	}
	
	private void tip()
	{
		tips = 	new String[]{"Army Always beats Enemy","Rival got beaten by King","Computer keeps learning","Queen gets respect from King","Chief can beats Rival and Army",
		"King, Queen respects Priest, Public","This App is Devloped by Rishav Singh","A Well Trained Computer beats You easily","Computer Learn Faster in Hard Levels",
		"King, Queen are head of Novel","Novel is head of Chief and Army","Chief Always beats Rival, Enemy","Queen gets Scared by Rivals","Priest, Public get Scared by Enemy",
		"Chief, Army got respects from Priest, Public","A Well Trained Computer beats You easily","Priest, Public Also got respect from Novel","Novel can beats Rivals"};
		final Handler h = new Handler();
		Thread t = new Thread(new Runnable()
			{
				int j;
				@Override
				public void run()
				{
					while(j<100)
					{
						if(tip==false)
						{
							h.post(new Runnable()
								{

									@Override
									public void run()
									{
										t4.setText("");
										s("Always Learn Tips to Score High");
									}
									
								
							});
							break;
						}
							
						h.post(new Runnable()
							{

								@Override
								public void run()
								{
									
									Random r = new Random();
									int i = r.nextInt(20);
									if(i<tips.length) 
									t4.setText(tips[i]);
									
								}
								
						});
						try
						{
							Thread.sleep(3000);
						}
						catch (InterruptedException e)
						{e.printStackTrace();}
						j++;
					}
				}
		});t.start();
	}
	
}
