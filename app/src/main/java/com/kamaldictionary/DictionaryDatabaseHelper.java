package com.kamaldictionary;
/*
DictionaryDatabaseHelper is used to create Database. SQLiteOpenHelper helps to create table, insert, update, delete data etc.
1.	Unimplemented methods[onCreate() and onUpgrade()] will be Implemented.
2.  Then, Add constructor (DictionaryDatabaseHelper).
3.	onCreate() method is used to create table.
4.	insertData() method is used to automatic insert data into database.
5.	updateData() method is used word-ta equal hoy
6.	deleteData() method is used to equal with wordDefinition.
7.	ArrayList create korbo sob word ek sathe neyear jonno during initial at first time
8.	getWordDefinition() method use korbo just ekta word Definition return korbe
9.	getWordDefinition() method use korbo returns one word by id
10.	initializeDatabaseFortheFirstTime() use korbo all data insert korar jonno ei data gulo ekta text
file thakbe ta directly database a bujhanor jonno "DictionaryLoader.java"
*/
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryDatabaseHelper extends SQLiteOpenHelper {
	//Final is used to apply restrictions on class, method and variable. Final class can't be inherited, final method can't be overridden and final variable value can't be changed.
	//If you want to change any name (like table name), then changes from here and also you can reuse those.
	final static String DICTIONARY_DATABASE="dictionary";
	final static String ITEM_ID_COLUMN="id";
	final static String WORD_COLUMN="word";
	final static String DEFINITION_COLUMN="definition";
	final static String CREATE_DATABASE_QUERY =
			"CREATE TABLE "+DICTIONARY_DATABASE+" ( "+ITEM_ID_COLUMN+" INTEGER PRIMARY KEY AUTOINCREMENT, "+WORD_COLUMN+" TEXT , "+DEFINITION_COLUMN+" TEXT)";
	final static String ON_UPGRADE_QUERY="DROP TABLE "+DICTIONARY_DATABASE;
	Context context;
	public DictionaryDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, DICTIONARY_DATABASE, factory, version);	//create the database named DICTIONARY_DATABASE
		this.context=context;									//assign the context and save it
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		//Create the table
		database.execSQL(CREATE_DATABASE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL(ON_UPGRADE_QUERY);
		onCreate(database);
	}

	//insert data into database by key & values = HashMap or, you can use as-usual insert query of SQL without ContentValues.
	public long insertData(WordDefinition wordDefinition) {
		SQLiteDatabase database=this.getWritableDatabase();
		ContentValues values=new ContentValues();					//ContentValues class is used to insert value according to own column.
		
		values.put(WORD_COLUMN, wordDefinition.word);				//Word Column of "WordDefinition.java" er word
		values.put(DEFINITION_COLUMN, wordDefinition.definition);	//Definition Column of "WordDefinition.java"
		
		return database.insert(DICTIONARY_DATABASE, null, values);
	}

	//update data into database by key & values = HashMap
	public long updateData(WordDefinition wordDefinition) {
		SQLiteDatabase database=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		
		values.put(WORD_COLUMN, wordDefinition.word);
		values.put(DEFINITION_COLUMN, wordDefinition.definition);

		return database.update(DICTIONARY_DATABASE, values, WORD_COLUMN+" =?", new String[]{wordDefinition.word});	//Jodi word-ta equal hoy tobe definition update hobe.
	}

	//delete data into database by key & values = HashMap
	public void deleteData(WordDefinition wordDefinition) {
		SQLiteDatabase database=this.getWritableDatabase();
		String queryString="DELETE FROM "+DICTIONARY_DATABASE+" WHERE "+WORD_COLUMN+" = '"+wordDefinition.word+"'";
		
		database.execSQL(queryString);
	}

	//to get all data in  a ArrayList for the initial time
	public ArrayList<WordDefinition> getAllWords() {
		ArrayList<WordDefinition> arrayList=new ArrayList<WordDefinition>();
		SQLiteDatabase database=this.getReadableDatabase();

		//Ei sob data(ArrayList) theke khujar particular kicu khujar jonno
		String selectAllQueryString="SELECT * FROM "+DICTIONARY_DATABASE;
		Cursor cursor=database.rawQuery(selectAllQueryString, null);		//rawQuery cursor er vitor rakhte hoy

		//Cursor onno kono jaygay thakle first position niye asbe
		if (cursor.moveToFirst()) {
			do {
				//1 column = word and 2 column = word definition
				//WordDefinition wordDefinition = new WordDefinition(cursor.getString(1), cursor.getString(2));
				WordDefinition wordDefinition=new WordDefinition(cursor.getString(cursor.getColumnIndex(WORD_COLUMN)), cursor.getString(cursor.getColumnIndex(DEFINITION_COLUMN)));
				arrayList.add(wordDefinition);				
			} while (cursor.moveToNext());			
		}	
		return arrayList;
	}

	//to get just one word
	public WordDefinition getWordDefinition(String word) {
		SQLiteDatabase database=this.getReadableDatabase();
		WordDefinition wordDefinition=null;
		
		String selectQueryString="SELECT * FROM "+DICTIONARY_DATABASE+ " WHERE "+WORD_COLUMN+" = '"+word+ "'";
		Cursor cursor=database.rawQuery(selectQueryString, null);
		
		if (cursor.moveToFirst()) {
			wordDefinition=new WordDefinition(cursor.getString(cursor.getColumnIndex(WORD_COLUMN)), cursor.getString(cursor.getColumnIndex(DEFINITION_COLUMN)));
		}
		return wordDefinition;
	}

	//to get just one word by id
	public WordDefinition getWordDefinition(long id) {
		SQLiteDatabase database=this.getReadableDatabase();
		WordDefinition wordDefinition=null;
		
		String selectQueryString="SELECT * FROM "+DICTIONARY_DATABASE+ " WHERE "+ITEM_ID_COLUMN+" = '"+id+ "'";
		Cursor cursor=database.rawQuery(selectQueryString, null);
		
		if (cursor.moveToFirst()) {
			wordDefinition=new WordDefinition(cursor.getString(cursor.getColumnIndex(WORD_COLUMN)), cursor.getString(cursor.getColumnIndex(DEFINITION_COLUMN)));
		}
		return wordDefinition;
	}

	//All data inserted by one data transection. Kajh-ta druto korar jonno
	public void initializeDatabaseFortheFirstTime(ArrayList<WordDefinition> wordDefinitions) {
		SQLiteDatabase database=this.getWritableDatabase();
		database.execSQL("BEGIN");
		
		ContentValues contentValues=new ContentValues();
		
		for (WordDefinition wordDefinition : wordDefinitions) {
			contentValues.put(WORD_COLUMN, wordDefinition.word);
			contentValues.put(DEFINITION_COLUMN, wordDefinition.definition);			
			database.insert(DICTIONARY_DATABASE, null, contentValues);
		}
		database.execSQL("COMMIT");
	}
}
