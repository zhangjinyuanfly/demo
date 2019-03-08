import 'package:flutter/material.dart';
import 'data.dart';

class PuLLZoomPage extends StatefulWidget {
  final Widget child;

  PuLLZoomPage({Key key, this.child}) : super(key: key);

  _PuLLZoomPageState createState() => _PuLLZoomPageState();
}

class _PuLLZoomPageState extends State<PuLLZoomPage> {
  ScrollController _scrollController = new ScrollController();


  double height = 100.0;

  @override
  void initState() {
    super.initState();
    _scrollController.addListener((){
      if(_scrollController.position.pixels == 0) {
        height =height + 5;
      }
      print('px=$_scrollController.position.pixels');
    });
  }
  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      slivers: <Widget>[
        SliverAppBar(
          // title: Text('title'),
          actions: <Widget>[
            // Text('3333'),
            // Text('444'),
          ],
          expandedHeight: height,
          flexibleSpace: FlexibleSpaceBar(
            title: Text('app bar'),
          ),
          pinned: true,
          
        ),
         SliverFixedExtentList(
            delegate: SliverChildListDelegate(products.map((product) {
              return _buildItem(context, product);
            }).toList()),
            itemExtent: 120.0,
          ),

      ],
      controller: _scrollController,
    );
  }

  Widget _buildItem(BuildContext context, ProductItem product) {
    return Container(
      height: 100.0,
      margin: const EdgeInsets.only(bottom: 5.0),
      padding: const EdgeInsets.only(left: 10.0),
      color: Colors.blueGrey,
      child: Stack(
        alignment: AlignmentDirectional.centerStart,
        children: <Widget>[
          Positioned(
              right: 40.0,
              child: Card(
                child: Center(
                  child: Text(
                    product.name,
                    style: Theme.of(context).textTheme.title,
                    textAlign: TextAlign.center,
                  ),
                ),
              )),
          ClipRRect(
            child: SizedBox(
              width: 70.0,
              height: 70.0,
              child: Image.asset(
                product.asset,
                fit: BoxFit.cover,
              ),
            ),
            borderRadius: BorderRadius.all(Radius.circular(8.0)),
          ),
        ],
      ),
    );
  }

  // @override
  // Widget build(BuildContext context) {
  //   return RefreshIndicator(
  //     child: Center(
  //       child: ListView.builder(
  //         itemCount: 100,
  //         itemBuilder: (context, index){
  //           return GestureDetector(
  //             child: Container(
  //               child: Text('woshi: $index'),
  //               height: 100.0,
  //             ),
  //             onTap: (){
  //               _click(index);
  //             },
  //           );
  //         },
  //         controller: _scrollController,
  //       ),
  //     ),
  //     onRefresh: _handleRefresh,
  //   );
  // }

  Future<Null> _handleRefresh() {
    setState(() {
      return null;
    });
  }
  
  void _click(int index) {

    print('click _ $index');
  }
  
  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    _scrollController.dispose();
  }

}

