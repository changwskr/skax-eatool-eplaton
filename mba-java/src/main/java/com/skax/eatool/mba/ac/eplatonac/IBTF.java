package com.skax.eatool.mba.ac.eplatonac;



import java.rmi.*;



public interface IBTF
{
  public EPlatonEvent execute(EPlatonEvent event) ;
  public abstract boolean BTF_SPinit() ;
  public abstract boolean BTF_SPmiddle() ;
  public abstract boolean BTF_SPend() ;

}
