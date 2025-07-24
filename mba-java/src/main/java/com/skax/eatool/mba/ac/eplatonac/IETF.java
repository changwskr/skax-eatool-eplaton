package com.skax.eatool.mba.ac.eplatonac;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.*;



public interface IETF
{
  public EPlatonEvent execute(EPlatonEvent event) ;
  public abstract boolean ETF_SPinit() ;
  public abstract boolean ETF_SPmiddle() ;
  public abstract boolean ETF_SPend() ;

}
