/**
 * Holder for cookie-related methods. For now only a single method
 */
const cookieWorker = {
  /**
   * @param name cookie name
   * @returns cookie value
   */
  extractCookie(name: string): string {
    const extraValue =
      document.cookie.match(
        new RegExp("(?:^| )(?:" + name + ")=([^;]+)")
      )?.[1] ?? null
    return extraValue
  },
}
export default cookieWorker
