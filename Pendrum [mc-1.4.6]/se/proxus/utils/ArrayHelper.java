package se.proxus.utils;

public class ArrayHelper<T1, T2> {

	public Object obj0 = null;
	public Object obj1 = null;

	public ArrayHelper(Object obj0, Object obj1) {
		this.obj0 = obj0;
		this.obj1 = obj1;
	}

	public Object[] getObject() {
		return (new Object[] {this.obj0, this.obj1});
	}
}