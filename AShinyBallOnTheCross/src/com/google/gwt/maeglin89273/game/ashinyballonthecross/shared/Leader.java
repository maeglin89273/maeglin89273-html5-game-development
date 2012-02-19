/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Maeglin Liao
 *
 */
public class Leader implements IsSerializable{
	
	private int position;
	private String id;
	private Date date;
	private long totalScores;
	public Leader(int pos,String nickname,Date date,long totalScores){
		position = pos;
		this.id = nickname;
		this.date = date;
		this.totalScores = totalScores;
	}
	private Leader(){
		
	}
	public int getPosition(){
		return position;
	}
	public String getID(){
		return id;
	}
	@SuppressWarnings("deprecation")
	public String getDateText(){
		return (date.getYear()+1900)+"/"+(date.getMonth()+1)+"/"+date.getDate();
	}
	public Date getDate(){
		return date;
	}
	public long getTotal(){
		return totalScores;
	}
}
