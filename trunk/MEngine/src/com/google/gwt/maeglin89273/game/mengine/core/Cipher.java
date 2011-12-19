/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.core;

import com.googlecode.gwt.crypto.bouncycastle.DataLengthException;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import com.googlecode.gwt.crypto.client.TripleDesCipher;

/**
 * @author Maeglin Liao
 *
 */
public final class Cipher {
	private final static byte[] KEY = new byte[]{(byte)-13,(byte)23,
		(byte)54,(byte)-29,(byte)12,(byte)-43,(byte)-32,(byte)-10,(byte)75,(byte)82,
		(byte)-91,(byte)55,(byte)-36,(byte)-82,(byte)57,(byte)73,(byte)53,(byte)-83};
	private TripleDesCipher cipher ;
	public Cipher(){
		cipher= new TripleDesCipher();
		cipher.setKey(KEY);
	}
	public String encrypt(String plainText){
		try {
		  return cipher.encrypt(plainText);
		} catch (DataLengthException e1) {
		  e1.printStackTrace();
		} catch (IllegalStateException e1) {
		  e1.printStackTrace();
		} catch (InvalidCipherTextException e1) {
		  e1.printStackTrace();
		}
		return null;
	}
	public String decrypt(String encryptedText){
		try {
		  return cipher.decrypt(encryptedText);
		} catch (DataLengthException e1) {
		  e1.printStackTrace();
		} catch (IllegalStateException e1) {
		  e1.printStackTrace();
		} catch (InvalidCipherTextException e1) {
		  e1.printStackTrace();
		}
		return null;
	}
}
