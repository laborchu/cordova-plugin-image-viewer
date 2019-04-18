var exec = require('cordova/exec');

var PLUGIN_NAME = 'ImageView';

var ImageView = {
  showImage: function(imageArray,index, success, error) {
    exec(success, error, PLUGIN_NAME, 'showImage', [imageArray,index]);
  }
};

module.exports = ImageView;
