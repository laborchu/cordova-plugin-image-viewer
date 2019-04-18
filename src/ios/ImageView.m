#import "ImageView.h"
#import "YBImageBrowser/YBImageBrowser.h"

@implementation ImageView

@synthesize callbackId;

-(void)pluginInitialize
{
}

- (void)showImage:(CDVInvokedUrlCommand*)command
{
    self.callbackId = command.callbackId;
    NSString* imageUrl = [command.arguments objectAtIndex:0];
    NSString* index = [command.arguments objectAtIndex:1];
    if(imageUrl){
        NSData *jsonData = [imageUrl dataUsingEncoding:NSUTF8StringEncoding];
        NSMutableArray* imageArray = [[NSMutableArray alloc] init];
        imageArray = [NSJSONSerialization JSONObjectWithData: jsonData options:NSJSONReadingMutableContainers error:nil];
        
        NSMutableArray *browserDataArr = [NSMutableArray array];
        for (NSDictionary* imageDic in imageArray) {
            YBImageBrowseCellData *data = [YBImageBrowseCellData new];
            data.url = [NSURL URLWithString:imageDic[@"url"]];
            [browserDataArr addObject:data];
        }
        
        YBImageBrowser *browser = [YBImageBrowser new];
        browser.dataSourceArray = browserDataArr;
        browser.currentIndex = [index integerValue];
        [browser show];
    }
}

@end
