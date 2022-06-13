## React Native Direct Phone Call

React Native Direct Phone Call module is targeted at making a direct call from your iOS and Android application. This module is shipped with the following features:

- Handle direct call for both single and multiple sim.
- Get sim card information

## Installation

```sh
npm install rn-direct-phone-call --save

Or

yarn add rn-direct-phone-call
```

## Usage
Import the package as below

```js
import { iosCall, andCall, getSimInfo } from "rn-direct-phone-call";

```

- To make a call on iOS device use iosCall method with the number in question as below;
```js
iosCall('123456789');

```

- To make a call on andriod device with a single sim card use andCall method as follows;
```js
andCall('123456789');

```

- To make a call on andriod device with a multiple sim cards use andCall method as follows;
###### Note
You are required to pass the sim index. You can use the getSimInfo to get sim card indexes and use then as per need.
```js
andCall('123456789', getSimInfo().simSlotIndex0);

```

- To Sim card/cards Information on an android device, use the getSimInfo method. The following information are captured - carrierName, simSlotIndex, deviceId and simSerialNumber.

```js
console.log(getSimInfo());
```

```es6
/**
  the object has the following info (change 0 to access more than 1 SIM):

  carrierName0: string;
  simSlotIndex0: string;
  displayName0: string;
*/

// Access the getSimInfo() variables as follows

getSimInfo().carrierName0
```

### Caveats

Might crash if tries to use in a phone without any SIM cards.

### Permissions

**WARNING: Minimum API Level is 23**

This plugin uses two different Android APIs to receive SIM data:
- `TelephonyManager` (since API level 1)
- `SubscriptionManager` (since API level 22)

Since Android 6 (API level 23) a few methods of `TelephonyManager` require permission `READ_PHONE_STATE`.

All methods of `SubscriptionManager` require permission `READ_PHONE_STATE`.

`SubscriptionManager` is able to access multiple SIM data.

## TODO

- Get sim information on iOS


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
