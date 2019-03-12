import 'dart:async';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter_easyrefresh/easy_refresh.dart';
import 'package:flutterdemo/scrollphysics/scroll_physics.dart';

import 'sliver_header.dart';

class UserProfilePage extends StatefulWidget {
  _UserProfilePageState createState() => _UserProfilePageState();
}

class _UserProfilePageState extends State<UserProfilePage> {
  List<String> _dataList = [
    '11',
    '222',
    '3',
    '4',
    '111',
    '222',
    '3',
    '4',
    '111',
    '222',
    '3',
    '4',
    '111',
    '222',
    '3',
    '4'
  ];

  ScrollController _scrollController = ScrollController();
  ScrollPhysics _physics;
  ScrollOverListener _scrollOverListener;

  void _topOver() {
    print('dddddddddddddd');
  }

  void _bottomOver() {}

  @override
  void initState() {
    super.initState();
    _headerHeight = 250;
    _headerScale = 1.0;
    _scrollOverListener = new ScrollOverListener(
        topOver: _topOver,
        bottomOver: _bottomOver,
        justScrollOver: true,
        refresh: false,
        loadMore: false);
//    _physics = RefreshAlwaysScrollPhysics(scrollOverListener: _scrollOverListener, headerPullBackRecord: false, footerPullBackRecord: false);
    _physics = MyScrollPhysics();
    _scrollController.addListener(() {
//      print('scroll = $_scrollController.position.pixels');
    });
  }

  GlobalKey<MyHeaderState> headerKey = GlobalKey<MyHeaderState>();
  GlobalKey<EasyRefreshState> _easyRefreshKey =
      new GlobalKey<EasyRefreshState>();

  @override
  Widget build(BuildContext context) {
    return Stack(
      alignment: Alignment.topCenter,
      children: <Widget>[
        EasyRefresh(
          key: _easyRefreshKey,
          onRefresh: _refresh,
          refreshHeader: MyHeader(
            key: headerKey,
            isFloat: true,
            bgColor: Colors.transparent,
            heightChange: _headerListener,
          ),
          child: CustomScrollView(
            slivers: <Widget>[
              SliverPersistentHeader(
                delegate: SliverHeader(
                  minHeight: 70,
                  maxHeight: _headerHeight,
                  child: Image.network(
                    'http://p3.so.qhmsg.com/bdr/1728__/t01becfc00e2d83cb28.jpg',
                    fit: BoxFit.cover,
                  ),
                ),
                pinned: true,
              ),
              SliverAppBar(
                pinned: true,
                expandedHeight: 70,
                leading: Text('fff'),
              ),
              SliverFixedExtentList(
                  itemExtent: 70.0,
                  delegate: SliverChildBuilderDelegate(
                    (context, index) {
                      return new Container(
                          height: 70.0,
                          child: Card(
                            child: new Center(
                              child: new Text(
                                'woshi $index',
                                style: new TextStyle(fontSize: 18.0),
                              ),
                            ),
                          ));
                    },
                    childCount: 100,
                  )),
            ],
          ),
        ),
        Container(
          margin: EdgeInsets.fromLTRB(0,10,0,0),
          alignment: Alignment.centerLeft,
          height: 70,
          child: Icon(Icons.arrow_back_ios),
        ),
      ],
    );
  }

  Future<Null> _refresh() async {
//    _dataList.clear();
//    await _loadFirstListData();
    return;
  }

  void _headerListener(double newHeight) {
    setState(() {
      _headerHeight = 250 + newHeight;
      _headerScale = 1.0 + newHeight / 100;
    });
  }
}

class MyHeader extends ClassicsHeader {
  // 提示刷新文字
  final String refreshText;

  // 准备刷新文字
  final String refreshReadyText;

  // 正在刷新文字
  final String refreshingText;

  // 刷新完成文字
  final String refreshedText;

  // 背景颜色
  final Color bgColor;

  // 字体颜色
  final Color textColor;

  // 触发刷新的高度
  final double refreshHeight;

  // 是否浮动
  final bool isFloat;

  // 显示额外信息(默认为时间)
  final bool showMore;

  // 更多信息
  final String moreInfo;

  // 更多信息文字颜色
  final Color moreInfoColor;
  HeightChange heightChange;

  // 构造函数
  MyHeader({
    @required GlobalKey<RefreshHeaderState> key,
    this.refreshText: "Pull to refresh",
    this.refreshReadyText: "Release to refresh",
    this.refreshingText: "Refreshing...",
    this.refreshedText: "Refresh finished",
    this.bgColor: Colors.blue,
    this.textColor: Colors.white,
    this.moreInfoColor: Colors.white,
    this.refreshHeight: 70.0,
    this.isFloat: false,
    this.showMore: false,
    this.moreInfo: "Updated at %T",
    this.heightChange,
  }) : super(
          key: key,
          refreshText: refreshText,
          refreshReadyText: refreshReadyText,
          refreshingText: refreshingText,
          refreshedText: refreshedText,
          bgColor: bgColor,
          textColor: textColor,
          moreInfoColor: moreInfoColor,
          refreshHeight: refreshHeight,
          isFloat: isFloat,
          showMore: showMore,
          moreInfo: moreInfo,
        );

  @override
  MyHeaderState createState() {
    return MyHeaderState(heightChange);
  }
}

double _headerHeight = 0;
double _headerScale = 0;

typedef void HeightChange(double newHeight);

class MyHeaderState extends ClassicsHeaderState {
  HeightChange heightChange;

  MyHeaderState(this.heightChange);

  @override
  void updateHeight(double newHeight) {
    super.updateHeight(newHeight);

    if (heightChange != null) {
      heightChange(newHeight);
    }

    print('height = $_headerHeight');
  }
}

// 顶部栏裁剪
//class TopBarClipper extends CustomClipper<Path> {
//  // 宽高
//  double width;
//  double height;
//
//  TopBarClipper(this.width, this.height) {
//    print('width = $width');
//  }
//
//  @override
//  Path getClip(Size size) {
//    Path path = new Path();
//    path.moveTo(0.0, 0.0);
//    path.lineTo(width, 0.0);
//    path.lineTo(width, height / 2);
//    path.lineTo(0.0, height);
//    return path;
//  }
//
//  @override
//  bool shouldReclip(CustomClipper<Path> oldClipper) {
//    return true;
//  }
//}
