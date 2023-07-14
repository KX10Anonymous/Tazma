import { StatusBar } from 'expo-status-bar';
import { StyleSheet, View } from 'react-native';
import RegisterScreen from './auth/Registration';

export default function App() {
  return (
    <View style={styles.container}>
      <RegisterScreen/>
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'black',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
