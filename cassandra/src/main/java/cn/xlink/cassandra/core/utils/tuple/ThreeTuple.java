package cn.xlink.cassandra.core.utils.tuple;

import java.io.Serializable;

public class ThreeTuple<A, B, C> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 499479028510975618L;
	/**
     *
     */

    public final A first;
    public final B second;
    public final C third;


    public ThreeTuple(A a, B b, C c) {
        first = a;
        second = b;
        third = c;
    }

}
