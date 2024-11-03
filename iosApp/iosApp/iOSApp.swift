import SwiftUI
import UIKit
import composeApp

struct MainView: UIViewControllerRepresentable {
  func makeUIViewController(context: Context) -> UIViewController {
    AppKt.MainViewController()
  }

  func updateUIViewController(
    _ uiViewController: UIViewController, context: Context
  ) {}
}

@main
struct iOSApp: App {
  init() {
    AppKt.doInitKoin()
  }

  var body: some Scene {
    WindowGroup {
      MainView()
        .ignoresSafeArea(.keyboard)  // Compose has own keyboard handler
        .ignoresSafeArea(edges: .all)
    }
  }
}
