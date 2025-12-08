import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        DiRootKt.doInitKoin()

EncryptionHandler().encrypt { data in
    // iOS AES encryption logic
    return data.encryptString
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
