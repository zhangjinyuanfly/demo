import 'dart:async';

import 'package:event_bus/event_bus.dart';
import 'package:flutter/material.dart';
import 'package:flutterdemo/generated/application.dart';
import 'package:flutterdemo/generated/translations.dart';
import 'package:flutterdemo/page/home_page.dart';
import 'package:flutterdemo/second_page.dart';
import 'package:rxdart/rxdart.dart';
import 'animation/anim_test.dart';
import 'page/pageview_demo.dart';
import 'page/tab_pageview_demo.dart';
void main() {
  runApp(MaterialApp(home:MyApp()));
}

class MyApp extends StatefulWidget {

  @override
  State<StatefulWidget> createState() {
    return MyAppState();
  }

}

class MyAppState extends State<MyApp> {

//  SpecificLocalizationDelegate _localizationsDelegate;

  EventBus _eventBus;

  PublishSubject subject;

  @override
  void initState() {
    super.initState();
//    _localizationsDelegate = SpecificLocalizationDelegate(null);
//    applic.onLocaleChangeCallback = onLocaleChange;

    _eventBus = EventBus();
    _eventBus.on<String>().listen((bean){
      print('eventbus = $bean');
    });


    _eventBus.on<String>()

        .listen((e){
      print('e = $e');
    });
    _eventBus.on<String>().asBroadcastStream();

    subject = PublishSubject();
    subject.listen((data){

      print('subject = $data');
    },onDone: (){
      print('subject done');
    });

  }


//  onLocaleChange(Locale locale) {
//    setState(() {
//      _localizationsDelegate = SpecificLocalizationDelegate(locale);
//      print('onchange');
//    });
//  }

  @override
  Widget build(BuildContext context) {

    return new MaterialApp(
      title: 'myApp',
      theme: ThemeData.light(),
      // home: HomePage(),
      // home: AnimDemo(),
      // home: DisplayPage(),
//      home: MyHomePage(),
        home:Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
          RaisedButton(onPressed: () {
//          _eventBus.fire('fffff');
            subject.add('asd');
          }
            ,child: Text('fire'),),
            navigtor(),
        ],)

      // localizationsDelegates: [
      //   GlobalWidgetsLocalizations.delegate,
      //   GlobalMaterialLocalizations.delegate,
      //   TranslationsDelegate(),
      // //  _localizationsDelegate,
      // ],
      // supportedLocales: applic.supportLocales(),
    );
  }

  Widget navigtor() {
    return Container(
      child: FloatingActionButton(
        onPressed: (){
          print('ffffffdddd');
          Navigator.of(context).push(new MaterialPageRoute(builder: (BuildContext context){
            print('ffffff');
            return SecondPage();
          }));
        },
        child: Text('跳转新页面'),
      ),

    );
  }

}

