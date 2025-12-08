import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        DiRootKt.doInitKoin()
        ExternalEncryptionHandler().addEncrypt { data in
            data
        }
        ExternalEncryptionHandler().addDecrypt { data in
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
