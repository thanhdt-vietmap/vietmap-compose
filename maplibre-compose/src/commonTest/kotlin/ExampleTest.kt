import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ExampleTest {
  @Test
  fun simpleTestShouldPass() {
    assertEquals(1, 1)
  }

  @Test
  @OptIn(ExperimentalTestApi::class)
  fun composeTestShouldPass() = runComposeUiTest { assertEquals(1, 1) }
}
