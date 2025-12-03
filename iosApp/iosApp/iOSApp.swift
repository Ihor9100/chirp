import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        DiRootKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
