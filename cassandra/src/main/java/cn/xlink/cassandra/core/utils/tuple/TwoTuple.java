package cn.xlink.cassandra.core.utils.tuple;

import java.io.Serializable;

public class TwoTuple<A,B> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6468787030660401201L;
	
	public final A first;
	public final B second;
	
	public TwoTuple(A a, B b) {
		first = a;
		second = b;
	}

}
