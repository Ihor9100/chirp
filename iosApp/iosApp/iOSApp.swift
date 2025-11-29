import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        ComposeAppDiKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
