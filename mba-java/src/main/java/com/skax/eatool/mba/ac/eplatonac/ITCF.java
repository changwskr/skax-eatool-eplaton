package com.skax.eatool.mba.ac.eplatonac;

import org.springframework.stereotype.Component;

@Component
public interface ITCF {

  void initialize();

  void cleanup();

  boolean isAvailable();

}
