package com.instagrand.protoui;

import java.util.Random;
import java.util.Vector;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for Storing information on individual pictures
 * It can handle either comments or questions, depending on how we want to implement that feature
 * @author Jacob Iott
 * @version 1.0
 */

public class PictureItem implements Parcelable {
	private String user;
	private Bitmap picture;
	private Vector<Comment> comments;
	private Vector<Question> questions;
	private String locName;
	private String location;
	private String description;
	private String title;
	private int id = 0;
	Random randomGenerator = new Random();
	
	/**
	 * Created to implement Parcelable
	 */
	public static final Parcelable.Creator<PictureItem> CREATOR = new Parcelable.Creator<PictureItem>() {
		public PictureItem createFromParcel(Parcel in) {
			return new PictureItem(in);
		}

		public PictureItem[] newArray(int size) {
				return new PictureItem[size];
		}
	};
	
	/**
	 * Constructor for New Picture
	 * @param u The Picture-Taker/Uploder's User name
	 * @param bm the picture in bitmap form
	 * @param ln The name of the location in which the picture was taken
	 * @param coords The String given to Google maps to find this picture's location
	 */
	public PictureItem(String t, String d, String u, Bitmap bm, String ln, String coords){
		if(id == 0)
			id = randomGenerator.nextInt(1000000) + 1;
		title = t;
		description = d;
		user = u;
		picture = bm;
		comments = new Vector<Comment>();
		questions = new Vector<Question>();
		locName = ln;
		location = coords;
	}
	
	/**
	 * Constructor for Picture with comments
	 * @param u The Picture-Taker/Uploder's User name
	 * @param bm the picture in bitmap form
	 * @param coms A vector full of comments about the picture
	 * @param ln The name of the location in which the picture was taken
	 * @param coords The String given to Google maps to find this picture's location
	 */
	public PictureItem(String t, String d, String u, Bitmap bm, Vector<Comment> coms, String ln, String coords){
		if(id == 0)
			id = randomGenerator.nextInt(1000000) + 1;
		title = t;
		description = d;
		user = u;
		picture = bm;
		comments = coms;
		questions = new Vector<Question>();
		locName = ln;
		location = coords;
	}
	
	/**
	 * Constructor for Picture with questions
	 * @param u The Picture-Taker/Uploder's User name
	 * @param bm the picture in bitmap form
	 * @param questions A vector full of questions about the picture
	 * @param ln The name of the location in which the picture was taken
	 * @param coords The String given to Google maps to find this picture's location
	 */
	public PictureItem(String t, String d, String u, Vector<Question> quest, Bitmap bm,  String ln, String coords){
		if(id == 0)
			id = randomGenerator.nextInt(1000000) + 1;
		title = t;
		description = d;
		user = u;
		picture = bm;
		questions = quest;
		comments = new Vector<Comment>();
		locName = ln;
		location = coords;
	}
	
	

	/**
	 * Adds a new comment
	 * @param c the new comment
	 */
	public void addComment(Comment c){
		comments.add(c);
	}
	
	/**
	 * Adds a new question
	 * @param q the new question
	 */
	public void addQuestion(Question q){
		questions.add(q);
	}
	
	/**
	 * Returns the comments about this picture
	 * @return
	 */
	public Vector<Comment> getComments(){
		return comments;
	}
	
	/**
	 * Returns the questions asked about this picture
	 * @return
	 */
	public Vector<Question> getQuestions(){
		return questions;
	}
	
	public void setPicture(Bitmap b){
		picture = b;
	}
	/**
	 * Returns the picture
	 * @return the picture
	 */
	public Bitmap getPicture(){
		return picture;
	}
	
	/**
	 * sets the name of the Location in which the picture was taken
	 * @return
	 */
	public void setLocationName(String loc){
		locName = loc;
	}
	
	/**
	 * Returns the name of the Location in which the picture was taken
	 * @return
	 */
	public String getLocationName(){
		return locName;
	}
	
	/**
	 * Returns the location of the picture on Google Maps
	 * @return
	 */
	public String getMapLocation(){
		return location;
	}
	
	/**
	 * Returns the Username of the picture's taker/uploader
	 * @return
	 */
	public String getUser(){
		return user;
	}
	
	/**
	 * Returns the description of the picture
	 * @return 
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Returns the title of the picture
	 * @return
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Sets the title of the Picture
	 * @param titl
	 */
	public void setTitle(String titl) {
		title = titl;
	}
	
	/**
	 * Sets the image's Description
	 * @param desc
	 */
	public void setDescription(String desc){
		description = desc;
	}

	
	/**
	 * Required to implement parcelable
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Creates a Parcel out of the instance.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(user);
		dest.writeString(locName);
		dest.writeString(location);
		dest.writeString(description);
		dest.writeParcelable(picture, flags);
		dest.writeInt(comments.size());
		for(int i = 0; i < comments.size(); i++){
			dest.writeParcelable(comments.get(i), flags);
		}
		dest.writeInt(questions.size());
		for(int i = 0; i < questions.size(); i++){
			dest.writeParcelable(questions.get(i), flags);
		}
	}
	
	/**
	 * Constructor to create a PictureItem from a Parcel
	 * @param in
	 */
	public PictureItem(Parcel in) {
		// TODO Auto-generated constructor stub
		title = in.readString();
		user = in.readString();
		locName = in.readString();
		location = in.readString();
		description = in.readString();
		picture = (Bitmap) in.readParcelable(getClass().getClassLoader());
		comments = new Vector<Comment>();
		comments = new Vector<Comment>();
		questions = new Vector<Question>();
		int coms = in.readInt();
		Object theNext;
		while(coms > 0){
			theNext = in.readParcelable(getClass().getClassLoader());
			if (theNext instanceof Comment){
				comments.add((Comment) theNext);
			} else if (theNext instanceof Question){
				questions.add((Question) theNext);
			}
			coms--;
		}
		int quests = in.readInt();
		while(quests > 0){
			theNext = in.readParcelable(getClass().getClassLoader());
			if (theNext instanceof Comment){
				comments.add((Comment) theNext);
			} else if (theNext instanceof Question){
				questions.add((Question) theNext);
			}
			quests--;
		}
	}
	
	/**
	 * Add a new comment to the picture
	 * @param u
	 * @param c
	 */
	public void addComment(String u, String c){
		comments.add(new Comment(u, c));
	}
	
	/**
	 * Add a new Question to the picture
	 * @param u
	 * @param c
	 */
	public void addQuestion(String u, String c){
		questions.add(new Question(u, c));
	}
	
	/**
	 * Set the picture's user
	 * @param theUse
	 */
	public void setUser(String theUse) {
		user = theUse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
