package cn.xlink.cassandra.core.exception;

/**
 * 自定义异常类
 * Created by Zhengzhenxie on 2017/9/20.
 */
public class XlinkException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5073198161914413486L;

	public XlinkException(String message) {
        super(message);
    }

    public XlinkException(Throwable cause) {
        super(cause);
    }

    public XlinkException(String message, Throwable cause) {
        super(message, cause);
    }

}
