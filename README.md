# In The Game Android Brightcove SDK

This SDK allows you to easily integrate the In The Game engagement platform in your Android app using the Brightcove video player.
The repository includes an example app that shows how to use the framework.


## Installation

In your Android project, choose **File > New > New module > Import from .aar file**. 

Select the **itgbcframework.aar** file (included in this repo).

Add the following imports (if missing) to your app's build.gradle:

```
implementation project(":itgbcframework")
implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0'
implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.61"
implementation "com.brightcove.player:android-sdk:6.10.1"
implementation "com.brightcove.player:exoplayer2:6.10.1"
implementation "androidx.media:media:1.1.0"
```

Inside the `android` tag in that same file, add:

```
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```

After the `android` tag, add:

```
repositories {
    maven {
        url 'https://repo.brightcove.com/releases'
    }
}
```

## Usage

To quickly show a full-screen activity with your interactive video channel (examples are in Kotlin):

```
val intent = Intent(this, ITGBCPlayerActivity::class.java)
var bundle = Bundle()
bundle.putString(ITGBCPlayerActivity.VIDEO_PARAM, "<brightcode_video_id>")
bundle.putString(ITGBCPlayerActivity.ACCOUNT_PARAM, "<brightcode_account_id>")
bundle.putString(ITGBCPlayerActivity.POLICY_KEY_PARAM, "<brightcode_policy_key>")
bundle.putString(ITGBCPlayerActivity.BROADCASTER_PARAM, "<your broadcaster name>")
intent.putExtras(bundle)
startActivity(intent)
```

To load the video channel in a view in your custom layout file: 
(add constraints or layout parameters as needed)

```
<com.tiagolira.itgbcframework.ITGBCPlayerView
  android:id="@+id/playerView"
  android:layout_width="match_parent"
  android:layout_height="100dp"
  android:background="#CCCCCC"/>
```

And load it in your activity/fragment (we recommend doing it in `onResume`):

```
playerView.load(
            "<brightcode_video_id>",
            "<brightcode_account_id>",
            "<brightcode_policy_key>",
            "<your broadcaster name>")
```

According to Brightcove guidelines, your activity should subclass `BrightcovePlayer`.
And you should setup the videoView before calling `onCreate` on the superclass as such:
```
override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_example)
        brightcoveVideoView = playerView.bcVideoView
        super.onCreate(savedInstanceState)
```

There are two additional parameters for further configuration: `language` and `allowsFullScreen`:

```
playerView.load(
            "<brightcode_video_id>",
            "<brightcode_account_id>",
            "<brightcode_policy_key>",
            "<your broadcaster name>", 
            "en", 
            false)
```

## Manual mode

If you need a custom configuration when loading the Brightcove player, we included a manual mode on the `ITGBCPlayerView`. In this mode the video will not load automatically and you can run your own video loading code.

Load the player view as:
```
playerView.loadManualConfig("<brightcode_video_id>", "<your_itg_broadcaster_name>")
```
And after configuring the player view, add your video code:
```
val emitter = playerView.bcVideoView.eventEmitter
val catalog = Catalog(emitter, "<brightcode_account_id>", "<brightcode_policy_key>")

catalog.findVideoByID("<brightcode_video_id>", (object : VideoListener() {
    override fun onVideo(video: Video?) {
        if (video != null) {
           playerView.bcVideoView.add(video)
           playerView.bcVideoView.start()
        }
    }
    override fun onError(error: String?) {
    }
}))
```

## Notes

Here is the activity loading code for Java:

```
Intent intent = new Intent(MainActivity.this, ITGBCPlayerActivity.class);
Bundle bundle = new Bundle();
bundle.putString(ITGBCPlayerActivity.getVIDEO_PARAM(), "<brightcode_video_id>");
bundle.putString(ITGBCPlayerActivity.getACCOUNT_PARAM(), "<brightcode_account_id>");
bundle.putString(ITGBCPlayerActivity.getPOLICY_KEY_PARAM(), "<brightcode_policy_key>");
bundle.putString(ITGBCPlayerActivity.getBROADCASTER_PARAM(), "<your broadcaster name>");
intent.putExtras(bundle);
startActivity(intent);
```

If you run into an Android AppCompat bug that causes webviews to crash, go to the app's build.gradle file and replace this line:
```
implementation 'androidx.appcompat:appcompat:1.1.0'
```

With:
```
implementation 'androidx.appcompat:appcompat:1.1.0-rc01'
```
