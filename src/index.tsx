import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'rn-direct-phone-call' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const RnDirectPhoneCall = NativeModules.RnDirectPhoneCall
  ? NativeModules.RnDirectPhoneCall
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function andCall(sCode: string, simIndex: number) {
  return RnDirectPhoneCall.andCall(sCode, simIndex);
}

export function iosCall(sCode: string) {
  return RnDirectPhoneCall.iosCall(sCode);
}

export function getSimInfo() {
  return RnDirectPhoneCall.getConstants();
}
