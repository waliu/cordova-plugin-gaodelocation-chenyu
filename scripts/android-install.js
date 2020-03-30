var fs = require("fs");
var path = require('path');
module.exports = function (context) {
    var tempPackName = "com.hosp123.testapp.location";
    var ConfigParser = null;
    try {
        ConfigParser = context.requireCordovaModule('cordova-common').ConfigParser;
    } catch (e) {
        // fallback
        ConfigParser = context.requireCordovaModule('cordova-lib/src/configparser/ConfigParser');
    }

    var config = new ConfigParser(path.join(context.opts.projectRoot, "config.xml")),
        packageName = config.android_packageName() || config.packageName();

    var fullfilename = path.join(context.opts.plugin.dir, 'src/android/SerialLocation.java');

    var targetDir = path.join(context.opts.projectRoot, "platforms", "android", "app", "src", "main", "java", "com", "chenyu", "GaoDeLocation");

    var data = fs.readFileSync(fullfilename, 'utf-8');

    str = data.replace(tempPackName, packageName);

    fs.writeFile(targetDir + "/SerialLocation.java", str, function (err) {
        if (err) return err;
    });

    // console.log("packageName："+packageName);

    // console.log(targetDir);

};
