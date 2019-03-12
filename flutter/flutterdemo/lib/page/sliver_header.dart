import 'dart:math' as math;

import 'package:flutter/material.dart';

class SliverHeader extends SliverPersistentHeaderDelegate {

  final double minHeight;
  final double maxHeight;
  final Widget child;

  final double alphaHeight = 60;

  SliverHeader({
    @required this.minHeight,
    @required this.maxHeight,
    @required this.child,
  });
  double op = 0;
  @override
  Widget build(BuildContext context, double shrinkOffset,
      bool overlapsContent) {
    if (shrinkOffset > alphaHeight && shrinkOffset < 180) {
      op = (shrinkOffset-alphaHeight) / (180-alphaHeight);
    } else if (shrinkOffset >= 180) {
      op = 1.0;

    } else if(shrinkOffset <= alphaHeight) {
      op = 0;
    }

    bool show = shrinkOffset > (maxHeight - minExtent);
    print('show=$show === $op === $shrinkOffset');
    return Stack(
      children: <Widget>[
        SizedBox.expand(child: child),
        Opacity(
          opacity: op,
          child: Container(
            decoration: BoxDecoration(color: Colors.white54),
            alignment: Alignment.center,
            width: double.infinity,
            height: 70,
            child: Text('来了老弟',style: TextStyle(fontSize: 10),),
          ),
        ),
        // Align(
        //   alignment: Alignment.bottomCenter,
        //   child: TabBar(
        //     tabs: <Widget>[
        //       RaisedButton(
        //         child: Text('111'),
        //       ),
        //       RaisedButton(
        //         child: Text('222'),
        //       ),
        //     ],
          
        // ),
        // ),
      ],
    );

//    return new SizedBox.expand(child: child);
  }

  @override
  // TODO: implement maxExtent
  double get maxExtent => math.max(maxHeight, minHeight);

  @override
  // TODO: implement minExtent
  double get minExtent => minHeight;

  @override
  bool shouldRebuild(SliverHeader oldDelegate) {
    return maxHeight != oldDelegate.maxHeight ||
        minHeight != oldDelegate.minHeight ||
        child != oldDelegate.child;
  }

}