package org.apache.logging.log4j.core.layout;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendedJsonAdapter implements ExtendedJson{
	
	private static final Logger LOG = LogManager.getLogger(ExtendedJsonAdapter.class);
	
	HashMap<String, Object> mixedFields = new HashMap<String, Object>();

	@Override
	public Map<String, Object> getMixedFields() {
		return mixedFields;
	}
	
	public ExtendedJsonAdapter() {
		try {
			mixedFields.put("hostname", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			LOG.warn("Could not get hostname", e);
		}
	}

}
