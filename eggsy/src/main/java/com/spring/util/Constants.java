package com.spring.util;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.Compression;
import org.apache.cassandra.thrift.ConsistencyLevel;

public class Constants {
	public static ConsistencyLevel CONSISTENCY_LEVEL = ConsistencyLevel.ONE;
	public static Compression COMPRESSION = Compression.NONE;
	public static ColumnParent USER_PARENT = new ColumnParent("USER");
	public static String QUERY_SELECT = "SELECT";
	public static String QUERY_SAVE = "SAVE";


}
