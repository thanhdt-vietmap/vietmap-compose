import SwiftUI
import UIKit
import composeApp

struct MainViewController: UIViewControllerRepresentable {
  func makeUIViewController(context: Context) -> UIViewController {
    MainViewControllerKt.MainViewController()
  }

  func updateUIViewController(
    _ uiViewController: UIViewController, context: Context
  ) {}
}

struct MainView: View {
  var body: some View {
    MainViewController()
      .ignoresSafeArea(.keyboard)  // Compose has own keyboard handler
      .ignoresSafeArea(edges: .all)
  }
}

@main
struct iOSApp: App {
  init() {
    Module_iosKt.doInitKoin(
      initUnzipper: UnzipperImpl.init
    )
  }

  var body: some Scene {
    WindowGroup {
      MainView()
    }
  }
}
