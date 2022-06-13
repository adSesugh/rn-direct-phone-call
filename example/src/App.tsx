import * as React from 'react';

import { StyleSheet, View, Button, ScrollView, Text, Dimensions } from 'react-native';
import { andCall, getSimInfo } from 'rn-direct-phone-call';

const {height} = Dimensions.get('window');

export default function App() {

  React.useEffect(() => {
    //console.log(getSimInfo());
  }, [])

  const startCall = () => {
    console.log(getSimInfo());
    return andCall('123', 1);
  }

  return (
    <View style={styles.container}>
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
       <Button title='Call Me' onPress={startCall} />
      </View>
      <ScrollView style={{ height: height/2 }}>
        <Text>{JSON.stringify(getSimInfo())}</Text>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 120,
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
