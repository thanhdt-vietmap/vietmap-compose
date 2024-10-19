import UIKit
import SwiftUI
import composeApp

class Platform: IPlatform {
    var name: String {
        return UIDevice.current.systemName + " " + UIDevice.current.systemVersion
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                .ignoresSafeArea(edges: .all)
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
