import 'package:flutter/material.dart';

typedef void LocalChangeCallback(Locale locale);

class APPLIC {


  final List<String> supportLanguages = ['en', 'zh'];

  LocalChangeCallback onLocaleChangeCallback;

  Iterable<Locale> supportLocales() => supportLanguages.map<Locale>((language) => new Locale(language));


  static final APPLIC _applic = APPLIC._internal();

  APPLIC._internal() {
  }

  factory APPLIC() {
    return _applic;
  }

}
APPLIC applic = new APPLIC();