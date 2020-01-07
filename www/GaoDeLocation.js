var exec = require('cordova/exec');

var GaoDe = {
    getCurrentPosition: (successFn, errorFn, option) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'getCurrentPosition', [option]);
    },
    startSerialLocation: (successFn, errorFn, option) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'startSerialLocation', [option]);
    },
    stopSerialLocation: (successFn, errorFn) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'stopSerialLocation', []);
    }
};

module.exports = GaoDe;
