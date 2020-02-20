package com.ecar.energybite.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by navin on 9/28/2019.
 */
public class CollectionUtility {

	public static boolean isCollectionNullOrEmptyByRemovingNullObj(Collection< ? > c) {
		if (c == null) {
			return true;
		}
		Iterator itr = c.iterator ();
		if(itr.hasNext ()) {
			if(itr.next () == null) {
				itr.remove ();
			}
		}
		return c.isEmpty ();
	}

	public static boolean isCollectionNullOrEmpty(Collection< ? > c) {
		return c == null || c.isEmpty ();
	}

	public static <T> boolean isArrayNullOrEmpty(T[] c) {
		return c == null || c.length == 0;
	}

	public static boolean isMapNullOrEmpty(Map< ? , ? > c) {
		return c == null || c.isEmpty ();
	}

	public static int getMapSize(Map< ? , ? > c) {
		return c != null ? c.size() : 0;
	}

	public static boolean hasAnyTrue(boolean[] arr) {
		if(arr != null && arr.length > 0) {
			for (boolean val : arr) {
				if(val) {
					return val;
				}
			}
		}
		return false;
	}

	public static boolean hasMoreThanOneEnabled(boolean[] arr) {
		boolean isValid = false;
		if(arr != null && arr.length > 0) {
			for (boolean val : arr) {
				if(val && !isValid) {
					isValid = true;
				} else if(val && isValid) {
					return true;
				}
			}
		}
		return false;
	}
}

