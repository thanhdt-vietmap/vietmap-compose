import SwiftUI
import ComposeApp

class Platform: IPlatform {
    var name: String {
        return UIDevice.current.systemName + " " + UIDevice.current.systemVersion
    }
}

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoin(platform:Platform.init)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
