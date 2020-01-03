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
    }
};

module.exports = GaoDe;
