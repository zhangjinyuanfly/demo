import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutterdemo/generated/application.dart';
import 'package:flutterdemo/generated/translations.dart';
import 'package:flutterdemo/page/home_page.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {

  @override
  State<StatefulWidget> createState() {
    return MyAppState();
  }

}

class MyAppState extends State<MyApp> {

  SpecificLocalizationDelegate _localizationsDelegate;

  @override
  void initState() {
    super.initState();
    _localizationsDelegate = SpecificLocalizationDelegate(null);
    applic.onLocaleChangeCallback = onLocaleChange;
  }


  onLocaleChange(Locale locale) {
    setState(() {
      _localizationsDelegate = SpecificLocalizationDelegate(locale);
      print('onchange');
    });
  }

  @override
  Widget build(BuildContext context) {

    return new MaterialApp(
      title: 'myApp',
      theme: ThemeData.light(),
      home: HomePage(),

      localizationsDelegates: [
        GlobalWidgetsLocalizations.delegate,
        GlobalMaterialLocalizations.delegate,
        TranslationsDelegate(),
      //  _localizationsDelegate,
      ],
      supportedLocales: applic.supportLocales(),
    );
  }
}
