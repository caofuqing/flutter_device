import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_device/flutter_device.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      // var a = await FlutterDevice.platformVersion ?? "";
      var b = await FlutterDevice.platformSystemMark;
      // var c = await FlutterDevice.platformUid;
      // print("a" + a.toString());
      print("b-------------------------->>>>>>>>" + b.toString());
      var cc = await FlutterDevice.platformDeviceModle;
      print("cc-------------------------->>>>>>>>" + cc.toString());

      // print("c" + c.toString());
      platformVersion = '1111.';
      // platformVersion =
      //     await  ?? 'Unknown platform version' + await FlutterDevice.platformSystemMark + await FlutterDevice.platformUid;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion + "1211";
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
