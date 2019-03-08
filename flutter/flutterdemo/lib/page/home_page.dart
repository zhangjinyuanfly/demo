import 'package:flutter/material.dart';
import 'package:flutterdemo/generated/translations.dart';
import 'user_profile_page.dart';
class HomePage extends StatefulWidget {

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _HomePageState();
  }



}

class _HomePageState extends State<HomePage> {


  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container(
      child: UserProfilePage(),
    );
  }

}