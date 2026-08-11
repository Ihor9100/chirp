import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    // Only with paid Apple Developer Account
    // @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    init() {
        DiRootKt.doInitKoin()
        EncryptionBridge.shared.addEncrypt { data in
            data
        }
        EncryptionBridge.shared.addDecrypt { data in
            data
        }
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL { url in
                    ExternalUriHandler.shared.onNewUri(uri: url.absoluteString)
                }
        }
    }
}
