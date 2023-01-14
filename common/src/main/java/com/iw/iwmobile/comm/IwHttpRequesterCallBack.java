/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile.comm;

/**
 *
 * @author helio-ubu1404
 */
public interface IwHttpRequesterCallBack<T> {

  /**
   * Called when an asynchronous call completes with error.
   * 
   * @param rsError
   */
  void onFailure(MobRecordsetError rsError);

  /**
   * Called when an asynchronous call completes successfully.
   * 
   * @param result the return value of the remote produced call
   */
  void onSuccess(T result);
}
