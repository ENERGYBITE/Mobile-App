package com.ecar.energybite.http;

/**
 * Created by anoop.gupta on 10/18/2016.
 */
public interface DataCallback<T> {

	void executeOnSuccess(T ret);

	void executeOnSuccess(T ret, String tag);

	void executeOnError(T ret);
}
