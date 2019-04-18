#import <Cordova/CDV.h>

@interface ImageView : CDVPlugin 
    @property NSString *callbackId;

    - (void)showImage:(CDVInvokedUrlCommand*)command;
@end