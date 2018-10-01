package com.kamaldictionary;
/*
Input & output stream er jonno ei class use hobe.
Ekhane duita parameter ase ekta Word & another alldefinition arraylist.
*/
import java.util.ArrayList;

public class WordDefinition {
	String word,definition;

	//create the constructor
	public WordDefinition(String word,ArrayList<String> alldefinition) {
		this.word=word;       //assign the word

		//Onk gulo string ekta String a rakhar jonno StringBuilder for alldefiniton ArrayList<String>
		StringBuilder stringBuilder=new StringBuilder();
		//for each loop diye ekta string er sathe ekta string add kore ekta stringBuilder rakhlam
		for (String string : alldefinition) {
			stringBuilder.append(string);
		}
		this.definition=stringBuilder.toString();  //assign the definition
	}
	//create the another constructor just for string
	public WordDefinition(String word,String alldefinition) {
		this.word=word;                   //assign the word
		this.definition=alldefinition;    //assign the definition
	}
}
