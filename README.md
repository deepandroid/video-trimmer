# Screenshots:
**Screenshot 1 :**

<img src="https://github.com/deepandroid/video-trimmer/blob/master/images/device-2018-06-06-170717.png" alt="Video Trimmer Screenshot 1" width="360" height="640" />


**Screenshot 2 :**

<img src="https://github.com/deepandroid/video-trimmer/blob/master/images/device-2018-06-06-170642.png" alt="Video Trimmer Screenshot 2" width="360" height="640" />


**Screenshot 3 :**

<img src="https://github.com/deepandroid/video-trimmer/blob/master/images/device-2018-06-06-170736.png" alt="Video Trimmer Screenshot 3" width="360" height="640" />


# Video Trimmer
Whatsapp like video trimmer to trim videos within a defined file size.

# Add in your project

**Gradle :**

maven {
       url 'https://dl.bintray.com/deeppatel13/maven/'
   }
   
implementation 'com.deep.videotrimmer:videotrimmer:1.0'

>**Note:** If you have jCenter() added, then no need to write maven dependancy. only using implementation line it will be integrated.

**XML :**


     <com.deep.videotrimmer.DeepVideoTrimmer
          android:layout_width="match_parent"
          android:layout_height="match_parent" />

# **Customization Settings :**

Mention your own path to save trimmed videos:
**setDestinationPath(StringPath);**

Mention your desired max duration for trimmed videos:
**setMaxDuration(int seconds);**  //Defaults to 100Seconds

Mention your desired max file size for trimmed videos:
**setMaxFileSize(int mb);**   //Defaults to 25Mb

Mention your desired video URI to get trimmed video:
**setVideoURI(Uri for video to trim);**
