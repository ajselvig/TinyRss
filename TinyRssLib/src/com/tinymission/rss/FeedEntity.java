package com.tinymission.rss;

import java.lang.reflect.Method;

import org.xml.sax.Attributes;

import android.util.Log;


/** Base class for feed entities that provides automatic property assignment.
 *
 */
public class FeedEntity {
	
	
	public FeedEntity(Attributes attributes) {
		if (attributes != null) {
			for (int i=0; i < attributes.getLength(); i++) {
				String name = attributes.getLocalName(i);
				String value = attributes.getValue(i);
				setProperty(name, value);
			}
		}
	}
	
	/** Sets the string value of a property by name.
	 * 
	 * @param name the name of the property
	 * @param value the string representation of the property value
	 */
	public void setProperty(String name, String value) {
		String methodName = "set" + name;
		String logTag = "FeedEntity: "+getClass().getName();
		try {
			for (Method m: getClass().getMethods()) {
				if (m.getName().equalsIgnoreCase(methodName) && m.getParameterTypes().length == 1) { // a single argument method with the correct name
					if (m.getParameterTypes()[0] == String.class) { // it's just a string argument, pass the string
						m.invoke(this, value);
						//Log.v(logTag, "Assigned property " + name + " string value: " + value);	
					}
					else if (m.getParameterTypes()[0] == Integer.class) { // it's an int argument, we can handle that
						int intVal = Integer.parseInt(value);
						m.invoke(this, intVal);
						//Log.v(logTag, "Assigned property " + name + " int value: " + Integer.toString(intVal));	
					}
					return;
				}
			}
			Log.w(logTag, "Couldn't find property setter " + methodName);
		} catch (Exception ex) {
			Log.w(logTag, "Error setting property: " + name);
			Log.w(logTag, ex.getMessage());
		}
	}
	
}
