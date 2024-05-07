const cookieWorker = {
  extractCookie(name: string): string {
    const extraValue =
      document.cookie.match(
        new RegExp("(?:^| )(?:" + name + ")=([^;]+)")
      )?.[1] ?? null;
    return extraValue;
  },
};
export default cookieWorker;
