package edu.ztone.timenote.po;

import java.io.Serializable;
import java.util.Date;

public class Diaries implements Serializable {

	private String ddate;
	private int did,weather,mood,fontsize,fontcolor,fontfamily;
	private String dcontent,dweek;
	private String photo1,photo2,photo3,photo4;
	public Diaries(String ddate, int did, int weather, int mood, int fontsize,
			int fontcolor, int fontfamily, String dcontent, String dweek,
			String photo1, String photo2, String photo3, String photo4) {
		super();
		this.ddate = ddate;
		this.did = did;
		this.weather = weather;
		this.mood = mood;
		this.fontsize = fontsize;
		this.fontcolor = fontcolor;
		this.fontfamily = fontfamily;
		this.dcontent = dcontent;
		this.dweek = dweek;
		this.photo1 = photo1;
		this.photo2 = photo2;
		this.photo3 = photo3;
		this.photo4 = photo4;
	}
	public Diaries() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Diaries [ddate=" + ddate + ", did=" + did + ", weather="
				+ weather + ", mood=" + mood + ", fontsize=" + fontsize
				+ ", fontcolor=" + fontcolor + ", fontfamily=" + fontfamily
				+ ", dcontent=" + dcontent + ", dweek=" + dweek + ", photo1="
				+ photo1 + ", photo2=" + photo2 + ", photo3=" + photo3
				+ ", photo4=" + photo4 + "]";
	}
	public String getDdate() {
		return ddate;
	}
	public void setDdate(String ddate) {
		this.ddate = ddate;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public int getWeather() {
		return weather;
	}
	public void setWeather(int weather) {
		this.weather = weather;
	}
	public int getMood() {
		return mood;
	}
	public void setMood(int mood) {
		this.mood = mood;
	}
	public int getFontsize() {
		return fontsize;
	}
	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}
	public int getFontcolor() {
		return fontcolor;
	}
	public void setFontcolor(int fontcolor) {
		this.fontcolor = fontcolor;
	}
	public int getFontfamily() {
		return fontfamily;
	}
	public void setFontfamily(int fontfamily) {
		this.fontfamily = fontfamily;
	}
	public String getDcontent() {
		return dcontent;
	}
	public void setDcontent(String dcontent) {
		this.dcontent = dcontent;
	}
	public String getDweek() {
		return dweek;
	}
	public void setDweek(String dweek) {
		this.dweek = dweek;
	}
	public String getPhoto1() {
		return photo1;
	}
	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}
	public String getPhoto2() {
		return photo2;
	}
	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	public String getPhoto3() {
		return photo3;
	}
	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}
	public String getPhoto4() {
		return photo4;
	}
	public void setPhoto4(String photo4) {
		this.photo4 = photo4;
	}
	
}