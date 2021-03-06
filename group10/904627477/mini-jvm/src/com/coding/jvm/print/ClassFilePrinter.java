package com.coding.jvm.print;

import com.coding.jvm.clz.ClassFile;
import com.coding.jvm.loader.ClassFileLoader;

public class ClassFilePrinter {
	ClassFile clzFile = null;
	public ClassFilePrinter(ClassFile clzFile){
		this.clzFile = clzFile;
	}
	
	public void print(){
		
		if(clzFile.getAccessFlag().isPublicClass()){
			System.out.println("Access flag : public  ");
		}
		System.out.println("Class Name:"+ clzFile.getClassName());
		
		System.out.println("Super Class Name:"+ clzFile.getSuperClassName());
		
		System.out.println("minor version:" + clzFile.getMinorVersion());
		
		System.out.println("major version:" + clzFile.getMinorVersion());
		
		ConstantPoolPrinter cnstPoolPrinter = new ConstantPoolPrinter(clzFile.getConstantPool());
		cnstPoolPrinter.print();
		
		
		
		
	}
	
	public static void main(String[] args){
		//String path = "C:\\Users\\liuxin\\git\\coding2017\\liuxin\\mini-jvm\\bin";
		String path = System.getProperty("user.dir")+"/bin/EmployeeV1.class";
		ClassFileLoader loader = new ClassFileLoader();
		loader.addClassPath(path);
		//String className = "com.coderising.jvm.test.EmployeeV1";
		//ClassFile clzFile = loader.loadClass(className);
		ClassFile  clzFile = loader.loadFile(path);
		ClassFilePrinter printer  = new ClassFilePrinter(clzFile);
		
		printer.print();
	}
}
