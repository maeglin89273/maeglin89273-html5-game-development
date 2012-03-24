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
public class PlayerCreatedResponse implements IsSerializable{
	public Date createTime;
	public CreateStatus status;
	public PlayerCreatedResponse(CreateStatus status){
		this(status, null);
	}
	public PlayerCreatedResponse(Date createTime){
		this(CreateStatus.SUCCESS,createTime);
	}
	private PlayerCreatedResponse(CreateStatus status,Date createTime){
		this.status=status;
		this.createTime=createTime;
	}
	private PlayerCreatedResponse(){
		
	}
	
	public CreateStatus getStatus(){
		return status;
	}
	public Date getCreateTime(){
		return createTime;
	}
}
