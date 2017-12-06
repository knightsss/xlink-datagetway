package cn.xlink.data.core.utils;

/**
 * 数据端点数据类型
 * 
 * @author shenweiran(shenweiran@xlink.cn)
 * @date 2015年10月29日 下午2:48:46
 */
public enum DataType {
	/**
	 * 未知类型
	 */
	Unknown(-1,"unknown","UNKNOWN","UNKNOWN"),
	/**
	 * 布尔类型
	 */
	Boolean(1,"bool","BOOLEAN",""),
	/**
	 * 单字节
	 */
	Byte(2,"uint8","SMALLINT","LONG"),
	/**
	 * 16位短整型
	 */
	Short(3, "int16","SMALLINT","LONG"),
	/**
	 * 32位整形
	 */
	Int(4,"int32","INT","LONG"),
	/**
	 * 浮点
	 */
	Float(5,"float","DOUBLE","FLOAT"),
	/**
	 * 字符串
	 */
	String(6,"string","VARCHAR","STRING"),
	/**
	 * 字节数组
	 */
	ByteArray(7,"bytearray","VARBINARY","STRING"),
	/**
	 * 16位短整型无符号
	 */
	UnsignedShort(8,"uint16","INT","LONG"),
	/**
	 * 32位整型无符号
	 */
	UnsignedInt(9,"uint32","BIGINT","LONG"),

	/**
	 * 数据端点类型
	 */
	Datapoint(20, "datapoint", "DATAPOINT",""),

	/**
	 * 时间戳
	 */
	Timestamp(21, "timestamp", "TIMESTAMP",""),
	;

	private final int type;
	private final String desc;
	private final String sqlDesc;
	private final String druidDesc;

	private DataType(int type, String desc, String sqlDesc, String druidDesc) {
		this.type = type;
		this.desc = desc;
		this.sqlDesc = sqlDesc;
		this.druidDesc = druidDesc;
	}

	public int type() {
		return type;
	}
	
	public String desc(){
		return desc;
	}

	public String sqlDesc() {
		return sqlDesc;
	}

	public String druidDesc() {
		return druidDesc;
	}

	public static final DataType fromType(int type) {
		for (DataType dataType : values()) {
			if (dataType.type == type) {
				return dataType;
			}
		}
		return Unknown;
	}
	
	public static final DataType fromDesc(String desc){
		for(DataType dataType:values()){
			if (dataType.desc().equals(desc)) {
				return dataType;
			}
		}
		return Unknown;
	}

	public static final boolean checkMetricType(DataType dataType) {
		switch (dataType) {
			case Int:
			case Float:
			case Short:
			case Byte:
			case UnsignedInt:
			case UnsignedShort:
				return true;
			default:
				return false;
		}
	}


}
