package com.skax.eatool.mbc.ac.eplatonac;


public abstract interface IEPlatonBizAction {

  // Methods
  void preAct(IEvent iEvent);
  IEvent act(IEvent iEvent) ;
  void postAct(IEvent iEvent);
}




