package com.coding.basic.stack;

import java.util.EmptyStackException;

import com.coding.basic.ArrayList;

public class Stack {
	private ArrayList elementData = new ArrayList();
	
	public void push(Object o){	
		elementData.add(o);
	}
	
	public Object pop(){
		if(elementData.size()==0){
			throw new EmptyStackException();
		}
		Object obj = elementData.remove(size()-1);
		return obj;
	}
	
	public Object peek(){
		if(elementData.size()==0){
			throw new EmptyStackException();
		}
		Object obj = elementData.get(size()-1);
		return obj;
	}
	
	public boolean isEmpty(){
		if(elementData.size()==0){
			return true;
		}
		return false;
	}
	
	public int size(){
		return elementData.size();
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		while(!this.isEmpty()){
			if(sb.length()!=0){
				sb.append(",");
			}
			sb.append(this.pop());
		}
		return sb.toString();
	}
}
