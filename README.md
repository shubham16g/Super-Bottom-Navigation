Skip to content
Search or jump to…

Pull requests
Issues
Marketplace
Explore

@shubham-gupta-16
Learn Git and GitHub without any code!
Using the Hello World guide, you’ll start a branch, write comments, and open a pull request.


shubham-gupta-16
/
Super-Bottom-Navigation
1
00
Code
Issues
Pull requests
Actions
Projects
Wiki
Security
Insights
Settings
Merge remote-tracking branch 'origin/master'

 master
Shubham Gupta committed 30 seconds ago
2 parents 7a8e319 + b4b870b commit 075501e62fcfe2f1fb748b20bce9410b44c4c8dc
Showing  with 55 additions and 0 deletions.
 55  README.md
@@ -0,0 +1,55 @@
# Super-Bottom-Navigation

[![](https://jitpack.io/v/shubham-gupta-16/Super-Bottom-Navigation.svg)](https://jitpack.io/#shubham-gupta-16/Super-Bottom-Navigation)

This is the best Bottom Navigation for Android.

## Gradle

### Add jitpack to the build.gradle (project):
  ```
 allprojects {
   repositories {
     ...
     maven { url 'https://jitpack.io' }
   }
 }
  ```

### Add the library to the dependencies:
```
 implementation 'com.github.shubham-gupta-16:Super-Bottom-Navigation:1.0.0'
  ```
## Docs

### In XML layout file:

```xml
    <com.shubhamgupta16.superbottomnav.SuperBottomNavigation
        android:id="@+id/superBottomNav"
        android:layout_alignParentBottom="true"
        android:background="#fff"
        android:layout_width="match_parent"
        app:menu_res="@menu/bottom_menu"
        app:icon_size="24dp"
        app:anim_duration="400"
        android:elevation="6dp"
        app:icon_color="@color/colorPrimary"
        app:text_color="#111"
        android:layout_height="wrap_content"/>
```
### In Java Activity file:
```java
    superBottomNav = findViewById(R.id.superBottomNav);
    superBottomNav.setOnItemSelectChangeListener(new SuperBottomNavigation.OnItemSelectChangeListener() {
        @Override
        public void onChange(int id, int position) {
            Log.d(TAG, "id: " + id + ", position: " + position);
        }
    });
```
### Also add Badges by using:
```java
    superBottomNav.setBadge(R.id.action_cart, cartCount);
```
0 comments on commit 075501e
@shubham-gupta-16


Leave a comment
No file chosen
Attach files by dragging & dropping, selecting or pasting them.
 You’re receiving notifications because you’re watching this repository.
© 2021 GitHub, Inc.
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
