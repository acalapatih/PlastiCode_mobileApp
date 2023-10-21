# PlastiCode

## Introduction
PlastiCode is an application that educates about the types of plastic along with their lifespan and level of danger. There are three main features in this application: plastic type detection, recommendations for plastic alternatives, and recommendations for plastic recycling locations. PlastiCode is built with Kotlin, TensorFlow Lite, and implements the Clean Code Architecture. This application was developed as the Capstone Project Bangkit 2023.

## Mock Up

[![Plasti-Code-Mockup-DETECTION.png](https://photos.google.com/photo/AF1QipOSdGNC8lobcKebVpREB4sw1_svVg1l-izE5PS8))

[![Plasti-Code-Mockup-LOCATION-AND-SETTING.png](https://i.postimg.cc/cCTCtzd7/Plasti-Code-Mockup-LOCATION-AND-SETTING.png)](https://postimg.cc/4H7srB7n)

## Table Of Content

- Introduction
- Features
- Libraries
- Project Structure

## Features

- Splash Screen
- Login & Register
- Dashboard
- Plastic Type Detection
- Plastic Type Detection Result
- Detection Result Details
- Plastic Recycling Recommendations
- Menu
- Detection History
- Background Mode Setting

## Libraries

- [Lifecycle & Livedata](https://developer.android.com/guide/components/activities/activity-lifecycle?hl=id)
- [Retrofit](https://square.github.io/retrofit/)
- [Navigation Component](https://developer.android.com/guide/navigation/get-started)
- [Kotlin Coroutines](https://www.googleadservices.com/pagead/aclk?sa=L&ai=DChcSEwiU2bPz88f_AhXVk2YCHXdgDDMYABAAGgJzbQ&ohost=www.google.com&cid=CAESbOD2gWGIEaIzh7xPUOGICyK2tbXIr0QUhhlGSrurjKcD6swxwpKj-7IrQ9_iwmDhml1_P_z6seVQZZNvkJ-fiMxTpf1xONyVn40ucS143xA8HR8Y35CCv_06CgyhYufQQc6JFf2g1WPjknZFow&sig=AOD64_1YR8UhDwd6LH3WrvCacezcHvoFUw&q&adurl&ved=2ahUKEwiUqKzz88f_AhUT7TgGHSukAJEQ0Qx6BAgIEAE)
- [Ok Http 3](https://square.github.io/okhttp/)
- [GSON](https://github.com/google/gson)
- [Glide](https://github.com/bumptech/glide)
- [Google Maps Services, Places API](https://developers.google.com/maps/documentation/places/android-sdk/overview)
- [Fragment](https://developer.android.com/guide/fragments?hl=id)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=id)
- [TensorFlow Lite](https://www.tensorflow.org/lite/android)
- [RxBinding](https://github.com/JakeWharton/RxBinding)

## Project Structure

- data
- factory
- network
	- ApiService
	- GoogleMapsApiService
	- ApiConfig
	- GoogleMapsApiConfig
- preference
- response
- ui
	- dashboard
	- detection
	- result
	- login
	- location
	- main
	- menu
	- setting
	- register
	- history
- utils
	- dataStore
- ml
	- model.tflite
