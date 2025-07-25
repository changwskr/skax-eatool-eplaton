package com.skax.eatool.mbc.ac.eplatonac;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


public interface ISTF
{
  public EPlatonEvent execute(EPlatonEvent event) ;
  public abstract boolean STF_SPinit() ;
  public abstract boolean STF_SPmiddle() ;
  public abstract boolean STF_SPend() ;

}
