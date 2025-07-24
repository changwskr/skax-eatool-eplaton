package com.skax.eatool.mba.ac.eplatonac;


public abstract interface IEPlatonBizAction {

  // Methods
  void preAct(IEvent iEvent);
  IEvent act(IEvent iEvent) ;
  void postAct(IEvent iEvent);
}




