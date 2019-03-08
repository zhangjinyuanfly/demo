import 'dart:convert';
import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart' show rootBundle;

import 'application.dart';

class Translations {

  static Map<dynamic, dynamic> _localizedValues;

  Locale locale;
  Translations(Locale locale) {
    this.locale = locale;

  }

  static Translations of(BuildContext context) {
    return Localizations.of<Translations>(context, Translations);
  }


  static Future<Translations> load(Locale locale) async {
    Translations translations = Translations(locale);
    String language = applic.supportLanguages.contains(locale.languageCode)? locale.languageCode : "en";

    String jsonContent = await rootBundle.loadString("assets/locale/i18n_$language.json");
    _localizedValues = json.decode(jsonContent);
    return translations;
  }

  String text(String key) {
    if(_localizedValues == null) {
      return key;
    }
    return _localizedValues[key]?? key;
  }

  // get currentLanguage => locale.languageCode;
}

class TranslationsDelegate extends LocalizationsDelegate<Translations> {
  @override
  bool isSupported(Locale locale) {
    // TODO: implement isSupported
    return applic.supportLanguages.contains(locale.languageCode);
  }

  @override
  Future<Translations> load(Locale locale) {
    // TODO: implement load
    return Translations.load(locale);
  }

  @override
  bool shouldReload(LocalizationsDelegate<Translations> old) {
    // TODO: implement shouldReload
    return false;
  }

}

class SpecificLocalizationDelegate extends LocalizationsDelegate<Translations> {

  final Locale _locale;
  SpecificLocalizationDelegate(this._locale);

  @override
  bool isSupported(Locale locale) {
    // TODO: implement isSupported
    return _locale != null;
  }

  @override
  Future<Translations> load(Locale locale) {
    // TODO: implement load
    return Translations.load(_locale);
  }

  @override
  bool shouldReload(LocalizationsDelegate<Translations> old) {
    // TODO: implement shouldReload
    return true;
  }
}