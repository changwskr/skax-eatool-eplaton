package com.skax.eatool.mbc.ac.eplatonac;

import org.springframework.stereotype.Component;

@Component
public interface ITCF {

  void initialize();

  void cleanup();

  boolean isAvailable();

}
