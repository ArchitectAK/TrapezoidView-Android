# TrapezoidView-Android
A library that let you design trapezoidal views in your android app.

Just add this to your project's app level `build.gradle` file

        compile 'com.android.graven.trapezoid:trapezoid:1.0.0'
        
And its easy to use in `layout.xml`

        <?xml version="1.0" encoding="utf-8"?>
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">
        
            <com.android.graven.trapezoid.TrapezoidImageView
                android:id="@+id/topImage"
                android:layout_width="match_parent"
                android:layout_height="150dp"
                android:src="@drawable/kotlin"
                android:scaleType="centerCrop"
                app:incline="25dp"
                app:position="top" />
        
            <com.android.graven.trapezoid.TrapezoidImageView
                android:layout_width="match_parent"
                android:layout_height="150dp"
                android:src="@drawable/java"
                android:scaleType="centerCrop"
                app:incline="25dp"
                app:position="middle" />
        
            <com.android.graven.trapezoid.TrapezoidImageView
                android:layout_width="match_parent"
                android:layout_height="150dp"
                android:src="@drawable/swift"
                android:scaleType="centerCrop"
                app:incline="25dp"
                app:position="bottom" />
        
        </LinearLayout>

        
        
and that's how it looks after implementation

<img src="https://github.com/AnkitDroidGit/TrapezoidView-Android/blob/master/screen/3.png"  width="400" height="700">




### Contact - Let's connect to learn together
- [Twitter](https://twitter.com/KumarAnkitRKE)
- [Github](https://github.com/AnkitDroidGit)
- [LinkedIn](https://www.linkedin.com/in/kumarankitkumar/)
- [Facebook](https://www.facebook.com/freeankit)
- [Slack](https://ankitdroid.slack.com)
- [Stackoverflow](https://stackoverflow.com/users/3282461/android)
- [Android App](https://play.google.com/store/apps/details?id=com.freeankit.ankitprofile)


### License

    Copyright 2018 Ankit Kumar
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
