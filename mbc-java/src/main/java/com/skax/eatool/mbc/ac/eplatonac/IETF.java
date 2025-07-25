package com.skax.eatool.mbc.ac.eplatonac;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface IETF
{
  public EPlatonEvent execute(EPlatonEvent event) ;
  public abstract boolean ETF_SPinit() ;
  public abstract boolean ETF_SPmiddle() ;
  public abstract boolean ETF_SPend() ;

}
