package jp.gr.java_conf.ussiy.app.propedit.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Encode implements Serializable {

	private String name;

	private String code;

	private int no;

	public Encode() {

	}

	public Encode(int no, String name, String code) {

		this.no = no;
		this.name = name;
		this.code = code;
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {

		ois.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {

		oos.defaultWriteObject();
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {

		this.code = code;
	}

	public int getNo() {

		return no;
	}

	public void setNo(int no) {

		this.no = no;
	}

	public String toString() {

		return getName();
	}

}