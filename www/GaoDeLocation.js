var exec = require('cordova/exec');

var GaoDe = {
    getCurrentPosition: (successFn, errorFn, ages) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'getCurrentPosition', [ages]);
    },
    startSerialLocation: (successFn, errorFn, ages) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'startSerialLocation', [ages]);
    },
    stopSerialLocation: (successFn, errorFn, ages) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'stopSerialLocation', [ages]);
    },
    // startBackgroundLocation: (successFn, errorFn, ages) => {
    //     exec(successFn, errorFn, 'GaoDeLocation', 'startBackgroundLocation', [ages]);
    // },
    // stopBackgroundLocation: (successFn, errorFn, ages) => {
    //     exec(successFn, errorFn, 'GaoDeLocation', 'stopBackgroundLocation', [ages]);
    // }
};

module.exports = GaoDe;
