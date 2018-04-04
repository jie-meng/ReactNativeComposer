import React from 'react';
import { NativeModules } from 'react-native'
import {
  AppRegistry,
} from 'react-native';
import { StyleSheet, Text, View } from 'react-native';

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = { username: NativeModules.RNManager.getUsername() };
    }

  render() {
    return (
      <View style={styles.container}>
        <Text>Open up App.js to start working on your app!</Text>
        <Text>Changes you make will automatically reload.</Text>
        <Text>{this.state.username}</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

AppRegistry.registerComponent('AwesomeRN', () => App);
