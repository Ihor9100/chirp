import Foundation
import ComposeApp
import UIKit
import UserNotifications
import FirebaseCore
import FirebaseMessaging


class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate, MessagingDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
//        I need to buy a payed Developer account to test Notifications,
//        only than I can use the API. Additionally google.services from
//        firebase must be added in the project

        FirebaseApp.configure()
        
        UNUserNotificationCenter.current().delegate = self
        Messaging.messaging().delegate = self
        
        return true
    }
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
        
        refreshToken()
    }
    
    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print("iOS: Failed to register for push notifications: \(error.localizedDescription)")
    }
    
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        guard let token = fcmToken, !token.isEmpty else {
            refreshToken()
            return
        }
        
        UserDefaults.standard.set(token, forKey: "FCM_TOKEN")
        FirebaseTokenBridge.shared.onNewToken(token: token)
    }
    
    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable : Any], fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        Messaging.messaging().appDidReceiveMessage(userInfo)
        completionHandler(.newData)
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        completionHandler([.banner])
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
        let userInfo = response.notification.request.content.userInfo
        
        if let chatId = userInfo["chatId"] as? String {
            let deepLinkUrl = "chirp://chat-details/\(chatId)"
            ExternalUriHandler.shared.onNewUri(uri: deepLinkUrl)
        }
        
        completionHandler()
    }
    
    func refreshToken() {
        Task {
            do {
                let fcmToken = try await Messaging.messaging().token()
                
                UserDefaults.standard.set(fcmToken, forKey: "FCM_TOKEN")
                FirebaseTokenBridge.shared.onNewToken(token: fcmToken)
            } catch {
                print("iOS: Error getting FCM token: \(error.localizedDescription)")
            }
        }
    }
}
