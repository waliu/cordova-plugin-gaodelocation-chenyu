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
    },
    pathOptimize: (successFn, errorFn, option) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'pathOptimize', [option]);
    },
    kalmanFilterPath: (successFn, errorFn, option) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'kalmanFilterPath', [option]);
    },
    removeNoisePoint: (successFn, errorFn, option) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'removeNoisePoint', [option]);
    },
    kalmanFilterPoint: (successFn, errorFn, option) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'kalmanFilterPoint', [option]);
    },
    reducerVerticalThreshold: (successFn, errorFn, option) => {
        exec(successFn, errorFn, 'GaoDeLocation', 'reducerVerticalThreshold', [option]);
    }
};

module.exports = GaoDe;
