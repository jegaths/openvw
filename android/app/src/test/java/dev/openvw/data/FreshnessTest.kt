package dev.openvw.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FreshnessTest {
  private val now = 1_794_471_436L

  @Test fun unknownTimestampIsNeitherStaleNorLabelled() {
    assertFalse(Freshness.isStale(0, now))
    assertEquals("", Freshness.ago(0, now))
  }

  @Test fun labels() {
    assertEquals("just now", Freshness.ago(now - 30, now))
    assertEquals("12 min ago", Freshness.ago(now - 12 * 60, now))
    assertEquals("5 h ago", Freshness.ago(now - 5 * 3600, now))
    // the real case: last report 18 Sep 2026 23:10 IST, checked 12 Nov 2026
    assertEquals("54 days ago", Freshness.ago(1_789_753_202L, now))
  }

  @Test fun staleAfterSixHours() {
    assertFalse(Freshness.isStale(now - 5 * 3600, now))
    assertTrue(Freshness.isStale(now - 7 * 3600, now))
    assertTrue(Freshness.isStale(1_789_753_202L, now))
  }
}
