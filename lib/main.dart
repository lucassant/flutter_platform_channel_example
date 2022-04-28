import 'package:background_service/event_channel_api.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  bool airplaneModeEnabled = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.max,
          children: [
            Text(
              'Data from MethodChannel',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            Text(
              '(Does not update automatically)',
              style: Theme.of(context).textTheme.subtitle2,
            ),
            const SizedBox(
              height: 16,
            ),
            Text('AirplaneMode enabled: $airplaneModeEnabled'),
            ElevatedButton(
                onPressed: () async {
                  airplaneModeEnabled = await EventChannelApi.getAirplaneMode;
                  setState(() {});
                },
                child: const Text('Get AirplaneMode')),
            const Divider(),
            Text(
              'Data from EventChannel',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            Text(
              '(Update automatically)',
              style: Theme.of(context).textTheme.subtitle2,
            ),
            const SizedBox(
              height: 16,
            ),
            StreamBuilder<bool>(
                stream: EventChannelApi.getAirplaneModeStream,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return const Text('No data');
                  }
                  return Text('AirplaneMode enabled: ${snapshot.data}');
                }),
          ],
        ),
      ),
    );
  }
}
