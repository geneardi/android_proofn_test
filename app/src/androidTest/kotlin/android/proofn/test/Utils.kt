package android.proofn.test

/**
 * Just waits until the SpashScreen is gone
 */
fun waitForSplashScreen() {
  try {
    Thread.sleep(2000)
  } catch (e: InterruptedException) {
    e.printStackTrace()
  }
}