package com.kamaldictionary;
/*
dictionary.text file theke read/load kore (WordDefinition.java) er ArrayList er vitor write korbo ja Database a thakbe.
Ekhane duita parameter pass korbe ekta BufferReader & DictionaryDatabaseHelper tar age Database create kore nite hobe
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

//Text file theke Dictionary load korbe
public class DictionaryLoader {

	//There are two parameter for reading data by BufferReader & writing data into database by DictionaryDatabaseHelper
	public static void loadData(BufferedReader bufferedReader, DictionaryDatabaseHelper dictionaryDatabaseHelper) {
		
		ArrayList<WordDefinition> allWords=new ArrayList<WordDefinition>();
		
		try {
			BufferedReader fileReader=bufferedReader;
			
			try {
				int c=17;
				c=fileReader.read();
				while (c!=(-1)) {
					
					StringBuilder stringBuilder=new StringBuilder();

					while ((char)c!='\n'&&c!=-1) {			//Jotokhn porjonto notun line pacche na totokhn porjonto read korbe.
						try {
							stringBuilder.append((char)c);
						} catch (Exception e) {
							System.out.println(stringBuilder.length());
							//e.printStackTrace();
						}
						c= fileReader.read();
						if (c==-1) {
							return;
						}
					}
					
					String wordString=stringBuilder.toString();
					
					ArrayList<String> definition=new ArrayList<String>();
					while (c=='\n'||c=='\t') {
						c= fileReader.read();
						if (c=='\n'||c=='\t'||c=='\r') {						//jotokhn porjonto new line, tab, na pabe tokhn porjonto read korbe.
							StringBuilder stringBuilder2=new StringBuilder();
							while (c!='\n') {
								stringBuilder2.append((char)c);
								c=fileReader.read();
							}
							String definitionString=stringBuilder2.toString();
							definition.add(definitionString);
						}else {
							break;
						}
					}
					
					wordString=wordString.trim();
					//Logger.log("word Loaded: "+(++counter)+" :"+wordString);
					allWords.add(new WordDefinition(wordString, definition));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				//Database a allWords pass korse
				dictionaryDatabaseHelper.initializeDatabaseFortheFirstTime(allWords);
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
