var exec = require('cordova/exec');

var GaoDe = {
    getCurrentPosition:function (successFn,errorFn) {
        exec(successFn,errorFn,'GaoDeLocation','getCurrentPosition',[]);
    }
};

module.exports = GaoDe;
