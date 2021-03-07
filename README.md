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
