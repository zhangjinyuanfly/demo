import 'package:flutter/material.dart';



class AnimDemo extends StatefulWidget {
  final Widget child;

  AnimDemo({Key key, this.child}) : super(key: key);

  _AnimDemoState createState() => _AnimDemoState();
}

class _AnimDemoState extends State<AnimDemo> with SingleTickerProviderStateMixin {

  Animation<double> scaleanimation;
  Animation<double> animation;
  AnimationController animationController;

  @override
  void initState() {
    super.initState();

    animationController = AnimationController(vsync:this, duration: Duration(milliseconds: 2000));
    animation =Tween(begin: 200.0, end: 250.0).animate(animationController)
    ..addListener((){
      setState(() {
        
      });
    })
    ..addStatusListener((state){
      if(state ==AnimationStatus.completed) {
        animationController.reverse();
      } else if(state ==AnimationStatus.dismissed) {
        animationController.forward();
      }
    });

scaleanimation =Tween(begin: 1.0, end: 1.25).animate(animationController);

    animationController.forward();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        Container(
          decoration: BoxDecoration(color: Colors.yellow),
          alignment: Alignment.center,
          width: double.infinity,
          height: animation.value,
          child: Container(
            width: double.infinity,
            child: ClipRect(
            
              child: ScaleTransition(
                scale: scaleanimation,
                child: Image.asset('assets/image/aaa.jpg',fit: BoxFit.cover,),
              ),
            )
          ),
        ),
        Text('dfefefef'),


        // Container(
        //   decoration: BoxDecoration(
        //     image: DecorationImage(
        //       image: AssetImage(
        //         'assets/image/aaa.jpg'
        //       ),
        //     ),
        //   ),
        //   width: double.infinity,
        //   height: animation.value,
        //   // child: Image.asset('assets/image/aaa.jpg', fit: BoxFit.cover,),
        //   child: Transform(
        //     transform: Matrix4.identity()..scale(scaleanimation.value),
        //     child: Image.asset('assets/image/aaa.jpg', fit: BoxFit.cover,),
        //   ),
        // ),
      ],
    );
  }
}