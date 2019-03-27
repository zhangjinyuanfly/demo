import 'dart:io';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'cover_flow.dart';

void main() {
  runApp(new MyApp());
  if (Platform.isAndroid) {
    // 以下两行 设置android状态栏为透明的沉浸。写在组件渲染之后，是为了在渲染后进行set赋值，覆盖状态栏，写在渲染之前MaterialApp组件会覆盖掉这个值。
    SystemUiOverlayStyle systemUiOverlayStyle =
        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
  }
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
          primaryColorBrightness: Brightness.dark,
          platform: TargetPlatform.iOS),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => HomeState();
}

class TabTitle {
  String title;
  int id;

  TabTitle(this.title, this.id);
}

class HomeState extends State<MyHomePage> with SingleTickerProviderStateMixin {
  TabController mTabController;
  PageController mPageController = PageController(initialPage: 0,viewportFraction:0.8);
  List<TabTitle> tabList;
  var currentPage = 0;
  var isPageCanChanged = true;
  var itemwidth = 288.0;

  ValueNotifier<PageController> valueNotifier;

  @override
  void initState() {
    super.initState();
    initTabData();
    mTabController = TabController(
      length: tabList.length,
      vsync: this,
    );
    mTabController.addListener(() {//TabBar的监听
      if (mTabController.indexIsChanging) {//判断TabBar是否切换
    
        print(mTabController.index);
        onPageChange(mTabController.index, p: mPageController);
      }
    });

    valueNotifier = ValueNotifier(PageController());
  }

  initTabData() {
    tabList = [
      new TabTitle('推荐', 10),
      new TabTitle('热点', 0),
      new TabTitle('社会', 1),
      new TabTitle('娱乐', 2),
      new TabTitle('体育', 3),
      new TabTitle('美文', 4),
      new TabTitle('科技', 5),
      new TabTitle('财经', 6),
      new TabTitle('时尚', 7)
    ];
  }
  onPageChange(int index, {PageController p, TabController t}) async {
    currentPage = index;
    if (p != null) {//判断是哪一个切换
      isPageCanChanged = false;
      await valueNotifier.value.animateToPage(index, duration: Duration(milliseconds: 500), curve: Curves.ease);//等待pageview切换完毕,再释放pageivew监听
      isPageCanChanged = true;
    } else {
      if(mTabController != null) {
        print("mTabController1111 = $index");
        mTabController.animateTo(index);//切换Tabbar
      } else {
        print("mTabController = $index");
      }
    }
  }

  // onPageChange(int index, {PageController p, TabController t}) async {
  //   currentPage = index;
  //   if (p != null) {//判断是哪一个切换
  //     isPageCanChanged = false;
  //     await mPageController.animateToPage(index, duration: Duration(milliseconds: 500), curve: Curves.ease);//等待pageview切换完毕,再释放pageivew监听
  //     isPageCanChanged = true;
  //   } else {
  //     if(mTabController != null) {
  //       print("mTabController1111 = $index");
  //       mTabController.animateTo(index);//切换Tabbar
  //     } else {
  //       print("mTabController = $index");
  //     }
  //   }
  // }

  @override
  void dispose() {
    super.dispose();
    mTabController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    

    return Scaffold(
      appBar: AppBar(
        title: Text("首页"),
        backgroundColor: Color(0xffd43d3d),
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.all_inclusive),
        backgroundColor: Color(0xffd43d3d),
        elevation: 2.0,
        highlightElevation: 2.0,
        onPressed: () {},
      ),
      body: Column(
        children: <Widget>[
          Container(
            color: new Color(0xfff4f5f6),
            height: 38.0,
            child: TabBar(
              isScrollable: true,
              //是否可以滚动
              controller: mTabController,
              labelColor: Colors.red,
              unselectedLabelColor: Color(0xff666666),
              labelStyle: TextStyle(fontSize: 16.0),
              tabs: tabList.map((item) {
                return Tab(
                  text: item.title,
                );
              }).toList(),
            ),
          ),
          Expanded(
            // PageView.builder
            child: CoverFlow(
              dismissibleItems:false,
              itemCount: tabList.length,
              dismissedCallback: disposeDismissed,
              currentItemChangedCallback :onCurrPageChanged,
              controllerValueNotifier: valueNotifier,
              // onPageChanged: (index) {
              //   if (isPageCanChanged) {//由于pageview切换是会回调这个方法,又会触发切换tabbar的操作,所以定义一个flag,控制pageview的回调
              //     onPageChange(index);
              //   }
              // },
              
              // controller: mPageController,

              itemBuilder: (BuildContext context, int index) {
                return Padding(
                padding: EdgeInsets.symmetric(
                  vertical: 16.0,
                  horizontal: 8.0,
                ),
                child: GestureDetector(
                  onTap: () {
                    Scaffold.of(context).showSnackBar(SnackBar(
                          backgroundColor: Colors.deepOrangeAccent,
                          duration: Duration(milliseconds: 800),
                          content: Center(
                            child: Text(
                              tabList[index].title,
                              style: TextStyle(fontSize: 25.0),
                            ),
                          ),
                        ));
                  },
                  child: Material(
                    elevation: 5.0,
                    borderRadius: BorderRadius.circular(8.0),
                    child:Container(
                       decoration: BoxDecoration(color: Colors.yellow),
                       child: Text(tabList[index].title),
                     )
                  ),
                ),
              );
                
                // return Text(tabList[index].title);
              },
            ),
          )
        ],
      ),
    );
  }

  disposeDismissed(int item, DismissDirection direction) {
    tabList.removeAt(item % tabList.length);
  }

  void onCurrPageChanged(int index) {
    if (isPageCanChanged) {//由于pageview切换是会回调这个方法,又会触发切换tabbar的操作,所以定义一个flag,控制pageview的回调
      onPageChange(index);
    }
  }
            
}